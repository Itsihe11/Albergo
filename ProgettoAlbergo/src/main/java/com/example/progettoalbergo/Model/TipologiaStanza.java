package com.example.progettoalbergo.Model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipologie_stanza")
public class TipologiaStanza {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private BigDecimal prezzo;
    private String descrizione;
    private Integer capienza;
    
    public TipologiaStanza() {
    	
    }
    
	public TipologiaStanza(Integer id, String nome, BigDecimal prezzo, String descrizione, Integer capienza) {
		setId(id);
		setNome(nome);
		setPrezzo(prezzo);
		setDescrizione(descrizione);
		setCapienza(capienza);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public BigDecimal getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(BigDecimal prezzo) {
		this.prezzo = prezzo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Integer getCapienza() {
		return capienza;
	}
	public void setCapienza(Integer capienza) {
		this.capienza = capienza;
	}
    
    
}
