package com.example.progettoalbergo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.progettoalbergo.Model.PrenotazioneStanza;
import com.example.progettoalbergo.Repository.PrenotazioneStanzaRepository;


@Service
public class PrenotazioneStanzaHib {
	@Autowired
	private PrenotazioneStanzaRepository prenotazioneStanzaRepository;
	
	public List<PrenotazioneStanza> getAllPrenotazioneStanze() {
		return prenotazioneStanzaRepository.findAll();
	}
	
	
	public void addprenotazioneStanza(PrenotazioneStanza nuovoprenotazioneStanza) {
		prenotazioneStanzaRepository.save(nuovoprenotazioneStanza);
	}
	
	
	public void cancellaprenotazioneStanza(Long id) {
		prenotazioneStanzaRepository.deleteById(id);
	}
	
	public Optional<PrenotazioneStanza> modificaStanza(Long id, PrenotazioneStanza prenotazioneStanzaDettagli) {
        return prenotazioneStanzaRepository.findById(id).map(prenotazioneStanzaEsistente -> {
        	prenotazioneStanzaEsistente.setPrenotazione(prenotazioneStanzaDettagli.getPrenotazione());
        	prenotazioneStanzaEsistente.setCheckin(prenotazioneStanzaDettagli.getCheckin());
        	prenotazioneStanzaEsistente.setCheckout(prenotazioneStanzaDettagli.getCheckout());
        	prenotazioneStanzaEsistente.setStanza(prenotazioneStanzaDettagli.getStanza());
        	prenotazioneStanzaEsistente.setPensione(prenotazioneStanzaDettagli.getPensione());
        	prenotazioneStanzaEsistente.setCostoPensione(prenotazioneStanzaDettagli.getCostoPensione());

        	
            
            return prenotazioneStanzaRepository.save(prenotazioneStanzaEsistente);
        });
    }
	
}
