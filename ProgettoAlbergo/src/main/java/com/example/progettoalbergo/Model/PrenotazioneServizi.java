package com.example.progettoalbergo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="prenotazione_servizi")
public class PrenotazioneServizi {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String codice_prenotazione;
	
	private String servizi;
	
	private Double prezzi;
	
	public PrenotazioneServizi() {
		
	}

	public PrenotazioneServizi(Long id, String codice_prenotazione, String servizi, Double prezzi) {
		super();
		setId_prenotazione_servizi(id);
		setCodice_prenotazione(codice_prenotazione);
		setServizi(servizi);
		setPrezzi(prezzi);
	}

	public Long getId_prenotazione_servizi() {
		return id;
	}

	public void setId_prenotazione_servizi(Long id) {
		this.id = id;
	}

	public String getCodice_prenotazione() {
		return codice_prenotazione;
	}

	public void setCodice_prenotazione(String codice_prenotazione) {
		this.codice_prenotazione = codice_prenotazione;
	}

	public String getServizi() {
		return servizi;
	}

	public void setServizi(String servizi) {
		this.servizi = servizi;
	}

	public Double getPrezzi() {
		return prezzi;
	}

	public void setPrezzi(Double prezzi) {
		this.prezzi = prezzi;
	}
	
	
	
}
