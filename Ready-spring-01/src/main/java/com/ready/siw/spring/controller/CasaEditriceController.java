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

import com.ready.siw.spring.controller.validator.CasaEditriceValidator;
import com.ready.siw.spring.model.CasaEditrice;
import com.ready.siw.spring.model.Libro;
import com.ready.siw.spring.service.CasaEditriceService;
import com.ready.siw.spring.service.LibroService;

@Controller
public class CasaEditriceController {

	@Autowired
	private CasaEditriceValidator casaEditriceValidator;

	@Autowired
	private CasaEditriceService casaEditriceService;

	@Autowired
	private LibroService libroService;

	/* Va alla pagine della CasaEditrice selezionato dall'elenco */
	@RequestMapping(value="/casaEditrice/{id}", method = RequestMethod.GET)
	public String goToPageCasaEditrice(@PathVariable("id") Long id, Model model) {
		model.addAttribute("casaEditrice", this.casaEditriceService.casaEditricePerId(id));
		return "casaEditrice.html";
	}

	/* Va alla pagina di ricerca CaseEditrici */
	@RequestMapping(value="/ricercaCaseEditrici", method = RequestMethod.GET)
	public String goToPageRicercaCaseEditrici(Model model) {
		model.addAttribute("caseEditrici", this.casaEditriceService.tutti());
		return "ricercaCaseEditrici.html";
	}

	// Apre la pagina per inserire una CasaEditrice creando un nuovo oggetto CasaEditrice
	@RequestMapping(value="/admin/paginaInserisciCasaEditrice", method = RequestMethod.GET)
	public String goToPageInserisciCasaEditrice(Model model) {
		model.addAttribute("casaEditrice", new CasaEditrice());
		model.addAttribute("libri", this.libroService.tutti());
		return "/admin/inserisciCasaEditrice.html";
	}

	// Inserisce il Libro appena creato nel DB
	@RequestMapping(value="/admin/inserisciCasaEditrice", method = RequestMethod.POST)
	public String saveCasaEditrice(@ModelAttribute("casaEditrice") CasaEditrice casaEditrice,
			 @RequestParam("fileImage") MultipartFile multipartFile, BindingResult casaEditriceBindingResult) throws IOException {
		this.casaEditriceValidator.validate(casaEditrice, casaEditriceBindingResult);
		if(!casaEditriceBindingResult.hasErrors()) {
			String fileName = null;
			if(!multipartFile.isEmpty()) {
				fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				casaEditrice.setLogo(fileName);
			}
			this.casaEditriceService.inserisci(casaEditrice);
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
			return "redirect:/ricercaCaseEditrici";
		} else
			return "/admin/inserisciCasaEditrice.html";
	}
	
	// Inserisce il Libro appena creato nel DB
	@RequestMapping(value="/admin/inserisciCasaEditriceModificata", method = RequestMethod.POST)
	public String saveCasaEditriceModificata(@ModelAttribute("casaEditrice") CasaEditrice casaEditrice, @Valid String isbnLibro,
			@RequestParam(value="oldFileName", required = false) String oldFileName, @RequestParam("fileImage") MultipartFile multipartFile, BindingResult casaEditriceBindingResult) throws IOException {
		this.casaEditriceValidator.validate(casaEditrice, casaEditriceBindingResult);
		if(!casaEditriceBindingResult.hasErrors()) {
			String fileName = null;
			if(!multipartFile.isEmpty()) {
				fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				casaEditrice.setLogo(fileName);
			} else {
				casaEditrice.setLogo(oldFileName);
			}
			if(isbnLibro != null && !isbnLibro.equals("0")) {
				if(casaEditrice.getLibri() == null) {
					casaEditrice.setLibri(new ArrayList<Libro>());
				}
				Libro libro = this.libroService.libroPerIsbn(isbnLibro);
				for(Libro l : this.casaEditriceService.casaEditricePerId(casaEditrice.getId()).getLibri()) {
					l.setCasaEditrice(null);
				}
				libro.setCasaEditrice(casaEditrice);
				this.libroService.inserisci(libro);
			} else {
				casaEditrice.setLibri(new ArrayList<Libro>());
				for(Libro l : this.casaEditriceService.casaEditricePerId(casaEditrice.getId()).getLibri()) {
					casaEditrice.getLibri().add(l);
				}
			}
			this.casaEditriceService.inserisci(casaEditrice);
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
			return "redirect:/ricercaCaseEditrici";
		} else
			return "/admin/modificaCasaEditriceForm.html";
	}

	// Apre la pagina per selezionare una CasaEditrice da modificare
	@RequestMapping(value="/admin/paginaScegliCasaEditriceDaModificare", method = RequestMethod.GET)
	public String goToPageModificaCasaEditrice(Model model) {
		model.addAttribute("caseEditrici", this.casaEditriceService.tutti());
		return "/admin/modificaCasaEditrice.html";
	}

	// Apre la form per modificare una CasaEditrice
	@RequestMapping(value="/admin/formModificaCasaEditrice/{id}", method = RequestMethod.GET)
	public String goToPageFormModificaCasaEditrice(@PathVariable("id") Long id, Model model) {
		model.addAttribute("casaEditrice", this.casaEditriceService.casaEditricePerId(id));
		model.addAttribute("libri", this.libroService.tutti());
		return "/admin/modificaCasaEditriceForm.html";
	}

	// Apre la pagina per eliminare una CasaEditrice
	@RequestMapping(value="/admin/paginaEliminaCasaEditrice", method = RequestMethod.GET)
	public String goToPageEliminaCasaEditrice(Model model) {
		model.addAttribute("caseEditrici", this.casaEditriceService.tutti());
		return "/admin/eliminaCasaEditrice.html";
	}

	// Elimina la CasaEditrice dal DB
	@RequestMapping(value = "/admin/eliminaCasaEditrice/{id}", method = RequestMethod.GET)
	public String deleteCasaEditrice(@PathVariable("id") Long id, 
			Model model) {
		CasaEditrice casaEditrice = this.casaEditriceService.casaEditricePerId(id);
		for (Libro libro : casaEditrice.getLibri()) {
			libro.setCasaEditrice(null);
		}
		casaEditrice.getLibri().clear();
		this.casaEditriceService.inserisci(casaEditrice);
		this.casaEditriceService.elimina(id);
		return "redirect:/admin/pannelloAmministratore";
	}

}