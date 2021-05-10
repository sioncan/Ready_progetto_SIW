package classi;

import lombok.Data;

@Data
public class Recensione {
	private Long id;
	private int votazione;
	private String titolo;
	private String testo;
	
	private Autore autore;
	private Libro libroRecensito;

}
