package com.ready.siw.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ready.siw.spring.model.Libro;
import com.ready.siw.spring.service.LibroService;

@Controller
public class RicercaLibriController {
	
	@Autowired
	private LibroService libroService;
    
    @GetMapping({"/cercaLibroPerTitoloOIsbn","/cercaLibroPerTitoloOIsbn{titoloOisbn}"})
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
	@GetMapping({"/cercaLibroPerGenere","/cercaLibroPerGenere{genere}"})
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
	@GetMapping({"/cercaLibroPerAutore","/cercaLibroPerAutore{autore}"})
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

}
