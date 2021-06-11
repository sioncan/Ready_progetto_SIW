package com.ready.siw.spring.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Recensione {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int voto;
	private String titolo;
	private String testo;

	@ManyToOne
	private Lettore recensore;

	@ManyToOne
	private Libro libro;

}
