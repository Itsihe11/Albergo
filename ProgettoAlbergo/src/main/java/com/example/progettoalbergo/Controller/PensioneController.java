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

import com.example.progettoalbergo.Model.Pensione;
import com.example.progettoalbergo.Services.PensioneHib;

public class PensioneController {

	@Autowired
	PensioneHib pensioneDependency;

	@GetMapping("/listaPensioni")
	public List<Pensione> listaPensioni() {
		return pensioneDependency.getAllPensioni();
	}

	@PostMapping("/datiPensione")
	public String inserisciPensione(@RequestBody Pensione nuovaPensione) {
		pensioneDependency.addPensione(nuovaPensione);

		return "Pensione inserita";
	}

	@DeleteMapping("/cancellaPensione")
	public String cancellaPensione(@PathVariable Long idpensione) {
		pensioneDependency.cancellaPensione(idpensione);

		return "Pensione cancellata";
	}

	@PutMapping("/{idpensione}")
	public Optional<Pensione> aggiornaPensione(@PathVariable Long idpensione, @RequestBody Pensione pensioneDettagli) {

		return pensioneDependency.modificaPensione(idpensione, pensioneDettagli);

	}

}
