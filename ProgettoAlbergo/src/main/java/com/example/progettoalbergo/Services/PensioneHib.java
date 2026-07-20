package com.example.progettoalbergo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.progettoalbergo.Model.Pensione;
import com.example.progettoalbergo.Model.Stanza;
import com.example.progettoalbergo.Model.Utente;
import com.example.progettoalbergo.Repository.PensioneRepository;
import com.example.progettoalbergo.Repository.UtenteRepository;

@Service
public class PensioneHib {

	@Autowired
	private PensioneRepository pensioneRepository;

	public List<Pensione> getAllPensioni() {
		return pensioneRepository.findAll();
	}

	public void addPensione(Pensione nuovaPensione) {
		pensioneRepository.save(nuovaPensione);
	}

	public void cancellaPensione(Long idpensione) {
		pensioneRepository.deleteById(idpensione);
	}

	public Optional<Pensione> modificaPensione(Long idpensione, Pensione pensioneDettagli) {
		return pensioneRepository.findById(idpensione).map(pensioneEsistente -> {
			pensioneEsistente.setTipo(pensioneDettagli.getTipo());
			pensioneEsistente.setPrezzo(pensioneDettagli.getPrezzo());

			return pensioneRepository.save(pensioneEsistente);
		});

	}
}
