package vezba.kol2_covid_g04_p02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Podaci {

	
	private List<String> strings;
	
	
	public Podaci() {
		this.strings = load();
	}
	

	private List<String> load() {
		
		List<String> lista = new ArrayList<String>();
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Podaci.class.getResourceAsStream("sars-cov-2.csv")))) {
			
			String linija;
			
			while ((linija = in.readLine()) != null)
				lista.add(linija);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	
	
	private boolean matchesCountry(String line, String input) {
		
		String[] tokens = line.split(",");
		
		if (tokens[6].toUpperCase().equals(input.toUpperCase()))
			return true;
		
		if (tokens[7].toUpperCase().equals(input.toUpperCase()))
			return true;
		
		if (tokens[8].toUpperCase().equals(input.toUpperCase()))
			return true;
		
		return false;
	}
	
	
	public String getZemlja(String input) {
		
		for (String s : strings)
			if (matchesCountry(s, input)) {
				
				String[] tokens = s.split(",");
				
				return String.format("%20s %10s %10s", tokens[6], tokens[10], tokens[9]);
			}
		
		return "";
	}
	
	
	public List<String> getStrings(String input) {
		
		return strings.stream()
				.filter(s -> matchesCountry(s, input))
				.map(s -> {
					String[] tokens = s.split(",");
					return String.format("%20s %10s %10s", tokens[0], tokens[4], tokens[5]);
				})
				.collect(Collectors.toList());
	}
}
