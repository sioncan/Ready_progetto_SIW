package com.ready.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ready.siw.spring.model.CasaEditrice;
import com.ready.siw.spring.repository.CasaEditriceRepository;

@Service
public class CasaEditriceService {
	
	@Autowired
	private CasaEditriceRepository casaEditriceRepository; 
	
	@Transactional
	public CasaEditrice inserisci(CasaEditrice casaEditrice) {
		return this.casaEditriceRepository.save(casaEditrice);
	}
	
	@Transactional
	public void elimina(Long id) {
		this.casaEditriceRepository.deleteById(id);
	}
	
	@Transactional
	public List<CasaEditrice> casaEditricePerNome(String nome) {
		return this.casaEditriceRepository.findByNome(nome);
	}
	
	@Transactional
	public List<CasaEditrice> casaEditricePerNazionalita(String nazionalita) {
		return this.casaEditriceRepository.findByNazionalita(nazionalita);
	}

	@Transactional
	public List<CasaEditrice> tutti() {
		return (List<CasaEditrice>) this.casaEditriceRepository.findAll();
	}

	@Transactional
	public CasaEditrice casaEditricePerId(Long id) {
		Optional<CasaEditrice> optional = this.casaEditriceRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(CasaEditrice casaEditrice) {
		List<CasaEditrice> c = this.casaEditriceRepository.findByNome(casaEditrice.getNome());
		if (c != null)
			return true;
		else 
			return false;
	}

}
