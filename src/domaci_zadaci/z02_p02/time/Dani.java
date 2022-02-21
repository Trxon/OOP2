package domaci_zadaci.z02_p02.time;

public enum Dani {
	
	PON("MO", "PON"),
	UTO("TU", "UTO"),
	SRE("WE", "SRE"),
	CET("TH", "CET"),
	PET("FR", "PET"),
	SUB("SA", "SUB"),
	NED("SU", "NED");
	
	private String eng;
	private String srp;
	
	private Dani(String eng, String srp) {
		this.eng = eng;
		this.srp = srp;
	}
	
	public static Dani naSrpskom(String naEngleskom) {
		
		for (Dani d : Dani.values())
			if (naEngleskom.equalsIgnoreCase(d.eng))
				return d;
		
		return null;
	}
}
