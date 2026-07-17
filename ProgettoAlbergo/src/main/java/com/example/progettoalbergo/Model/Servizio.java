package com.example.progettoalbergo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="prenotazione_servizi")
public class Servizio {

	@Id
	private Long id_prenotazione_servizi;
	
	private String codice_prenotazione;
	
	private String servizi;
	
	private double prezzi;

	public Long getId_prenotazione_servizi() {
		return id_prenotazione_servizi;
	}

	public void setId_prenotazione_servizi(Long id_prenotazione_servizi) {
		this.id_prenotazione_servizi = id_prenotazione_servizi;
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

	public double getPrezzi() {
		return prezzi;
	}

	public void setPrezzi(double prezzi) {
		this.prezzi = prezzi;
	}
	
	
	
}
