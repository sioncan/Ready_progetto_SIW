package com.ready.siw.spring.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Lettore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String username;
	private char[] password;
	private String email;
	private String nome;
	private String cognome;
	private LocalDate dataNascita;
	private String luogoNascita;

}
