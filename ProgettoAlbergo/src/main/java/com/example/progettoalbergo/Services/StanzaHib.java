package com.example.progettoalbergo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.progettoalbergo.Model.Stanza;
import com.example.progettoalbergo.Repository.StanzaRepository;


@Service
public class StanzaHib {
	@Autowired
	private StanzaRepository StanzaRepository;
	
	public List<Stanza> getAllStanze() {
		return StanzaRepository.findAll();
	}
	
	
	public void addStanza(Stanza nuovoStanza) {
		StanzaRepository.save(nuovoStanza);
	}
	
	
	public void cancellaStanza(Long id) {
		StanzaRepository.deleteById(id);
	}
	
	public Optional<Stanza> modificaStanza(Long id, Stanza stanzaDettagli) {
        return StanzaRepository.findById(id).map(stanzaEsistente -> {
            stanzaEsistente.setNumeroStanza(stanzaDettagli.getNumeroStanza());
            stanzaEsistente.setTipologia(stanzaDettagli.getTipologia());
            stanzaEsistente.setStatus(stanzaDettagli.getStatus());
            
            return StanzaRepository.save(stanzaEsistente);
        });
    }
}
