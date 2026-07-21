package com.example.progettoalbergo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.progettoalbergo.Model.Prenotazione;


@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, String>{

	Optional<Prenotazione> findByEmailAndPin(String email, String pin);

	
}
