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
import org.springframework.web.bind.annotation.RequestParam;

import com.ready.siw.spring.controller.validator.RecensioneValidator;
import com.ready.siw.spring.model.Lettore;
import com.ready.siw.spring.model.Libro;
import com.ready.siw.spring.model.Recensione;
import com.ready.siw.spring.service.LettoreService;
import com.ready.siw.spring.service.LibroService;
import com.ready.siw.spring.service.RecensioneService;

@Controller
public class RecensioneController {
	
	@Autowired
	private LettoreService lettoreService;

	@Autowired
	private RecensioneService recensioneService;
	
	@Autowired
	private LibroService libroService;

	@Autowired
	private RecensioneValidator recensioneValidator;

	// Apre la pagina per inserire la recensione creando un nuovo oggetto Recensione
	@RequestMapping(value="/paginaInserisciRecensione/{isbn}", method = RequestMethod.GET)
	public String goToPageInserisciRecensione(@PathVariable("isbn") String isbn, Model model) {
		model.addAttribute("recensione", new Recensione());
		model.addAttribute("libro", libroService.libroPerIsbn(isbn));
		return "inserisciRecensione.html";
	}

	// Inserisce la recensione nel DB e ritorna alla pagina del libro mostrando tutte le recensioni
	@RequestMapping(value = "/inserisciRecensione/{isbn}/{username}", method = RequestMethod.POST)
	public String newRecensione(@PathVariable(value="username") String username, @PathVariable("isbn") String isbn, @ModelAttribute("recensione") Recensione recensione, 
			Model model, BindingResult bindingResult) {
		this.recensioneValidator.validate(recensione, bindingResult);
		if (!bindingResult.hasErrors()) {
			model.addAttribute("libro", libroService.libroPerIsbn(isbn));
			Lettore lett = this.lettoreService.lettorePerUsername(username);
			Libro l = this.libroService.libroPerIsbn(isbn);
			lett.getRecensioni().add(recensione);
			l.getRecensioni().add(recensione);
			recensione.setRecensore(lett);
			recensione.setLibro(l);
			this.lettoreService.inserisci(lett);
			this.libroService.inserisci(l);
			this.recensioneService.inserisci(recensione);
			return "redirect:/paginaLibro/{isbn}";
		}
		return "inserisciRecensione.html";
	}

}
