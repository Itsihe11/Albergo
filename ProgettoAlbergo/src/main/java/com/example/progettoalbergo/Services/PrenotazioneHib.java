package com.example.progettoalbergo.Services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Prenotazione creaPrenotazione(Long idStanza, LocalDate checkIn, LocalDate checkOut, String pensione, BigDecimal pensioneCosto ) {
    	
    	List<Stanza> libere = stanzaRepository.findStanzeDisponibili(checkIn, checkOut);
    	
    	boolean isDisponibile= libere.stream().anyMatch(s-> s.getId().equals(idStanza));
    	
    	Stanza stanza = stanzaRepository.findById(idStanza)
                .orElseThrow(() -> new IllegalArgumentException("Stanza non trovata con ID: " + idStanza));

       
        Prenotazione prenotazione = new Prenotazione();
        

        
        PrenotazioneStanza dettaglio = new PrenotazioneStanza();
        dettaglio.setStanza(stanza);
        dettaglio.setCheckin(checkIn);
        dettaglio.setCheckout(checkOut);
        dettaglio.setPensione(pensione);
        dettaglio.setCostoPensione(costoPensione);

       
        prenotazione.addStanza(dettaglio);

        
        return prenotazioneRepository.save(prenotazione);
    
    }
	

}
