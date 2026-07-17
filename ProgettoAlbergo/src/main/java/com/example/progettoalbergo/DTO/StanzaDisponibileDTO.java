package com.example.progettoalbergo.DTO;

import java.math.BigDecimal;

import com.example.progettoalbergo.Model.Stanza;

public record StanzaDisponibileDTO(
	    Integer id,
	    String numeroStanza,
	    String tipologiaNome,
	    BigDecimal prezzoNotte,
	    Integer capienza
	) {
	    public static StanzaDisponibileDTO from(Stanza s) {
	        return new StanzaDisponibileDTO(
	            s.getId(),
	            s.getNumeroStanza(),
	            s.getTipologia().getNome(),
	            s.getTipologia().getPrezzo(),
	            s.getTipologia().getCapienza()
	        );
	    }
	}