package com.ready.siw.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ready.siw.spring.model.CasaEditrice;
import com.ready.siw.spring.service.CasaEditriceService;

@Controller
public class RicercaCaseEditriciController {
	
	@Autowired
	private CasaEditriceService casaEditriceService;
	
	// Ricerca la Casa Editrice dal DB per Nome
	@RequestMapping(value={"/cercaCasaEditricePerNome","/cercaCasaEditricePerNome{nomeCasaEditrice}"}, method = RequestMethod.GET)
	public String findByNome(@Param("nomeCasaEditrice") String nomeCasaEditrice, Model model) {
		if(nomeCasaEditrice.isEmpty()) {
			return "ricercaCaseEditrici";
		}
		else {
			List<CasaEditrice> caseEditrici = this.casaEditriceService.casaEditricePerNome(nomeCasaEditrice);
			model.addAttribute("caseEditrici", caseEditrici);
			model.addAttribute("nomeCasaEditrice", nomeCasaEditrice);
			return "ricercaCaseEditrici";
		}
	}
	
	// Ricerca la Casa Editrice dal DB per Nazionalità
	@RequestMapping(value={"/cercaCasaEditricePerNazionalita","/cercaCasaEditricePerNazionalita{nazionalita}"}, method = RequestMethod.GET)
	public String findByNazionalita(@Param("nazionalita") String nazionalita, Model model) {
		if(nazionalita.isEmpty()) {
			return "ricercaCaseEditrici";
		}
		else {
			List<CasaEditrice> caseEditrici = this.casaEditriceService.casaEditricePerNazionalita(nazionalita);
			model.addAttribute("caseEditrici", caseEditrici);
			model.addAttribute("nazionalita", nazionalita);
			return "ricercaCaseEditrici";
		}
	}

}
