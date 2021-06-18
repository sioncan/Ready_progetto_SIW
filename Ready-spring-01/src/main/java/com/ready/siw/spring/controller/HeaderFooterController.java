package com.ready.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ready.siw.spring.service.AutoreService;
import com.ready.siw.spring.service.LibroService;


@Controller
public class HeaderFooterController {
	/* tasti dell'header = Libri, Autori, Case editrici */

	@Autowired
	private LibroService libroService;
	
	@Autowired
	private AutoreService autoreService;

	/* tasto Libri - va alla pagina dell'elenco dei libri prendendoli dal DB */
	@RequestMapping(value="/ricercaLibri", method = RequestMethod.GET)
	public String goToPageRicercaLibri(Model model) {
		model.addAttribute("libri", this.libroService.tutti());
		return "index.html";
	}

	/* tasto Autori - va alla pagina dell'elenco degli autori prendendoli dal DB */
	@RequestMapping(value = "/ricercaAutori", method = RequestMethod.GET)
	public String goToPageRicercaAutori(Model model) {
		model.addAttribute("autori", this.autoreService.tutti());
		return "ricercaAutori.html";
	}

	/* tasto Case editrici - va alla pagina dell'elenco delle case editrici */
	@RequestMapping(value="/ricercaCaseEditrici")
	public String goToPageRicercaCaseEditrici() {
		return "ricercaCaseEditrici.html";
	}

	/* tasti del footer = Pannello Amministratore */

	/* tasto Case editrici - va alla pagina dell'elenco delle case editrici */
	@RequestMapping(value="/pannelloAmministratore")
	public String goToPagePannelloAmministratore() {
		return "/admin/pannello.html";
	}
}
