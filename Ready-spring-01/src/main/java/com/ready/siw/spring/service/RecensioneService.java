package com.ready.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ready.siw.spring.model.Recensione;
import com.ready.siw.spring.repository.RecensioneRepository;


@Service
public class RecensioneService {
	
	@Autowired
	private RecensioneRepository recensioneRepository; 
	
	@Transactional
	public Recensione inserisci(Recensione recensione) {
		return this.recensioneRepository.save(recensione);
	}
	
	@Transactional
	public List<Recensione> recensionePerVoto(int voto) {
		return this.recensioneRepository.findByVoto(voto);
	}
	
	@Transactional
	public List<Recensione> recensionePerTitolo(String titolo) {
		return this.recensioneRepository.findByTitolo(titolo);
	}
	
	@Transactional
	public List<Recensione> recensionePerTesto(String testo) {
		return this.recensioneRepository.findByTitolo(testo);
	}

	@Transactional
	public List<Recensione> tutti() {
		return (List<Recensione>) this.recensioneRepository.findAll();
	}

	@Transactional
	public Recensione recensionePerId(Long id) {
		Optional<Recensione> optional = this.recensioneRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	
	@Transactional
	public boolean alreadyExists(Recensione recensione) {
		List<Recensione> studenti = this.recensioneRepository.findByTesto(recensione.getTesto());
		if (studenti.size() > 0)
			return true;
		else 
			return false;
	}
}
