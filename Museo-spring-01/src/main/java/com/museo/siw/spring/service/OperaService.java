package com.museo.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.museo.siw.spring.repository.OperaRepository;
import com.museo.siw.spring.model.Artista;
import com.museo.siw.spring.model.Opera;

@Service
public class OperaService {
	
	@Autowired 
	private OperaRepository operaRepository;

	@Transactional
	public Opera inserisci(Opera opera) {
		return this.operaRepository.save(opera);
	}
	
	@Transactional
	public void elimina(Long id) {
		this.operaRepository.deleteById(id);
	}
	
	@Transactional
	public Opera operaPerTitolo(String titolo) {
		return this.operaRepository.findByTitolo(titolo);
	}
	
	@Transactional
	public List<Opera> operaPerAnnoRealizzazione(String annoRealizzazione) {
		return this.operaRepository.findByAnnoRealizzazione(annoRealizzazione);
	}
	
	@Transactional
	public List<Opera> tutti() {
		return (List<Opera>) this.operaRepository.findAll();
	}
	
	@Transactional
	public Opera operaPerId(Long id) {
		Optional<Opera> optional = this.operaRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	
	@Transactional
	public boolean alreadyExists(Opera opera) {
		Opera o = this.operaRepository.findByTitolo(opera.getTitolo());
		if (o != null)
			return true;
		else 
			return false;
	}
}
