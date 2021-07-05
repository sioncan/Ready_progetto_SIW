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

import com.museo.siw.spring.controller.validator.OperaValidator;
import com.museo.siw.spring.model.Artista;
import com.museo.siw.spring.model.Collezione;
import com.museo.siw.spring.model.Opera;
import com.museo.siw.spring.service.ArtistaService;
import com.museo.siw.spring.service.CollezioneService;
import com.museo.siw.spring.service.OperaService;

@Controller
public class OperaController {
	
	@Autowired
	private OperaValidator operaValidator;
	
	@Autowired
	private OperaService operaService;
	
	@Autowired
	private CollezioneService collezioneService;
	
	@Autowired
	private ArtistaService artistaService;
	
	/* Va alla home/index prendendo le Collezioni dal DB */
	@RequestMapping(value= {"/", "/home", "index"}, method = RequestMethod.GET)
	public String goToPageHome(Model model) {
		model.addAttribute("opere", this.operaService.tutti());
		return "index.html";
	}
	
	/* Va alla pagine dell'Opera selezionato dall'elenco */
	@RequestMapping(value="/paginaOpera/{id}", method = RequestMethod.GET)
	public String goToPageOpera(@PathVariable("id") Long id, Model model) {
		model.addAttribute("opera", this.operaService.operaPerId(id));
		return "opera.html";
	}
	
	/* Va alla sezione Opere prendendo tutte le Opere dal DB*/
	@RequestMapping(value="/sezioneOpere")
	public String goToPageSezioneOpere(Model model) {
		model.addAttribute("opere", this.operaService.tutti());
		return "sezioneOpere.html";
	}
	
	// Apre la pagina per inserire un'Opera creando un nuovo oggetto Opera
	@RequestMapping(value="/admin/paginaInserisciOpera", method = RequestMethod.GET)
	public String goToPageInserisciOpera(Model model) {
		model.addAttribute("opera", new Opera());
		model.addAttribute("collezioni", this.collezioneService.tutti());
		model.addAttribute("artisti", this.artistaService.tutti());
		return "/admin/inserisciOpera.html";
	}
	
	// Inserisce l'Opera appena creato nel DB
	@RequestMapping(value= "/admin/inserisciOpera", method = RequestMethod.POST)
	public String saveOpera(@ModelAttribute("opera") Opera opera,   
			@RequestParam(value="fileImage", required = false) MultipartFile multipartFile, BindingResult operaBindingResult) throws IOException {
		this.operaValidator.validate(opera, operaBindingResult);
		if (!operaBindingResult.hasErrors()) {
			String fileName = null;
			if(!multipartFile.isEmpty()) {
				fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				opera.setImmagine(fileName);
			}
			this.operaService.inserisci(opera);
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
			return "redirect:/home";
		} else
			return "/admin/inserisciOpera.html";
	}

	// Inserisce l'Opera appena modificata nel DB
	@RequestMapping(value= "/admin/inserisciOperaModificata", method = RequestMethod.POST)
	public String saveOperaModificata(@ModelAttribute("opera") Opera opera,  @Valid Long idArtista, 
			@Valid Long idCollezione, @RequestParam(value="oldFileName", required = false) String oldFileName, 
			@RequestParam(value="fileImage", required = false) MultipartFile multipartFile, BindingResult operaBindingResult) throws IOException {
		this.operaValidator.validate(opera, operaBindingResult);
		if (!operaBindingResult.hasErrors()) {
			String fileName = null;
			if(!multipartFile.isEmpty()) {
				fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				opera.setImmagine(fileName);
			} else {
				opera.setImmagine(oldFileName);
			}
			if(idArtista != null && idArtista != 0) {
				Artista artista = this.artistaService.artistaPerId(idArtista);
				opera.setArtista(artista);
				this.artistaService.inserisci(artista);
			} else {
				opera.setArtista(this.operaService.operaPerId(opera.getId()).getArtista());
			}
			if(idCollezione != null && idCollezione != 0) {
                Collezione collezione = this.collezioneService.collezionePerId(idCollezione);
                if(collezione.getOpere() == null) {
                	collezione.setOpere(new ArrayList<Opera>());
                }
                collezione.getOpere().add(opera);
                this.collezioneService.inserisci(collezione);
            }
			this.operaService.inserisci(opera);
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
			return "redirect:/home";
		} else
			return "/admin/modificaOperaForm.html";
	}
	
	// Apre la pagina per selezionare un'Opera da modificare
	@RequestMapping(value="/admin/paginaScegliOperaDaModificare", method = RequestMethod.GET)
	public String goToPageScegliOperaDaModificare(Model model) {
		model.addAttribute("opere", this.operaService.tutti());
		return "/admin/modificaOpera.html";
	}
	
	// Apre la form per modificare l'Opera
	@RequestMapping(value="/admin/formModificaOpera/{id}", method = RequestMethod.GET)
	public String goToPageFormModificaOpera(@PathVariable("id") Long id, Model model) {
		model.addAttribute("opera", this.operaService.operaPerId(id));
		model.addAttribute("collezioni", this.collezioneService.tutti());
		model.addAttribute("artisti", this.artistaService.tutti());
		return "/admin/modificaOperaForm.html";
	}

	// Apre la pagina per eliminare un'Opera
	@RequestMapping(value="/admin/paginaEliminaOpera", method = RequestMethod.GET)
	public String goToPageEliminaOpera(Model model) {
		model.addAttribute("opere", this.operaService.tutti());
		return "/admin/eliminaOpera.html";
	}

	// Elimina l'Opera dal DB
	@RequestMapping(value = "/admin/eliminaOpera/{id}", method = RequestMethod.GET)
	public String deleteOpera(@PathVariable("id") Long id, 
			Model model) {
		Opera opera = this.operaService.operaPerId(id);
		for(Collezione collezione : opera.getCollezioni()) {
			collezione.getOpere().remove(opera);
		}
		if(opera.getArtista() != null)
			opera.getArtista().getOpere().remove(opera);
		opera.getCollezioni().clear();
		opera.setArtista(null);
		this.operaService.inserisci(opera);
		this.operaService.elimina(id);
		return "redirect:/admin/pannelloAmministratore";
	}

}
