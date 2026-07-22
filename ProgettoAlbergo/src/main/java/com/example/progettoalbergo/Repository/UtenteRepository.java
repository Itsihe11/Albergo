package com.example.progettoalbergo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.progettoalbergo.Model.Admin;
import com.example.progettoalbergo.Model.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long>{
	
	Optional<Utente> findByEmail(String email);
	
	Optional<Utente> findByEmailAndPin(String email, String pin);

    boolean existsByEmail(String email);
}
