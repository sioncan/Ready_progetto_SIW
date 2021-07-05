package com.ready.siw.spring.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ready.siw.spring.controller.validator.AutoreValidator;
import com.ready.siw.spring.model.Autore;
import com.ready.siw.spring.model.Libro;
import com.ready.siw.spring.service.AutoreService;
import com.ready.siw.spring.service.LibroService;

@Controller
public class AutoreController {

	@Autowired
	private AutoreValidator autoreValidator;

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
	@RequestMapping(value="/admin/paginaInserisciAutore", method = RequestMethod.GET)
	public String goToPageInserisciAutore(Model model) {
		model.addAttribute("autore", new Autore());
		model.addAttribute("libri", this.libroService.tutti());
		return "/admin/inserisciAutore.html";
	}

	// Inserisce l'Autore appena creato nel DB
	@RequestMapping(value="/admin/inserisciAutore", method = RequestMethod.POST)
	public String saveAutore(@ModelAttribute("autore") Autore autore, 
			@RequestParam("fileImage") MultipartFile multipartFile, BindingResult autoreBindingResult) throws IOException {
		this.autoreValidator.validate(autore, autoreBindingResult);
		if(!autoreBindingResult.hasErrors()) {
			String fileName = null;
			if(!multipartFile.isEmpty()) {
				fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				autore.setImmagine(fileName);
			} 
			this.autoreService.inserisci(autore);
			if(!multipartFile.isEmpty()) {
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
			}
			return "redirect:/ricercaAutori";
		} else
			return "/admin/inserisciAutore.html";
	}

	// Inserisce l'Autore appena modificato nel DB
	@RequestMapping(value="/admin/inserisciAutoreModificato", method = RequestMethod.POST)
	public String saveAutoreModificato(@ModelAttribute("autore") Autore autore,  
			@Valid String isbnLibro, @RequestParam(value="oldFileName", required = false) String oldFileName,
			@RequestParam("fileImage") MultipartFile multipartFile, BindingResult autoreBindingResult) throws IOException {
		this.autoreValidator.validate(autore, autoreBindingResult);
		if(!autoreBindingResult.hasErrors()) {
			String fileName = null;
			if(!multipartFile.isEmpty()) {
				fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				autore.setImmagine(fileName);
			} else {
				autore.setImmagine(oldFileName);
			}
			if(isbnLibro != null && !isbnLibro.equals("0")) {
				if(autore.getLibri() == null) {
					autore.setLibri(new ArrayList<Libro>());
				}
				Libro libro = this.libroService.libroPerIsbn(isbnLibro);
				autore.getLibri().add(libro);
				this.libroService.inserisci(libro);
			} else {
				autore.setLibri(new ArrayList<Libro>());
				for(Libro l : this.autoreService.autorePerId(autore.getId()).getLibri()) {
					autore.getLibri().add(l);
				}
			}
			this.autoreService.inserisci(autore);
			if(!multipartFile.isEmpty()) {
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
			}
			return "redirect:/ricercaAutori";
		} else
			return "/admin/modificaAutoreForm.html";
	}

	// Apre la pagina per selezionare un Autore da modificare
	@RequestMapping(value="/admin/paginaScegliAutoreDaModificare", method = RequestMethod.GET)
	public String goToPageModificaAutore(Model model) {
		model.addAttribute("autori", this.autoreService.tutti());
		return "/admin/modificaAutore.html";
	}

	// Apre la form per modificare l'Autore
	@RequestMapping(value="/admin/formModificaAutore/{id}", method = RequestMethod.GET)
	public String goToPageFormModificaAutore(@PathVariable("id") Long id, Model model) {
		model.addAttribute("autore", this.autoreService.autorePerId(id));
		model.addAttribute("libri", this.libroService.tutti());
		return "/admin/modificaAutoreForm.html";
	}

	// Apre la pagina per eliminare un Autore
	@RequestMapping(value="/admin/paginaEliminaAutore", method = RequestMethod.GET)
	public String goToPageEliminaAutore(Model model) {
		model.addAttribute("autori", this.autoreService.tutti());
		return "/admin/eliminaAutore.html";
	}

	// Elimina l'Autore dal DB
	@RequestMapping(value = "/admin/eliminaAutore/{id}", method = RequestMethod.GET)
	public String deleteAutore(@PathVariable("id") Long id, 
			Model model) {
		Autore autore = this.autoreService.autorePerId(id);
		for (Libro libro : autore.getLibri()) {
			libro.getAutori().remove(autore);
		}
		autore.getLibri().clear();
		this.autoreService.inserisci(autore);
		this.autoreService.elimina(id);
		return "redirect:/admin/pannelloAmministratore";
	}

}