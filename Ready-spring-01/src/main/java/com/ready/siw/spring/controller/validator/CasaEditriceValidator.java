package com.ready.siw.spring.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ready.siw.spring.model.CasaEditrice;
import com.ready.siw.spring.service.CasaEditriceService;

@Component
public class CasaEditriceValidator implements Validator {
	
	@Autowired
	private CasaEditriceService casaEditriceService;
	
    final Integer MIN_NOME_LENGTH = 2;
    final Integer MAX_NOME_LENGTH = 20;
    final Integer MIN_NAZIONALITA_LENGTH = 2;
    final Integer MAX_NAZIONALITA_LENGTH = 20;
    final Integer MIN_SEDE_LENGTH = 2;
    final Integer MAX_SEDE_LENGTH = 20;
    final Integer MIN_WEBSITE_LENGTH = 2;
    final Integer MAX_WEBSITE_LENGTH = 20;
    private static final Logger logger = LoggerFactory.getLogger(AutoreValidator.class);

	@Override
	public void validate(Object o, Errors errors) {
		CasaEditrice casaEditrice = (CasaEditrice)o;
		String nome = casaEditrice.getNome().trim();
		String nazionalita = casaEditrice.getNazionalita().trim();
		String sede = casaEditrice.getSede().trim();
		String website = casaEditrice.getWebsite().trim();
		
        if (nome.isEmpty())
            errors.rejectValue("nome", "required");
        else if (nome.length() < MIN_NOME_LENGTH || nome.length() > MAX_NOME_LENGTH)
            errors.rejectValue("nome", "size");
        
        if (nazionalita.isEmpty())
            errors.rejectValue("nazionalita", "required");
        else if (nazionalita.length() < MIN_NAZIONALITA_LENGTH || nazionalita.length() > MAX_NAZIONALITA_LENGTH)
            errors.rejectValue("nazionalita", "size");

        if (sede.isEmpty())
            errors.rejectValue("sede", "required");
        else if (sede.length() < MIN_SEDE_LENGTH || sede.length() > MAX_SEDE_LENGTH)
            errors.rejectValue("sede", "size");
        
        if (website.isEmpty())
            errors.rejectValue("website", "required");
        else if (website.length() < MIN_WEBSITE_LENGTH || website.length() > MAX_WEBSITE_LENGTH)
            errors.rejectValue("website", "size");
		
		/*if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.casaEditriceService.alreadyExists((CasaEditrice)o)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}*/
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return CasaEditrice.class.equals(aClass);
	}

}
