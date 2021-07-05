package com.ready.siw.spring.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;


import lombok.Data;

@Data
@Entity
public class Libro {
	
	@Id
	private String isbn;
	private String titolo;
	@Column(length = 500)
	private String sinossi;
	private String genere;
	private String dataPubblicazione;
    @Column(nullable = true, length = 64)
    private String copertina;
	public Integer votoMedio;
	
	@ManyToOne
	private CasaEditrice casaEditrice;
	
	@OneToMany (mappedBy="libro")
	private List<Recensione> recensioni;
	
	@ManyToMany (mappedBy="libri")
	private List<Lettore> lettori;
	
	@ManyToMany (mappedBy="libri")
	private List<Autore> autori;
	
    @Transient
    public String getCopertinaImagePath() {
        if (copertina == null || isbn == null) return null;
         
        return "/images/" + copertina;
    }

	@Transient
	public int calcolaVotoMedio() {
		votoMedio = 0;
		if(!this.recensioni.isEmpty()) {
			for (Recensione recensione : this.recensioni) {
				votoMedio += recensione.getVoto();
			}
			return (votoMedio/(this.recensioni.size()));
		} else {
			return 0;
		}
	}
}
