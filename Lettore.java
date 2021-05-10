package classi;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class Lettore {
	private String username;
	private char[] password;
	private String email;
	private String nome;
	private String cognome;
	private LocalDate dataNascita;
	private String luogoNascita;
	
	private List<Libro> libriLetti;
	private List<Recensione> recensioniScritte;


}
