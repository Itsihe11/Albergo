package com.example.progettoalbergo.DTO;

import java.util.List;

import com.example.progettoalbergo.Model.Ospite;

public class GestionePrenotazioneRequest {
	

	    private String email;
	    private String pin;
	    private List<Ospite> ospiti;
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPin() {
			return pin;
		}
		public void setPin(String pin) {
			this.pin = pin;
		}
		public List<Ospite> getOspiti() {
			return ospiti;
		}
		public void setOspiti(List<Ospite> ospiti) {
			this.ospiti = ospiti;
		}

	
}
