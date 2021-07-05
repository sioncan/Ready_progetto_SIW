package com.ready.siw.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ready.siw.spring.model.Libro;
import com.ready.siw.spring.service.LibroService;

@Controller
public class RicercaLibriController {
	
	@Autowired
	private LibroService libroService;
    
    @RequestMapping(value={"/cercaLibroPerTitoloOIsbn","/cercaLibroPerTitoloOIsbn{titoloOisbn}"}, method = RequestMethod.GET)
	public String findByTitoloOrIsbn(@Param("titoloOisbn") String titoloOisbn, Model model) {
		if(titoloOisbn.isEmpty()) {
			return "index";
		}
		else {
			List<Libro> libri = this.libroService.libroPerTitoloOIsbn(titoloOisbn);
			model.addAttribute("libri", libri);
			model.addAttribute("titoloOisbn", titoloOisbn);
			return "index";
		}
	}
	
	// Ricerca il Libro dal DB per Genere
	@RequestMapping(value={"/cercaLibroPerGenere","/cercaLibroPerGenere{genere}"}, method = RequestMethod.GET)
	public String findByGenere(@Param("genere") String genere, Model model) {
		if(genere.isEmpty()) {
			return "index";
		}
		else {
			List<Libro> libri = this.libroService.libroPerGenere(genere);
			model.addAttribute("libri", libri);
			model.addAttribute("genere", genere);
			return "index";
		}
	}
	
	// Ricerca il Libro dal DB per Autore
	@RequestMapping(value={"/cercaLibroPerAutore","/cercaLibroPerAutore{autore}"}, method = RequestMethod.GET)
	public String findByAutore(@Param("autore") String autore, Model model) {
		if(autore.isEmpty()) {
			return "index";
		}
		else {
			List<Libro> libri = this.libroService.libroPerAutore(autore);
			model.addAttribute("libri", libri);
			model.addAttribute("autore", autore);
			return "index";
		}
	}
	
	// Ricerca il Libro dal DB per CasaEditrice
	@RequestMapping(value={"/cercaLibroPerCasaEditrice","/cercaLibroPerCasaEditrice{casaEditrice}"}, method = RequestMethod.GET)
	public String findByCasaEditrice(@Param("casaEditrice") String casaEditrice, Model model) {
		if(casaEditrice.isEmpty()) {
			return "index";
		}
		else {
			List<Libro> libri = this.libroService.libroPerCasaEditrice(casaEditrice);
			model.addAttribute("libri", libri);
			model.addAttribute("casaEditrice", casaEditrice);
			return "index";
		}
	}

}
