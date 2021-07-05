package com.museo.siw.spring.controller;

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

import com.museo.siw.spring.model.Opera;
import com.museo.siw.spring.model.Artista;
import com.museo.siw.spring.service.ArtistaService;
import com.museo.siw.spring.service.OperaService;
import com.museo.siw.spring.controller.validator.ArtistaValidator;


@Controller
public class ArtistaController {

	@Autowired
	private ArtistaValidator artistaValidator;

	@Autowired
	private ArtistaService artistaService;

	@Autowired
	private OperaService operaService;

	/* Va alla pagine dell'Artista selezionato dall'elenco */
	@RequestMapping(value="/paginaArtista/{id}", method = RequestMethod.GET)
	public String goToPageArtista(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artista", this.artistaService.artistaPerId(id));
		return "artista.html";
	}

	/* Va alla sezione Artisti prendendo tutti gli Artisti dal DB*/
	@RequestMapping(value="/sezioneArtisti", method = RequestMethod.GET)
	public String goToPageSezioneArtisti(Model model) {
		model.addAttribute("artisti", this.artistaService.tutti());
		return "sezioneArtisti.html";
	}

	// Apre la pagina per inserire un Artista creando un nuovo oggetto Artista
	@RequestMapping(value="/admin/paginaInserisciArtista", method = RequestMethod.GET)
	public String goToPageInserisciArtista(Model model) {
		model.addAttribute("artista", new Artista());
		model.addAttribute("opere", this.operaService.tutti());
		return "/admin/inserisciArtista.html";
	}

	// Inserisce l'Artista appena creato nel DB
	@RequestMapping(value="/admin/inserisciArtista", method = RequestMethod.POST)
	public String saveArtista(@ModelAttribute("artista") Artista artista,
			 @RequestParam("fileImage") MultipartFile multipartFile, BindingResult artistaBindingResult) throws IOException {
		this.artistaValidator.validate(artista, artistaBindingResult);
		if(!artistaBindingResult.hasErrors()) {
			String fileName = null;
			if(!multipartFile.isEmpty()) {
				fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				artista.setImmagine(fileName);
			}
			this.artistaService.inserisci(artista);
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
			return "redirect:/sezioneArtisti";
		} else
			return "/admin/inserisciArtista.html";
	}
	
	// Inserisce l'Artista appena modificato nel DB
	@RequestMapping(value="/admin/inserisciArtistaModificato", method = RequestMethod.POST)
	public String saveArtistaModificato(@ModelAttribute("artista") Artista artista, @Valid Long idOpera,
			@RequestParam(value="oldFileName", required = false) String oldFileName, @RequestParam("fileImage") MultipartFile multipartFile, BindingResult artistaBindingResult) throws IOException {
		this.artistaValidator.validate(artista, artistaBindingResult);
		if(!artistaBindingResult.hasErrors()) {
			String fileName = null;
			if(!multipartFile.isEmpty()) {
				fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				artista.setImmagine(fileName);
			} else {
				artista.setImmagine(oldFileName);
			}
			if(idOpera != null && idOpera != 0) {
				if(artista.getOpere() == null) {
					artista.setOpere(new ArrayList<Opera>());
				}
				Opera opera = this.operaService.operaPerId(idOpera);
				for(Opera o : this.artistaService.artistaPerId(artista.getId()).getOpere()) {
					o.setArtista(null);
				}
				opera.setArtista(artista);
				this.operaService.inserisci(opera);
			} else {
				artista.setOpere(new ArrayList<Opera>());
				for(Opera o : this.artistaService.artistaPerId(artista.getId()).getOpere()) {
					artista.getOpere().add(o);
				}
			}
			this.artistaService.inserisci(artista);
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
			return "redirect:/sezioneArtisti";
		} else
			return "/admin/modificaArtistaForm.html";
	}

	// Apre la pagina per selezionare un Artista da modificare
	@RequestMapping(value="/admin/paginaScegliArtistaDaModificare", method = RequestMethod.GET)
	public String goToPageScegliArtistaDaModificare(Model model) {
		model.addAttribute("artisti", this.artistaService.tutti());
		return "/admin/modificaArtista.html";
	}

	// Apre la form per modificare l'Artista
	@RequestMapping(value="/admin/formModificaArtista/{id}", method = RequestMethod.GET)
	public String goToPageFormModificaArtista(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artista", this.artistaService.artistaPerId(id));
		model.addAttribute("opere", this.operaService.tutti());
		return "/admin/modificaArtistaForm.html";
	}

	// Apre la pagina per eliminare un Artista
	@RequestMapping(value="/admin/paginaEliminaArtista", method = RequestMethod.GET)
	public String goToPageEliminaArtista(Model model) {
		model.addAttribute("artisti", this.artistaService.tutti());
		return "/admin/eliminaArtista.html";
	}

	// Elimina l'Artista dal DB
	@RequestMapping(value = "/admin/eliminaArtista/{id}", method = RequestMethod.GET)
	public String deleteArtista(@PathVariable("id") Long id, 
			Model model) {
		Artista artista = this.artistaService.artistaPerId(id);
		for(Opera opera : artista.getOpere()) {
			opera.setArtista(null);
		}
		artista.getOpere().clear();
		this.artistaService.inserisci(artista);
		this.artistaService.elimina(id);
		return "redirect:/admin/pannelloAmministratore";
	}

}
