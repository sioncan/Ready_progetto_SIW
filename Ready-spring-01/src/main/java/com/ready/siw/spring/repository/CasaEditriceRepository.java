package com.ready.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ready.siw.spring.model.CasaEditrice;

public interface CasaEditriceRepository extends CrudRepository<CasaEditrice, Long>{
	
	public List<CasaEditrice> findByNome(String nome);
	
	public List<CasaEditrice> findByNazionalita(String nazionalita);

}
