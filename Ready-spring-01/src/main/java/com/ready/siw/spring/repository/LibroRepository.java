package com.ready.siw.spring.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ready.siw.spring.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, String> {

	@Query(value="SELECT * FROM Libro l WHERE l.titolo LIKE %:titolo%", nativeQuery=true)
	public List<Libro> findByTitolo(@Param("titolo") String titolo);
	
	public List<Libro> findByDataPubblicazione(LocalDate dataPubblicazione);
	
	@Query(value="SELECT * FROM Libro l WHERE l.genere LIKE %:genere%", nativeQuery=true)
	public List<Libro> findByGenere(String genere);

}
