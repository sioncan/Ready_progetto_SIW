package com.ready.siw.spring.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Lettore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String email;
	private String nome;
	private String cognome;
	private LocalDate dataNascita;
	private String luogoNascita;
	
	@OneToMany (mappedBy="recensore")
	private List<Recensione> recensioni;
	
	@ManyToMany
	private List<Libro> libri;

}
