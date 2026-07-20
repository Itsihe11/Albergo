package com.example.progettoalbergo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pensione")
public class Pensione {

	@Id
	private Long idpensione;

	private String tipo;
	private Double prezzo;

	public Pensione(Long idpensione, String tipo, Double prezzo) {
		setIdpensione(idpensione);
		setTipo(tipo);
		setPrezzo(prezzo);
	}

	public Long getIdpensione() {
		return idpensione;
	}

	public void setIdpensione(Long idpensione) {
		this.idpensione = idpensione;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}

}
