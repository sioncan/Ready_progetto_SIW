package com.museo.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.museo.siw.spring.model.Collezione;
import com.museo.siw.spring.repository.CollezioneRepository;

@Service
public class CollezioneService {

	@Autowired
	private CollezioneRepository collezioneRepository;
	
	@Transactional
	public Collezione inserisci(Collezione collezione) {
		return this.collezioneRepository.save(collezione);
	}
	
	@Transactional
	public void elimina(Long id) {
		this.collezioneRepository.deleteById(id);
	}
	
	@Transactional
	public Collezione collezionePerNome(String nome) {
		return this.collezioneRepository.findByNome(nome);
	}
	
	@Transactional
	public List<Collezione> tutti() {
		return (List<Collezione>) this.collezioneRepository.findAll();
	}

	@Transactional
	public Collezione collezionePerId(Long id) {
		Optional<Collezione> optional = this.collezioneRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	
	@Transactional
	public boolean alreadyExists(Collezione collezione) {
		Collezione c = this.collezioneRepository.findByNome(collezione.getNome());
		if (c != null)
			return true;
		else 
			return false;
	}
}
