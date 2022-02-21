package vezba.kol2_covid_g01_p02;

import java.io.Serializable;

public class Zemlja implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	
	private final String country;
	private final String geoId;
	private final String countryterritoryCode;
	private final int popData2018;
	private final String continent;
	
	
	public Zemlja(String country, String geoId, String countryterritoryCode, String popData2018String, String continent) {
		this.country = country;
		this.geoId = geoId;
		this.countryterritoryCode = countryterritoryCode;
		this.popData2018 = "".equals(popData2018String) ? 0 : Integer.parseInt(popData2018String.trim());
		this.continent = continent;
	}


	public String getCountry() 				{ return country; 				}
	public String getGeoId() 				{ return geoId; 				}
	public String getCountryterritoryCode() { return countryterritoryCode; 	}
	public int getPopData2018() 			{ return popData2018; 			}
	public String getContinent() 			{ return continent; 			}
	
	
	@Override
	public String toString() {
		return String.format("%s | %s | %s | %d | %s", country, geoId, countryterritoryCode, popData2018, continent);
	}


	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((continent == null) ? 0 : continent.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((countryterritoryCode == null) ? 0 : countryterritoryCode.hashCode());
		result = prime * result + ((geoId == null) ? 0 : geoId.hashCode());
		result = prime * result + popData2018;
		
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
		
		if (countryterritoryCode == null) {
			if (other.countryterritoryCode != null)
				return false;
		} else if (!countryterritoryCode.equals(other.countryterritoryCode))
			return false;
		
		if (geoId == null) {
			if (other.geoId != null)
				return false;
		} else if (!geoId.equals(other.geoId))
			return false;
		
		if (popData2018 != other.popData2018)
			return false;
		
		return true;
	}
}
