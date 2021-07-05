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
}
