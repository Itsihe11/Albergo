package com.example.progettoalbergo.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "servizio")
public class Servizio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idservizio")
    private Long idservizio;

    @Column(name = "nomeservizio")
    private String nomeservizio;

    @Column(name = "prezzo")
    private Double prezzo;

    // 🟢 NUOVO CAMPO
    @Column(name = "descrizione")
    private String descrizione;

    public Servizio() {}

    // --- Getter e Setter ---
    public Long getIdservizio() { return idservizio; }
    public void setIdservizio(Long idservizio) { this.idservizio = idservizio; }

    public String getNomeservizio() { return nomeservizio; }
    public void setNomeservizio(String nomeservizio) { this.nomeservizio = nomeservizio; }

    public Double getPrezzi() { return prezzo; }
    public void setPrezzi(Double prezzo) { this.prezzo = prezzo; }

    // 🟢 NUOVO
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
}