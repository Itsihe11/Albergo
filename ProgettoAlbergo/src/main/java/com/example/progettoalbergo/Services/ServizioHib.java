package com.example.progettoalbergo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.progettoalbergo.Model.Servizio;
import com.example.progettoalbergo.Repository.ServizioRepository;

@Service
public class ServizioHib {
	@Autowired
	
	private ServizioRepository servizioRepository;
	
	public List<Servizio> getAllServizi() {
		return servizioRepository.findAll();
	}
	
	
	public void addServizio(Servizio nuovoServizio) {
		servizioRepository.save(nuovoServizio);
	}
	
	
	public void cancellaServizio(Long id) {
		servizioRepository.deleteById(id);
	}
	
	public Optional<Servizio> modificaServizio(Long id, Servizio servizioDettagli) {
        return servizioRepository.findById(id).map(servizioEsistente -> {
        	servizioEsistente.setCodice_prenotazione(servizioDettagli.getCodice_prenotazione());
        	servizioEsistente.setServizi(servizioDettagli.getServizi());
        	servizioEsistente.setPrezzi(servizioDettagli.getPrezzi());
            
            return servizioRepository.save(servizioEsistente);
        });
    }
	
}
