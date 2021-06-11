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

import com.ready.siw.spring.controller.validator.LibroValidator;
import com.ready.siw.spring.service.LibroService;

@Controller
public class LibroController {
	
//	@Autowired
//	private LibroService libroService;
//	
//    @Autowired
//    private LibroValidator libroValidator;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
}
