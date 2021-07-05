package com.ready.siw.spring.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ready.siw.spring.model.Libro;

public interface LibroRepository extends CrudRepository<Libro, String> {
	
	public List<Libro> findByIsbn(String isbn);

	@Query(value="SELECT * FROM Libro l WHERE l.titolo LIKE %:titolo%", nativeQuery=true)
	public List<Libro> findByTitolo(@Param("titolo") String titolo);
	
	@Query(value="SELECT * FROM Libro l WHERE l.titolo LIKE %:titoloOisbn% OR l.isbn LIKE %:titoloOisbn%", nativeQuery=true)
	public List<Libro> findByTitoloOrIsbn(@Param("titoloOisbn") String titoloOisbn);
	
	public List<Libro> findByDataPubblicazione(LocalDate dataPubblicazione);
	
	@Query(value="SELECT * FROM Libro l WHERE l.genere LIKE %:genere%", nativeQuery=true)
	public List<Libro> findByGenere(@Param("genere") String genere);
	
	@Query(value="SELECT *\n"
			+ "FROM Libro JOIN casa_editrice ON casa_editrice.id=casa_editrice_id\n"
			+ "WHERE casa_editrice.nome LIKE %:casaEditrice%", nativeQuery=true)
	public List<Libro> findByCasaEditrice(@Param("casaEditrice") String casaEditrice);
	
	@Query(value="SELECT *\n"
			+ "FROM Libro JOIN (Autore JOIN autore_libri ON autore.id=autori_id) ON\n"
			+ "libro.isbn=libri_isbn WHERE Autore.nome_cognome LIKE %:autore%", nativeQuery=true)
	public List<Libro> findByAutore(@Param("autore") String autore);

}
