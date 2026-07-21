package com.example.progettoalbergo.DTO;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.example.progettoalbergo.Model.Ospite;

public class PrenotazioneRequest {

    private Long idStanza;

    // 🟢 Mappa sia "checkin" che "checkIn"
    @JsonProperty("checkin")
    @JsonAlias({"checkIn", "checkin"})
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkin;

    // 🟢 Mappa sia "checkout" che "checkOut"
    @JsonProperty("checkout")
    @JsonAlias({"checkOut", "checkout"})
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkout;

    private Long idPensione;
    private String tipoPrenotazione;
    private String dovePrenotazione;
    private String tipoPagamento;
    private List<Ospite> ospiti;
    private List<Long> serviziAggiuntivi;

    public PrenotazioneRequest() {}

    // GETTER & SETTER PRINCIPALI
    public Long getIdStanza() { return idStanza; }
    public void setIdStanza(Long idStanza) { this.idStanza = idStanza; }

    public LocalDate getCheckin() { return checkin; }
    public void setCheckin(LocalDate checkin) { this.checkin = checkin; }

    public LocalDate getCheckout() { return checkout; }
    public void setCheckout(LocalDate checkout) { this.checkout = checkout; }

    // 🟢 SETTER ALTERNATIVI (per catturare camelCase da Jackson)
    @JsonProperty("checkIn")
    public void setCheckIn(LocalDate checkIn) { this.checkin = checkIn; }

    @JsonProperty("checkOut")
    public void setCheckOut(LocalDate checkOut) { this.checkout = checkOut; }

    public Long getIdPensione() { return idPensione; }
    public void setIdPensione(Long idPensione) { this.idPensione = idPensione; }

    public String getTipoPrenotazione() { return tipoPrenotazione; }
    public void setTipoPrenotazione(String tipoPrenotazione) { this.tipoPrenotazione = tipoPrenotazione; }

    public String getDovePrenotazione() { return dovePrenotazione; }
    public void setDovePrenotazione(String dovePrenotazione) { this.dovePrenotazione = dovePrenotazione; }

    public String getTipoPagamento() { return tipoPagamento; }
    public void setTipoPagamento(String tipoPagamento) { this.tipoPagamento = tipoPagamento; }

    public List<Ospite> getOspiti() { return ospiti; }
    public void setOspiti(List<Ospite> ospiti) { this.ospiti = ospiti; }

    public List<Long> getServiziAggiuntivi() { return serviziAggiuntivi; }
    public void setServiziAggiuntivi(List<Long> serviziAggiuntivi) { this.serviziAggiuntivi = serviziAggiuntivi; }
}