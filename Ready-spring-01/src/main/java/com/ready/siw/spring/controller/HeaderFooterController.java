package com.ready.siw.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HeaderFooterController {
	/* tasti del footer = Pannello Amministratore */

	/* Va al Pannello amministratore */
	@RequestMapping(value="/pannelloAmministratore")
	public String goToPagePannelloAmministratore() {
		return "/admin/pannello.html";
	}
}
