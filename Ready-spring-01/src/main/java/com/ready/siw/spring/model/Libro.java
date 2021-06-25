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
	@Column(length = 64)
	private String copertina;
	
	@ManyToOne
	private CasaEditrice casaEditrice;
	
	@OneToMany (mappedBy="libro")
	private List<Recensione> recensioni;
	
	@ManyToMany (mappedBy="libri")
	private List<Lettore> lettori;
	
	@ManyToMany (mappedBy="libri")
	private List<Autore> autori;

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getSinossi() {
		return sinossi;
	}

	public void setSinossi(String sinossi) {
		this.sinossi = sinossi;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public String getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(String dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}
	
	@Transient
    public String getPhotosImagePath() {
        if (copertina == null || isbn == null) return null;
         
        return "/copertina/" + isbn + "/" + this.copertina;
    }
	
	public String getCopertina() {
		return copertina;
	}

	public void setCopertina(String copertina) {
		this.copertina = copertina;
	}

	public CasaEditrice getCasaEditrice() {
		return casaEditrice;
	}

	public void setCasaEditrice(CasaEditrice casaEditrice) {
		this.casaEditrice = casaEditrice;
	}

	public List<Recensione> getRecensioni() {
		return recensioni;
	}

	public void setRecensioni(List<Recensione> recensioni) {
		this.recensioni = recensioni;
	}

	public List<Lettore> getLettori() {
		return lettori;
	}

	public void setLettori(List<Lettore> lettori) {
		this.lettori = lettori;
	}

	public List<Autore> getAutori() {
		return autori;
	}

	public void setAutori(List<Autore> autori) {
		this.autori = autori;
	}
}
