package com.example.progettoalbergo.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.progettoalbergo.DTO.PrenotazioneRequest;
import com.example.progettoalbergo.Model.Ospite;
import com.example.progettoalbergo.Model.Prenotazione;
import com.example.progettoalbergo.Model.Servizio;
import com.example.progettoalbergo.Model.Stanza;
import com.example.progettoalbergo.Services.PrenotazioneHib;

@RestController
@RequestMapping("/api/prenotazione")
public class PrenotazioneController {
	
	
	@Autowired PrenotazioneHib prenotazioneDependancy;
	
	@GetMapping("/disponibile/{checkin}/{checkout}")
	public List<Stanza> listaStanzaDisponibile(@PathVariable LocalDate checkin , @PathVariable LocalDate checkout){
		
		List<Stanza> disponibile = prenotazioneDependancy.getStanzeDisponibili(checkin, checkout);
		
		return disponibile;
		
	}
	
	@PostMapping("/prenota/{idStanza}")
	public Prenotazione prenota(
	        @PathVariable Long idStanza,
	        @RequestBody PrenotazioneRequest request) {

	    return prenotazioneDependancy.creaPrenotazione(
	            idStanza,
	            request.getCheckin(),
	            request.getCheckout(),
	            request.getPensione(),
	            request.getCostoPensione(),
	            request.getTipoPrenotazione(),
	            request.getDovePrenotazione(),
	            request.getCostoTotale(),
	            request.getDeposito(),
	            request.getTipoPagamento(),
	            request.getOspiti(),
	            request.getServiziAggiuntivi()
	    );
	}
}
