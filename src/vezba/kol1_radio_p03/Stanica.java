package vezba.kol1_radio_p03;

public class Stanica {

	
	private double frek;
	private String naziv;
	private String drzava;
	private Zanr zanr;
	
	
	public double frek() { return frek; }
	public String naziv() { return naziv; }
	public String drzava() { return drzava; }
	public Zanr zanr() { return zanr; }
	
	
	public void setFrek(String frek) {
		if (frek == null) return;
		this.frek = Double.parseDouble(frek.trim());
	}
	
	
	public void setNaziv(String naziv) {
		if (naziv == null) return;
		this.naziv = naziv;
	}
	
	
	public void setDrzava(String drzava) {
		if (drzava == null) return;
		this.drzava = drzava;
	}
	
	
	public void setZanr(String zanr) {
		if (zanr == null) return;
		this.zanr = Zanr.fromString(zanr);
	}
	
	
	@Override
	public String toString() {
		return String.format("%.1f,%s,%s,%s", frek(), naziv(), drzava(), zanr());
	}	
}
