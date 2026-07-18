package com.example.progettoalbergo.Services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.progettoalbergo.Model.Ospite;
import com.example.progettoalbergo.Model.Prenotazione;
import com.example.progettoalbergo.Model.PrenotazioneStanza;
import com.example.progettoalbergo.Model.Servizio;
import com.example.progettoalbergo.Model.Stanza;
import com.example.progettoalbergo.Repository.OspiteRepository;
import com.example.progettoalbergo.Repository.PrenotazioneRepository;
import com.example.progettoalbergo.Repository.PrenotazioneStanzaRepository;
import com.example.progettoalbergo.Repository.ServizioRepository;
import com.example.progettoalbergo.Repository.StanzaRepository;

import jakarta.transaction.Transactional;

@Service
public class PrenotazioneHib {
	@Autowired
    private StanzaRepository stanzaRepository;
	
	@Autowired
	private PrenotazioneRepository prenotazioneRepository;
	
	@Autowired
	private PrenotazioneStanzaRepository prenotazioneStanzaRepository;
	
	@Autowired
	private OspiteRepository ospiteRepository;
	
	@Autowired
	private ServizioRepository servizioRepository;


	public List<Prenotazione> getAllPrenotazione() {
		return prenotazioneRepository.findAll();
	}
	
	

	

    public List<Stanza> getStanzeDisponibili(LocalDate checkIn, LocalDate checkOut) {
      
        return stanzaRepository.findStanzeDisponibili(checkIn, checkOut);
    }
    
    @Transactional
    public Prenotazione creaPrenotazione(Long idStanza,
                                         LocalDate checkIn,
                                         LocalDate checkOut,
                                         String pensione,
                                         BigDecimal costoPensione,
                                         String tipoPrenotazione,
                                         String dovePrenotazione,
                                         Double deposito,
                                         String tipoPagamento,
                                         List<Ospite> ospiti,
                                         List<Servizio> serviziAggiuntivi) {

        String codicePrenotazione = UUID.randomUUID().toString();
        double calcoloCostoTotale = 0.0;
        
        if ("stanza+spa".equalsIgnoreCase(tipoPrenotazione)) {
            calcoloCostoTotale += 200.0;
        }

        boolean includeStanza = "stanza".equalsIgnoreCase(tipoPrenotazione) || "stanza+spa".equalsIgnoreCase(tipoPrenotazione);
        Stanza stanza = null;

        if (includeStanza) {
            if (idStanza == null || checkIn == null || checkOut == null) {
                throw new IllegalArgumentException("Dati stanza o date mancanti per una prenotazione di tipo stanza.");
            }
            if (!checkOut.isAfter(checkIn)) {
                throw new IllegalArgumentException("La data di checkout deve essere successiva al check-in.");
            }

            stanza = stanzaRepository.findById(idStanza)
                    .orElseThrow(() -> new IllegalArgumentException("Stanza non trovata con ID: " + idStanza));

            boolean disponibile = stanzaRepository
                    .findStanzeDisponibili(checkIn, checkOut)
                    .stream()
                    .anyMatch(s -> s.getId().equals(idStanza));
            
            

            if (!disponibile) {
                throw new IllegalArgumentException("La stanza non è disponibile nel periodo selezionato.");
                
                
            }
            
            long numeroNotti = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);

            calcoloCostoTotale = stanza.getTipologia().getPrezzo().doubleValue() * numeroNotti;
        }

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setCodice_prenotazione(codicePrenotazione);
        prenotazione.setTipo_prenotazione(tipoPrenotazione);
        prenotazione.setDove_prenotazione(dovePrenotazione);
        prenotazione.setTipo_pagamento(tipoPagamento);

        if ("online".equalsIgnoreCase(dovePrenotazione)) {
            prenotazione.setDeposito(calcoloCostoTotale / 10);
        } else {
            prenotazione.setDeposito(deposito);
        }
        prenotazione.setCosto_totale(calcoloCostoTotale);

        prenotazioneRepository.save(prenotazione);

        if (includeStanza && stanza != null) {
            PrenotazioneStanza dettaglio = new PrenotazioneStanza();
            dettaglio.setPrenotazione(prenotazione);
            dettaglio.setStanza(stanza);
            dettaglio.setCheckin(checkIn);
            dettaglio.setCheckout(checkOut);
            dettaglio.setPensione(pensione);
            dettaglio.setCostoPensione(costoPensione);
            prenotazioneStanzaRepository.save(dettaglio);
        }

        if (ospiti != null && !ospiti.isEmpty()) {
            for (Ospite ospite : ospiti) {
                ospite.setcodicePrenotazione(codicePrenotazione);
            }
            ospiteRepository.saveAll(ospiti);
        }

        if (serviziAggiuntivi != null && !serviziAggiuntivi.isEmpty()) {
            for (Servizio servizio : serviziAggiuntivi) {
                servizio.setCodice_prenotazione(codicePrenotazione);
            }
            servizioRepository.saveAll(serviziAggiuntivi);
        }

        return prenotazione;
    }
    
    @Transactional
    public String gestionePrenotazione(String codice,
                                       boolean modificaOspiti,
                                       String email,
                                       String pin,
                                       List<Ospite> nuoviOspiti) {

        Prenotazione prenotazione = prenotazioneRepository.findById(codice)
                .orElseThrow(() ->
                        new IllegalArgumentException("Prenotazione non trovata"));


      
        if (modificaOspiti) {

            if (email == null || pin == null) {
                throw new IllegalArgumentException(
                        "Email e PIN obbligatori per modificare gli ospiti.");
            }

            
            prenotazione.setEmail(email);
            prenotazione.setPin(pin);

            prenotazioneRepository.save(prenotazione);
            
            ospiteRepository.deleteAll(
                    ospiteRepository.findByCodicePrenotazione(codice)
            );


           
            if (nuoviOspiti != null && !nuoviOspiti.isEmpty()) {

                for (Ospite ospite : nuoviOspiti) {
                    ospite.setcodicePrenotazione(codice);
                }

                ospiteRepository.saveAll(nuoviOspiti);
            }

            return "Prenotazione aggiornata con nuovi ospiti.";
        }


        
        List<Ospite> ospiti =
                ospiteRepository.findByCodicePrenotazione(codice);

        ospiteRepository.deleteAll(ospiti);

        prenotazioneRepository.delete(prenotazione);

        return "Prenotazione cancellata.";
    }

}
