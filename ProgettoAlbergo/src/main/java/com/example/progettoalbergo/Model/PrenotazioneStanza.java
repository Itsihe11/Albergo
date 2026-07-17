package com.example.progettoalbergo.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "prenotazione_stanza")
public class PrenotazioneStanza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stanza", nullable = false)
    private Stanza stanza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codice_prenotazione", nullable = false)
    private Prenotazione prenotazione;

    private LocalDate checkin;
    private LocalDate checkout;

    private String pensione;

    @Column(name = "costo_pensione")
    private BigDecimal costoPensione;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Stanza getStanza() {
		return stanza;
	}

	public void setStanza(Stanza stanza) {
		this.stanza = stanza;
	}

	public Prenotazione getPrenotazione() {
		return prenotazione;
	}

	public void setPrenotazione(Prenotazione prenotazione) {
		this.prenotazione = prenotazione;
	}

	public LocalDate getCheckin() {
		return checkin;
	}

	public void setCheckin(LocalDate checkin) {
		this.checkin = checkin;
	}

	public LocalDate getCheckout() {
		return checkout;
	}

	public void setCheckout(LocalDate checkout) {
		this.checkout = checkout;
	}

	public String getPensione() {
		return pensione;
	}

	public void setPensione(String pensione) {
		this.pensione = pensione;
	}

	public BigDecimal getCostoPensione() {
		return costoPensione;
	}

	public void setCostoPensione(BigDecimal costoPensione) {
		this.costoPensione = costoPensione;
	}

    
}