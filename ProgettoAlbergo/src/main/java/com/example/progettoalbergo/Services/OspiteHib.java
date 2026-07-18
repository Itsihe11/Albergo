package com.example.progettoalbergo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.progettoalbergo.Model.Ospite;
import com.example.progettoalbergo.Repository.OspiteRepository;


@Service
public class OspiteHib {
	@Autowired
	private OspiteRepository ospiteRepository;
	
	public List<Ospite> getAllOspite() {
		return ospiteRepository.findAll();
	}
	
	
	public void addOspite(Ospite nuovoOspite) {
		ospiteRepository.save(nuovoOspite);
	}
	
	
	public void cancellaOspite(Long id) {
		ospiteRepository.deleteById(id);
	}
	
	public Optional<Ospite> modificaOspite(Long id, Ospite OspiteDettagli) {
        return ospiteRepository.findById(id).map(OspiteEsistente -> {
        	OspiteEsistente.setcodicePrenotazione(OspiteDettagli.getcodicePrenotazione());
        	OspiteEsistente.setNome(OspiteDettagli.getNome());
        	OspiteEsistente.setDatanascita(OspiteDettagli.getDatanascita());
            
            return ospiteRepository.save(OspiteEsistente);
        });
    }
}
