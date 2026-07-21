package com.example.progettoalbergo.Services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.progettoalbergo.Model.Ospite;
import com.example.progettoalbergo.Model.Pensione;
import com.example.progettoalbergo.Model.Prenotazione;
import com.example.progettoalbergo.Model.PrenotazioneServizi;
import com.example.progettoalbergo.Model.PrenotazioneStanza;
import com.example.progettoalbergo.Model.Servizio;
import com.example.progettoalbergo.Model.Stanza;
import com.example.progettoalbergo.Model.Utente;
import com.example.progettoalbergo.Repository.OspiteRepository;
import com.example.progettoalbergo.Repository.PensioneRepository;
import com.example.progettoalbergo.Repository.PrenotazioneRepository;
import com.example.progettoalbergo.Repository.PrenotazioneServizioRepository;
import com.example.progettoalbergo.Repository.PrenotazioneStanzaRepository;
import com.example.progettoalbergo.Repository.ServizioRepository;
import com.example.progettoalbergo.Repository.StanzaRepository;
import com.example.progettoalbergo.Repository.UtenteRepository;

import jakarta.transaction.Transactional;

@Service
public class PrenotazioneHib {

    @Autowired private StanzaRepository stanzaRepository;
    @Autowired private PrenotazioneRepository prenotazioneRepository;
    @Autowired private PrenotazioneStanzaRepository prenotazioneStanzaRepository;
    @Autowired private OspiteRepository ospiteRepository;
    @Autowired private PrenotazioneServizioRepository prenotazioneServizioRepository;
    @Autowired private PensioneRepository pensioneRepository;
    @Autowired private ServizioRepository servizioRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    
    
    public List<Prenotazione> getAllPrenotazione() {
        return prenotazioneRepository.findAll();
    }

    // 🟢 Ritorna la lista dei servizi per popolare il Dropdown in Angular
    public List<Servizio> getAllServizi() {
        return servizioRepository.findAll();
    }

    public List<Stanza> getStanzeDisponibili(LocalDate checkIn, LocalDate checkOut) {
        return stanzaRepository.findStanzeDisponibili(checkIn, checkOut);
    }

