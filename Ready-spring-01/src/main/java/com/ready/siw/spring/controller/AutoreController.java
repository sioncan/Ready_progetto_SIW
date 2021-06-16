package com.ready.siw.spring.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ready.siw.spring.model.Autore;
import com.ready.siw.spring.service.AutoreService;

@Controller
public class AutoreController {
	
	@Autowired
	private AutoreService autoreService;

	// Inserisce l'Autore appena creato nel DB
	@RequestMapping(value = "/inserisciAutore", method = RequestMethod.POST)
	public String newAutore(@ModelAttribute("autore") Autore autore, 
			Model model, BindingResult bindingResult) {
			this.autoreService.inserisci(autore);
			return "/admin/pannello.html";
	}
	
	// Elimina l'Autore dal DB
	@RequestMapping(value = "/eliminaAutore", method = RequestMethod.POST)
	public String deleteAutore(@ModelAttribute("autore") Autore autore, 
			Model model, BindingResult bindingResult) {
			this.autoreService.elimina(autore.getNome(), autore.getCognome());
			return "/admin/pannello.html";
	}

}
