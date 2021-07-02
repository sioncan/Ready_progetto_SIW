package com.ready.siw.spring.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ready.siw.spring.model.Credentials;
import com.ready.siw.spring.model.Lettore;
import com.ready.siw.spring.model.Libro;
import com.ready.siw.spring.service.CredentialsService;
import com.ready.siw.spring.service.LettoreService;

@Controller
public class LettoreController {
	
	@Autowired
	private LettoreService lettoreService;
	
	@Autowired
	private CredentialsService credentialsService;

	/* Va al all'Area utente caricando tutte le recensioni dell'utente */
	@RequestMapping(value="/areaUtente/{username}", method = RequestMethod.GET)
	public String goToPageAreaUtente(@PathVariable(value="username") String username, Model model) {
		model.addAttribute("lettore", this.lettoreService.lettorePerUsername(username));
		return "areaUtente.html";
	}
	
	/* Va al alla form per modificare i dati utente/lettore */
	@RequestMapping(value="/paginaModificaDatiLettore/{username}", method = RequestMethod.GET)
	public String goToPageModificaDatiLettore(@PathVariable(value="username") String username, Model model) {
		model.addAttribute("lettore", this.lettoreService.lettorePerUsername(username));
		model.addAttribute("credentials", this.credentialsService.getCredentials(username));
		return "modificaDatiLettore.html";
	}
	
	/* Salva il Lettore con i dati modificati nel DB */
	@RequestMapping(value="/modificaDatiLettore/{username}", method = RequestMethod.POST)
	public String saveModifiedLettore(@RequestParam("cognome") String cognome, @RequestParam("nome") String nome, @PathVariable(value="username") String username, Model model) {
		Lettore lettore = this.lettoreService.lettorePerUsername(username);
		lettore.setNome(nome);
		lettore.setCognome(cognome);
		this.lettoreService.inserisci(lettore);
		return "redirect:/ricercaLibri";
	}
	
	/* Va al alla form per selezionare l'immagine del utente/lettore */
	@RequestMapping(value="/paginaSelezionaImmagineLettore/{username}", method = RequestMethod.GET)
	public String goToPageSelezionaImmagineLettore(@PathVariable(value="username") String username, Model model) {
		model.addAttribute("lettore", this.lettoreService.lettorePerUsername(username));
		model.addAttribute("credentials", this.credentialsService.getCredentials(username));
		return "selezioneImmagineLettore.html";
	}
	
	// Inserisce l'immagine del Lettore nel DB
	@PostMapping("/inserisciImmagineLettore/{username}")
	public String saveImmagineLettore(@PathVariable(value="username") String username,
			@RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		Lettore lettore = this.lettoreService.lettorePerUsername(username);
		lettore.setImmagine(fileName);
		this.lettoreService.inserisci(lettore);
		String uploadDir = "./src/main/resources/static/images/";
		
		Path uploadPath = Paths.get(uploadDir);
		
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch(IOException e) {
			throw new IOException("Could not save uploaded fileImage: " + fileName);
		}
		return "redirect:/ricercaLibri";
	}
}