    @Transactional
    public Prenotazione creaPrenotazione(Long idStanza,
                                         LocalDate checkIn,
                                         LocalDate checkOut,
                                         Long idPensione, 
                                         String tipoPrenotazione,
                                         String dovePrenotazione,
                                         String tipoPagamento,
                                         List<Ospite> ospiti,
                                         List<Long> idServiziAggiuntivi) { 

        String codicePrenotazione = UUID.randomUUID().toString();
        
        System.out.println("Checkin ricevuto in Java: " + checkIn);
        System.out.println("Checkout ricevuto in Java: " + checkOut);

        boolean isOnline = "online".equalsIgnoreCase(dovePrenotazione) || "web".equalsIgnoreCase(dovePrenotazione);
        boolean isSede = "sede".equalsIgnoreCase(dovePrenotazione);

        if (isOnline) {
            if (!"carta".equalsIgnoreCase(tipoPagamento) && !"bonifico".equalsIgnoreCase(tipoPagamento)) {
                throw new IllegalArgumentException("Online consentiti solo carta o bonifico");
            }
        } else if (isSede) {
            if (!"carta".equalsIgnoreCase(tipoPagamento)) {
                throw new IllegalArgumentException("In sede è consentito solo pagamento carta");
            }
        }

        if (ospiti == null || ospiti.isEmpty()) {
            throw new IllegalArgumentException("È necessario inserire almeno un ospite.");
        }

        double calcoloCostoTotale = 0.0;

        // 🟢 SOLTANTO DUE OPZIONI: ALBERGO oppure SPA
        boolean isAlbergo = "albergo".equalsIgnoreCase(tipoPrenotazione) || "stanza".equalsIgnoreCase(tipoPrenotazione);
        boolean isSpa = "spa".equalsIgnoreCase(tipoPrenotazione);

        Stanza stanza = null;
        Pensione pensione = null;

        // 1. Se è prenotazione SPA, costo base d'ingresso
        if (isSpa) {
            calcoloCostoTotale += 200.0; // Prezzo base ingresso SPA
        }

        // 2. Se è prenotazione ALBERGO, calcolo stanza + notti + pensione
        if (isAlbergo) {
            if (idStanza == null || checkIn == null || checkOut == null) {
                throw new IllegalArgumentException("Dati stanza o date mancanti per la prenotazione Albergo.");
            }
            if (!checkOut.isAfter(checkIn)) {
                throw new IllegalArgumentException("La data di checkout deve essere successiva al check-in.");
            }

            stanza = stanzaRepository.findById(idStanza)
                    .orElseThrow(() -> new IllegalArgumentException("Stanza non trovata con ID: " + idStanza));

            boolean disponibile = stanzaRepository.findStanzeDisponibili(checkIn, checkOut)
                    .stream()
                    .anyMatch(s -> s.getId().equals(idStanza));

            if (!disponibile) {
                throw new IllegalArgumentException("La stanza non è disponibile nel periodo selezionato.");
            }

            if (ospiti.size() > stanza.getTipologia().getCapienza()) {
                throw new IllegalArgumentException("Numero ospiti superiore alla capienza della stanza.");
            }

            long numeroNotti = ChronoUnit.DAYS.between(checkIn, checkOut);
            calcoloCostoTotale += stanza.getTipologia().getPrezzo().doubleValue() * numeroNotti;

            if (idPensione != null) {
                pensione = pensioneRepository.findById(idPensione).orElse(null);
                if (pensione != null && pensione.getPrezzo() != null) {
                    calcoloCostoTotale += pensione.getPrezzo().doubleValue() * numeroNotti;
                }
            }
        }

        // 3. CALCOLO SERVIZI AGGIUNTIVI DAL DROPDOWN
        if (idServiziAggiuntivi != null && !idServiziAggiuntivi.isEmpty()) {
            for (Long idServizio : idServiziAggiuntivi) {
                Servizio servizio = servizioRepository.findById(idServizio)
                        .orElseThrow(() -> new IllegalArgumentException("Servizio non trovato con ID: " + idServizio));

                if (servizio.getPrezzi() != null) {
                    calcoloCostoTotale += servizio.getPrezzi().doubleValue();
                }
            }
        }

        // 🟢 Salva l'entità principale Prenotazione
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setCodice_prenotazione(codicePrenotazione);
        prenotazione.setTipo_prenotazione(isAlbergo ? "ALBERGO" : "SPA");
        prenotazione.setDove_prenotazione(dovePrenotazione);
        prenotazione.setTipo_pagamento(tipoPagamento);
        prenotazione.setDeposito(isOnline ? calcoloCostoTotale / 10.0 : 0.0);
        prenotazione.setCosto_totale(calcoloCostoTotale);
        prenotazione.setStato("PENDENTE");
        prenotazione.setPagato(false);

        prenotazioneRepository.save(prenotazione);

        // 🟢 Salva Dettaglio Stanza solo se è un'esperienza ALBERGO
        if (isAlbergo && stanza != null) {
            PrenotazioneStanza dettaglio = new PrenotazioneStanza();
            dettaglio.setPrenotazione(prenotazione);
            dettaglio.setStanza(stanza);
            dettaglio.setPensione(pensione); 
            dettaglio.setCheckin(checkIn);
            dettaglio.setCheckout(checkOut);
            prenotazioneStanzaRepository.save(dettaglio);
        }

        // 🟢 Salva i Servizi Aggiuntivi collegati
        if (idServiziAggiuntivi != null && !idServiziAggiuntivi.isEmpty()) {
            for (Long idServizio : idServiziAggiuntivi) {
                Servizio servizio = servizioRepository.findById(idServizio).orElse(null);
                if (servizio != null) {
                    PrenotazioneServizi ps = new PrenotazioneServizi();
                    ps.setCodice_prenotazione(codicePrenotazione);
                    ps.setServizio(servizio); 
                    prenotazioneServizioRepository.save(ps);
                }
            }
        }

        // 🟢 Salva gli Ospiti
        for (Ospite ospite : ospiti) {
            ospite.setcodicePrenotazione(codicePrenotazione);
        }
        ospiteRepository.saveAll(ospiti);

        return prenotazione;
    }

