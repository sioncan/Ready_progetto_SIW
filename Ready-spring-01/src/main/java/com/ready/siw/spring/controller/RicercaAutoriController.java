package com.ready.siw.spring.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ready.siw.spring.model.Autore;
import com.ready.siw.spring.service.AutoreService;

@Controller
public class RicercaAutoriController {
	
	@Autowired
	private AutoreService autoreService;
    
	// Ricerca l'Autore dal DB per Nome o cognome
	@GetMapping({"/cercaAutorePerNomeOCognome","/cercaAutorePerNomeOCognome{nomeCognome}"})
	public String findByNome(@Param("nomeCognome") String nomeCognome, Model model) {
		if(nomeCognome.isEmpty()) {
			return "ricercaAutori";
		}
		else {
			List<Autore> autori = this.autoreService.autorePerNomeOCognome(nomeCognome);
			model.addAttribute("autori", autori);
			model.addAttribute("nomeCognome", nomeCognome);
			return "ricercaAutori";
		}
	}
	
	// Ricerca l'Autore dal DB per Nazionalita
	@GetMapping({"/cercaAutorePerNazionalita","/cercaAutorePerNazionalita{nazionalita}"})
	public String findByNAzionalita(@Param("nazionalita") String nazionalita, Model model) {
		if(nazionalita.isEmpty()) {
			return "ricercaAutori";
		}
		else {
			List<Autore> autori = this.autoreService.autorePerNazionalita(nazionalita);
			model.addAttribute("autori", autori);
			model.addAttribute("nazionalita", nazionalita);
			return "ricercaAutori";
		}
	}

}
