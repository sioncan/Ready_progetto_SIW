package com.ready.siw.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ready.siw.spring.model.Autore;

public interface AutoreRepository extends CrudRepository<Autore, Long>{
	
	@Query(value="SELECT * FROM Autore a WHERE a.nome_cognome LIKE %:nomeCognome%", nativeQuery=true)
	public List<Autore> findByNomeOrCognome(@Param("nomeCognome") String nomeCognome);
	
	@Query(value="SELECT * FROM Autore a WHERE a.nazionalita LIKE %:nazionalita%", nativeQuery=true)
	public List<Autore> findByNazionalita(@Param("nazionalita") String nazionalita);
	
	public List<Autore> findByGeneri(@Param("genere") String genere);

}
