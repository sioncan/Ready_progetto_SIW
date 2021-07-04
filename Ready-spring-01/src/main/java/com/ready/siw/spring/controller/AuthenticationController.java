package com.ready.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ready.siw.spring.controller.validator.CredentialsValidator;
import com.ready.siw.spring.controller.validator.LettoreValidator;
import com.ready.siw.spring.model.Credentials;
import com.ready.siw.spring.model.Lettore;
import com.ready.siw.spring.service.CredentialsService;

@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private LettoreValidator lettoreValidator;
	
	@Autowired
	private CredentialsValidator credentialsValidator;
	
	// apre la pagine per registrarsi e crea 2 nuovi oggetti (Lettore, Credentials)
	@RequestMapping(value = "/register", method = RequestMethod.GET) 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new Lettore());
		model.addAttribute("credentials", new Credentials());
		return "registraLettore.html";
	}
	
	// apre la pagina per loggare
	@RequestMapping(value = "/login", method = RequestMethod.GET) 
	public String showLoginForm (Model model) {
		return "loginForm.html";
	}
	
	// Mostra il fallimento del login per dati errati
	@RequestMapping(value = "/loginFail", method = RequestMethod.GET) 
	public String showLoginFormErrors (Model model) {
		model.addAttribute("logErr", true);
		return "loginForm.html";
	}
	
	// effettua il logout
	@RequestMapping(value = "/logout", method = RequestMethod.GET) 
	public String logout(Model model) {
		SecurityContextHolder.getContext().setAuthentication(null);
		return "redirect:/ricercaLibri";
	}
	
	// controlla il ruolo dell'utente e apre la pagina di conseguenza
    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
    		return "admin/pannello";
        }
        return "index.html";
    }
	
    // conferma la registrazione salvando nel DB il Lettore e le sue Credentials
    @RequestMapping(value = { "/register" }, method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") Lettore lettore,
                 BindingResult userBindingResult,
                 @ModelAttribute("credentials") Credentials credentials,
                 BindingResult credentialsBindingResult,
                 Model model) {

        // validate user and credentials fields
        this.lettoreValidator.validate(lettore, userBindingResult);
        this.credentialsValidator.validate(credentials, credentialsBindingResult);

        // if neither of them had invalid contents, store the User and the Credentials into the DB
        if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
            // set the user and store the credentials;
            // this also stores the User, thanks to Cascade.ALL policy
            credentials.setLettore(lettore);
            credentialsService.saveCredentials(credentials);
            return "registrazioneSuccesso.html";
        }
        return "registraLettore.html";
    }
}