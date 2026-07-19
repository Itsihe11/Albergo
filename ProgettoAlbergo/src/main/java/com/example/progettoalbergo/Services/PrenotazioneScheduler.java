package com.example.progettoalbergo.Services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.progettoalbergo.Model.Prenotazione;
import com.example.progettoalbergo.Model.PrenotazioneStanza;
import com.example.progettoalbergo.Repository.PrenotazioneRepository;
import com.example.progettoalbergo.Repository.PrenotazioneStanzaRepository;

import jakarta.transaction.Transactional;

@Service
public class PrenotazioneScheduler {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private PrenotazioneStanzaRepository prenotazioneStanzaRepository;

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void annullaPrenotazioniScadute() {

        LocalDate oggi = LocalDate.now();

        List<PrenotazioneStanza> lista = prenotazioneStanzaRepository.findAll();

        for (PrenotazioneStanza ps : lista) {

            Prenotazione prenotazione = ps.getPrenotazione();

            if ("CHECK_IN".equals(prenotazione.getStato())
                    || "ANNULLATA".equals(prenotazione.getStato())
                    || "COMPLETATA".equals(prenotazione.getStato())) {
                continue;
            }

            LocalDate limite = ps.getCheckin().plusDays(2);

            if (oggi.isAfter(limite)) {

                prenotazione.setStato("ANNULLATA");

                prenotazioneRepository.save(prenotazione);
            }
        }
    }
}