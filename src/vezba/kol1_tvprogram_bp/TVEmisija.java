package vezba.kol1_tvprogram_bp;

public class TVEmisija implements Comparable<TVEmisija> {

	private String naziv;
	private String vreme;
	
	public TVEmisija() {
		
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getVreme() {
		return vreme;
	}

	public void setVreme(String vreme) {
		this.vreme = vreme;
	}
	
	public boolean jeOk() {
		return naziv != null && vreme != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
		result = prime * result + ((vreme == null) ? 0 : vreme.hashCode());
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
		TVEmisija other = (TVEmisija) obj;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		if (vreme == null) {
			if (other.vreme != null)
				return false;
		} else if (!vreme.equals(other.vreme))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return naziv + " " + vreme;
	}

	@Override
	public int compareTo(TVEmisija emisija) {
		int rez = this.vreme.compareTo(emisija.vreme);
		return rez;
	}
	
}
