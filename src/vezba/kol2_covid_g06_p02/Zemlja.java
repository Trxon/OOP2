package vezba.kol2_covid_g06_p02;

public class Zemlja {

	
	private final String country;
	private final String geoId;
	private final String code;
	private final int popData;
	private final String continent;
	
	
	public Zemlja(String country, String geoId, String code, String popDataString, String continent) {
		this.country = country;
		this.geoId = geoId;
		this.code = code;
		this.popData = "".equals(popDataString) ? 0 : Integer.parseInt(popDataString);
		this.continent = continent;
	}


	public String getCountry() {
		return country;
	}


	public String getGeoId() {
		return geoId;
	}


	public String getCode() {
		return code;
	}


	public int getPopData() {
		return popData;
	}


	public String getContinent() {
		return continent;
	}
	
	
	@Override
	public String toString() {
		return String.format("%s %s %s %d %s", country, geoId, code, popData, continent);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((continent == null) ? 0 : continent.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((geoId == null) ? 0 : geoId.hashCode());
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
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
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
		if (geoId == null) {
			if (other.geoId != null)
				return false;
		} else if (!geoId.equals(other.geoId))
			return false;
		if (popData != other.popData)
			return false;
		return true;
	}
}
