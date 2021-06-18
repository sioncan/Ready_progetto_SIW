package com.ready.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ready.siw.spring.model.Autore;
import com.ready.siw.spring.model.CasaEditrice;
import com.ready.siw.spring.model.Libro;
import com.ready.siw.spring.service.AutoreService;
import com.ready.siw.spring.service.LibroService;

@Controller
public class PannelloController {

	@Autowired
	private LibroService libroService;

	@Autowired
	private AutoreService autoreService;

	// Apre la pagina per inserire un Libro creando un nuovo oggetto Libro
	@RequestMapping(value="/paginaInserisciLibro", method = RequestMethod.GET)
	public String goToPageInserisciLibro(Model model) {
		model.addAttribute("libro", new Libro());
		return "/admin/inserisciLibro.html";
	}

	// Apre la pagina per inserire un Autore creando un nuovo oggetto Autore
	@RequestMapping(value="/paginaInserisciAutore", method = RequestMethod.GET)
	public String goToPageInserisciAutore(Model model) {
		model.addAttribute("autore", new Autore());
		return "/admin/inserisciAutore.html";
	}

	// Apre la pagina per inserire una CasaEditrice creando un nuovo oggetto CasaEditrice
	@RequestMapping(value="/paginaInserisciCasaEditrice", method = RequestMethod.GET)
	public String goToPageInserisciCasaEditrice(Model model) {
		model.addAttribute("casaEditrice", new CasaEditrice());
		return "/admin/inserisciCasaEditrice.html";
	}

	// Apre la pagina per eliminare un Libro
	@RequestMapping(value="/paginaEliminaLibro", method = RequestMethod.GET)
	public String goToPageEliminaLibro(Model model) {
		return "/admin/eliminaLibro.html";
	}

	// Apre la pagina per eliminare un Autore
	@RequestMapping(value="/paginaEliminaAutore", method = RequestMethod.GET)
	public String goToPageEliminaAutore(Model model) {
		return "/admin/eliminaAutore.html";
	}

	// Apre la pagina per eliminare una CasaEditrice
	@RequestMapping(value="/paginaEliminaCasaEditrice", method = RequestMethod.GET)
	public String goToPageEliminaCasaEditrice(Model model) {
		return "/admin/eliminaCasaEditrice.html";
	}

	// Apre la pagina per collegare un Libro ad un Autore
	@RequestMapping(value="/paginaCollegaLibroAutore", method = RequestMethod.GET)
	public String goToPageCollegaLibroAutore(Model model) {
		return "/admin/collegaLibroAutore.html";
	}

	// collega un Libro ad un Autore
	@RequestMapping(value="/collegaLibroAutore", method = RequestMethod.POST)
	public String makeRelationLibroAutore(@RequestParam(value = "id") Long id, @RequestParam(value = "isbn") String isbn, Model model) {
		Libro libro = libroService.libroPerIsbn(isbn);
		Autore autore = autoreService.autorePerId(id);
		libro.getAutori().add(autore);
		autore.getLibri().add(libro);
		libroService.inserisci(libro);
		autoreService.inserisci(autore);
		return "index.html";
	}
}
