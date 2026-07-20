package com.example.progettoalbergo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="prenotazione_servizi")
public class PrenotazioneServizi {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String codice_prenotazione;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servizio") 
    private Servizio servizio;

    public Servizio getServizio() {
        return servizio;
    }

    public void setServizio(Servizio servizio) {
        this.servizio = servizio;
    }

	
	public PrenotazioneServizi() {
		
	}

	public PrenotazioneServizi(Long id, String codice_prenotazione) {
		super();
		setId_prenotazione_servizi(id);
		setCodice_prenotazione(codice_prenotazione);

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




	
	
}
