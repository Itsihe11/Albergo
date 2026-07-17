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
import com.example.progettoalbergo.Model.Stanza;
import com.example.progettoalbergo.Repository.OspiteRepository;
import com.example.progettoalbergo.Repository.PrenotazioneRepository;
import com.example.progettoalbergo.Repository.PrenotazioneStanzaRepository;
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
                                         Double costoTotale,
                                         Double deposito,
                                         String tipoPagamento,
                                         List<Ospite> ospiti) {
        if (!checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException(
                    "La data di checkout deve essere successiva al check-in.");
        }

        Stanza stanza = stanzaRepository.findById(idStanza)
                .orElseThrow(() ->
                        new IllegalArgumentException("Stanza non trovata con ID: " + idStanza));

        boolean disponibile = stanzaRepository
                .findStanzeDisponibili(checkIn, checkOut)
                .stream()
                .anyMatch(s -> s.getId().equals(idStanza));

        if (!disponibile) {
            throw new IllegalArgumentException(
                    "La stanza non è disponibile nel periodo selezionato.");
        }

        Prenotazione prenotazione = new Prenotazione();

        prenotazione.setCodice_prenotazione(UUID.randomUUID().toString());
        prenotazione.setTipo_prenotazione(tipoPrenotazione);
        prenotazione.setDove_prenotazione(dovePrenotazione);

        if (costoTotale != null) {
            prenotazione.setCosto_totale(costoTotale);
        }

        if ("online".equalsIgnoreCase(dovePrenotazione) && costoTotale != null) {
            prenotazione.setDeposito(costoTotale + (costoTotale / 10));
        }

        prenotazione.setTipo_pagamento(tipoPagamento);

        
        prenotazioneRepository.save(prenotazione);

   
        if (ospiti != null && !ospiti.isEmpty()) {

            for (Ospite ospite : ospiti) {
                ospite.setCodice_prenotazione(
                        prenotazione.getCodice_prenotazione()
                );
            }

            ospiteRepository.saveAll(ospiti);
        }
       
        PrenotazioneStanza dettaglio = new PrenotazioneStanza();

        dettaglio.setPrenotazione(prenotazione);
        dettaglio.setStanza(stanza);
        dettaglio.setCheckin(checkIn);
        dettaglio.setCheckout(checkOut);
        dettaglio.setPensione(pensione);
        dettaglio.setCostoPensione(costoPensione);

        prenotazioneStanzaRepository.save(dettaglio);


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
                    ospite.setCodice_prenotazione(codice);
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
