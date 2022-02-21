package vezba.kol2_covid_g06_p03;

public class Zemlja {

	
	private final String country;
	private final String continent;
	private final int popData;
	
	
	public Zemlja(String country, String continent, String popDataString) {
		super();
		this.country = country;
		this.continent = continent;
		this.popData = "".equals(popDataString) ? 0 : Integer.parseInt(popDataString.trim());
	}


	public String getCountry() {
		return country;
	}


	public String getContinent() {
		return continent;
	}


	public int getPopData() {
		return popData;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((continent == null) ? 0 : continent.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + popData;
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
		Zemlja other = (Zemlja) obj;
		if (continent == null) {
			if (other.continent != null)
				return false;
		} else if (!continent.equals(other.continent))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (popData != other.popData)
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return String.format("%s %s %d", country, continent, popData);
	}
}
