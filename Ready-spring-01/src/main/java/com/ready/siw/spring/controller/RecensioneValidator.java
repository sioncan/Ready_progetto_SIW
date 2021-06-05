package com.ready.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ready.siw.spring.model.Recensione;
import com.ready.siw.spring.service.RecensioneService;


@Component
public class RecensioneValidator implements Validator {

	@Autowired
	private RecensioneService recensioneService;
	
    private static final Logger logger = LoggerFactory.getLogger(RecensioneValidator.class);
    
	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titolo", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "testo", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "voto", "required");

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.recensioneService.alreadyExists((Recensione)o)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Recensione.class.equals(aClass);
	}
 
}
