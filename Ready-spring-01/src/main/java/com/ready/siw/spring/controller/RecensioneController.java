package com.ready.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ready.siw.spring.model.Recensione;
import com.ready.siw.spring.service.RecensioneService;

@Controller
public class RecensioneController {

	@Autowired
	private RecensioneService recensioneService;

	@Autowired
	private RecensioneValidator recensioneValidator;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// Apre la pagina per inserire la recensione creando un nuovo oggetto Recensione
	@RequestMapping(value="/addRecensione", method = RequestMethod.GET)
	public String addRecensione(Model model) {
		logger.debug("addRecensionea");
		model.addAttribute("recensione", new Recensione());
		return "inserisciRecensione.html";
	}

	// Inserisce la recensione nel DB e ritorna alla pagina del libro mostrando tutte le recensioni
	@RequestMapping(value = "/inserisciRecensione", method = RequestMethod.POST)
	public String newRecensione(@ModelAttribute("recensione") Recensione recensione, 
			Model model, BindingResult bindingResult) {
		this.recensioneValidator.validate(recensione, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.recensioneService.inserisci(recensione);
			model.addAttribute("recensioni", this.recensioneService.tutti());
			return "index.html";
		}
		return "inserisciRecensione.html";
	}

	// Apre la pagine del libro mostrando tutte le recensioni
	@RequestMapping(value = "inserisciRecensione", method = RequestMethod.GET)
	public String caricaRecensioniLibro(Model model) {
		model.addAttribute("recensioni", this.recensioneService.tutti());
		return "index.html";
	}
}
