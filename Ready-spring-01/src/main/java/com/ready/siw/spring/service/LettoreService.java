package com.ready.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ready.siw.spring.model.Lettore;
import com.ready.siw.spring.repository.LettoreRepository;

@Service
public class LettoreService {
	
	@Autowired
	private LettoreRepository lettoreRepository; 
	
	@Transactional
	public Lettore inserisci(Lettore lettore) {
		return this.lettoreRepository.save(lettore);
	}
	
	@Transactional
	public List<Lettore> lettorePerNome(String nome) {
		return this.lettoreRepository.findByNome(nome);
	}
	
	@Transactional
	public List<Lettore> lettorePerCognome(String cognome) {
		return this.lettoreRepository.findByCognome(cognome);
	}
	
	@Transactional
	public List<Lettore> lettorePerNomeECognome(String nome, String cognome) {
		return this.lettoreRepository.findByNomeAndCognome(nome, cognome);
	}
	
	@Transactional
	public List<Lettore> lettorePerEmail(String email) {
		return this.lettoreRepository.findByEmail(email);
	}

	@Transactional
	public List<Lettore> tutti() {
		return (List<Lettore>) this.lettoreRepository.findAll();
	}

	@Transactional
	public Lettore lettorePerUsername(String username) {
		Optional<Lettore> optional = this.lettoreRepository.findById(username);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Lettore lettore) {
		List<Lettore> lettori = this.lettoreRepository.findByEmail(lettore.getEmail());
		if (lettori.size() > 0)
			return true;
		else 
			return false;
	}

}
