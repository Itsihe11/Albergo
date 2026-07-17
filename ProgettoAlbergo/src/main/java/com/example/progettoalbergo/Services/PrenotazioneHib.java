package com.example.progettoalbergo.Services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.progettoalbergo.Model.Prenotazione;
import com.example.progettoalbergo.Model.PrenotazioneStanza;
import com.example.progettoalbergo.Model.Stanza;
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
	


	public List<Prenotazione> getAllPrenotazione() {
		return prenotazioneRepository.findAll();
	}
	
	
	public void cancellaPrenotazione(String codice) {
		prenotazioneRepository.deleteById(codice);
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
                                         String tipoPagamento) {
    	Prenotazione prenotazione = new Prenotazione();

    	prenotazione.setCodice_prenotazione(UUID.randomUUID().toString());
    	prenotazione.setTipo_prenotazione(tipoPrenotazione);
    	prenotazione.setDove_prenotazione(dovePrenotazione);

    	if (costoTotale != null) {
    	    prenotazione.setCosto_totale(costoTotale);
    	}

    	if (dovePrenotazione.equalsIgnoreCase("online")) {
    	    prenotazione.setDeposito(costoTotale+(costoTotale/10));
    	}

    	prenotazione.setTipo_pagamento(tipoPagamento);

    	return prenotazioneRepository.save(prenotazione);
    }

}
