package vezba.kol1_patuljci_p03;

public class Patuljak {

	
	private final String ime;
	private final int ubioGoblina;
	private final int godinaRodjenja;
	private final double iskopaoZlata;

	
	public Patuljak(String ime, int ubioGoblina, int godinaRodjenja, double iskopaoZlata) {

		this.ime = ime;
		this.ubioGoblina = ubioGoblina;
		this.godinaRodjenja = godinaRodjenja;
		this.iskopaoZlata = iskopaoZlata;
	}


	public String getIme() {
		return ime;
	}


	public int getUbioGoblina() {
		return ubioGoblina;
	}


	public int getGodinaRodjenja() {
		return godinaRodjenja;
	}


	public double getIskopaoZlata() {
		return iskopaoZlata;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + godinaRodjenja;
		result = prime * result + ((ime == null) ? 0 : ime.hashCode());
		long temp;
		temp = Double.doubleToLongBits(iskopaoZlata);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ubioGoblina;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
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


	@Override
	public String toString() {
		return "Patuljak [ime=" + ime + ", ubioGoblina=" + ubioGoblina + ", godinaRodjenja=" + godinaRodjenja
				+ ", iskopaoZlata=" + iskopaoZlata + "]";
	}
}
