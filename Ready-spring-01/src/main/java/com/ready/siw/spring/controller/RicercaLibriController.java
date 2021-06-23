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

import com.ready.siw.spring.model.Libro;
import com.ready.siw.spring.service.LibroService;

@Controller
public class RicercaLibriController {
	
	@Autowired
	private LibroService libroService;

	// Ricerca il Libro dal DB per Titolo
	@GetMapping({"/cercaLibroPerTitolo","/cercaLibroPerTitolo{titolo}"})
	public String findByTitolo(@Param("titolo") String titolo, Model model) {
		if(titolo.isEmpty()) {
			return "index";
		}
		else {
			List<Libro> libri = this.libroService.libroPerTitolo(titolo);
			model.addAttribute("libri", libri);
			model.addAttribute("titolo", titolo);
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
			List<Libro> libri = this.libroService.libroPerTitolo(genere);
			model.addAttribute("libri", libri);
			model.addAttribute("genere", genere);
			return "index";
		}
	}

}
