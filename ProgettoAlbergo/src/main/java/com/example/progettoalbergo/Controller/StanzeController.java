package com.example.progettoalbergo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.progettoalbergo.Model.Stanza;
import com.example.progettoalbergo.Services.StanzaHib;


@RestController
@RequestMapping("/api/stanza")
public class StanzeController {
	
	@Autowired StanzaHib stanzaDependancy;
	
	
	@GetMapping("/lista")
	public List<Stanza> tornaTutti(){
		return stanzaDependancy.getAllStanze();
	}
	
}