package vezba.kol2_covid_g01_p02;

import java.io.Serializable;

public class Poruka implements Serializable {


	private static final long serialVersionUID = 1L;

	
	private Object sadrzaj;
	
	
	public Poruka(Object sadrzaj) {
		this.sadrzaj = sadrzaj;
	}
	
	
	public Object getSadrzaj() 	{ return sadrzaj;	}
}
