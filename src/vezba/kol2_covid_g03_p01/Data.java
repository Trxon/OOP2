package vezba.kol2_covid_g03_p01;

import java.time.LocalDate;

public class Data {
	
	private int cases;
	private int deaths;
	private LocalDate first;
	
	public Data() { }
	
	public Data(int cases, int deaths, LocalDate first) {
		this.cases = cases;
		this.deaths = deaths;
		this.first = first;
	}
	
	public void add(Podatak p) {
		
		cases += p.getCases();
		deaths += p.getDeaths();
		
		if (first == null || first.compareTo(p.getDate()) > 0)
			first = p.getDate();
	}
	
	public Data join(Data d) {
		
		cases += d.cases;
		deaths += d.deaths;
		
		if (first == null || first.compareTo(d.first) > 0)
			first = d.first;
		
		return this;
	}

	public int getCases() 					{ return cases; 		}
	public void setCases(int cases) 		{ this.cases = cases; 	}

	public int getDeaths() 					{ return deaths; 		}
	public void setDeaths(int deaths) 		{ this.deaths = deaths; }

	public LocalDate getFirst() 			{ return first; 		}
	public void setFirst(LocalDate first) 	{ this.first = first; 	}
	
	public double getDeathPercentage() {
		return cases > 0 ? 100.0 * deaths / cases : 0; 
	}
}
