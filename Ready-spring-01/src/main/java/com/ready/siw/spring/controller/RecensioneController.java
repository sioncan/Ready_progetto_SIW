package com.ready.siw.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	private String getLettoreLoggato() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

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
			Libro libro = this.libroService.libroPerIsbn(isbn);
			model.addAttribute("libro", libro);
			Lettore lett = this.lettoreService.lettorePerUsername(username);
			List<Recensione> recensioniDelLibro = libro.getRecensioni();
			for(Recensione r : recensioniDelLibro) {
                if((r.getRecensore().getUsername()).equals(username)) {
                    model.addAttribute("logErr", true);
                    model.addAttribute("libro", libro);
                    return "inserisciRecensione.html";
                }
            }
			lett.getRecensioni().add(recensione);
			recensioniDelLibro.add(recensione);
			recensione.setRecensore(lett);
			recensione.setLibro(libro);
			this.lettoreService.inserisci(lett);
			this.libroService.inserisci(libro);
			this.recensioneService.inserisci(recensione);
			return "redirect:/libro/{isbn}";
		} else {
			model.addAttribute("libro", libroService.libroPerIsbn(isbn));
			return "inserisciRecensione.html";
		}
	}
	
	// Elimina la recensione selezionata
	@RequestMapping(value="/modificaRecensione/{id}", method = RequestMethod.GET)
	public String goToPageModificaRecensione(@PathVariable("id") Long id, Model model) {
		Recensione r = this.recensioneService.recensionePerId(id);
		if(!this.getLettoreLoggato().equals(r.getRecensore().getUsername())){
            return "error";
        } else {
        	model.addAttribute("recensione", this.recensioneService.recensionePerId(id));
        	model.addAttribute("libro", this.recensioneService.recensionePerId(id).getLibro());
        	return "inserisciRecensione.html";
        }
	}
	
	// Elimina la recensione selezionata
	@RequestMapping(value="/eliminaRecensione/{id}", method = RequestMethod.GET)
	public String goToPageInserisciRecensione(@PathVariable("id") Long id, Model model) {
		Recensione r = this.recensioneService.recensionePerId(id);
		if(!this.getLettoreLoggato().equals(r.getRecensore().getUsername())){
            return "error";
        } else {
        	this.recensioneService.elimina(id);
        	return "redirect:/ricercaLibri";
        }
	}

}
