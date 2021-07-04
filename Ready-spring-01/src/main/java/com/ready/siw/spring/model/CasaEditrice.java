package com.ready.siw.spring.model;

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
}
