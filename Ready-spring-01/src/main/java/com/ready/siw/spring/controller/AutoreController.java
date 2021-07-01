package com.ready.siw.spring.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ready.siw.spring.model.Autore;
import com.ready.siw.spring.model.Libro;
import com.ready.siw.spring.service.AutoreService;
import com.ready.siw.spring.service.LibroService;

@Controller
public class AutoreController {

	@Autowired
	private AutoreService autoreService;
	
	@Autowired
	private LibroService libroService;
	
	/* Va alla pagine dell'Autore selezionato dall'elenco */
	@RequestMapping(value="/autore/{id}", method = RequestMethod.GET)
	public String goToPageAutore(@PathVariable("id") Long id, Model model) {
		model.addAttribute("autore", this.autoreService.autorePerId(id));
		return "autore.html";
	}

	/* Va alla pagina di ricerca Autori prendendoli dal DB */
	@RequestMapping(value = "/ricercaAutori", method = RequestMethod.GET)
	public String goToPageRicercaAutori(Model model) {
		model.addAttribute("autori", this.autoreService.tutti());
		return "ricercaAutori.html";
	}

	// Apre la pagina per inserire un Autore creando un nuovo oggetto Autore
	@RequestMapping(value="/paginaInserisciAutore", method = RequestMethod.GET)
	public String goToPageInserisciAutore(Model model) {
		model.addAttribute("autore", new Autore());
		model.addAttribute("libri", this.libroService.tutti());
		return "/admin/inserisciAutore.html";
	}

//	// Inserisce l'Autore appena creato nel DB
//	@RequestMapping(value = "/inserisciAutore", method = RequestMethod.POST)
//	public String saveAutore(@ModelAttribute("autore") Autore autore, 
//			Model model, BindingResult bindingResult) {
//		this.autoreService.inserisci(autore);
//		return "/admin/pannello.html";
//	}
	
	// Inserisce l'Autore appena creato nel DB
	@PostMapping("/inserisciAutore")
	public String saveAutore(@ModelAttribute("autore") Autore autore,
			@Valid String isbnLibro, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		autore.setImmagine(fileName);
		Libro libro = this.libroService.libroPerIsbn(isbnLibro);
		autore.setLibri(new ArrayList<Libro>());
		autore.getLibri().add(libro);
		this.libroService.inserisci(libro);
		this.autoreService.inserisci(autore);
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
		return "redirect:/ricercaAutori";
	}

	// Apre la pagina per selezionare un Autore da modificare
	@RequestMapping(value="/paginaScegliAutoreDaModificare", method = RequestMethod.GET)
	public String goToPageModificaAutore(Model model) {
		model.addAttribute("autori", this.autoreService.tutti());
		return "/admin/modificaAutore.html";
	}

	// Apre la form per modificare l'Autore
	@RequestMapping(value="/formModificaAutore/{id}", method = RequestMethod.GET)
	public String goToPageFormModificaAutore(@PathVariable("id") Long id, Model model) {
		model.addAttribute("autore", this.autoreService.autorePerId(id));
		return "/admin/inserisciAutore.html";
	}

	// Apre la pagina per eliminare un Autore
	@RequestMapping(value="/paginaEliminaAutore", method = RequestMethod.GET)
	public String goToPageEliminaAutore(Model model) {
		model.addAttribute("autori", this.autoreService.tutti());
		return "/admin/eliminaAutore.html";
	}

	// Elimina l'Autore dal DB
	@RequestMapping(value = "/eliminaAutore/{id}", method = RequestMethod.GET)
	public String deleteAutore(@PathVariable("id") Long id, 
			Model model) {
		Autore autore = this.autoreService.autorePerId(id);
		for (Libro libro : autore.getLibri()) {
			libro.getAutori().remove(autore);
		}
		autore.getLibri().clear();
		this.autoreService.inserisci(autore);
		this.autoreService.elimina(id);
		return "redirect:/pannelloAmministratore";
	}

}
