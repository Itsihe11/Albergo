package com.example.progettoalbergo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.progettoalbergo.Model.Admin;
import com.example.progettoalbergo.Model.Utente;
import com.example.progettoalbergo.Repository.UtenteRepository;

@Service
public class UtenteHib {
	
	@Autowired
	private UtenteRepository utenteRepository;

	public List<Utente> getAllUtenti() {
		return utenteRepository.findAll();
	}

	public void addUtente(Utente nuovoUtente) {
		utenteRepository.save(nuovoUtente);
	}

	public void cancellaUtente(Long idcliente) {
		utenteRepository.deleteById(idcliente);
	}

	public Optional<Utente> modificaUtente(Long idcliente, Utente UtenteDettagli) {
		return utenteRepository.findById(idcliente).map(UtenteEsistente -> {
			UtenteEsistente.setEmail(UtenteDettagli.getEmail());
			UtenteEsistente.setPin(UtenteDettagli.getPin());

			return utenteRepository.save(UtenteEsistente);
		});
	}

}
