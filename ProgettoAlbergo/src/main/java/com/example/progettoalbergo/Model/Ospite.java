package com.example.progettoalbergo.Model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="ospite")
public class Ospite {
	
	@Id
	private Long id_ospite;
	
	private String codice_prenotazione;
	private String nome;
	private Date datanascita;
	public Long getId_ospite() {
		return id_ospite;
	}
	public void setId_ospite(Long id_ospite) {
		this.id_ospite = id_ospite;
	}
	public String getCodice_prenotazione() {
		return codice_prenotazione;
	}
	public void setCodice_prenotazione(String codice_prenotazione) {
		this.codice_prenotazione = codice_prenotazione;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDatanascita() {
		return datanascita;
	}
	public void setDatanascita(Date datanascita) {
		this.datanascita = datanascita;
	}
	
	

}
