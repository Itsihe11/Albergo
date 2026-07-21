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

    // 🟢 MAPPA IL CAMPO CON IL NOME CORRETTO DELLA COLONNA NEL DB
    // Se nel tuo DB MySQL la colonna si chiama "prezzo", usa name = "prezzo"
    @Column(name = "prezzo") 
    private Double prezzi; // puoi lasciare 'prezzi' in Java, l'importante è il name = "prezzo"

    public Servizio() {}

    // --- Getter e Setter ---
    public Long getIdservizio() { return idservizio; }
    public void setIdservizio(Long idservizio) { this.idservizio = idservizio; }

    public String getNomeservizio() { return nomeservizio; }
    public void setNomeservizio(String nomeservizio) { this.nomeservizio = nomeservizio; }

    public Double getPrezzi() { return prezzi; }
    public void setPrezzi(Double prezzi) { this.prezzi = prezzi; }
}