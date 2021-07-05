package com.museo.siw.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.museo.siw.spring.model.Curatore;


public interface CuratoreRepository extends JpaRepository<Curatore, Long> {

	public List<Curatore> findByNome(String nome);
	
	public List<Curatore> findByCognome(String cognome);

	public Curatore findByNomeAndCognome(String nome, String cognome);
}
