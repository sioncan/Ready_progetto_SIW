package com.ready.siw.spring.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ready.siw.spring.model.Libro;
import com.ready.siw.spring.service.LibroService;

@Component
public class LibroValidator implements Validator {
	
	@Autowired
	private LibroService libroService;
	
    final Integer MIN_TITOLO_LENGTH = 2;
    final Integer MAX_TITOLO_LENGTH = 20;
    final Integer MIN_ISBN_LENGTH = 13;
    final Integer MAX_ISBN_LENGTH = 17;
    private static final Logger logger = LoggerFactory.getLogger(LibroValidator.class);

	@Override
	public void validate(Object o, Errors errors) {
		Libro libro = (Libro)o;
		String titolo = libro.getTitolo().trim();
		String isbn = libro.getIsbn().trim();
		
        if (titolo.isEmpty())
            errors.rejectValue("titolo", "required");
        else if (titolo.length() < MIN_TITOLO_LENGTH || titolo.length() > MAX_TITOLO_LENGTH)
            errors.rejectValue("titolo", "size");

        if (isbn.isEmpty())
            errors.rejectValue("isbn", "required");
        else if (isbn.length() < MIN_ISBN_LENGTH || isbn.length() > MAX_ISBN_LENGTH)
            errors.rejectValue("isbn", "size");
    
		
		/*if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.libroService.alreadyExists((Libro)o)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}*/
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Libro.class.equals(aClass);
	}

}
