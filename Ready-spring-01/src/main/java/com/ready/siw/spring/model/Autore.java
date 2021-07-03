package com.ready.siw.spring.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
public class Autore {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nomeCognome;
	private String dataNascita;
	private String luogoNascita;
	private String dataMorte;
	private String luogoMorte;
	private String nazionalita;
	@Column(length = 10000)
	private String biografia;
	@ElementCollection
	private List<String> generi;
	@Column(nullable = true, length = 64)
	private String immagine;

	@ManyToMany 
	private List<Libro> libri;

	@Transient
	public String getImmagineImagePath() {
		if (immagine == null || id == null) return null;

		return "/images/" + immagine;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCognome() {
		return nomeCognome;
	}

	public void setNomeCognome(String nome) {
		this.nomeCognome = nome;
	}

	public String getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getLuogoNascita() {
		return luogoNascita;
	}

	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	public String getDataMorte() {
		return dataMorte;
	}

	public void setDataMorte(String dataMorte) {
		this.dataMorte = dataMorte;
	}

	public String getLuogoMorte() {
		return luogoMorte;
	}

	public void setLuogoMorte(String luogoMorte) {
		this.luogoMorte = luogoMorte;
	}

	public String getNazionalita() {
		return nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}
}
