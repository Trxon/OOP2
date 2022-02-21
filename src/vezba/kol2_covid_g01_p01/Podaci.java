package vezba.kol2_covid_g01_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Podaci {
	
	
	public List<Zemlja> zemlje;
	public List<Podatak> podaci;
	
	
	public Podaci() {
		
		zemlje = new ArrayList<Zemlja>();
		podaci = new ArrayList<Podatak>();
		
		String text = load();
		obradiPodatke(text);
	}

	
	public List<Zemlja> zemlje() {
		return zemlje;
	}
	
	
	public List<LocalDate> datumi() {
		return podaci.stream().map(Podatak::getDate).collect(Collectors.toList());
	}
	
	
	public Podatak podaci(Zemlja zemlja, LocalDate datum) {
		return podaci.stream()
				.filter(p -> p.getCountry().equals(zemlja) && p.getDate().equals(datum))
				.findFirst().orElse(null);
	}

	
	private void obradiPodatke(String text) {
		
		for (String s : text.split("\n")) {
			
			String[] tokens = s.split(",");
			
			Zemlja z = new Zemlja(tokens[6], tokens[7], tokens[8], tokens[9], tokens[10]);
			
			if (!zemlje.contains(z))
				zemlje.add(z);
			
			Zemlja zRef = zemlje.stream()
					.filter(x -> x.getCountry().equals(z.getCountry()))
					.findFirst().orElse(null);
			
			podaci.add(new Podatak(tokens[0], tokens[4], tokens[5], zRef));
		}
	}


	private static String load() {
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Podaci.class.getResourceAsStream("sars-cov-2.csv")))) {
			
			StringBuilder sb = new StringBuilder();
			
			String line = in.readLine();
			line = null;
			
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
