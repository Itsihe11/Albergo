package com.example.progettoalbergo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente {
	@Id
	private Long idcliente;
	private String nome;
	private String cognome;
	private String email;

	public Cliente() {

	}

	public Cliente(Long idcliente, String nome, String cognome, String email) {
		setIdcliente(idcliente);
		setNome(nome);
		setCognome(cognome);
		setEmail(email);
	}

	public Long getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(Long idcliente) {
		this.idcliente = idcliente;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
