package com.museo.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.museo.siw.spring.model.Artista;

public interface ArtistaRepository extends CrudRepository<Artista, Long>{
	
	public List<Artista> findByNome(String nome);
	
	public List<Artista> findByCognome(String cognome);

	public Artista findByNomeAndCognome(String nome, String cognome);
	
}
