package com.example.progettoalbergo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.progettoalbergo.Model.TipologiaStanza;
import com.example.progettoalbergo.Repository.TipologiaStanzaRepository;

@Service
public class TipologiaStanzaHib {
	@Autowired
	private TipologiaStanzaRepository tipologiaStanzaRepository;
	
	public List<TipologiaStanza> getAllTipologiaStanze() {
		return tipologiaStanzaRepository.findAll();
	}
	
	
	public void addTipologiaStanza(TipologiaStanza nuovoTipologiaStanza) {
		tipologiaStanzaRepository.save(nuovoTipologiaStanza);
	}
	
	
	public void cancellaTipologiaStanza(Long id) {
		tipologiaStanzaRepository.deleteById(id);
	}
	
	public Optional<TipologiaStanza> modificaTipologiaStanza(Long id, TipologiaStanza tipologiaStanzaDettagli) {
        return tipologiaStanzaRepository.findById(id).map(tipologiaStanzaEsistente -> {
        	tipologiaStanzaEsistente.setNome(tipologiaStanzaDettagli.getNome());
        	tipologiaStanzaEsistente.setPrezzo(tipologiaStanzaDettagli.getPrezzo());
        	tipologiaStanzaEsistente.setDescrizione(tipologiaStanzaDettagli.getDescrizione());
        	tipologiaStanzaEsistente.setCapienza(tipologiaStanzaDettagli.getCapienza());

        	
            return tipologiaStanzaRepository.save(tipologiaStanzaEsistente);
        });
    }
}
