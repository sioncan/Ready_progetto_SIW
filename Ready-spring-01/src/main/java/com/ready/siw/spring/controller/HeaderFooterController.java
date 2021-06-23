package com.ready.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ready.siw.spring.service.AutoreService;
import com.ready.siw.spring.service.LibroService;


@Controller
public class HeaderFooterController {

	/* tasti del footer = Pannello Amministratore */

	/* Va al Pannello amministratore */
	@RequestMapping(value="/pannelloAmministratore")
	public String goToPagePannelloAmministratore() {
		return "/admin/pannello.html";
	}
}
