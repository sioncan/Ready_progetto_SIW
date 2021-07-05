package com.museo.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.museo.siw.spring.model.Artista;
import com.museo.siw.spring.model.Collezione;
import com.museo.siw.spring.repository.ArtistaRepository;

@Service
public class ArtistaService {
	
	@Autowired
	private ArtistaRepository artistaRepository;
	
	@Transactional
	public Artista inserisci(Artista artista) {
		return this.artistaRepository.save(artista);
	}
	
	@Transactional
	public void elimina(Long id) {
		this.artistaRepository.deleteById(id);
	}
	
	@Transactional
	public List<Artista> artistaPerNome(String nome) {
		return this.artistaRepository.findByNome(nome);
	}
	
	@Transactional
	public List<Artista> artistaPerCognome(String cognome) {
		return this.artistaRepository.findByCognome(cognome);
	}
	
	@Transactional
	public Artista artistaPerNomeECognome(String nome, String cognome) {
		return this.artistaRepository.findByNomeAndCognome(nome, cognome);
	}

	@Transactional
	public List<Artista> tutti() {
		return (List<Artista>) this.artistaRepository.findAll();
	}

	@Transactional
	public Artista artistaPerId(Long id) {
		Optional<Artista> optional = this.artistaRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Artista artista) {
		Artista a = this.artistaRepository.findByNomeAndCognome(artista.getNome(), artista.getCognome());
		if (a != null)
			return true;
		else 
			return false;
	}
}
