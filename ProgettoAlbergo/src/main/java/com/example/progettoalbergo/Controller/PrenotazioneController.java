package com.example.progettoalbergo.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.progettoalbergo.DTO.GestionePrenotazioneRequest;
import com.example.progettoalbergo.DTO.PrenotazioneRequest;
import com.example.progettoalbergo.Model.Ospite;
import com.example.progettoalbergo.Model.Pensione;
import com.example.progettoalbergo.Model.Prenotazione;
import com.example.progettoalbergo.Model.Servizio; // 🟢 IMPORT AGGIUNTO
import com.example.progettoalbergo.Model.Stanza;
import com.example.progettoalbergo.Services.PensioneHib;
import com.example.progettoalbergo.Services.PrenotazioneHib;
import com.example.progettoalbergo.Services.ServizioHib;

@RestController
@RequestMapping("/api/prenotazione")
public class PrenotazioneController {

    @Autowired 
    private PrenotazioneHib prenotazioneDependancy;
    
    @Autowired
    private PensioneHib pensioneRepository;
    
    
    @Autowired
    private ServizioHib servizioDependancy;
    
    public static class ModificaOspitiRequest {
        private String codicePrenotazione;
        private String emailUtente;
        private String pinUtente;
        private List<Ospite> ospiti;

        public String getCodicePrenotazione() { return codicePrenotazione; }
        public void setCodicePrenotazione(String codicePrenotazione) { this.codicePrenotazione = codicePrenotazione; }

        public String getEmailUtente() { return emailUtente; }
        public void setEmailUtente(String emailUtente) { this.emailUtente = emailUtente; }

        public String getPinUtente() { return pinUtente; }
        public void setPinUtente(String pinUtente) { this.pinUtente = pinUtente; }

        public List<Ospite> getOspiti() { return ospiti; }
        public void setOspiti(List<Ospite> ospiti) { this.ospiti = ospiti; }
    }

    @PutMapping("/modifica-ospiti-account")
    public ResponseEntity<?> modificaOspitiAccount(@RequestBody ModificaOspitiRequest req) {
        try {
            String risultato = prenotazioneDependancy.gestionePrenotazione(
                req.getCodicePrenotazione(),
                true,
                req.getEmailUtente(),
                req.getPinUtente(),
                req.getOspiti()
            );
            return ResponseEntity.ok(Map.of("message", risultato));
        } catch (IllegalArgumentException e) {
            // 🟢 RESTITUISCE UN JSON PULITO: {"error": "Numero ospiti..."}
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Errore interno: " + e.getMessage()));
        }
    }

    
    
    @GetMapping("/codice/{codice}")
    public ResponseEntity<?> getPrenotazioneByCodice(@PathVariable String codice) {
        try {
            Prenotazione p = prenotazioneDependancy.getPrenotazioneByCodice(codice);
            return ResponseEntity.ok(p);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // 🟢 POST: /api/prenotazione/login-utente
    @PostMapping("/login-utente")
    public ResponseEntity<?> loginUtente(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String pin = body.get("pin");
            Prenotazione p = prenotazioneDependancy.loginUtente(email, pin);
            return ResponseEntity.ok(p);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    
    @GetMapping("/pensione")
    public List<Pensione> getPensioni() {
        return pensioneRepository.getAllPensioni();
    }

    // 🟢 NUOVO ENDPOINT: Recupera la lista dei servizi dal DB per il dropdown Angular
    @GetMapping("/servizi")
    public List<Servizio> getServizi() {
        return servizioDependancy.getAllServizi();
    }

    @GetMapping("/spa/prezzo")
    public ResponseEntity<Double> getPrezzoSpa() {
        return ResponseEntity.ok(200.0);
    }

    @GetMapping("/disponibile/{checkin}/{checkout}")
    public List<Stanza> listaStanzaDisponibile(@PathVariable LocalDate checkin, @PathVariable LocalDate checkout) {
        return prenotazioneDependancy.getStanzeDisponibili(checkin, checkout);
    }

    @PostMapping("/prenota")
    public Prenotazione prenota(@RequestBody PrenotazioneRequest request) {
        return prenotazioneDependancy.creaPrenotazione(
                request.getIdStanza(),
                request.getCheckin(),
                request.getCheckout(),
                request.getIdPensione(),            
                request.getTipoPrenotazione(),
                request.getDovePrenotazione(),
                request.getTipoPagamento(),
                request.getOspiti(),
                request.getServiziAggiuntivi() // Passa la List<Long> degli ID dei servizi      
        );
    }

    @PutMapping("/annulla/{codicePrenotazione}/{modificaOspiti}")
    public String annulla(
            @PathVariable String codicePrenotazione,
            @PathVariable boolean modificaOspiti,
            @RequestBody(required = false) GestionePrenotazioneRequest request) {

        String email = null;
        String pin = null;
        List<Ospite> ospiti = null;

        if (request != null) {
            email = request.getEmail();
            pin = request.getPin();
            ospiti = request.getOspiti();
        }

        return prenotazioneDependancy.gestionePrenotazione(
                codicePrenotazione,
                modificaOspiti,
                email,
                pin,
                ospiti
        );
    }

    @PutMapping("/checkin/{codice}")
    public String checkIn(@PathVariable String codice) {
        return prenotazioneDependancy.checkIn(codice);
    }

    @PutMapping("/checkout/{codice}")
    public String checkOut(@PathVariable String codice) {
        return prenotazioneDependancy.checkOut(codice);
    }
}