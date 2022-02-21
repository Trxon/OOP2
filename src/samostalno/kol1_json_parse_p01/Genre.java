package samostalno.kol1_json_parse_p01;

public enum Genre {

	KIDS		("KIDS")	,
	THRILLER	("THRILLER"),
	ACTION		("ACTION")	,
	DRAMA		("DRAMA")	,
	HORROR		("HORROR")	,
	ANIMATED	("ANIMATED");
	
	private String genre;
	
	private Genre(String genre) {
		this.genre = genre;
	}
	
	public static Genre fromString(String in) {
		for (Genre g : Genre.values())
			if (g.genre.equalsIgnoreCase(in))
				return g;
		
		return null;
	}
	
	@Override
	public String toString() {
		return this.genre;
	}
}
