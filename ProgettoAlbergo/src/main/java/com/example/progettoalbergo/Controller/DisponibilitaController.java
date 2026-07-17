package com.example.progettoalbergo.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.progettoalbergo.DTO.StanzaDisponibileDTO;
import com.example.progettoalbergo.Services.DisponibilitaService;

@RestController
@RequestMapping("/api/disponibilita")
public class DisponibilitaController {

    private final DisponibilitaService disponibilitaService;

    public DisponibilitaController(DisponibilitaService disponibilitaService) {
        this.disponibilitaService = disponibilitaService;
    }

    @GetMapping
    public List<StanzaDisponibileDTO> cerca(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkin,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkout,
            @RequestParam(defaultValue = "1") Integer ospiti) {
        return disponibilitaService.cercaDisponibili(checkin, checkout, ospiti);
    }
}