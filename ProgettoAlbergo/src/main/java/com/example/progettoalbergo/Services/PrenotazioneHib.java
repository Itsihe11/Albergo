package com.example.progettoalbergo.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.progettoalbergo.Model.Prenotazione;
import com.example.progettoalbergo.Model.Stanza;
import com.example.progettoalbergo.Repository.PrenotazioneRepository;
import com.example.progettoalbergo.Repository.StanzaRepository;

@Service
public class PrenotazioneHib {
	@Autowired
    private StanzaRepository stanzaRepository;
	
	@Autowired
	private PrenotazioneRepository PrenotazioneRepository;
	
	public List<Prenotazione> getAllPrenotazione() {
		return PrenotazioneRepository.findAll();
	}
	
	
	
	public void cancellaPrenotazione(String codice) {
		PrenotazioneRepository.deleteById(codice);
	}
	
	

    public List<Stanza> getStanzeDisponibili(LocalDate checkIn, LocalDate checkOut) {
      
        return stanzaRepository.findStanzeDisponibili(checkIn, checkOut);
    }
	

}
