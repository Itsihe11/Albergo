package com.example.progettoalbergo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.progettoalbergo.Model.PrenotazioneStanza;

@Repository
public interface PrenotazioneStanzaRepository extends JpaRepository<PrenotazioneStanza, Long>{
	
	@Query("SELECT ps FROM PrenotazioneStanza ps WHERE ps.prenotazione.codice_prenotazione = :codice")
    Optional<PrenotazioneStanza> findByPrenotazioneCodicePrenotazione(@Param("codice") String codice);
}
