package vezba.kol1_radio_p04;

public enum Zanr {

	POP("pop"),
	ROCK("rock"),
	FOLK("folk"),
	SPIRITUAL("spiritual"),
	MULTI("multi");
	
	private String zanr;
	
	private Zanr(String zanr) {
		this.zanr = zanr;
	}
	
	public static Zanr fromString(String s) {
		
		for (Zanr z : Zanr.values())
			if (z.toString().equalsIgnoreCase(s))
				return z;
		
		return Zanr.MULTI;
	}
}
