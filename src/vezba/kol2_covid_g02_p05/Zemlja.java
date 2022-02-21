package vezba.kol2_covid_g02_p05;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Zemlja {

	
	private final String country;
	private final int popData;
	private final String continent;
	
	private int cases;
	private int deaths;
	private LocalDate date;
	
	
	private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	
	public Zemlja(String country, String popDataString, String continent, String casesString, String deathsString, String dateString) {
		this.country = country.trim();
		this.popData = "".equals(popDataString) ? 0 : Integer.parseInt(popDataString.trim());
		this.continent = continent.trim();
		this.cases = "".equals(casesString) ? 0 : Integer.parseInt(casesString.trim());
		this.deaths = "".equals(deathsString) ? 0 : Integer.parseInt(deathsString.trim());
		this.date = LocalDate.parse(dateString, dateFormat);
	}


	public String getCountry() {
		return country;
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


	public static DateTimeFormatter getDateFormat() {
		return dateFormat;
	}


	public void setCases(int cases) {
		this.cases = cases;
	}


	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}


	public void setDate(LocalDate date) {
		this.date = date;
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
		return "Zemlja [country=" + country + ", popData=" + popData + ", continent=" + continent + ", cases=" + cases
				+ ", deaths=" + deaths + ", date=" + date + "]";
	}
	
	
	
}
