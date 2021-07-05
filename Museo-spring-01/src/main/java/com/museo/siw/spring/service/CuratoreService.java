package com.museo.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.museo.siw.spring.model.Curatore;
import com.museo.siw.spring.repository.CuratoreRepository;

@Service
public class CuratoreService {
	
	@Autowired
	private CuratoreRepository curatoreRepository;
	
	@Transactional
	public Curatore inserisci(Curatore curatore) {
		return this.curatoreRepository.save(curatore);
	}
	
	@Transactional
	public void elimina(Long id) {
		this.curatoreRepository.deleteById(id);
	}
	
	@Transactional
	public List<Curatore> curatorePerNome(String nome) {
		return this.curatoreRepository.findByNome(nome);
	}
	
	@Transactional
	public List<Curatore> curatorePerCognome(String cognome) {
		return this.curatoreRepository.findByCognome(cognome);
	}
	
	@Transactional
	public Curatore curatorePerNomeECognome(String nome, String cognome) {
		return this.curatoreRepository.findByNomeAndCognome(nome, cognome);
	}

	@Transactional
	public List<Curatore> tutti() {
		return (List<Curatore>) this.curatoreRepository.findAll();
	}

	@Transactional
	public Curatore curatorePerId(Long id) {
		Optional<Curatore> optional = this.curatoreRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Curatore curatore) {
		Curatore c = this.curatoreRepository.findByNomeAndCognome(curatore.getNome(), curatore.getCognome());
		if (c != null)
			return true;
		else 
			return false;
	}
}
