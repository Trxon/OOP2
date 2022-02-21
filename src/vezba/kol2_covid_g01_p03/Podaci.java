package vezba.kol2_covid_g01_p03;

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
		readData();
	}


	private void readData() {
		
		List<Zemlja> zemlje = new ArrayList<Zemlja>();
		List<Podatak> podaci = new ArrayList<Podatak>();
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Podaci.class.getResourceAsStream("sars-cov-2.csv")))) {
			
			String line = in.readLine();
			
			while ((line = in.readLine()) != null) {
				
				String[] tokens = line.split(",");
				
				if (tokens[10].equalsIgnoreCase("other"))
					continue;
				
				Zemlja z = new Zemlja(tokens[6], tokens[7], tokens[8], tokens[9], tokens[10]);
				
				if (!zemlje.contains(z))
					zemlje.add(z);
				
				podaci.add(
						new Podatak(tokens[0], tokens[4], tokens[5], 
						zemlje.stream()
								.filter(x -> x.getCountry().equalsIgnoreCase(z.getCountry()))
								.findFirst().get()));
			}
			
			this.zemlje = zemlje;
			this.podaci = podaci;
			
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
		return podaci.stream()
				.filter(p -> p.getDate().equals(datum))
				.filter(p -> p.getCountry().equals(zemlja))
				.findFirst().orElse(null);
	}
	
	
	public Zemlja zemljaByString(String z) {
		
		for (Zemlja zemlja : zemlje)
			if (zemlja.getCountry().equalsIgnoreCase(z))
				return zemlja;
			else if (zemlja.getGeoId().equalsIgnoreCase(z))
				return zemlja;
			else if (zemlja.getCode().equalsIgnoreCase(z))
				return zemlja;
		
		return null;
	}
}
