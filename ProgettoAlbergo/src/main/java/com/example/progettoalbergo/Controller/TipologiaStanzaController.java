package com.example.progettoalbergo.Controller;

import java.math.BigDecimal;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.progettoalbergo.Model.TipologiaStanza;
import com.example.progettoalbergo.Repository.TipologiaStanzaRepository;

@RestController
@RequestMapping("/api/dipendente/tipologie")
public class TipologiaStanzaController {

	private final TipologiaStanzaRepository tipologiaRepository;

	public TipologiaStanzaController(TipologiaStanzaRepository tipologiaRepository) {
		this.tipologiaRepository = tipologiaRepository;
	}

	@GetMapping
	public List<TipologiaStanza> tutte() {
		return tipologiaRepository.findAll();
	}
}
