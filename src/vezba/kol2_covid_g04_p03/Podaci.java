package vezba.kol2_covid_g04_p03;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Podaci {

	
	private List<String> podaci;
	
	
	public Podaci() {
		load();
	}


	private void load() {
		
		this.podaci = new ArrayList<String>();
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Podaci.class.getResourceAsStream("sars-cov-2.csv")))) {
			
			String linija = in.readLine();
			
			while ((linija = in.readLine()) != null)
				this.podaci.add(linija);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public List<String> podaciZaZemlju(String input) {
		
		List<String> podaciZaZemlju = podaci.stream()
				.filter(s -> {
					
					String[] tokens = s.split(",");
					if (input.equalsIgnoreCase(tokens[6]) || input.equalsIgnoreCase(tokens[7]) || input.equalsIgnoreCase(tokens[8]))
						return true;
					
					return false;
				}).collect(Collectors.toList());
		
		if (podaciZaZemlju == null || podaciZaZemlju.size() == 0)
			return null;
		
		class Podatak implements Comparable<Podatak> {
			
			int cases, deaths;
			LocalDate date;

			@Override
			public int compareTo(Podatak o) {
				return o.date.compareTo(date);
			}
			
			@Override
			public String toString() {
				return String.format("%s %d %d", date, cases, deaths);
			}
		}
		
		List<Podatak> podaci = podaciZaZemlju.stream()
				.map(s -> {
					
					String[] tokens = s.split(",");
					
					Podatak p = new Podatak();
					
					p.cases = "".equals(tokens[4]) ? 0 : Integer.parseInt(tokens[4].trim());
					p.deaths = "".equals(tokens[5]) ? 0 : Integer.parseInt(tokens[5].trim());
					p.date = LocalDate.parse(tokens[0].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					
					return p;
					
				}).sorted()
				.collect(Collectors.toList());
		
		List<String> formatirano = new ArrayList<String>();
		
		String[] tokens = podaciZaZemlju.get(0).split(",");
		
		formatirano.add(String.format("%s %s %s", tokens[6], tokens[10], tokens[8]));
		
		for (Podatak p : podaci)
			formatirano.add(p.toString());
		
		formatirano.add("");
		
		return formatirano;
	}
	
	
	public static void main(String[] args) {
		
		Podaci p = new Podaci();
		
		List<String> f = p.podaciZaZemlju("Serbia");
		f.stream().forEach(System.out::println);
	}
}
