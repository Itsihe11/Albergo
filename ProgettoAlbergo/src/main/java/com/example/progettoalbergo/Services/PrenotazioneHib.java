package com.example.progettoalbergo.Services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.progettoalbergo.Model.Ospite;
import com.example.progettoalbergo.Model.Prenotazione;
import com.example.progettoalbergo.Model.PrenotazioneServizi;
import com.example.progettoalbergo.Model.PrenotazioneStanza;
import com.example.progettoalbergo.Model.Servizio;
import com.example.progettoalbergo.Model.Stanza;
import com.example.progettoalbergo.Repository.OspiteRepository;
import com.example.progettoalbergo.Repository.PrenotazioneRepository;
import com.example.progettoalbergo.Repository.PrenotazioneServizioRepository;
import com.example.progettoalbergo.Repository.PrenotazioneStanzaRepository;
import com.example.progettoalbergo.Repository.ServizioRepository;
import com.example.progettoalbergo.Repository.StanzaRepository;

import jakarta.transaction.Transactional;

@Service
public class PrenotazioneHib {
	@Autowired
    private StanzaRepository stanzaRepository;
	
	@Autowired
	private PrenotazioneRepository prenotazioneRepository;
	
	@Autowired
	private PrenotazioneStanzaRepository prenotazioneStanzaRepository;
	
	@Autowired
	private OspiteRepository ospiteRepository;
	
	@Autowired
	private PrenotazioneServizioRepository servizioRepository;


	public List<Prenotazione> getAllPrenotazione() {
		return prenotazioneRepository.findAll();
	}
	
	

	

    public List<Stanza> getStanzeDisponibili(LocalDate checkIn, LocalDate checkOut) {
      
        return stanzaRepository.findStanzeDisponibili(checkIn, checkOut);
    }
    
    @Transactional
    public Prenotazione creaPrenotazione(Long idStanza,
                                         LocalDate checkIn,
                                         LocalDate checkOut,
                                         String pensione,
                                         BigDecimal costoPensione,
                                         String tipoPrenotazione,
                                         String dovePrenotazione,
                                         String tipoPagamento,
                                         List<Ospite> ospiti,
                                         List<PrenotazioneServizi> serviziAggiuntivi) {

        String codicePrenotazione = UUID.randomUUID().toString();
        
        if("online".equalsIgnoreCase(dovePrenotazione)){

            if(!"carta".equalsIgnoreCase(tipoPagamento)
               &&
               !"bonifico".equalsIgnoreCase(tipoPagamento)){

                throw new IllegalArgumentException(
                "Online consentiti solo carta o bonifico");
            }
        }


        if("sede".equalsIgnoreCase(dovePrenotazione)){

            if(!"carta".equalsIgnoreCase(tipoPagamento)){

                throw new IllegalArgumentException(
                "In sede è consentito solo pagamento carta");
            }
        }
        
        if (ospiti == null || ospiti.isEmpty()) {
            throw new IllegalArgumentException(
                "È necessario inserire almeno un ospite."
            );
        }
        
        double calcoloCostoTotale = 0.0;
        
        
        boolean includeStanza = "stanza".equalsIgnoreCase(tipoPrenotazione) || "stanza+spa".equalsIgnoreCase(tipoPrenotazione);
        Stanza stanza = null;

        if (includeStanza) {
            if (idStanza == null || checkIn == null || checkOut == null) {
                throw new IllegalArgumentException("Dati stanza o date mancanti per una prenotazione di tipo stanza.");
            }
            if (!checkOut.isAfter(checkIn)) {
                throw new IllegalArgumentException("La data di checkout deve essere successiva al check-in.");
            }

            stanza = stanzaRepository.findById(idStanza)
                    .orElseThrow(() -> new IllegalArgumentException("Stanza non trovata con ID: " + idStanza));

            boolean disponibile = stanzaRepository
                    .findStanzeDisponibili(checkIn, checkOut)
                    .stream()
                    .anyMatch(s -> s.getId().equals(idStanza));
            
            int numeroOspiti = 0;

            if(ospiti != null){
                numeroOspiti = ospiti.size();
            }
            

            if(numeroOspiti > stanza.getTipologia().getCapienza()){

                throw new IllegalArgumentException(
                    "Numero ospiti superiore alla capienza della stanza"
                );
            }
            

            if (!disponibile) {
                throw new IllegalArgumentException("La stanza non è disponibile nel periodo selezionato.");
                
                
            }
            
            long numeroNotti = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);

            calcoloCostoTotale = stanza.getTipologia().getPrezzo().doubleValue() * numeroNotti;
        }
        
        if (costoPensione != null) {
            calcoloCostoTotale += costoPensione.doubleValue();
        }
        

