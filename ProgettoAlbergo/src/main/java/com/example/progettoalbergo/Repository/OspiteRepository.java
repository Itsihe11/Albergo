package com.example.progettoalbergo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.progettoalbergo.Model.Ospite;

@Repository
public interface OspiteRepository extends JpaRepository<Ospite, Long>{

	List<Ospite> findByCodicePrenotazione(String codice);

}
