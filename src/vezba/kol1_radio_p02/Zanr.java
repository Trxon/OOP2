package vezba.kol1_radio_p02;

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
	
	public static Zanr fromString(String in) {
		for (Zanr z : Zanr.values())
			if (z.zanr.equalsIgnoreCase(in))
				return z;
		
		return null;
	}
	
	@Override
	public String toString() {
		return zanr;
	}
}
