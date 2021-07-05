package com.museo.siw.spring.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.museo.siw.spring.controller.validator.CuratoreValidator;
import com.museo.siw.spring.model.Collezione;
import com.museo.siw.spring.model.Curatore;
import com.museo.siw.spring.service.CollezioneService;
import com.museo.siw.spring.service.CuratoreService;

@Controller
public class CuratoreController {

	@Autowired
	private CuratoreValidator curatoreValidator;

	@Autowired
	private CuratoreService curatoreService;

	@Autowired
	private CollezioneService collezioneService;

	// Apre la pagina per inserire un Curatore creando un nuovo oggetto Curatore
	@RequestMapping(value="/admin/paginaInserisciCuratore", method = RequestMethod.GET)
	public String goToPageInserisciCuratore(Model model) {
		model.addAttribute("curatore", new Curatore());
		model.addAttribute("collezioni", this.collezioneService.tutti());
		return "/admin/inserisciCuratore.html";
	}

	// Inserisce il Curatore appena creato nel DB
	@RequestMapping(value="/admin/inserisciCuratore", method = RequestMethod.POST)
	public String saveCuratore(@ModelAttribute("curatore") Curatore curatore,
			BindingResult curatoreBindingResult) throws IOException {
		this.curatoreValidator.validate(curatore, curatoreBindingResult);
		if(!curatoreBindingResult.hasErrors()) {
			this.curatoreService.inserisci(curatore);
			return "redirect:/home";
		} else
			return "/admin/inserisciCuratore.html";
	}

	// Inserisce il Curatore appena modificato nel DB
	@RequestMapping(value="/admin/inserisciCuratoreModificato", method = RequestMethod.POST)
	public String saveCuratoreModificato(@ModelAttribute("curatore") Curatore curatore, @Valid Long idCollezione,
			  BindingResult curatoreBindingResult) throws IOException {
		this.curatoreValidator.validate(curatore, curatoreBindingResult);
		if(!curatoreBindingResult.hasErrors()) {
			if(idCollezione != null && idCollezione != 0) {
				if(curatore.getCollezioni() == null) {
					curatore.setCollezioni(new ArrayList<Collezione>());
				}
				Collezione collezione = this.collezioneService.collezionePerId(idCollezione);
				for(Collezione c : this.curatoreService.curatorePerId(curatore.getId()).getCollezioni()) {
					c.setCuratore(null);
				}
				collezione.setCuratore(curatore);
				this.collezioneService.inserisci(collezione);
			} else {
				curatore.setCollezioni(new ArrayList<Collezione>());
				for(Collezione c : this.curatoreService.curatorePerId(curatore.getId()).getCollezioni()) {
					curatore.getCollezioni().add(c);
				}
			}
			this.curatoreService.inserisci(curatore);
			return "redirect:/home";
		} else
			return "/admin/modificaCuratoreForm.html";
	}

	// Apre la pagina per selezionare un Curatore da modificare
	@RequestMapping(value="/admin/paginaScegliCuratoreDaModificare", method = RequestMethod.GET)
	public String goToPageScegliCuratoreDaModificare(Model model) {
		model.addAttribute("curatori", this.curatoreService.tutti());
		return "/admin/modificaCuratore.html";
	}

	// Apre la form per modificare il Curatore
	@RequestMapping(value="/admin/formModificaCuratore/{id}", method = RequestMethod.GET)
	public String goToPageFormModificaCuratore(@PathVariable("id") Long id, Model model) {
		model.addAttribute("curatore", this.curatoreService.curatorePerId(id));
		model.addAttribute("collezioni", this.collezioneService.tutti());
		return "/admin/modificaCuratoreForm.html";
	}

	// Apre la pagina per eliminare un Curatore
	@RequestMapping(value="/admin/paginaEliminaCuratore", method = RequestMethod.GET)
	public String goToPageEliminaCuratore(Model model) {
		model.addAttribute("curatori", this.curatoreService.tutti());
		return "/admin/eliminaCuratore.html";
	}

	// Elimina il Curatore dal DB
	@RequestMapping(value = "/admin/eliminaCuratore/{id}", method = RequestMethod.GET)
	public String deleteCuratore(@PathVariable("id") Long id, 
			Model model) {
		Curatore curatore = this.curatoreService.curatorePerId(id);
		for(Collezione collezione : curatore.getCollezioni()) {
			collezione.setCuratore(null);
		}
		curatore.getCollezioni().clear();
		this.curatoreService.inserisci(curatore);
		this.curatoreService.elimina(id);
		return "redirect:/admin/pannelloAmministratore";
	}
}
