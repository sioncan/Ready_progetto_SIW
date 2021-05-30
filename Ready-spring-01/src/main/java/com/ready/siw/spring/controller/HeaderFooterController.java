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
	
    @RequestMapping(value = "/paginaAutori")
    public String goToPageAutori() {
    		return "autore.html";
    }
    
    @RequestMapping(value="/paginaLibri")
    public String goToPageLibri() {
        return "index.html";
    }
    
    @RequestMapping(value="/paginaCasaEditrici")
    public String goToPageCasaEditrici() {
        return "casaEditrice.html";
    }
}
