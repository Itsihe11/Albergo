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

    @Query("""
        SELECT s FROM Stanza s
        WHERE s.status = 'disponibile'
          AND s.tipologia.capienza >= :numeroOspiti
          AND NOT EXISTS (
              SELECT ps FROM PrenotazioneStanza ps
              WHERE ps.stanza = s
                AND ps.checkin < :checkout
                AND ps.checkout > :checkin
          )
        """)
    List<Stanza> trovaStanzeDisponibili(
        @Param("checkin") LocalDate checkin,
        @Param("checkout") LocalDate checkout,
        @Param("numeroOspiti") Integer numeroOspiti
    );
}
