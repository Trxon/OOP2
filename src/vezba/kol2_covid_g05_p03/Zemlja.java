package vezba.kol2_covid_g05_p03;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Zemlja {

	
	private final String country;
	private final String geoId;
	private final String code;
	private final int popData;
	private final String continent;
	
	private final int cases;
	private final int deaths;
	private final LocalDate date;
	
	
	public final static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");


	public Zemlja(String country, String geoId, String code, String popDataString, String continent, String casesString, 
			String deathsString, String dateString) {
		super();
		this.country = country;
		this.geoId = geoId;
		this.code = code;
		this.popData = "".equals(popDataString) ? 0 : Integer.parseInt(popDataString.trim());
		this.continent = continent;
		this.cases = "".equals(casesString) ? 0 : Integer.parseInt(casesString.trim());
		this.deaths = "".equals(deathsString) ? 0 : Integer.parseInt(deathsString.trim());
		this.date = LocalDate.parse(dateString, dateFormat);
	}
	
	
	public void add() {
		
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


	public int getCases() {
		return cases;
	}


	public int getDeaths() {
		return deaths;
	}


	public LocalDate getDate() {
		return date;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cases;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((continent == null) ? 0 : continent.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + deaths;
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
		if (cases != other.cases)
			return false;
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
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (deaths != other.deaths)
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
	
	
	@Override
	public String toString() {
		return String.format("%s %s %s %d %s | %d %d %s", country, geoId, code, popData, continent, cases, deaths, date);
	}
}
