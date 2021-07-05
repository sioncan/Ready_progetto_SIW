package com.ready.siw.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ready.siw.spring.model.CasaEditrice;

public interface CasaEditriceRepository extends CrudRepository<CasaEditrice, Long>{
	
	@Query(value="SELECT * FROM casa_editrice c WHERE c.nome LIKE %:nomeCasaEditrice%", nativeQuery=true)
	public List<CasaEditrice> findByNome(@Param("nomeCasaEditrice") String nomeCasaEditrice);
	
	public List<CasaEditrice> findByNazionalita(@Param("nazionalita") String nazionalita);

}
