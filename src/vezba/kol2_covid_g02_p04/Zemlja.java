package vezba.kol2_covid_g02_p04;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Zemlja {

	
	private final String country;
	private final String continent;
	private final int popData;
	private int cases;
	private int deaths;
	private LocalDate date;
	
	
	private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");


	public Zemlja(String country, String continent, String popDataString, String casesString, String deathsString, String dateString) {
		this.country = country;
		this.continent = continent;
		this.popData = "".equals(popDataString) ? 0 : Integer.parseInt(popDataString.trim());
		this.cases = "".equals(casesString) ? 0 : Integer.parseInt(casesString.trim());
		this.deaths = "".equals(deathsString) ? 0 : Integer.parseInt(deathsString.trim());
		this.date = LocalDate.parse(dateString, dateFormat);
	}


	public int getDeaths() {
		return deaths;
	}


	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public DateTimeFormatter getDateFormat() {
		return dateFormat;
	}


	public void setDateFormat(DateTimeFormatter dateFormat) {
		this.dateFormat = dateFormat;
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
	
	
	public double getCasesPercentage() {
		return 100.0 * cases / popData;
	}
	
	
	public void updateCases(String casesString) {
		this.cases = "".equals(casesString) ? 0 : Integer.parseInt(casesString.trim());
	}
	
	
	public void updateDeaths(String deathsString) {
		this.deaths = "".equals(deathsString) ? 0 : Integer.parseInt(deathsString.trim());
	}
	
	
	public void updateDate(String dateString) {
		this.date = LocalDate.parse(dateString, dateFormat);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cases;
		result = prime * result + ((continent == null) ? 0 : continent.hashCode());
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
		if (continent == null) {
			if (other.continent != null)
				return false;
		} else if (!continent.equals(other.continent))
			return false;
		if (popData != other.popData)
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return String.format("%s %d | %d %d %.2f (on %s)", country, popData, cases, deaths, getCasesPercentage(), date);
	}
}
