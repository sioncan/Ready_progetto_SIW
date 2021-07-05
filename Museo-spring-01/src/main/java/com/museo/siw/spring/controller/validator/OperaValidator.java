package com.museo.siw.spring.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.museo.siw.spring.model.Opera;
import com.museo.siw.spring.service.OperaService;

@Component
public class OperaValidator implements Validator {
	
	@Autowired
	private OperaService operaService;
	
    final Integer MIN_TITOLO_LENGTH = 2;
    final Integer MAX_TITOLO_LENGTH = 20;
    final Integer MIN_DESCRIZIONE_LENGTH = 20;
    final Integer MAX_DESCRIZIONE_LENGTH = 500;
    private static final Logger logger = LoggerFactory.getLogger(OperaValidator.class);

	@Override
	public void validate(Object o, Errors errors) {
		Opera opera = (Opera)o;
		String titolo = opera.getTitolo().trim();
		String descrizione = opera.getDescrizione().trim();
		
        if (titolo.isEmpty())
            errors.rejectValue("titolo", "required");
        else if (titolo.length() < MIN_TITOLO_LENGTH || titolo.length() > MAX_TITOLO_LENGTH)
            errors.rejectValue("titolo", "size");

        if (descrizione.isEmpty())
            errors.rejectValue("descrizione", "required");
        else if (descrizione.length() < MIN_DESCRIZIONE_LENGTH || descrizione.length() > MAX_DESCRIZIONE_LENGTH)
            errors.rejectValue("descrizione", "size");
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Opera.class.equals(aClass);
	}

}
