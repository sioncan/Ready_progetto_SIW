package com.museo.siw.spring.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.museo.siw.spring.model.Artista;
import com.museo.siw.spring.service.ArtistaService;

@Component
public class ArtistaValidator implements Validator {

	@Autowired
	private ArtistaService artistaService;

	final Integer MIN_NOME_LENGTH = 2;
	final Integer MAX_NOME_LENGTH = 20;
	final Integer MIN_LUOGONASCITA_LENGTH = 2;
	final Integer MAX_LUOGONASCITA_LENGTH = 20;
	final Integer MIN_NAZIONALITA_LENGTH = 2;
	final Integer MAX_NAZIONALITA_LENGTH = 20;
	private static final Logger logger = LoggerFactory.getLogger(ArtistaValidator.class);

	@Override
	public void validate(Object o, Errors errors) {
		Artista artista = (Artista)o;
		String nome = artista.getNome().trim();
		String cognome = artista.getCognome().trim();
		String luogoNascita = artista.getLuogoNascita().trim();
		String nazionalita = artista.getNazionalita().trim();

		if (nome.isEmpty())
			errors.rejectValue("nome", "required");
		else if (nome.length() < MIN_NOME_LENGTH || nome.length() > MAX_NOME_LENGTH)
			errors.rejectValue("nome", "size");

		if (cognome.isEmpty())
			errors.rejectValue("cognome", "required");
		else if (cognome.length() < MIN_NOME_LENGTH || cognome.length() > MAX_NOME_LENGTH)
			errors.rejectValue("cognome", "size");

		if (luogoNascita.isEmpty())
			errors.rejectValue("luogoNascita", "required");
		else if (luogoNascita.length() < MIN_LUOGONASCITA_LENGTH || luogoNascita.length() > MAX_LUOGONASCITA_LENGTH)
			errors.rejectValue("luogoNascita", "size");

		if (nazionalita.isEmpty())
			errors.rejectValue("nazionalita", "required");
		else if (nazionalita.length() < MIN_NAZIONALITA_LENGTH || nazionalita.length() > MAX_NAZIONALITA_LENGTH)
			errors.rejectValue("nazionalita", "size");
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Artista.class.equals(aClass);
	}

}
