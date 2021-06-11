package com.ready.siw.spring.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ElencoAutoriController {
	
	/* apre la pagina dell'autore selezionato dall'elenco*/
    @RequestMapping(value = "/apriAutore")
    public String goToAutore() {
    		return "autore.html";
    }

}
