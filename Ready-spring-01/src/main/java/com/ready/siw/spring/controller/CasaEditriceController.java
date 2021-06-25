package com.ready.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ready.siw.spring.model.CasaEditrice;
import com.ready.siw.spring.service.CasaEditriceService;

@Controller
public class CasaEditriceController {

	@Autowired
	private CasaEditriceService casaEditriceService;
	
	/* Va alla pagine della CasaEditrice selezionato dall'elenco */
	@RequestMapping(value="/paginaCasaEditrice/{id}", method = RequestMethod.GET)
	public String goToPageCasaEditrice(@PathVariable("id") Long id, Model model) {
		model.addAttribute("casaEditrice", this.casaEditriceService.casaEditricePerId(id));
		return "casaEditrice.html";
	}

	/* Va alla pagina di ricerca CaseEditrici */
	@RequestMapping(value="/ricercaCaseEditrici", method = RequestMethod.GET)
	public String goToPageRicercaCaseEditrici(Model model) {
		model.addAttribute("caseEditrici", this.casaEditriceService.tutti());
		return "ricercaCaseEditrici.html";
	}

	// Apre la pagina per inserire una CasaEditrice creando un nuovo oggetto CasaEditrice
	@RequestMapping(value="/paginaInserisciCasaEditrice", method = RequestMethod.GET)
	public String goToPageInserisciCasaEditrice(Model model) {
		model.addAttribute("casaEditrice", new CasaEditrice());
		return "/admin/inserisciCasaEditrice.html";
	}

	// Inserisce la CasaEditrice appena creata nel DB
	@RequestMapping(value = "/inserisciCasaEditrice", method = RequestMethod.POST)
	public String newCasaEditrice(@ModelAttribute("casaEditrice") CasaEditrice casaEditrice, 
			Model model, BindingResult bindingResult) {
		this.casaEditriceService.inserisci(casaEditrice);
		return "/admin/pannello.html";
	}

	// Apre la pagina per selezionare una CasaEditrice da modificare
	@RequestMapping(value="/paginaScegliCasaEditriceDaModificare", method = RequestMethod.GET)
	public String goToPageScegliCasaEditriceDaModificare(Model model) {
		model.addAttribute("caseEditrici", this.casaEditriceService.tutti());
		return "/admin/scegliCasaEditriceDaModificare.html";
	}

	// Apre la form per modificare una CasaEditrice
	@RequestMapping(value="/formModificaCasaEditrice/{id}", method = RequestMethod.GET)
	public String goToPageFormModificaCasaEditrice(@PathVariable("id") Long id, Model model) {
		model.addAttribute("casaEditrice", this.casaEditriceService.casaEditricePerId(id));
		return "/admin/inserisciCasaEditrice.html";
	}

	// Apre la pagina per eliminare una CasaEditrice
	@RequestMapping(value="/paginaEliminaCasaEditrice", method = RequestMethod.GET)
	public String goToPageEliminaCasaEditrice(Model model) {
		model.addAttribute("caseEditrici", this.casaEditriceService.tutti());
		return "/admin/eliminaCasaEditrice.html";
	}

	// Elimina la CasaEditrice dal DB
	@RequestMapping(value = "/eliminaCasaEditrice/{id}", method = RequestMethod.GET)
	public String deleteCasaEditrice(@PathVariable("id") Long id, 
			Model model) {
		this.casaEditriceService.elimina(id);
		return "redirect:/pannelloAmministratore";
	}

}
