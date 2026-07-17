package com.example.progettoalbergo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.progettoalbergo.Model.TipologiaStanza;

@Repository
public interface TipologiaStanzaRepository extends JpaRepository<TipologiaStanza, Integer>{

}
