package com.example.progettoalbergo.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.progettoalbergo.Model.Admin;
import com.example.progettoalbergo.Model.Utente;
import com.example.progettoalbergo.Repository.AdminRepository;
import com.example.progettoalbergo.Repository.UtenteRepository;
import com.example.progettoalbergo.Services.UtenteHib;

@RestController
@RequestMapping("/api/utente")
public class UtenteController {

	@Autowired
	private UtenteHib utenteDependency;

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private AdminRepository adminRepository;

	// 🟢 NUOVO ENDPOINT DI LOGIN (Gestisce sia Admin che Cliente)
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> credenziali) {
		String emailOUsername = credenziali.get("email") != null ? credenziali.get("email").trim() : "";
		String pinOPassword = credenziali.get("pin") != null ? credenziali.get("pin").trim() : "";

		if (emailOUsername.isBlank() || pinOPassword.isBlank()) {
			return ResponseEntity.status(400).body(Map.of("error", "Inserisci credenziali valide!"));
		}

		// 1. Cerca nella tabella ADMIN (username + password)
		Optional<Admin> adminOpt = adminRepository.findByUsernameAndPassword(emailOUsername, pinOPassword);
		if (adminOpt.isPresent()) {
			Admin admin = adminOpt.get();
			Map<String, Object> response = new HashMap<>();
			response.put("username", admin.getUsername());
			response.put("ruolo", "ADMIN");
			response.put("messaggio", "Accesso Amministratore effettuato");
			return ResponseEntity.ok(response);
		}

		// 2. Se non trova l'Admin, cerca nella tabella UTENTE (email + pin)
		Optional<Utente> utenteOpt = utenteRepository.findByEmailAndPin(emailOUsername, pinOPassword);
		if (utenteOpt.isPresent()) {
			Utente utente = utenteOpt.get();
			Map<String, Object> response = new HashMap<>();
			response.put("email", utente.getEmail());
			response.put("ruolo", "CLIENTE");
			response.put("messaggio", "Accesso Cliente effettuato");
			return ResponseEntity.ok(response);
		}

		// 3. Credenziali non trovate in nessuna tabella
		return ResponseEntity.status(400).body(Map.of("error", "Credenziali non valide o account inesistente."));
	}

	@GetMapping("/listaUtenti")
	public List<Utente> listaUtenti() {
		return utenteDependency.getAllUtenti();
	}

	@PostMapping("/datiUtente")
	public String inserisciUtente(@RequestBody Utente nuovoUtente) {
		utenteDependency.addUtente(nuovoUtente);
		return "Utente inserito";
	}

	// 🛠️ Corretta la rotta aggiungendo /{idcliente}
	@DeleteMapping("/cancellaUtente/{idcliente}")
	public String cancellaUtente(@PathVariable Long idcliente) {
		utenteDependency.cancellaUtente(idcliente);
		return "Utente cancellato";
	}

	@PutMapping("/{idcliente}")
	public Optional<Utente> aggiornaUtente(@PathVariable Long idcliente, @RequestBody Utente utenteDettagli) {
		return utenteDependency.modificaUtente(idcliente, utenteDettagli);
	}
}