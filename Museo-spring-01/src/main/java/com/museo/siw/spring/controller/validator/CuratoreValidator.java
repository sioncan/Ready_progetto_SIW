package com.museo.siw.spring.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.museo.siw.spring.model.Curatore;
import com.museo.siw.spring.service.CuratoreService;

@Component
public class CuratoreValidator implements Validator {
	
	@Autowired
	private CuratoreService curatoreService;
	
    final Integer MIN_NOME_LENGTH = 2;
    final Integer MAX_NOME_LENGTH = 20;
    private static final Logger logger = LoggerFactory.getLogger(CuratoreValidator.class);

	@Override
	public void validate(Object o, Errors errors) {
		Curatore curatore = (Curatore)o;
		String nome = curatore.getNome().trim();
		String cognome = curatore.getCognome().trim();
		
		if (nome.isEmpty())
			errors.rejectValue("nome", "required");
		else if (nome.length() < MIN_NOME_LENGTH || nome.length() > MAX_NOME_LENGTH)
			errors.rejectValue("nome", "size");

		if (cognome.isEmpty())
			errors.rejectValue("cognome", "required");
		else if (cognome.length() < MIN_NOME_LENGTH || cognome.length() > MAX_NOME_LENGTH)
			errors.rejectValue("cognome", "size");
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Curatore.class.equals(aClass);
	}

}
