package vezba.kol2_covid_g03_p02;

import java.time.LocalDate;

class Data {
	
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
		this.cases += p.getCases();
		this.deaths += p.getDeaths();
		
		if (this.first == null || first.compareTo(p.getDate()) < 0)
			this.first = p.getDate();
	}
	
	public Data join(Data d) {
		this.cases += d.getCases();
		this.deaths += d.getDeaths();
		
		if (this.first == null || this.first.compareTo(d.first) < 0)
			this.first = d.getFirst();
		
		return this;
	}

	public int getCases() {
		return cases;
	}

	public void setCases(int cases) {
		this.cases = cases;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public LocalDate getFirst() {
		return first;
	}

	public void setFirst(LocalDate first) {
		this.first = first;
	}
	
	public double getDeathPercentage() {
		return 100.0 * this.deaths / this.cases;
	}
}
