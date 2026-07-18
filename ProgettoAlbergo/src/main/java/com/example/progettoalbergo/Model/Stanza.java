package com.example.progettoalbergo.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "stanza")
public class Stanza {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "numero_stanza")
	private String numeroStanza;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo")
	private TipologiaStanza tipologia;

	private String status;

	public Stanza() {

	}

	public Stanza(Long id, String numeroStanza, TipologiaStanza tipologia, String status) {
		super();
		setId(id);
		setNumeroStanza(numeroStanza);
		setTipologia(tipologia);
		setStatus(status);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroStanza() {
		return numeroStanza;
	}

	public void setNumeroStanza(String numeroStanza) {
		this.numeroStanza = numeroStanza;
	}

	public TipologiaStanza getTipologia() {
		return tipologia;
	}

	public void setTipologia(TipologiaStanza tipologia) {
		this.tipologia = tipologia;
	}

}