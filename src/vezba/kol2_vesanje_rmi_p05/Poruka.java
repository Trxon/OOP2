package vezba.kol2_vesanje_rmi_p05;

import java.io.Serializable;

public class Poruka implements Serializable {


	private static final long serialVersionUID = 1L;

	
	private Odgovor odgovor;
	private String recTrenutno;
	
	
	public Poruka(Odgovor odgovor, String recTrenutno) {
		this.odgovor = odgovor;
		this.recTrenutno = recTrenutno;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Odgovor getOdgovor() 	{ return odgovor; 		}
	public String getRecTrenutno() 	{ return recTrenutno; 	}
}
