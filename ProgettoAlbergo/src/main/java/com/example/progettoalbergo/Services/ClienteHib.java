package com.example.progettoalbergo.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.progettoalbergo.Model.Cliente;
import com.example.progettoalbergo.Repository.ClienteRepository;

@Service
public class ClienteHib {
	@Autowired
	private ClienteRepository clienteRepository;
	
	public List<Cliente> getAllClienti() {
		return clienteRepository.findAll();
	}

}
