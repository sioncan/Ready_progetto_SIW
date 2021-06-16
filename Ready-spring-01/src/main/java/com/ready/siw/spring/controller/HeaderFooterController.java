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


@Controller
public class HeaderFooterController {
	/* tasti dell'header = Home, Login, Register, Libri, Autori, Case editrici */
	
	/* tasto Libri - va alla pagina dell'elenco dei libri */
    @RequestMapping(value="/paginaElencoLibri")
    public String goToPageLibri() {
        return "index.html";
    }
	
	/* tasto Autori - va alla pagina dell'elenco degli autori */
    @RequestMapping(value = "/paginaElencoAutori")
    public String goToPageAutori() {
    		return "elencoAutori.html";
    }
    
	/* tasto Case editrici - va alla pagina dell'elenco delle case editrici */
    @RequestMapping(value="/paginaElencoCaseEditrici")
    public String goToPageCasaEditrici() {
        return "elencoCaseEditrici.html";
    }
}
