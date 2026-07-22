package com.example.progettoalbergo.Model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ospite")
public class Ospite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String codicePrenotazione;
	private String nome;
	private String cognome;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty("dataNascita")
	private Date datanascita;

	public Ospite() {

	}

	public Ospite(Long id, String codicePrenotazione, String nome,String cognome, Date datanascita) {
		super();
		setId_ospite(id);
		setcodicePrenotazione(codicePrenotazione);
		setNome(nome);
		setCognome(cognome);
		setDatanascita(datanascita);
	}

	public Long getId_ospite() {
		return id;
	}

	public void setId_ospite(Long id) {
		this.id= id;
	}

	public String getcodicePrenotazione() {
		return codicePrenotazione;
	}

	public void setcodicePrenotazione(String codicePrenotazione) {
		this.codicePrenotazione = codicePrenotazione;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDatanascita() {
		return datanascita;
	}

	public void setDatanascita(Date datanascita) {
		this.datanascita = datanascita;
	}
	

}
