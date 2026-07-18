package com.example.progettoalbergo.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.progettoalbergo.Model.Stanza;

@Repository
public interface StanzaRepository extends JpaRepository<Stanza, Long> {

	@Query("SELECT s FROM Stanza s WHERE s.id NOT IN (" +
	           "SELECT ps.stanza.id FROM PrenotazioneStanza ps " +
	           "WHERE ps.checkin < :checkOut AND ps.checkout > :checkIn)")
	    List<Stanza> findStanzeDisponibili(@Param("checkIn") LocalDate checkIn, 
	                                       @Param("checkOut") LocalDate checkOut);

}
