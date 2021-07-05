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

import com.ready.siw.spring.controller.validator.LibroValidator;
import com.ready.siw.spring.model.Autore;
import com.ready.siw.spring.model.CasaEditrice;
import com.ready.siw.spring.model.Libro;
import com.ready.siw.spring.model.Recensione;
import com.ready.siw.spring.service.AutoreService;
import com.ready.siw.spring.service.CasaEditriceService;
import com.ready.siw.spring.service.LibroService;

@Controller
public class LibroController {

	@Autowired
	private LibroValidator libroValidator;

	@Autowired
	private LibroService libroService;

	@Autowired
	private CasaEditriceService casaEditriceService;

	@Autowired
	private AutoreService autoreService;

	/* Va alla pagine del Libro selezionato dall'elenco */
	@RequestMapping(value="/libro/{isbn}", method = RequestMethod.GET)
	public String goToPageLibro(@PathVariable("isbn") String isbn, Model model) {
		Libro libro = this.libroService.libroPerIsbn(isbn);
		int votoMedio = libro.calcolaVotoMedio();
		model.addAttribute("libro", libro);
		model.addAttribute("votoMedio", votoMedio);
		return "libro.html";
	}

	/* Va alla pagina di ricerca Libri prendendoli dal DB */
	@RequestMapping(value= {"/", "/ricercaLibri"}, method = RequestMethod.GET)
	public String goToPageRicercaLibri(Model model) {
		model.addAttribute("libri", this.libroService.tutti());
		return "index.html";
	}

	// Apre la pagina per inserire un Libro creando un nuovo oggetto Libro
	@RequestMapping(value="/admin/paginaInserisciLibro", method = RequestMethod.GET)
	public String goToPageInserisciLibro(Model model) {
		model.addAttribute("libro", new Libro());
		model.addAttribute("caseEditrici", this.casaEditriceService.tutti());
		model.addAttribute("autori", this.autoreService.tutti());
		return "/admin/inserisciLibro.html";
	}

	// Inserisce il Libro appena creato nel DB
	@RequestMapping(value= "/admin/inserisciLibro", method = RequestMethod.POST)
	public String saveLibro(@ModelAttribute("libro") Libro libro,   
			@RequestParam(value="fileImage", required = false) MultipartFile multipartFile, BindingResult libroBindingResult) throws IOException {
		this.libroValidator.validate(libro, libroBindingResult);
		if (!libroBindingResult.hasErrors()) {
			String fileName = null;
			if(!multipartFile.isEmpty()) {
				fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				libro.setCopertina(fileName);
			}
			this.libroService.inserisci(libro);
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
			return "redirect:/ricercaLibri";
		} else
			return "/admin/inserisciLibro.html";
	}

	// Inserisce il Libro appena creato nel DB
	@RequestMapping(value= "/admin/inserisciLibroModificato", method = RequestMethod.POST)
	public String saveLibroModificato(@ModelAttribute("libro") Libro libro,  @Valid Long idCasaEditrice, 
			@Valid Long idAutore, @RequestParam(value="oldFileName", required = false) String oldFileName, 
			@RequestParam(value="fileImage", required = false) MultipartFile multipartFile, BindingResult libroBindingResult) throws IOException {
		this.libroValidator.validate(libro, libroBindingResult);
		if (!libroBindingResult.hasErrors()) {
			String fileName = null;
			if(!multipartFile.isEmpty()) {
				fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				libro.setCopertina(fileName);
			} else {
				libro.setCopertina(oldFileName);
			}
			if(idCasaEditrice != null && idCasaEditrice != 0) {
				CasaEditrice casaEditrice = this.casaEditriceService.casaEditricePerId(idCasaEditrice);
				libro.setCasaEditrice(casaEditrice);
				this.casaEditriceService.inserisci(casaEditrice);
			} else {
				libro.setCasaEditrice(this.libroService.libroPerIsbn(libro.getIsbn()).getCasaEditrice());
			}
			if(idAutore != null && idAutore != 0) {
                Autore autore = this.autoreService.autorePerId(idAutore);
                if(autore.getLibri() == null) {
                    autore.setLibri(new ArrayList<Libro>());
                }
                autore.getLibri().add(libro);
                this.autoreService.inserisci(autore);
            }
			this.libroService.inserisci(libro);
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
			return "redirect:/ricercaLibri";
		} else
			return "/admin/modificaLibroForm.html";
	}

	// Apre la pagina per selezionare un Libro da modificare
	@RequestMapping(value="/admin/paginaScegliLibroDaModificare", method = RequestMethod.GET)
	public String goToPageModificaLibro(Model model) {
		model.addAttribute("libri", this.libroService.tutti());
		return "/admin/modificaLibro.html";
	}

	// Apre la form per modificare il Libro
	@RequestMapping(value="/admin/formModificaLibro/{isbn}", method = RequestMethod.GET)
	public String goToPageFormModificaLibro(@PathVariable("isbn") String isbn, Model model) {
		model.addAttribute("libro", this.libroService.libroPerIsbn(isbn));
		model.addAttribute("caseEditrici", this.casaEditriceService.tutti());
		model.addAttribute("autori", this.autoreService.tutti());
		return "/admin/modificaLibroForm.html";
	}

	// Apre la pagina per eliminare un Libro
	@RequestMapping(value="/admin/paginaEliminaLibro", method = RequestMethod.GET)
	public String goToPageEliminaLibro(Model model) {
		model.addAttribute("libri", this.libroService.tutti());
		return "/admin/eliminaLibro.html";
	}

	// Elimina il Libro dal DB
	@RequestMapping(value = "/admin/eliminaLibro/{isbn}", method = RequestMethod.GET)
	public String deleteLibro(@PathVariable("isbn") String isbn, 
			Model model) {
		Libro libro = this.libroService.libroPerIsbn(isbn);
		for (Recensione recensione : libro.getRecensioni()) {
			recensione.setLibro(null);;
		}
		for (Autore autore : libro.getAutori()) {
			autore.getLibri().remove(libro);
		}
		if(libro.getCasaEditrice() != null)
			libro.getCasaEditrice().getLibri().remove(libro);
		libro.getRecensioni().clear();
		libro.getAutori().clear();
		libro.setCasaEditrice(null);
		this.libroService.inserisci(libro);
		this.libroService.elimina(isbn);
		return "redirect:/admin/pannelloAmministratore";
	}
}
