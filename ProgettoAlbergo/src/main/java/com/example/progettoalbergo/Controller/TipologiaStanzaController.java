package com.example.progettoalbergo.Controller;

import java.math.BigDecimal;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.progettoalbergo.Model.TipologiaStanza;

@RestController
@RequestMapping("/api/dipendente/tipologie")
public class TipologiaStanzaController {

    private final TipologiaStanzaRepository tipologiaRepository;

    public TipologiaStanzaController(TipologiaStanzaRepository tipologiaRepository) {
        this.tipologiaRepository = tipologiaRepository;
    }

    @GetMapping
    public List<TipologiaStanza> tutte() {
        return tipologiaRepository.findAll();
    }

    @PostMapping
    public TipologiaStanza crea(@RequestBody @Validated TipologiaStanzaRequest req) {
        TipologiaStanza t = new TipologiaStanza();
        t.setNome(req.nome());
        t.setPrezzo(req.prezzo());
        t.setCapienza(req.capienza());
        t.setDescrizione(req.descrizione());
        return tipologiaRepository.save(t);
    }

    @PutMapping("/{id}")
    public TipologiaStanza modifica(@PathVariable Integer id, @RequestBody @Validated TipologiaStanzaRequest req) {
        TipologiaStanza t = tipologiaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        t.setNome(req.nome());
        t.setPrezzo(req.prezzo());
        t.setCapienza(req.capienza());
        t.setDescrizione(req.descrizione());
        return tipologiaRepository.save(t);
    }
}

public record TipologiaStanzaRequest(
    @NotBlank String nome,
    @NotNull @Positive BigDecimal prezzo,
    @NotNull @Positive Integer capienza,
    String descrizione
) {}