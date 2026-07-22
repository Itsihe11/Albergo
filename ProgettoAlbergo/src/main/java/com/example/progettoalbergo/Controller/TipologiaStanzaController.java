	package com.example.progettoalbergo.Controller;
	
	import java.math.BigDecimal;
	import java.util.List;
	
	import org.antlr.v4.runtime.misc.NotNull;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.PutMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;
	import org.springframework.web.server.ResponseStatusException;
	
	import com.example.progettoalbergo.Model.TipologiaStanza;
	import com.example.progettoalbergo.Services.TipologiaStanzaHib;
	
	@RestController
	@RequestMapping("/api/dipendente/tipologie")
	@CrossOrigin(origins = "*")
	public class TipologiaStanzaController {
	
		@Autowired
		TipologiaStanzaHib TipologiaDependency;
	
		@GetMapping("/tipologieStanze")
		public List<TipologiaStanza> listaTipologie() {
	
			return TipologiaDependency.getAllTipologiaStanze();
	
		}
	
		@PostMapping("/datiTipologia")
		public String inserisciDati(@RequestBody TipologiaStanza nuovaTipologia) {
	
			TipologiaDependency.addTipologiaStanza(nuovaTipologia);
	
			return "Tipologia inserita";
	
		}
	
		@DeleteMapping("/cancellaTipologia/{id}")
		public String cancellaTipologia(@PathVariable Long id) {
	
			TipologiaDependency.cancellaTipologiaStanza(id);
	
			return "Tipologia cancellata";
	
		}
	
	}
