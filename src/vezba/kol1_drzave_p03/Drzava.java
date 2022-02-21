package vezba.kol1_drzave_p03;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Drzava {
	
	
	private static Pattern PATTERN_LINE = Pattern.compile("^(?<zemlja>[^;]+)\\;(?<kontinent>[^;]+);(?<stanovnici>[^;]+);(?<budzet>[\\s\\S]+?)$");

	
	private String 	zemlja,
					kontinent;
	private long	brojStanovnika;
	private double	budzet;
	
	
	public String 	zemlja() 			{ return zemlja; 			}
	public String 	kontinent() 		{ return kontinent; 		}
	public long 	brojStanovnika() 	{ return brojStanovnika; 	}
	public double 	budzet() 			{ return budzet; 			}
	
	
	public void setZemlja(String zemlja) {
		if (zemlja == null) return;
		this.zemlja = zemlja;
	}
	
	
	public void setKontinent(String kontinent) {
		if (kontinent == null) return;
		this.kontinent = kontinent;
	}
	
	
	public void setBrojStanovnika(String brojStanovnika) {
		if (brojStanovnika == null) return;
		this.brojStanovnika = Long.parseLong(brojStanovnika.trim());
	}
	
	
	public void setBudzet(String budzet) {
		if (budzet == null) return;
		this.budzet = Double.parseDouble(budzet.trim());
	}
	
	
	@Override
	public String toString() {
		return String.format("%s %s %d %.2f", zemlja(), kontinent().toUpperCase(), brojStanovnika(), budzet());
	}
	
	
	public String toStringFormatted() {
		return String.format("%30s %20s %25d %23.2f", zemlja(), kontinent().toUpperCase(), brojStanovnika(), budzet());
	}
	
	
	public static Drzava fromString(String line) {
		
		Matcher matcherLine = PATTERN_LINE.matcher(line);
		
		if (matcherLine.find()) {
			
			Drzava d = new Drzava();
			
			d.setZemlja(matcherLine.group("zemlja"));
			d.setKontinent(matcherLine.group("kontinent"));
			d.setBrojStanovnika(matcherLine.group("stanovnici"));
			d.setBudzet(matcherLine.group("budzet"));
			
			return d;
		}
		
		return null;
	}
	
	
	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		
		result = prime * result + (int) (brojStanovnika ^ (brojStanovnika >>> 32));
		long temp = Double.doubleToLongBits(budzet);
		
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((kontinent == null) ? 0 : kontinent.hashCode());
		result = prime * result + ((zemlja == null) ? 0 : zemlja.hashCode());
		
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) return true;
		if (obj == null) return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Drzava other = (Drzava) obj;
		
		if (brojStanovnika != other.brojStanovnika)
			return false;
		
		if (Double.doubleToLongBits(budzet) != Double.doubleToLongBits(other.budzet))
			return false;
		
		if (kontinent == null) {
			if (other.kontinent != null)
				return false;
		} else if (!kontinent.equals(other.kontinent))
			return false;
		
		if (zemlja == null) {
			if (other.zemlja != null)
				return false;
		} else if (!zemlja.equals(other.zemlja))
			return false;
		
		return true;
	}
}
