package com.example.progettoalbergo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="servizio")
public class Servizio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idservizio;
	
	private String nomeservizio;
	
	private Double prezzi;
	
	public Servizio() {
		
	}

	public Servizio(Long idservizio, String nomeservizio, Double prezzi) {
		super();
		this.idservizio = idservizio;
		this.nomeservizio = nomeservizio;
		this.prezzi = prezzi;
	}

	public Long getIdservizio() {
		return idservizio;
	}

	public void setIdservizio(Long idservizio) {
		this.idservizio = idservizio;
	}

	public String getNomeservizio() {
		return nomeservizio;
	}

	public void setNomeservizio(String nomeservizio) {
		this.nomeservizio = nomeservizio;
	}

	public Double getPrezzi() {
		return prezzi;
	}

	public void setPrezzi(Double prezzi) {
		this.prezzi = prezzi;
	}

	
	
	
}
