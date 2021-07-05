package com.museo.siw.spring.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
public class Collezione {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	@Column(length = 500)
	private String descrizione;
	@Column(nullable = true, length = 64)
	private String immagine;

	@ManyToMany(mappedBy ="collezioni")
	private List<Opera> opere;
	
	@ManyToMany(mappedBy ="collezioniPreferite")
	private List<User> users;
	
	@ManyToOne
	private Curatore curatore;
	
	@Transient
	public String getImmagineImagePath() {
		if (immagine == null || id == null) return null;

		return "/images/" + immagine;
	}
}
