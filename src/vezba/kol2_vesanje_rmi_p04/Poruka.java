package vezba.kol2_vesanje_rmi_p04;

import java.io.Serializable;

public class Poruka implements Serializable {

	private static final long serialVersionUID = 1L;

	private Odgovor odgovor;
	private String recTrenutno;
	
	public Poruka(Odgovor odgovor, String recTrenutno) {
		this.odgovor = odgovor;
		this.recTrenutno = recTrenutno;
	}
	
	public String getRecTrenutno() {
		return recTrenutno;
	}
	
	public Odgovor getOdgovor() {
		return odgovor;
	}
}
