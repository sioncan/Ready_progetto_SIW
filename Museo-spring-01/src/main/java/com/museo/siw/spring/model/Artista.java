package com.museo.siw.spring.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
public class Artista {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String cognome;
	private String dataNascita;
	private String luogoNascita;
	private String dataMorte;
	private String luogoMorte;
	private String nazionalita;
	@Column(length = 500)
	private String biografia;
	@Column(nullable = true, length = 64)
	private String immagine;
	
	@OneToMany(mappedBy="artista")
	private List<Opera> opere;
	
	@Transient
	public String getImmagineImagePath() {
		if (immagine == null || id == null) return null;

		return "/images/" + immagine;
	}
}
