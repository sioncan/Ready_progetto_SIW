package com.ready.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ready.siw.spring.model.Lettore;

public interface LettoreRepository extends CrudRepository<Lettore, String>{
	
	public List<Lettore> findByEmail(String email);
	
	public List<Lettore> findByNome(String nome);
	
	public List<Lettore> findByCognome(String cognome);

	public List<Lettore> findByNomeAndCognome(String nome, String cognome);

}
