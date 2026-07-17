package com.example.progettoalbergo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.progettoalbergo.Model.Admin;
import com.example.progettoalbergo.Repository.AdminRepository;


@Service
public class AdminHib {
	@Autowired
	private AdminRepository adminRepository;
	
	public List<Admin> getAllAdmin() {
		return adminRepository.findAll();
	}
	
	
	public void addAdmin(Admin nuovoAdmin) {
		adminRepository.save(nuovoAdmin);
	}
	
	
	public void cancellaAdmin(Long id) {
		adminRepository.deleteById(id);
	}
	
	public Optional<Admin> modificaAdmin(Long id, Admin AdminDettagli) {
        return adminRepository.findById(id).map(AdminEsistente -> {
        	AdminEsistente.setUsername(AdminDettagli.getUsername());
        	AdminEsistente.setPassword(AdminDettagli.getPassword());
            
            return adminRepository.save(AdminEsistente);
        });
    }
	
}
