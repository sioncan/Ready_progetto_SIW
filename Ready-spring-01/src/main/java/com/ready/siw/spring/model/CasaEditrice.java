package com.ready.siw.spring.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class CasaEditrice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String nazionalita;
	private String sede;
	private String website;
	private LocalDate dataFondazione;
	
	@OneToMany (mappedBy="casaEditrice")
	private List<Libro> libri;

}
