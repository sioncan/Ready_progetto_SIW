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

import com.museo.siw.spring.controller.validator.CollezioneValidator;
import com.museo.siw.spring.model.Collezione;
import com.museo.siw.spring.model.Curatore;
import com.museo.siw.spring.model.Opera;
import com.museo.siw.spring.service.CollezioneService;
import com.museo.siw.spring.service.CuratoreService;
import com.museo.siw.spring.service.OperaService;

@Controller
public class CollezioneController {
	
	@Autowired
	private CollezioneValidator collezioneValidator;

	@Autowired
	private CollezioneService collezioneService;
	
	@Autowired
	private OperaService operaService;
	
	@Autowired
	private CuratoreService curatoreService;

	/* Va alla pagine della Collezione selezionato dall'elenco */
	@RequestMapping(value="/paginaCollezione/{id}", method = RequestMethod.GET)
	public String goToPageCollezione(@PathVariable("id") Long id, Model model) {
		model.addAttribute("collezione", this.collezioneService.collezionePerId(id));
		return "collezione.html";
	}

	/* Va alla sezione Collezione prendendo tutte le Collezioni dal DB*/
	@RequestMapping(value="/sezioneCollezioni")
	public String goToPageSezioneCollezioni(Model model) {
		model.addAttribute("collezioni", this.collezioneService.tutti());
		return "sezioneCollezioni.html";
	}

	// Apre la pagina per inserire una Collezione creando un nuovo oggetto Collezione
	@RequestMapping(value="/admin/paginaInserisciCollezione", method = RequestMethod.GET)
	public String goToPageInserisciCollezione(Model model) {
		model.addAttribute("collezione", new Collezione());
		model.addAttribute("opere", this.operaService.tutti());
		model.addAttribute("curatori", this.curatoreService.tutti());
		return "/admin/inserisciCollezione.html";
	}

	// Inserisce la Collezione appena creata nel DB
	@RequestMapping(value= "/admin/inserisciCollezione", method = RequestMethod.POST)
	public String saveCollezione(@ModelAttribute("collezione") Collezione collezione,   
			@RequestParam(value="fileImage", required = false) MultipartFile multipartFile, BindingResult collezioneBindingResult) throws IOException {
		this.collezioneValidator.validate(collezione, collezioneBindingResult);
		if (!collezioneBindingResult.hasErrors()) {
			String fileName = null;
			if(!multipartFile.isEmpty()) {
				fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				collezione.setImmagine(fileName);
			}
			this.collezioneService.inserisci(collezione);
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
			return "/admin/inserisciCollezione.html";
	}

	// Inserisce la Collezione appena modificata nel DB
	@RequestMapping(value= "/admin/inserisciCollezioneModificata", method = RequestMethod.POST)
	public String saveCollezioneModificata(@ModelAttribute("collezione") Collezione collezione,  @Valid Long idCuratore, 
			@Valid Long idOpera, @RequestParam(value="oldFileName", required = false) String oldFileName, 
			@RequestParam(value="fileImage", required = false) MultipartFile multipartFile, BindingResult collezioneBindingResult) throws IOException {
		this.collezioneValidator.validate(collezione, collezioneBindingResult);
		if (!collezioneBindingResult.hasErrors()) {
			String fileName = null;
			if(!multipartFile.isEmpty()) {
				fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				collezione.setImmagine(fileName);
			} else {
				collezione.setImmagine(oldFileName);
			}
			if(idCuratore != null && idCuratore != 0) {
				Curatore curatore = this.curatoreService.curatorePerId(idCuratore);
				collezione.setCuratore(curatore);
				this.curatoreService.inserisci(curatore);
			} else {
				collezione.setCuratore(this.collezioneService.collezionePerId(collezione.getId()).getCuratore());
			}
			if(idOpera != null && idOpera != 0) {
                Opera opera = this.operaService.operaPerId(idOpera);
                if(opera.getCollezioni() == null) {
                	opera.setCollezioni(new ArrayList<Collezione>());
                }
                opera.getCollezioni().add(collezione);
                this.operaService.inserisci(opera);
            }
			this.collezioneService.inserisci(collezione);
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
			return "redirect:/sezioneCollezioni";
		} else
			return "/admin/modificaCollezioneForm.html";
	}

	// Apre la pagina per selezionare una Collezione da modificare
	@RequestMapping(value="/admin/paginaScegliCollezioneDaModificare", method = RequestMethod.GET)
	public String goToPageScegliCollezioneDaModificare(Model model) {
		model.addAttribute("collezioni", this.collezioneService.tutti());
		return "/admin/modificaCollezione.html";
	}

	// Apre la form per modificare la Collezione
	@RequestMapping(value="/admin/formModificaCollezione/{id}", method = RequestMethod.GET)
	public String goToPageFormModificaCollezione(@PathVariable("id") Long id, Model model) {
		model.addAttribute("collezione", this.collezioneService.collezionePerId(id));
		model.addAttribute("opere", this.operaService.tutti());
		model.addAttribute("curatori", this.curatoreService.tutti());
		return "/admin/modificaCollezioneForm.html";
	}

	// Apre la pagina per eliminare una Collezione
	@RequestMapping(value="/admin/paginaEliminaCollezione", method = RequestMethod.GET)
	public String goToPageEliminaCollezione(Model model) {
		model.addAttribute("collezioni", this.collezioneService.tutti());
		return "/admin/eliminaCollezione.html";
	}

	// Elimina la Collezione dal DB
	@RequestMapping(value = "/admin/eliminaCollezione/{id}", method = RequestMethod.GET)
	public String deleteCollezione(@PathVariable("id") Long id, 
			Model model) {
		Collezione collezione = this.collezioneService.collezionePerId(id);
		for(Opera opera : collezione.getOpere()) {
			opera.getCollezioni().remove(collezione);
		}
		if(collezione.getCuratore() != null)
			collezione.getCuratore().getCollezioni().remove(collezione);
		collezione.getOpere().clear();
		collezione.setCuratore(null);
		this.collezioneService.inserisci(collezione);
		this.collezioneService.elimina(id);
		return "redirect:/admin/pannelloAmministratore";
	}

}
