package vezba.kol2_covid_g05_p01;

import java.io.Serializable;
import java.time.LocalDate;

public class Data implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	
	private Zemlja country;
	private int cases;
	private int deaths;
	private LocalDate first;
	
	
	public Data() { }
	
	
	public Data(Zemlja country, int cases, int deaths, LocalDate first) {
		this.country = country;
		this.cases = cases;
		this.deaths = deaths;
		this.first = first;
	}
	
	
	public void add(Podatak p) {
		this.cases += p.getCases();
		this.deaths += p.getDeaths();
		
		if (this.first == null || this.first.compareTo(p.getDate()) > 0)
			this.first = p.getDate();
	}
	
	
	public Data join(Data d) {
		this.cases += d.cases;
		this.deaths += d.deaths;
		
		if (this.first == null || this.first.compareTo(d.first) > 0)
			this.first = d.first;
		
		return this;
	}


	public Zemlja getCountry()	{ return country;	}
	public int getCases() 		{ return cases; 	} 
	public int getDeaths() 		{ return deaths; 	} 
	public LocalDate getFirst() { return first; 	} 
	
	
	public double getPercent() {
		return 100.0 * getDeaths() / getCases();
	}
}
