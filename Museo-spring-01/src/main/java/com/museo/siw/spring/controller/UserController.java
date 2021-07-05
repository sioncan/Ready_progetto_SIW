package com.museo.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.museo.siw.spring.controller.validator.UserValidator;
import com.museo.siw.spring.service.UserService;
import com.museo.siw.spring.model.User;
import com.museo.siw.spring.service.CredentialsService;

@Controller
public class UserController {

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private UserService userService;

	@Autowired
	private CredentialsService credentialsService;

	private String getUserLoggato() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	/* Va all'Area utente  */
	@RequestMapping(value="/areaUtente/{username}", method = RequestMethod.GET)
	public String goToPageAreaUtente(@PathVariable(value="username") String username, Model model) {
		if(!this.getUserLoggato().equals(username)){
			return "error";
		} else {
			model.addAttribute("user", this.userService.userPerUsername(username));
			return "areaUtente.html";
		}
	}

	/* Va alla form per modificare i dati dell'user */
	@RequestMapping(value="/paginaModificaDatiUser/{username}", method = RequestMethod.GET)
	public String goToPageModificaDatiUser(@PathVariable(value="username") String username, Model model) {
		if(!this.getUserLoggato().equals(username)){
			return "error";
		} else {
			model.addAttribute("user", this.userService.userPerUsername(username));
			model.addAttribute("credentials", this.credentialsService.getCredentials(username));
			return "modificaDatiUser.html";
		}
	}

	/* Salva l'User con i dati modificati nel DB */
	@RequestMapping(value="/modificaDatiUser", method = RequestMethod.POST)
	public String saveModifiedUser(@ModelAttribute("user") User user, 
			BindingResult userBindingResult, Model model) {
		this.userValidator.validate(user, userBindingResult);
		if(!userBindingResult.hasErrors()) {
			this.userService.saveUser(user);
			return "redirect:/areaUtente/"+ this.getUserLoggato();
		} else
			return "modificaDatiUsere.html";
	}

	/* Va al alla form per selezionare l'immagine utente */
	@RequestMapping(value="/paginaSelezionaImmagineUser/{username}", method = RequestMethod.GET)
	public String goToPageSelezionaImmagineUser(@PathVariable(value="username") String username, Model model) {
		if(!this.getUserLoggato().equals(username)){
			return "error";
		} else {
			model.addAttribute("user", this.userService.userPerUsername(username));
			model.addAttribute("credentials", this.credentialsService.getCredentials(username));
			return "selezioneImmagineUser.html";
		}
	}
}