        if (serviziAggiuntivi != null && !serviziAggiuntivi.isEmpty()) {

            for (PrenotazioneServizi servizio : serviziAggiuntivi) {

                if ("stanza+spa".equalsIgnoreCase(tipoPrenotazione)
                        && "SPA".equalsIgnoreCase(servizio.getServizi())) {
                    continue;
                }

                calcoloCostoTotale += servizio.getPrezzi();
            }
        }

        if ("stanza+spa".equalsIgnoreCase(tipoPrenotazione)) {
            calcoloCostoTotale += 200;
        }
        
        
        
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setCodice_prenotazione(codicePrenotazione);
        prenotazione.setTipo_prenotazione(tipoPrenotazione);
        prenotazione.setDove_prenotazione(dovePrenotazione);
        prenotazione.setTipo_pagamento(tipoPagamento);

        if ("online".equalsIgnoreCase(dovePrenotazione)) {
            prenotazione.setDeposito(calcoloCostoTotale / 10);
        } else {
            prenotazione.setDeposito(0.0);
        }
        prenotazione.setCosto_totale(calcoloCostoTotale);
        
        prenotazione.setStato("PENDENTE");
        prenotazione.setPagato(false);

        prenotazioneRepository.save(prenotazione);
        
        if (serviziAggiuntivi != null && !serviziAggiuntivi.isEmpty()) {
            for (PrenotazioneServizi servizio : serviziAggiuntivi) {
                servizio.setCodice_prenotazione(codicePrenotazione);
            }
            servizioRepository.saveAll(serviziAggiuntivi);
        }

        if (includeStanza && stanza != null) {
            PrenotazioneStanza dettaglio = new PrenotazioneStanza();
            dettaglio.setPrenotazione(prenotazione);
            dettaglio.setStanza(stanza);
            dettaglio.setCheckin(checkIn);
            dettaglio.setCheckout(checkOut);
            dettaglio.setPensione(pensione);
            dettaglio.setCostoPensione(costoPensione);
            prenotazioneStanzaRepository.save(dettaglio);
            stanzaRepository.save(stanza);
        }

        if (ospiti != null && !ospiti.isEmpty()) {
            for (Ospite ospite : ospiti) {
                ospite.setcodicePrenotazione(codicePrenotazione);
            }
            ospiteRepository.saveAll(ospiti);
        }


        return prenotazione;
    }
    
    @Transactional
    public String gestionePrenotazione(String codice,
                                       boolean modificaOspiti,
                                       String email,
                                       String pin,
                                       List<Ospite> nuoviOspiti) {

        Prenotazione prenotazione = prenotazioneRepository.findById(codice)
                .orElseThrow(() ->
                        new IllegalArgumentException("Prenotazione non trovata"));


      
        if (modificaOspiti) {


            if (email == null || email.isBlank() ||
            	    pin == null || pin.isBlank()) {

            	    throw new IllegalArgumentException(
            	        "Email e PIN sono obbligatori per modificare gli ospiti.");
            	}

            
            prenotazione.setEmail(email);
            prenotazione.setPin(pin);

            prenotazioneRepository.save(prenotazione);
            
            ospiteRepository.deleteAll(
                    ospiteRepository.findByCodicePrenotazione(codice)
            );


           
            if (nuoviOspiti != null && !nuoviOspiti.isEmpty()) {

                for (Ospite ospite : nuoviOspiti) {
                    ospite.setcodicePrenotazione(codice);
                }

                ospiteRepository.saveAll(nuoviOspiti);
            }

            return "Prenotazione aggiornata con nuovi ospiti.";
        }


        
        List<Ospite> ospiti =
                ospiteRepository.findByCodicePrenotazione(codice);

        ospiteRepository.deleteAll(ospiti);

        prenotazione.setStato("ANNULLATA");

        prenotazioneRepository.save(prenotazione);

        return "Prenotazione annullata. La caparra rimane all'hotel.";

    }
    
    @Transactional
    public String checkIn(String codice){

        Prenotazione p =
        prenotazioneRepository.findById(codice)
        .orElseThrow();

        if(!p.getStato().equals("PENDENTE")
           &&
           !p.getStato().equals("CONFERMATA")){

            throw new IllegalArgumentException(
            "Prenotazione non valida");
        }


        p.setStato("CHECK_IN");

        prenotazioneRepository.save(p);

        return "Check-in effettuato";
    }
    
    @Transactional
    public String checkOut(String codice){

        Prenotazione p =
        prenotazioneRepository.findById(codice)
        .orElseThrow();


        if(!p.getStato().equals("CHECK_IN")){
            throw new IllegalArgumentException(
            "Cliente non presente");
        }


        p.setPagato(true);
        

        p.setStato("COMPLETATA");


        prenotazioneRepository.save(p);


        return "Checkout completato e pagamento registrato";
    }

}
