package vezba.kol2_covid_g05_p02;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Podatak implements Serializable {

	
	private static final long serialVersionUID = 1L;

	
	private final LocalDate date;
	private final int cases;
	private final int deaths;
	
	
	public Podatak(String dateString, String casesString, String deathsString) {
		this.date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.cases = "".equals(casesString) ? 0 : Integer.parseInt(casesString.trim());
		this.deaths = "".equals(deathsString) ? 0 : Integer.parseInt(deathsString.trim());
	}


	public LocalDate getDate() 					{ return date; 			} 
	public int getCases() 						{ return cases; 		} 
	public int getDeaths() 						{ return deaths; 		}
	
	
	@Override
	public String toString() {
		return String.format("%s | %d | %d", date, cases, deaths);
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
