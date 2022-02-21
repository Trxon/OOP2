package vezba.kol1_radio_p04;

public class Stanica {

	
	private double frek;
	private String naziv;
	private String drzava;
	private Zanr zanr;
	
	
	public Stanica(double frek, String naziv, String drzava, Zanr zanr) {
		this.frek = frek;
		this.naziv = naziv;
		this.drzava = drzava;
		this.zanr = zanr;
	}


	public double getFrek() {
		return frek;
	}


	public String getNaziv() {
		return naziv;
	}


	public String getDrzava() {
		return drzava;
	}


	public Zanr getZanr() {
		return zanr;
	}


	public void setFrek(double frek) {
		this.frek = frek;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}


	public void setZanr(Zanr zanr) {
		this.zanr = zanr;
	}


	@Override
	public String toString() {
		return "Stanica [frek=" + frek + ", naziv=" + naziv + ", drzava=" + drzava + ", zanr=" + zanr + "]";
	}
	
	
}
