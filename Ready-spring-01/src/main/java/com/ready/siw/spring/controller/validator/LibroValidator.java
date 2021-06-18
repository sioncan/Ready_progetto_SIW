package com.ready.siw.spring.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ready.siw.spring.model.Libro;
import com.ready.siw.spring.service.LibroService;

@Component
public class LibroValidator implements Validator {
	@Autowired
	private LibroService libroService;
	
    private static final Logger logger = LoggerFactory.getLogger(LibroValidator.class);

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titolo", "required");

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.libroService.alreadyExists((Libro)o)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Libro.class.equals(aClass);
	}

}
