package com.example.progettoalbergo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.progettoalbergo.Model.Utente;
import com.example.progettoalbergo.Services.UtenteHib;

@RestController
@RequestMapping("/api/utente")
public class UtenteController {

	@Autowired
	UtenteHib utenteDependency;

	@GetMapping("/listaUtenti")
	public List<Utente> listaUtenti() {
		return utenteDependency.getAllUtenti();
	}

	@PostMapping("/datiUtente")
	public String inserisciUtente(@RequestBody Utente nuovoUtente) {
		utenteDependency.addUtente(nuovoUtente);

		return "Utente inserito";
	}

	@DeleteMapping("/cancellaUtente")
	public String cancellaUtente(@PathVariable Long idcliente) {
		utenteDependency.cancellaUtente(idcliente);

		return "Utente cancellato";
	}

	@PutMapping("/{idcliente}")
	public Optional<Utente> aggiornaUtente(@PathVariable Long idcliente, @RequestBody Utente utenteDettagli) {

		return utenteDependency.modificaUtente(idcliente, utenteDettagli);

	}

}
