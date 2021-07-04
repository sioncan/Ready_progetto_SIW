package com.ready.siw.spring.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ready.siw.spring.model.Autore;
import com.ready.siw.spring.model.CasaEditrice;
import com.ready.siw.spring.model.Libro;
import com.ready.siw.spring.service.AutoreService;
import com.ready.siw.spring.service.CasaEditriceService;
import com.ready.siw.spring.service.LibroService;

@Controller
public class PannelloController {

	@Autowired
	private AutoreService autoreService;
	
	@Autowired
	private CasaEditriceService casaEditriceService;
	
	@Autowired
	private LibroService libroService;
	
	// Apre la pagina per collegare un Libro ad un Autore
	@RequestMapping(value="/admin/paginaCollegaLibroAutore", method = RequestMethod.GET)
	public String goToPageCollegaLibroAutore(Model model) {
		model.addAttribute("libri", this.libroService.tutti());
		model.addAttribute("autori", this.autoreService.tutti());
		return "/admin/collegaLibroAutore.html";
	}
	
	// Apre la pagina per collegare un Libro ad una Casa Editrice
	@RequestMapping(value="/admin/paginaCollegaLibroCasaEditrice", method = RequestMethod.GET)
	public String goToPageCollegaLibroCasaEditrice(Model model) {
		model.addAttribute("libri", this.libroService.tutti());
		model.addAttribute("caseEditrici", this.casaEditriceService.tutti());
		return "/admin/collegaLibroCasaEditrice.html";
	}

	// collega un Libro ad un Autore
	@RequestMapping(value="/admin/collegaLibroAutore", method = RequestMethod.POST)
	public String makeRelationLibroAutore(@Valid Long id, @Valid String isbn, Model model) {
		Libro libro = libroService.libroPerIsbn(isbn);
		Autore autore = autoreService.autorePerId(id);
		libro.getAutori().add(autore);
		autore.getLibri().add(libro);
		libroService.inserisci(libro);
		autoreService.inserisci(autore);
		return "redirect:/admin/pannelloAmministratore";
	}
	
	// collega un Libro ad una Casa Editrice
	@RequestMapping(value="/admin/collegaLibroCasaEditrice", method = RequestMethod.POST)
	public String makeRelationLibroCasaEditrice(@Valid Long id, @Valid String isbn, Model model) {
		Libro libro = libroService.libroPerIsbn(isbn);
		CasaEditrice casaEditrice = casaEditriceService.casaEditricePerId(id);
		libro.setCasaEditrice(casaEditrice);
		casaEditrice.getLibri().add(libro);
		libroService.inserisci(libro);
		casaEditriceService.inserisci(casaEditrice);
		return "redirect:/admin/pannelloAmministratore";
	}
}