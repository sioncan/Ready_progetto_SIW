package com.museo.siw.spring.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.museo.siw.spring.model.Artista;
import com.museo.siw.spring.model.Collezione;
import com.museo.siw.spring.model.Opera;
import com.museo.siw.spring.service.ArtistaService;
import com.museo.siw.spring.service.CollezioneService;
import com.museo.siw.spring.service.OperaService;

@Controller
public class PannelloController {

	@Autowired
	private CollezioneService collezioneService;
	
	@Autowired
	private OperaService operaService;
	
	@Autowired
	private ArtistaService artistaService;
	
	// Apre la pagina per inserire un'Opera in una Collezione
	@RequestMapping(value="/admin/paginaOperaCollezione", method = RequestMethod.GET)
	public String goToPageCollegaOperaCollezione(Model model) {
		model.addAttribute("opere", this.operaService.tutti());
		model.addAttribute("collezioni", this.collezioneService.tutti());
		return "/admin/collegaOperaCollezione.html";
	}
	
	// Apre la pagina per collegare un'Opera ad un Artista
	@RequestMapping(value="/admin/paginaCollegaOperaArtista", method = RequestMethod.GET)
	public String goToPageCollegaOperaArtista(Model model) {
		model.addAttribute("opere", this.operaService.tutti());
		model.addAttribute("artisti", this.artistaService.tutti());
		return "/admin/collegaOperaArtista.html";
	}

	// collega un Libro ad un Autore
	@RequestMapping(value="/admin/collegaOperaCollezione", method = RequestMethod.POST)
	public String makeRelationOperaCollezione(@Valid Long idOpera, @Valid Long idCollezione, Model model) {
		Collezione collezione = collezioneService.collezionePerId(idCollezione);
		Opera opera = operaService.operaPerId(idOpera);
		collezione.getOpere().add(opera);
		opera.getCollezioni().add(collezione);
		collezioneService.inserisci(collezione);
		operaService.inserisci(opera);
		return "redirect:/admin/pannelloAmministratore";
	}
	
	// collega un'Opera a un Artista
	@RequestMapping(value="/admin/collegaOperaArtista", method = RequestMethod.POST)
	public String makeRelationOperaArtista(@Valid Long idOpera, @Valid Long idArtista, Model model) {
		Opera opera = operaService.operaPerId(idOpera);
		Artista artista = artistaService.artistaPerId(idArtista);
		opera.setArtista(artista);
		artista.getOpere().add(opera);
		operaService.inserisci(opera);
		artistaService.inserisci(artista);
		return "redirect:/admin/pannelloAmministratore";
	}
}
