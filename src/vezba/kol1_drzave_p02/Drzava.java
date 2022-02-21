package vezba.kol1_drzave_p02;

public class Drzava {

	
	private String ime;
	private String kontinent;
	private long brStanovnika;
	private double budzet;
	
	
	public Drzava(String ime, String kontinent, long brStanovnika, double budzet) {
		
		if (ime == null)
			throw new IllegalArgumentException("ime -> ");
		this.ime = ime;
		
		if (kontinent == null)
			throw new IllegalArgumentException("kontinent -> ");
		this.kontinent = kontinent;
		
		this.brStanovnika = brStanovnika;
		this.budzet = budzet;
	}
	
	
	public String ime() 		{ return ime; 			}
	public String kontinent() 	{ return kontinent; 	}
	public long brStanovnika() 	{ return brStanovnika; 	}
	public double budzet() 		{ return budzet; 		}
	
	
	public static Drzava fromString(String drzava) {
		
		String[] tokens = drzava.split(";");
		
		if (tokens.length != 4)
			throw new IllegalArgumentException("linija -> ");
		
		return fromString(tokens[0], tokens[1], tokens[2], tokens[3]);
	}


	private static Drzava fromString(String... tokens) {
		
		try {
			return new Drzava(tokens[0], tokens[1], Long.parseLong(tokens[2].trim()), Double.parseDouble(tokens[3].trim()));
		} catch (NullPointerException | NumberFormatException | IndexOutOfBoundsException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	
	@Override
	public String toString() {
		return ime + " " + kontinent + " " + brStanovnika + " " + budzet;
	}
}
