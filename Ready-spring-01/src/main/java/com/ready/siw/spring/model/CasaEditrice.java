package com.ready.siw.spring.model;

import java.time.LocalDate;
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
public class CasaEditrice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String nazionalita;
	private String sede;
	private String website;
	private String dataFondazione;
    @Column(nullable = true, length = 64)
    private String logo;
	
	@OneToMany (mappedBy="casaEditrice")
	private List<Libro> libri;
	
    @Transient
    public String getLogoImagePath() {
        if (logo == null || id == null) return null;
         
        return "/images/" + logo;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNazionalita() {
		return nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDataFondazione() {
		return dataFondazione;
	}

	public void setDataFondazione(String dataFondazione) {
		this.dataFondazione = dataFondazione;
	}

	public List<Libro> getLibri() {
		return libri;
	}

	public void setLibri(List<Libro> libri) {
		this.libri = libri;
	}

}
