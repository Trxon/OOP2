package vezba.kol1_drzave_p04;

public class Drzava {

	
	private String ime;
	private String kontinent;
	private long brStanovnika;
	private double budzet;
	
	
	public Drzava(String ime, String kontinent, long brStanovnika, double budzet) {
		this.ime = ime;
		this.kontinent = kontinent;
		this.brStanovnika = brStanovnika;
		this.budzet = budzet;
	}
	
	
	public static Drzava fromString(String drzava) {
		
		String[] tokens = drzava.split(";");
		
		return new Drzava(
				tokens[0].trim(), 
				tokens[1].trim(), 
				Long.parseLong(tokens[2].trim()), 
				Double.parseDouble(tokens[3].trim()));
	}


	public String getIme() {
		return ime;
	}


	public String getKontinent() {
		return kontinent;
	}


	public long getBrStanovnika() {
		return brStanovnika;
	}


	public double getBudzet() {
		return budzet;
	}


	public void setIme(String ime) {
		this.ime = ime;
	}


	public void setKontinent(String kontinent) {
		this.kontinent = kontinent;
	}


	public void setBrStanovnika(long brStanovnika) {
		this.brStanovnika = brStanovnika;
	}


	public void setBudzet(double budzet) {
		this.budzet = budzet;
	}


	@Override
	public String toString() {
		return "Drzava [ime=" + ime + ", kontinent=" + kontinent + ", brStanovnika=" + brStanovnika + ", budzet="
				+ budzet + "]";
	}
}
