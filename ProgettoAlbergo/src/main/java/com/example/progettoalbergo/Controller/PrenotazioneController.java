package com.example.progettoalbergo.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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
import com.example.progettoalbergo.Model.Prenotazione;
import com.example.progettoalbergo.Model.Stanza;
import com.example.progettoalbergo.Services.PrenotazioneHib;

@RestController
@RequestMapping("/api/prenotazione")
public class PrenotazioneController {

    @Autowired 
    private PrenotazioneHib prenotazioneDependancy;

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
                request.getServiziAggiuntivi()      
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