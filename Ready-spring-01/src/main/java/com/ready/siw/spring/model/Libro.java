package com.ready.siw.spring.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Libro {
	
	@Id
	private String isbn;
	private String titolo;
	private String sinossi;
	private String dataPubblicazione;
	
	@ManyToOne
	private CasaEditrice casaEditrice;
	
	@OneToMany (mappedBy="libro")
	private List<Recensione> recensioni;
	
	@ManyToMany (mappedBy="libri")
	private List<Lettore> lettori;
	
	@ManyToMany (mappedBy="libri")
	private List<Autore> autori;
}
