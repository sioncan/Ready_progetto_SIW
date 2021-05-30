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
	public List<Autore> autorePerNome(String nome) {
		return this.autoreRepository.findByNome(nome);
	}
	
	@Transactional
	public List<Autore> autorePerCognome(String cognome) {
		return this.autoreRepository.findByCognome(cognome);
	}
	
	@Transactional
	public List<Autore> autorePerNomeECognome(String nome, String cognome) {
		return this.autoreRepository.findByNomeAndCognome(nome, cognome);
	}

	@Transactional
	public List<Autore> tutti() {
		return (List<Autore>) this.autoreRepository.findAll();
	}

	@Transactional
	public Autore lettorePerId(Long id) {
		Optional<Autore> optional = this.autoreRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

}
