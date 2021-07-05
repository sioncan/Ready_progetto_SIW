package com.museo.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.museo.siw.spring.model.Opera;

public interface OperaRepository extends CrudRepository<Opera, Long>{
	
	public Opera findByTitolo(String titolo);
	
	public List<Opera> findByAnnoRealizzazione(String annoRealizzazione);

}