    @Transactional
    public String gestionePrenotazione(String codice, boolean modificaOspiti, String email, String pin, List<Ospite> nuoviOspiti) {
        Prenotazione prenotazione = prenotazioneRepository.findById(codice)
                .orElseThrow(() -> new IllegalArgumentException("Prenotazione non trovata"));

        if (modificaOspiti) {
            if (email == null || email.isBlank() || pin == null || pin.isBlank()) {
                throw new IllegalArgumentException("Email e PIN sono obbligatori per modificare gli ospiti.");
            }

            if (nuoviOspiti == null || nuoviOspiti.isEmpty()) {
                throw new IllegalArgumentException("È necessario inserire almeno un ospite.");
            }

            // 🟢 1. CONTROLLO CAPIENZA STANZA
            boolean isAlbergo = "ALBERGO".equalsIgnoreCase(prenotazione.getTipo_prenotazione());
            if (isAlbergo) {
                PrenotazioneStanza dettaglioStanza = prenotazioneStanzaRepository
                        .findByPrenotazioneCodicePrenotazione(codice)
                        .orElse(null);

                if (dettaglioStanza != null && dettaglioStanza.getStanza() != null) {
                    int capienzaMassima = dettaglioStanza.getStanza().getTipologia().getCapienza();
                    
                    if (nuoviOspiti.size() > capienzaMassima) {
                        throw new IllegalArgumentException(
                            "Numero ospiti inseriti (" + nuoviOspiti.size() + 
                            ") superiore alla capienza massima della stanza (" + capienzaMassima + " posti)."
                        );
                    }
                }
            }

            // 🟢 2. SALVA EMAIL E PIN SULLA PRENOTAZIONE
            prenotazione.setEmail(email);
            prenotazione.setPin(pin);
            prenotazioneRepository.save(prenotazione);

            // 🟢 3. SE HAI UN UTENTEREPOSITORY, SALVA/AGGIORNA L'ACCOUNT UTENTE
            if (utenteRepository != null) {
                Utente utente = utenteRepository.findByEmail(email).orElse(new Utente());
                utente.setEmail(email);
                utente.setPin(pin);
                utenteRepository.save(utente);
            }

            // 🟢 4. ELIMINA I VECCHI OSPITI E FORZA IL FLUSH SUBITO (FONDAMENTALE!)
            List<Ospite> vecchiOspiti = ospiteRepository.findByCodicePrenotazione(codice);
            ospiteRepository.deleteAll(vecchiOspiti);
            ospiteRepository.flush(); // 👈 Imponiamo a Hibernate di eseguire la cancellazione in DB prima dell'insert

            // 🟢 5. ASSOCIA IL CODICE PRENOTAZIONE AI NUOVI OSPITI E SALVA
            for (Ospite ospite : nuoviOspiti) {
                // Nota: usa il setter per il codice prenotazione presente nel tuo modello Ospite
                ospite.setcodicePrenotazione(codice); 
            }
            ospiteRepository.saveAll(nuoviOspiti);

            return "Prenotazione aggiornata con nuovi ospiti.";
        }

        // Logica annullamento...
        List<Ospite> ospiti = ospiteRepository.findByCodicePrenotazione(codice);
        ospiteRepository.deleteAll(ospiti);
        prenotazione.setStato("ANNULLATA");
        prenotazioneRepository.save(prenotazione);

        return "Prenotazione annullata. La caparra rimane all'hotel.";
    }
    @Transactional
    public String checkIn(String codice) {
        Prenotazione p = prenotazioneRepository.findById(codice)
                .orElseThrow(() -> new IllegalArgumentException("Prenotazione non trovata"));

        if (!p.getStato().equals("PENDENTE") && !p.getStato().equals("CONFERMATA")) {
            throw new IllegalArgumentException("Prenotazione non valida");
        }

        p.setStato("CHECK_IN");
        prenotazioneRepository.save(p);
        return "Check-in effettuato";
    }

    @Transactional
    public String checkOut(String codice) {
        Prenotazione p = prenotazioneRepository.findById(codice)
                .orElseThrow(() -> new IllegalArgumentException("Prenotazione non trovata"));

        if (!p.getStato().equals("CHECK_IN")) {
            throw new IllegalArgumentException("Cliente non presente");
        }

        p.setPagato(true);
        p.setStato("COMPLETATA");
        prenotazioneRepository.save(p);

        return "Checkout completato e pagamento registrato";
    }
    
 // 🟢 RECUPERA LA PRENOTAZIONE INSIEME AI SUOI OSPITI
    public Prenotazione getPrenotazioneByCodice(String codice) {
        Prenotazione p = prenotazioneRepository.findById(codice)
                .orElseThrow(() -> new IllegalArgumentException("Prenotazione non trovata per il codice: " + codice));

        // 🟢 Cerca gli ospiti nel database per questo codice e associali
        List<Ospite> ospiti = ospiteRepository.findByCodicePrenotazione(codice);
        p.setOspiti(ospiti);

        return p;
    }

    // 🟢 FAI LA STESSA COSA NEL LOGIN UTENTE
    public Prenotazione loginUtente(String email, String pin) {
        Prenotazione p = prenotazioneRepository.findByEmailAndPin(email, pin)
                .orElseThrow(() -> new IllegalArgumentException("Email o PIN errati."));

        List<Ospite> ospiti = ospiteRepository.findByCodicePrenotazione(p.getCodice_prenotazione());
        p.setOspiti(ospiti);

        return p;
    }
    
    
}