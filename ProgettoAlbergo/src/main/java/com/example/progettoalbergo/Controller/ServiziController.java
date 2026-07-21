package com.example.progettoalbergo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.progettoalbergo.Model.Servizio;
import com.example.progettoalbergo.Services.ServizioHib;

@RestController
@RequestMapping("api/dipendenti/servizi")
public class ServiziController {



	@Autowired
	ServizioHib ServizioDependency;

	@GetMapping("/listaServizi")
	public List<Servizio> listaServizi() {

		return ServizioDependency.getAllServizi();


	}

	@PostMapping("/datiServizi")
	public String Servizi(@RequestBody Servizio nuovoServizio) {

		ServizioDependency.addServizio(nuovoServizio);

		return "Servizio inserito";

	}

	@DeleteMapping("/cancellaServizi")
	public String cancellaServizi(@PathVariable Long id) {

		ServizioDependency.cancellaServizio(id);

		return "Servizio cancellato";

	}

	@PutMapping("/{id}")
	public Optional<Servizio> aggiornaServizio(@PathVariable Long id, @RequestBody Servizio servizioDettagli) {

		return ServizioDependency.modificaServizio(id, servizioDettagli);

	}

}
