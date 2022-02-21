package vezba.kol1_tvprogram_p01;

import java.time.LocalTime;

public class TVEmisija implements Comparable<TVEmisija> {

	
	private LocalTime vreme;
	private String naziv;
	
	
	public LocalTime vreme() 	{ return vreme; }
	public String naziv() 		{ return naziv; }
	
	
	public void setVreme(String vreme) {
		if (vreme == null) return;
		this.vreme = LocalTime.parse(vreme);
	}
	
	
	public void setNaziv(String naziv) {
		if (naziv == null) return;
		this.naziv = naziv;
	}
	
	
	public boolean isComplete() {
		if (naziv == null || vreme == null) return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return vreme + " " + naziv;
	}
	
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o) return true;
		if (o == null) return false;
		
		if (getClass() != o.getClass())
			return false;
		
		TVEmisija e = (TVEmisija) o;
		
		if (!naziv.equalsIgnoreCase(e.naziv) || !vreme.equals(e.vreme))
			return false;
		
		return true;
	}
	
	
	@Override
	public int hashCode() {
		
		if (!isComplete()) return 0;
		
		final int prime = 31;
		int res = 1;
		
		res = res * prime + naziv.hashCode();
		res = res * prime + vreme.hashCode();
		
		return res;
	}
	
	
	@Override
	public int compareTo(TVEmisija o) {
		return vreme.compareTo(o.vreme);
	}
}
