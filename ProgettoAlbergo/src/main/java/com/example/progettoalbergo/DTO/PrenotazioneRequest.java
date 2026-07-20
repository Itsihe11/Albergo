package com.example.progettoalbergo.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.example.progettoalbergo.Model.Ospite;
import com.example.progettoalbergo.Model.PrenotazioneServizi;
import com.example.progettoalbergo.Model.Servizio;

public class PrenotazioneRequest {
	private Long idStanza;
    private LocalDate checkin;
    private LocalDate checkout;

    private Long idPensione;

    private String tipoPrenotazione;
    private String dovePrenotazione;

    private Double costoTotale;
    private Double deposito;

    private String tipoPagamento;

    private List<Ospite> ospiti;
    private List<Long> serviziAggiuntivi;
    
    
    
	public Long getIdStanza() {
		return idStanza;
	}
	public void setIdStanza(Long idStanza) {
		this.idStanza = idStanza;
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

	public Long getIdPensione() {
		return idPensione;
	}
	public void setIdPensione(Long idPensione) {
		this.idPensione = idPensione;
	}
	public String getTipoPrenotazione() {
		return tipoPrenotazione;
	}
	public void setTipoPrenotazione(String tipoPrenotazione) {
		this.tipoPrenotazione = tipoPrenotazione;
	}
	public String getDovePrenotazione() {
		return dovePrenotazione;
	}
	public void setDovePrenotazione(String dovePrenotazione) {
		this.dovePrenotazione = dovePrenotazione;
	}
	public Double getCostoTotale() {
		return costoTotale;
	}
	public void setCostoTotale(Double costoTotale) {
		this.costoTotale = costoTotale;
	}
	public Double getDeposito() {
		return deposito;
	}
	public void setDeposito(Double deposito) {
		this.deposito = deposito;
	}
	public String getTipoPagamento() {
		return tipoPagamento;
	}
	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}
	public List<Ospite> getOspiti() {
		return ospiti;
	}
	public void setOspiti(List<Ospite> ospiti) {
		this.ospiti = ospiti;
	}
	public List<Long> getServiziAggiuntivi() {
		return serviziAggiuntivi;
	}
	public void setServiziAggiuntivi(List<Long> serviziAggiuntivi) {
		this.serviziAggiuntivi = serviziAggiuntivi;
	}

	


}
