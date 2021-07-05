package com.museo.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HeaderFooterController {
	/* tasti dell'header = Musa, Collezioni, Autori, Informazioni */
	
	/* tasto Musa - va alla home/index */
	@RequestMapping(value="/sezioneInformazioni")
	public String goToPageSezioneInformazioni() {
		return "sezioneInformazioni.html";
	}
	
	/* Va al Pannello amministratore */
	@RequestMapping(value="/admin/pannelloAmministratore")
	public String goToPagePannelloAmministratore() {
		return "/admin/pannello.html";
	}
}
