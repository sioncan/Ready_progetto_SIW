package classi;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class Libro {
	private String isbn;
	private String titolo;
	private String sinossi;
	private LocalDate dataPubblicazione;
	private List<String> generi;
	
	private List<Autore> autori;
	private List<Lettore> lettori;
	private List<Recensione> recensioni;
	private CasaEditrice casaEditrice;
	
}
