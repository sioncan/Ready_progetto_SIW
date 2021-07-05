package com.museo.siw.spring.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.museo.siw.spring.model.Collezione;
import com.museo.siw.spring.service.CollezioneService;

@Component
public class CollezioneValidator implements Validator {
	
	@Autowired
	private CollezioneService collezioneService;

	final Integer MIN_NOME_LENGTH = 2;
	final Integer MAX_NOME_LENGTH = 20;
	final Integer MIN_DESCRIZIONE_LENGTH = 20;
	final Integer MAX_DESCRIZIONE_LENGTH = 500;
	private static final Logger logger = LoggerFactory.getLogger(CollezioneValidator.class);

	@Override
	public void validate(Object o, Errors errors) {
		Collezione collezione = (Collezione)o;
		String nome = collezione.getNome().trim();
		String descrizione = collezione.getDescrizione().trim();

		if (nome.isEmpty())
			errors.rejectValue("nome", "required");
		else if (nome.length() < MIN_NOME_LENGTH || nome.length() > MAX_NOME_LENGTH)
			errors.rejectValue("nome", "size");

		if (descrizione.isEmpty())
			errors.rejectValue("descrizione", "required");
		else if (descrizione.length() < MIN_DESCRIZIONE_LENGTH || descrizione.length() > MAX_DESCRIZIONE_LENGTH)
			errors.rejectValue("descrizione", "size");
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Collezione.class.equals(aClass);
	}

}
