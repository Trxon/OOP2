package vezba.kol1_patuljci_p02;

public class Patuljak {

	
	private String ime;
	private int ubioGoblina;
	private int godinaRodjenja;
	private double iskopaoZlata;
	
	
	public String ime() 			{ return ime; 				}
	public int ubioGoblina() 		{ return ubioGoblina; 		}
	public int godinaRodjenja() 	{ return godinaRodjenja; 	}
	public double iskopaoZlata() 	{ return iskopaoZlata; 		}
	
	
	public void setIme(String ime) {
		if (ime == null) return;
		this.ime = ime.trim();
	}
	
	
	public void setUbioGoblina(String ubioGoblina) {
		if (ubioGoblina == null) return;
		this.ubioGoblina = Integer.parseInt(ubioGoblina.trim());
	}
	
	
	public void setGodinaRodjenja(String godinaRodjenja) {
		if (godinaRodjenja == null) return;
		this.godinaRodjenja = Integer.parseInt(godinaRodjenja.trim());
	}
	
	
	public void setIskopaoZlata(String iskopaoZlata) {
		if (iskopaoZlata == null) return;
		this.iskopaoZlata = Double.parseDouble(iskopaoZlata.trim());
	}
	
	
	public boolean isComplete() {
		
		if (ime() == null || godinaRodjenja() <= 0 || ubioGoblina() <= 0 || iskopaoZlata() <= 0.0)
			return false;
		
		return true;
	}
	
	
	@Override
	public String toString() {
		return String.format("%s (%d), ubio goblina : %d, iskopao zlata : %.2f", 
				ime(), ubioGoblina(), godinaRodjenja(), iskopaoZlata());
	}
	
	
	@Override
	public int hashCode() {
		
		if (!isComplete()) return 0;
		
		final int prime = 31;
		int result = 1;
		
		result = prime * result + godinaRodjenja;
		result = prime * result + ((ime == null) ? 0 : ime.hashCode());
		
		long temp = Double.doubleToLongBits(iskopaoZlata);
		
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ubioGoblina;
		
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) return true;
		if (obj == null) return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Patuljak other = (Patuljak) obj;
		
		if (godinaRodjenja != other.godinaRodjenja)
			return false;
		
		if (ime == null) {
			if (other.ime != null)
				return false;
		} else if (!ime.equals(other.ime))
			return false;
		
		if (Double.doubleToLongBits(iskopaoZlata) != Double.doubleToLongBits(other.iskopaoZlata))
			return false;
		
		if (ubioGoblina != other.ubioGoblina)
			return false;
		
		return true;
	}
}
