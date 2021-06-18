package com.ready.siw.spring.controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ready.siw.spring.model.Autore;
import com.ready.siw.spring.service.AutoreService;

@Controller
public class ElencoAutoriController {
	
	@Autowired
	private AutoreService autoreService;
	
	/* apre la pagina dell'autore selezionato dall'elenco*/
    @RequestMapping(value = "/apriAutore")
    public String goToAutore() {
    		return "autore.html";
    }
    
	// Ricerca l'Autore dal DB per Nome
	@GetMapping({"/cercaAutorePerNome","/cercaAutorePerNome{nome}"})
	public String findByNome(@Param("nome") String nome, Model model) {
		if(nome.isEmpty()) {
			return "ricercaAutori";
		}
		else {
			List<Autore> autori = this.autoreService.autorePerNome(nome);
			model.addAttribute("autori", autori);
			model.addAttribute("nome", nome);
			return "ricercaAutori";
		}
	}
	
	// Ricerca l'Autore dal DB per Nome e cognome
	@GetMapping({"/cercaAutorePerNomeECognome","/cercaAutorePerNomeECognome{nome,cognome}"})
	public String findByNomeAndCognome(@Param("nome") String nome, @Param("nome") String cognome, Model model) {
		if(nome.isEmpty() || cognome.isEmpty()) {
			return "ricercaAutori";
		}
		else {
			List<Autore> autori = this.autoreService.autorePerNomeECognome(nome,cognome);
			model.addAttribute("autori", autori);
			model.addAttribute("nome", nome);
			model.addAttribute("cognome", cognome);
			return "ricercaAutori";
		}
	}

}
