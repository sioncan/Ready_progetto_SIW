package classi;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class Autore {
	private Long id;
	private String nome;
	private String cognome;
	private LocalDate dataNascita;
	private String luogoNascita;
	private LocalDate dataMorte;
	private String luogoMorte;
	private List<String> generi;
	
	private List<Libro> libriScritti;

}
