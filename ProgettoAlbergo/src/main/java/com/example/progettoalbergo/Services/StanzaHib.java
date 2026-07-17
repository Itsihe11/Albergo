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
	private StanzaRepository stanzaRepository;
	
	public List<Stanza> getAllStanze() {
		return stanzaRepository.findAll();
	}
	
	
	public void addStanza(Stanza nuovoStanza) {
		stanzaRepository.save(nuovoStanza);
	}
	
	
	public void cancellaStanza(Long id) {
		stanzaRepository.deleteById(id);
	}
	
	public Optional<Stanza> modificaStanza(Long id, Stanza stanzaDettagli) {
        return stanzaRepository.findById(id).map(stanzaEsistente -> {
            stanzaEsistente.setNumeroStanza(stanzaDettagli.getNumeroStanza());
            stanzaEsistente.setTipologia(stanzaDettagli.getTipologia());
            stanzaEsistente.setStatus(stanzaDettagli.getStatus());
            
            return stanzaRepository.save(stanzaEsistente);
        });
    }
}
