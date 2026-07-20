package com.example.progettoalbergo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.progettoalbergo.Model.Utente;

@Repository
public interface ClienteRepository extends JpaRepository<Utente, Long>{

}
