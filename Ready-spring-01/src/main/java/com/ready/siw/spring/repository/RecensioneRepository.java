package com.ready.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ready.siw.spring.model.Recensione;

public interface RecensioneRepository extends CrudRepository<Recensione, Long>{
	
	public List<Recensione> findByVoto(int voto);
	
	public List<Recensione> findByTitolo(String titolo);
	
	public List<Recensione> findByTesto(String testo);

}
