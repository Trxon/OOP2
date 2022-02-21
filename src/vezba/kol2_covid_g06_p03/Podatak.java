package vezba.kol2_covid_g06_p03;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Podatak implements Comparable<Podatak> {

	
	private final int cases;
	private final int deaths;
	private final LocalDate date;
	
	
	public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");


	public Podatak(String casesString, String deathsString, String dateString) {
		super();
		this.cases = "".equals(casesString) ? 0 : Integer.parseInt(casesString.trim());
		this.deaths = "".equals(deathsString) ? 0 : Integer.parseInt(deathsString.trim());
		this.date = LocalDate.parse(dateString, dateFormat);
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
		return String.format(" %s | %6d | %6d", date, cases, deaths);
	}


	@Override
	public int compareTo(Podatak o) {
		return date.compareTo(o.date);
	}
}
