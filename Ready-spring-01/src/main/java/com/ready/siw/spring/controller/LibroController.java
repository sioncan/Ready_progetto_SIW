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
import org.springframework.web.bind.annotation.RequestParam;

import com.ready.siw.spring.controller.validator.LibroValidator;
import com.ready.siw.spring.model.Libro;
import com.ready.siw.spring.service.LibroService;

@Controller
public class LibroController {

	@Autowired
	private LibroService libroService;
	
	/* Va alla pagine del Libro selezionato dall'elenco */
	@RequestMapping(value="/paginaLibro/{isbn}", method = RequestMethod.GET)
	public String goToPageLibro(@PathVariable("isbn") String isbn, Model model) {
		model.addAttribute("libro", this.libroService.libroPerIsbn(isbn));
		return "libro.html";
	}

	/* Va alla pagina di ricerca Libri prendendoli dal DB */
	@RequestMapping(value="/ricercaLibri", method = RequestMethod.GET)
	public String goToPageRicercaLibri(Model model) {
		model.addAttribute("libri", this.libroService.tutti());
		return "index.html";
	}

	// Apre la pagina per inserire un Libro creando un nuovo oggetto Libro
	@RequestMapping(value="/paginaInserisciLibro", method = RequestMethod.GET)
	public String goToPageInserisciLibro(Model model) {
		model.addAttribute("libro", new Libro());
		return "/admin/inserisciLibro.html";
	}

	// Inserisce il Libro appena creato nel DB
	@RequestMapping(value = "/inserisciLibro", method = RequestMethod.POST)
	public String newLibro(@ModelAttribute("libro") Libro libro, 
			Model model, BindingResult bindingResult) {
		this.libroService.inserisci(libro);
		return "/admin/pannello.html";
	}

	// Apre la pagina per selezionare un Libro da modificare
	@RequestMapping(value="/paginaScegliLibroDaModificare", method = RequestMethod.GET)
	public String goToPageScegliLibroDaModificare(Model model) {
		model.addAttribute("libri", this.libroService.tutti());
		return "/admin/scegliLibroDaModificare.html";
	}

	// Apre la form per modificare il Libro
	@RequestMapping(value="/formModificaLibro/{isbn}", method = RequestMethod.GET)
	public String goToPageFormModificaLibro(@PathVariable("isbn") String isbn, Model model) {
		model.addAttribute("libro", this.libroService.libroPerIsbn(isbn));
		return "/admin/inserisciLibro.html";
	}

	// Apre la pagina per eliminare un Libro
	@RequestMapping(value="/paginaEliminaLibro", method = RequestMethod.GET)
	public String goToPageEliminaLibro(Model model) {
		model.addAttribute("libri", this.libroService.tutti());
		return "/admin/eliminaLibro.html";
	}

	// Elimina il Libro dal DB
	@RequestMapping(value = "/eliminaLibro/{isbn}", method = RequestMethod.GET)
	public String deleteLibro(@PathVariable("isbn") String isbn, 
			Model model) {
		this.libroService.elimina(isbn);
		return "redirect:/pannelloAmministratore";
	}
}
