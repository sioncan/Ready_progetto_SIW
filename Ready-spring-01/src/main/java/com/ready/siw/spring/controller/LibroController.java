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

import com.ready.siw.spring.controller.validator.LibroValidator;
import com.ready.siw.spring.model.Libro;
import com.ready.siw.spring.service.LibroService;

@Controller
public class LibroController {
	
	@Autowired
	private LibroService libroService;

	// Inserisce il Libro appena creato nel DB
	@RequestMapping(value = "/inserisciLibro", method = RequestMethod.POST)
	public String newLibro(@ModelAttribute("libro") Libro libro, 
			Model model, BindingResult bindingResult) {
			this.libroService.inserisci(libro);
			return "/admin/pannello.html";
	}
	
	// Elimina il Libro dal DB
	@RequestMapping(value = "/eliminaLibro", method = RequestMethod.POST)
	public String deleteLibro(@ModelAttribute("libro") Libro libro, 
			Model model, BindingResult bindingResult) {
			this.libroService.elimina(libro.getIsbn());
			return "/admin/pannello.html";
	}
    
}
