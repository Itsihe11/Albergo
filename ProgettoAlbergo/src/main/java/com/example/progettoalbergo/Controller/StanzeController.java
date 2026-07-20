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

import com.example.progettoalbergo.Model.Servizio;
import com.example.progettoalbergo.Model.Stanza;
import com.example.progettoalbergo.Services.StanzaHib;

@RestController
@RequestMapping("/api/dipendente/stanza")
public class StanzeController {

	@Autowired
	StanzaHib stanzaDependency;

	@GetMapping("/listaStanze")
	public List<Stanza> tornaTutti() {
		return stanzaDependency.getAllStanze();
	}

	@PostMapping("/datiStanza")
	public String inserisciStanza(@RequestBody Stanza nuovaStanza) {
		stanzaDependency.addStanza(nuovaStanza);

		return "Stanza inserita";
	}

	@DeleteMapping("/cancellaStanza")
	public String cancellaStanza(@PathVariable Long id) {
		stanzaDependency.cancellaStanza(id);

		return "Stanza cancellata";
	}

	@PutMapping("/{id}")
	public Optional<Stanza> aggiornaStanza(@PathVariable Long id, @RequestBody Stanza stanzaDettagli) {

		return stanzaDependency.modificaStanza(id, stanzaDettagli);

	}

}