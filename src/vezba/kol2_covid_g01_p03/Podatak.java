package vezba.kol2_covid_g01_p03;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Podatak implements Serializable {


	private static final long serialVersionUID = 1L;

	
	private final LocalDate date;
	private final int cases;
	private final int deaths;
	private final Zemlja country;
	
	
	public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");


	public Podatak(String dateString, String casesString, String deathsString, Zemlja country) {
		this.date = LocalDate.parse(dateString, dateFormat);
		this.cases = "".equals(casesString) ? 0 : Integer.parseInt(casesString.trim());
		this.deaths = "".equals(deathsString) ? 0 : Integer.parseInt(deathsString.trim());
		this.country = country;
	}


	public LocalDate getDate() {
		return date;
	}


	public int getCases() {
		return cases;
	}


	public int getDeaths() {
		return deaths;
	}


	public Zemlja getCountry() {
		return country;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cases;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + deaths;
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
		Podatak other = (Podatak) obj;
		if (cases != other.cases)
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
		return true;
	}
	
	
	@Override
	public String toString() {
		return String.format(" %12s | %10d | %10d || %s", date, cases, deaths, country);
	}
}
