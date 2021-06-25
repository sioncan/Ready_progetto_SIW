package com.ready.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ready.siw.spring.model.Autore;
import com.ready.siw.spring.service.AutoreService;

@Controller
public class AutoreController {

	@Autowired
	private AutoreService autoreService;
	
	/* Va alla pagine dell'Autore selezionato dall'elenco */
	@RequestMapping(value="/autore/{id}", method = RequestMethod.GET)
	public String goToPageAutore(@PathVariable("id") Long id, Model model) {
		model.addAttribute("autore", this.autoreService.autorePerId(id));
		return "autore.html";
	}

	/* Va alla pagina di ricerca Autori prendendoli dal DB */
	@RequestMapping(value = "/ricercaAutori", method = RequestMethod.GET)
	public String goToPageRicercaAutori(Model model) {
		model.addAttribute("autori", this.autoreService.tutti());
		return "ricercaAutori.html";
	}

	// Apre la pagina per inserire un Autore creando un nuovo oggetto Autore
	@RequestMapping(value="/paginaInserisciAutore", method = RequestMethod.GET)
	public String goToPageInserisciAutore(Model model) {
		model.addAttribute("autore", new Autore());
		return "/admin/inserisciAutore.html";
	}

	// Inserisce l'Autore appena creato nel DB
	@RequestMapping(value = "/inserisciAutore", method = RequestMethod.POST)
	public String newAutore(@ModelAttribute("autore") Autore autore, 
			Model model, BindingResult bindingResult) {
		this.autoreService.inserisci(autore);
		return "/admin/pannello.html";
	}

	// Apre la pagina per selezionare un Autore da modificare
	@RequestMapping(value="/paginaScegliAutoreDaModificare", method = RequestMethod.GET)
	public String goToPageScegliAutoreDaModificare(Model model) {
		model.addAttribute("autori", this.autoreService.tutti());
		return "/admin/scegliAutoreDaModificare.html";
	}

	// Apre la form per modificare l'Autore
	@RequestMapping(value="/formModificaAutore/{id}", method = RequestMethod.GET)
	public String goToPageFormModificaAutore(@PathVariable("id") Long id, Model model) {
		model.addAttribute("autore", this.autoreService.autorePerId(id));
		return "/admin/inserisciAutore.html";
	}

	// Apre la pagina per eliminare un Autore
	@RequestMapping(value="/paginaEliminaAutore", method = RequestMethod.GET)
	public String goToPageEliminaAutore(Model model) {
		model.addAttribute("autori", this.autoreService.tutti());
		return "/admin/eliminaAutore.html";
	}

	// Elimina l'Autore dal DB
	@RequestMapping(value = "/eliminaAutore/{id}", method = RequestMethod.GET)
	public String deleteAutore(@PathVariable("id") Long id, 
			Model model) {
		this.autoreService.elimina(id);
		return "redirect:/pannelloAmministratore";
	}

}
