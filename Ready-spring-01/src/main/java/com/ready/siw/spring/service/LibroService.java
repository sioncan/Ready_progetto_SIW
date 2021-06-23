package com.ready.siw.spring.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ready.siw.spring.model.Autore;
import com.ready.siw.spring.model.Libro;
import com.ready.siw.spring.repository.LibroRepository;

@Service
public class LibroService {
	
	@Autowired
	private LibroRepository libroRepository; 

	
	@Transactional
	public Libro inserisci(Libro libro) {
		return this.libroRepository.save(libro);
	}
	
	@Transactional
	public void elimina(String isbn) {
		this.libroRepository.deleteById(isbn);
	}
	
	@Transactional
	public List<Libro> libroPerTitolo(String titolo) {
		return this.libroRepository.findByTitolo(titolo);
	}
	
	@Transactional
	public List<Libro> libroPerGenere(String genere) {
		return this.libroRepository.findByTitolo(genere);
	}
	
	@Transactional
	public List<Libro> libroPerDataPubblicazione(LocalDate dataPubblicazione) {
		return this.libroRepository.findByDataPubblicazione(dataPubblicazione);
	}

	@Transactional
	public List<Libro> tutti() {
		return (List<Libro>) this.libroRepository.findAll();
	}

	@Transactional
	public Libro libroPerIsbn(String isbn) {
		Optional<Libro> optional = this.libroRepository.findById(isbn);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Libro libro) {
		List<Libro> libri = this.libroRepository.findByTitolo(libro.getTitolo());
		if (libri.size() > 0)
			return true;
		else 
			return false;
	}
	
}
