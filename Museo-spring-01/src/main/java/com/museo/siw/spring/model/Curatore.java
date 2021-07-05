package com.museo.siw.spring.model;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import lombok.Data;

@Data
@Entity
public class Curatore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String cognome;
	private String dataNascita;
	private String luogoNascita;
	private String email;
	private Integer telefono;
	private Integer matricola;

	@OneToMany(mappedBy="curatore")
	private List<Collezione> collezioni;
}
