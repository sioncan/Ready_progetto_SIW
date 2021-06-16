package com.ready.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ready.siw.spring.model.Autore;

public interface AutoreRepository extends CrudRepository<Autore, Long>{
	
	public List<Autore> findByNome(String nome);
	
	public List<Autore> findByCognome(String cognome);

	public List<Autore> findByNomeAndCognome(String nome, String cognome);

	public void deleteByNomeAndCognome(String nome, String cognome);
}
