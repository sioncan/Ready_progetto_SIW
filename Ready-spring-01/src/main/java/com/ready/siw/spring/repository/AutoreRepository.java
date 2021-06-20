package com.ready.siw.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ready.siw.spring.model.Autore;

public interface AutoreRepository extends JpaRepository<Autore, Long>{
	
	@Query(value="SELECT * FROM Autore a WHERE a.nome LIKE %:nome%", nativeQuery=true)
	public List<Autore> findByNome(@Param("nome") String nome);

	public void deleteByNome(String nome);
	
	@Query(value="SELECT * FROM Autore a WHERE a.nazionalita LIKE %:nazionalita%", nativeQuery=true)
	public List<Autore> findByNazionalita(@Param("nazionalita") String nazionalita);
}
