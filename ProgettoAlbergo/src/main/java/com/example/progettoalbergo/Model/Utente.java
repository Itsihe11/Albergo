package com.example.progettoalbergo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "utente")
public class Utente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idutente;
	private String email;
	private String pin;

	public Utente() {

	}

	public Utente(Long idcliente, String email, String pin) {
		setIdcliente(idcliente);
		setEmail(email);
		setPin(pin);
	}

	public Long getIdcliente() {
		return idutente;
	}

	public void setIdcliente(Long idcliente) {
		this.idutente = idcliente;
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

}
