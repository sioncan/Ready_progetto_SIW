package com.ready.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ready.siw.spring.model.Autore;
import com.ready.siw.spring.repository.AutoreRepository;


@Service
public class AutoreService {
	
	@Autowired
	private AutoreRepository autoreRepository; 
	
	@Transactional
	public Autore inserisci(Autore autore) {
		return this.autoreRepository.save(autore);
	}
	
	@Transactional
	public void elimina(Long id) {
		this.autoreRepository.deleteById(id);
	}
	
	@Transactional
	public List<Autore> autorePerNomeOCognome(String nomeCognome) {
		return this.autoreRepository.findByNomeOrCognome(nomeCognome);
	}
	
	@Transactional
	public List<Autore> autorePerNazionalita(String nazionalita) {
		return this.autoreRepository.findByNazionalita(nazionalita);
	}

	@Transactional
	public List<Autore> tutti() {
		return (List<Autore>) this.autoreRepository.findAll();
	}

	@Transactional
	public Autore autorePerId(Long id) {
		Optional<Autore> optional = this.autoreRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

}
