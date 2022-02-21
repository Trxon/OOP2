package vezba.kol2_vesanje_rmi_p01;

import java.io.Serializable;

public class Poruka implements Serializable {

	private static final long serialVersionUID = 1L;

	private Odgovor odgovor;
	private String recTrenutno;
	
	public Poruka(Odgovor odgovor, String recTrenutno) {
		this.odgovor = odgovor;
		this.recTrenutno = recTrenutno;
	}

	public Odgovor odgovor() 	{ return odgovor; 		}
	public String recTrenutno() { return recTrenutno; 	}
}
