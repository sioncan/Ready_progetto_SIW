package com.ready.siw.spring.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ready.siw.spring.model.Lettore;

/**
 * Validator for User
 */
@Component
public class LettoreValidator implements Validator {

    final Integer MAX_NAME_LENGTH = 100;
    final Integer MIN_NAME_LENGTH = 2;

    // controlla che i campi nome e cognome non siano vuoti e che siano della giusta lunghezza (MAX_NAME_LENGTH, MIN_NAME_LENGTH)
    @Override
    public void validate(Object o, Errors errors) {
    	Lettore lettore = (Lettore) o;
        String nome = lettore.getNome().trim();
        String cognome = lettore.getCognome().trim();

        if (nome.isEmpty())
            errors.rejectValue("nome", "required");
        else if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("nome", "size");

        if (cognome.isEmpty())
            errors.rejectValue("cognome", "required");
        else if (cognome.length() < MIN_NAME_LENGTH || cognome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("cognome", "size");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Lettore.class.equals(clazz);
    }

}
