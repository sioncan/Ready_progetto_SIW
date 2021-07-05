package com.museo.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.museo.siw.spring.model.Collezione;

public interface CollezioneRepository extends CrudRepository<Collezione, Long>{
	
	public Collezione findByNome(String nome);

}
