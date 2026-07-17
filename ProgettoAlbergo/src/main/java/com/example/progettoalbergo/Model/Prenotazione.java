package com.example.progettoalbergo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "prenotazione")
public class Prenotazione {

	@Id
	private String codice_prenotazione;

	private String email;

	private String pin;

	private String tipo_prenotazione;

	private String dove_prenotazione;

	private Double costo_totale;

	private Double deposito;

	private String tipo_pagamento;

	public Prenotazione() {

	}

	public Prenotazione(String codice_prenotazione, String email, String pin, String tipo_prenotazione,
			String dove_prenotazione, Double costo_totale, Double deposito, String tipo_pagamento) {
		super();
		setCodice_prenotazione(codice_prenotazione);
		setEmail(email);
		setPin(pin);
		setTipo_prenotazione(tipo_prenotazione);
		setDove_prenotazione(dove_prenotazione);
		setCosto_totale(costo_totale);
		setDeposito(deposito);
		setTipo_pagamento(tipo_pagamento);
	}

	public String getCodice_prenotazione() {
		return codice_prenotazione;
	}

	public void setCodice_prenotazione(String codice_prenotazione) {
		this.codice_prenotazione = codice_prenotazione;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getTipo_prenotazione() {
		return tipo_prenotazione;
	}

	public void setTipo_prenotazione(String tipo_prenotazione) {
		this.tipo_prenotazione = tipo_prenotazione;
	}

	public String getDove_prenotazione() {
		return dove_prenotazione;
	}

	public void setDove_prenotazione(String dove_prenotazione) {
		this.dove_prenotazione = dove_prenotazione;
	}

	public double getCosto_totale() {
		return costo_totale;
	}

	public void setCosto_totale(Double costo_totale) {
		this.costo_totale = costo_totale;
	}

	public double getDeposito() {
		return deposito;
	}

	public void setDeposito(Double deposito) {
		this.deposito = deposito;
	}

	public String getTipo_pagamento() {
		return tipo_pagamento;
	}

	public void setTipo_pagamento(String tipo_pagamento) {
		this.tipo_pagamento = tipo_pagamento;
	}

}
