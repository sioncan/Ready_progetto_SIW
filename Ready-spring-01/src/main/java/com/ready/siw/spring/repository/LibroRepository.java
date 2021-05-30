package com.ready.siw.spring.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ready.siw.spring.model.Libro;

public interface LibroRepository extends CrudRepository<Libro, String> {

	public List<Libro> findByTitolo(String titolo);
	
	public List<Libro> findByDataPubblicazione(LocalDate dataPubblicazione);

}
