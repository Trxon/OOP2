package vezba.kol2_covid_g02_p03;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Zemlja implements Serializable {

	
	private static final long serialVersionUID = 1L;

	
	private final String country;
	private final String continent;
	private final int popData;
	private int cases;
	private int deaths;
	private LocalDate date;
	
	
	private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");


	public Zemlja(String country, String continent, String popDataString, String casesString, String deathsString, String dateString) {
		this.country = country;
		this.continent = continent;
		this.popData = "".equals(popDataString) ? 0 : Integer.parseInt(popDataString.trim());
		this.cases = "".equals(casesString) ? 0 : Integer.parseInt(casesString.trim());
		this.deaths = "".equals(deathsString) ? 0 : Integer.parseInt(deathsString.trim());
		this.date = LocalDate.parse(dateString, dateFormat);
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


	public int getCases() {
		return cases;
	}


	public int getDeaths() {
		return deaths;
	}


	public LocalDate getDate() {
		return date;
	}


	public DateTimeFormatter getDateFormat() {
		return dateFormat;
	}
	
	
	public void updateDate(String dateString) {
		this.date = LocalDate.parse(dateString, dateFormat);
	}
	
	
	public void updateCases(String casesString) {
		this.cases = "".equals(casesString) ? 0 : Integer.parseInt(casesString.trim()); 
	}
	
	
	public void updateDeaths(String deathsString) {
		this.deaths = "".equals(deathsString) ? 0 : Integer.parseInt(deathsString.trim()); 
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
	
	
	public double getCasesPercentage() {
		return 100.0 * cases / popData;
	}
	
	
	public String showData() {
		return String.format("%40s | %10d | %10d | %10d | %6.2f", country, popData, cases, deaths, getCasesPercentage());
	}
	
	
	@Override
	public String toString() {
		return String.format("%10s %40s %10d || %s %10d %10d", continent, country, popData, date, cases, deaths);
	}
}
