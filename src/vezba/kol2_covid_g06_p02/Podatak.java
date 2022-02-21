package vezba.kol2_covid_g06_p02;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Podatak {

	
	private final LocalDate date;
	private final int cases;
	private final int deaths;
	
	
	public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	
	public Podatak(String dateString, String casesString, String deathsString) {
		this.date = LocalDate.parse(dateString, dateFormat);
		this.cases = "".equals(casesString) ? 0 : Integer.parseInt(casesString);
		this.deaths = "".equals(deathsString) ? 0 : Integer.parseInt(deathsString);
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
	
	
	@Override
	public String toString() {
		return String.format("%10s %10d %10d", date, cases, deaths);
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
	
	
	
}
