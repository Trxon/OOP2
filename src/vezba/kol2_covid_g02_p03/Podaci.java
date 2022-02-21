package vezba.kol2_covid_g02_p03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Podaci {

	
	private Map<String, List<Zemlja>> mapa;
	
	
	public Podaci() {
		this.mapa = loadData();
	}


	private Map<String, List<Zemlja>> loadData() {
		
		Map<String, List<Zemlja>> mapa = new HashMap<String, List<Zemlja>>();
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Podaci.class.getResourceAsStream("sars-cov-2.json")))) {
			
			String line = in.readLine();
			StringBuilder sb = new StringBuilder();
			
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			
			Pattern p0 = Pattern.compile("\\\"dateRep\\\":\\s*?\\\"(?<date>.*?)\\\",\\s*?\\\"day\\\":\\s*?\\\"(?<day>.*?)\\\",\\s*?\\\"month\\\":\\s*?\\\"(?<month>.*?)\\\",\\s*?\\\"year\\\":\\s*?\\\"(?<year>.*?)\\\",\\s*?\\\"cases\\\":\\s*?\\\"(?<cases>.*?)\\\",\\s*?\\\"deaths\\\":\\s*?\\\"(?<deaths>.*?)\\\",\\s*?\\\"countriesAndTerritories\\\":\\s*?\\\"(?<country>.*?)\\\",\\s*?\\\"geoId\\\":\\s*?\\\"(?<geoId>.*?)\\\",\\s*?\\\"countryterritoryCode\\\":\\s*?\\\"(?<code>.*?)\\\",\\s*?\\\"popData2018\\\":\\s*?\\\"(?<popData>.*?)\\\",\\s*?\\\"continentExp\\\":\\s*?\\\"(?<continent>.*?)\\\"");
			Matcher m0 = p0.matcher(sb.toString());
			
			while (m0.find()) {
				
				String country = m0.group("country");
				String continent = m0.group("continent");
				String popDataString = m0.group("popData");
				String casesString = m0.group("cases");
				String deathsString = m0.group("deaths");
				String dateString = m0.group("date");
				
				if (continent.equalsIgnoreCase("other"))
					continue;
				
				if (mapa.containsKey(continent)) {
					
					boolean found = false;
					
					for (Zemlja z : mapa.get(continent))
						if (z.getCountry().equalsIgnoreCase(country)) {
							
							found = true;
							break;
						}
					
					if (found) continue;
					
					mapa.get(continent).add(new Zemlja(country, continent, popDataString, casesString, deathsString, dateString));
				} else {
					
					mapa.put(continent, new ArrayList<Zemlja>());
					mapa.get(continent).add(new Zemlja(country, continent, popDataString, casesString, deathsString, dateString));
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return mapa;
	}


	public Map<String, List<Zemlja>> getMapa() {
		return mapa;
	}
	
	
	public static void main(String[] args) {
		
		Podaci p = new Podaci();
		p.mapa.entrySet().stream()
			.map(e -> String.format("%s%n%s", 
					e.getKey(), 
					e.getValue().stream()
							.map(z -> String.format("\t%s", z))
							.reduce("", (s1, s2) -> "".equals(s1) ? s2 : s1 + "\n" + s2)))
			.forEach(System.out::println);
		
		p.getMapa().keySet().stream().sorted().collect(Collectors.toList());
		
		System.out.println(p.getMapa().keySet().stream().sorted().collect(Collectors.toList()));
	}
}
