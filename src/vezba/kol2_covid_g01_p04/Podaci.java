package vezba.kol2_covid_g01_p04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Podaci {

	
	private List<Zemlja> zemlje;
	private List<Podatak> podaci;
	
	
	public Podaci() {
		load();
	}


	private void load() {
		
		this.zemlje = new ArrayList<Zemlja>();
		this.podaci = new ArrayList<Podatak>();
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Podaci.class.getResourceAsStream("sars-cov-2.csv")))) {
			
			String line = in.readLine();
			StringBuilder sb = new StringBuilder();
			
			while ((line = in.readLine()) != null) {
				
				String[] tokens = line.split(",");
				
				if (tokens[10].equalsIgnoreCase("Other"))
					continue;
				
				Zemlja zemlja = new Zemlja(tokens[6], tokens[7], tokens[8], tokens[9], tokens[10]);
				if (!zemlje.contains(zemlja))
					zemlje.add(zemlja);
				
				Zemlja zRef = zemlje.stream().filter(z -> z.getCountry().equals(zemlja.getCountry())).findFirst().orElse(null);
				
				podaci.add(new Podatak(tokens[4], tokens[5], tokens[0], zRef));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<Zemlja> zemlje() {
		return zemlje;
	}
	
	
	public List<LocalDate> datumi() {
		return podaci.stream().map(Podatak::getDate).distinct().collect(Collectors.toList());
	}
	
	
	public Podatak podaci(Zemlja zemlja, LocalDate datum) {
		return podaci.stream().filter(p -> p.getCountry().equals(zemlja) && p.getDate().equals(datum)).findFirst().orElse(null);
	}
}
