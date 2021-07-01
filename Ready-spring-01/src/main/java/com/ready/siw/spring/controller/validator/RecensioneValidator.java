package com.ready.siw.spring.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ready.siw.spring.model.Recensione;
import com.ready.siw.spring.service.RecensioneService;


@Component
public class RecensioneValidator implements Validator {
	
    final Integer MAX_TITOLO_LENGTH = 20;
    final Integer MIN_TITOLO_LENGTH = 2;
    final Integer MAX_TESTO_LENGTH = 500;
    final Integer MIN_TESTO_LENGTH = 2;

	@Autowired
	private RecensioneService recensioneService;
	
    private static final Logger logger = LoggerFactory.getLogger(RecensioneValidator.class);
    
    // controlla che i campi dell'inserimento recensione non siano vuoti e rispettino le lunghezze (MIN, MAX)
	@Override
	public void validate(Object o, Errors errors) {
		Recensione recensione = (Recensione)o;
		String titolo = recensione.getTitolo().trim();
		String testo = recensione.getTesto().trim();
		int voto = recensione.getVoto();

        if (titolo.isEmpty())
            errors.rejectValue("titolo", "required");
        else if (titolo.length() < MIN_TITOLO_LENGTH || titolo.length() > MAX_TITOLO_LENGTH)
            errors.rejectValue("titolo", "size");

        if (testo.isEmpty())
            errors.rejectValue("testo", "required");
        else if (testo.length() < MIN_TESTO_LENGTH || testo.length() > MAX_TESTO_LENGTH)
            errors.rejectValue("testo", "size");
		
		if(voto == 0) {
            errors.rejectValue("voto", "required");
		}

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.recensioneService.alreadyExists((Recensione)o)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Recensione.class.equals(aClass);
	}
 
}
