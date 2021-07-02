package com.ready.siw.spring.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ready.siw.spring.model.Autore;
import com.ready.siw.spring.service.AutoreService;

@Component
public class AutoreValidator implements Validator {
	
	@Autowired
	private AutoreService autoreService;
	
    final Integer MIN_NOMECOMPLETO_LENGTH = 2;
    final Integer MAX_NOMECOMPLETO_LENGTH = 40;
    final Integer MIN_LUOGONASCITA_LENGTH = 2;
    final Integer MAX_LUOGONASCITA_LENGTH = 20;
    final Integer MIN_NAZIONALITA_LENGTH = 2;
    final Integer MAX_NAZIONALITA_LENGTH = 20;
    private static final Logger logger = LoggerFactory.getLogger(AutoreValidator.class);

	@Override
	public void validate(Object o, Errors errors) {
		Autore autore = (Autore)o;
		String nomeCognome = autore.getNomeCognome().trim();
		String luogoNascita = autore.getLuogoNascita().trim();
		String nazionalita = autore.getNazionalita().trim();
		
        if (nomeCognome.isEmpty())
            errors.rejectValue("nomeCognome", "required");
        else if (nomeCognome.length() < MIN_NOMECOMPLETO_LENGTH || nomeCognome.length() > MAX_NOMECOMPLETO_LENGTH)
            errors.rejectValue("nomeCognome", "size");

        if (luogoNascita.isEmpty())
            errors.rejectValue("luogoNascita", "required");
        else if (luogoNascita.length() < MIN_LUOGONASCITA_LENGTH || luogoNascita.length() > MAX_LUOGONASCITA_LENGTH)
            errors.rejectValue("luogoNascita", "size");
        
        if (nazionalita.isEmpty())
            errors.rejectValue("nazionalita", "required");
        else if (nazionalita.length() < MIN_NAZIONALITA_LENGTH || nazionalita.length() > MAX_NAZIONALITA_LENGTH)
            errors.rejectValue("nazionalita", "size");
		
		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.autoreService.alreadyExists((Autore)o)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Autore.class.equals(aClass);
	}

}