package com.example.progettoalbergo.Services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.progettoalbergo.DTO.StanzaDisponibileDTO;
import com.example.progettoalbergo.Repository.StanzaRepository;

@Service
public class DisponibilitaService {

    private final StanzaRepository stanzaRepository;

    public DisponibilitaService(StanzaRepository stanzaRepository) {
        this.stanzaRepository = stanzaRepository;
    }

    public List<StanzaDisponibileDTO> cercaDisponibili(LocalDate checkin, LocalDate checkout, int numeroOspiti) {
        if (!checkin.isBefore(checkout)) {
            throw new IllegalArgumentException("Il checkout deve essere successivo al checkin");
        }
        return stanzaRepository.trovaStanzeDisponibili(checkin, checkout, numeroOspiti)
                .stream()
                .map(StanzaDisponibileDTO::from)
                .toList();
    }
}