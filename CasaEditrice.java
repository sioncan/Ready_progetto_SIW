package classi;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class CasaEditrice {
	private Long id;
	private String nome;
	private LocalDate dataCreazione;
	private String paese;
	private String sede;
	private String website;
	
	private List<Libro> libriPubblicati;

}
