package com.example.progettoalbergo.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.progettoalbergo.Model.Stanza;
import com.example.progettoalbergo.Repository.StanzaRepository;

@Service
public class StanzaHib {
	@Autowired
	private StanzaRepository StanzaRepository;
	
	public List<Stanza> getAllStanze() {
		return StanzaRepository.findAll();
	}

}
