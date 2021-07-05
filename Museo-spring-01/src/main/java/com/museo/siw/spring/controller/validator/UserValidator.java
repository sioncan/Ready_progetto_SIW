package com.museo.siw.spring.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.museo.siw.spring.model.User;

@Component
public class UserValidator implements Validator {

    final Integer MIN_NAME_LENGTH = 2;
    final Integer MAX_NAME_LENGTH = 20;

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        String nome = user.getNome().trim();
        String cognome = user.getCognome().trim();

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
        return User.class.equals(clazz);
    }

}

