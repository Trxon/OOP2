package vezba.kol2_covid_g02_p04;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Podaci {

	
	private Map<String, List<Zemlja>> mapa;
	
	
	public Podaci() {
		load();
	}


	private void load() {
		
		this.mapa = new HashMap<String, List<Zemlja>>();
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Podaci.class.getResourceAsStream("sars-cov-2.json")))) {
			
			String line = in.readLine();
			StringBuilder sb = new StringBuilder();
			
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			
			Pattern p0 = Pattern.compile("(?sm)\\\"dateRep\\\":\\s*?\\\"(?<date>.*?)\\\",.*?\\\"cases\\\":\\s*?\\\"(?<cases>\\d*?)\\\",\\s*?\\\"deaths\\\":\\s*?\\\"(?<deaths>\\d*?)\\\",\\s*?\\\"countriesAndTerritories\\\":\\s*?\\\"(?<country>.*?)\\\",\\s*?\\\"geoId\\\":\\s*?\\\"(?<geoId>.*?)\\\",\\s*?\\\"countryterritoryCode\\\":\\s*?\\\"(?<code>.*?)\\\",\\s*?\\\"popData2018\\\":\\s*?\\\"(?<popData>\\d*?)\\\",\\s*?\\\"continentExp\\\":\\s*?\\\"(?<continent>.*?)\\\"");
			Matcher m0 = p0.matcher(sb.toString());
			
			while (m0.find()) {
				
				String dateString = m0.group("date");
				String casesString = m0.group("cases");
				String deathsString = m0.group("deaths");
				String country = m0.group("country");
				String popDataString = m0.group("popData");
				String continent = m0.group("continent");
				
				if (continent.equalsIgnoreCase("Other"))
					continue;
				
				LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				
				if (!mapa.containsKey(continent))
					mapa.put(continent, new ArrayList<Zemlja>());
				
				List<Zemlja> zemlje = mapa.get(continent);
				
				Zemlja found = null;
				
				for (Zemlja z : zemlje)
					if (z.getCountry().equalsIgnoreCase(country))
						found = z;
				
				if (found != null) {
					
					if (found.getDate().compareTo(date) < 0) {
						found.updateCases(casesString);
						found.updateDeaths(deathsString);
						found.updateDate(dateString);
					}
				} else {
					
					mapa.get(continent).add(new Zemlja(
							country, continent, popDataString, casesString, deathsString, dateString));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public List<String> kontinenti() {
		return mapa.keySet().stream().sorted().collect(Collectors.toList());
	}
	
	
	public List<Zemlja> zemlje(String kontinent) {
		List<Zemlja> out = mapa.get(kontinent);
		out.sort(Comparator.comparingDouble(Zemlja::getCasesPercentage));
		return out;
	}
	
	
	public static void main(String[] args) {
		
		Podaci p = new Podaci();
		System.out.println(p.mapa);
		System.out.println(p.kontinenti());
		
		List<Zemlja> lista = p.zemlje("Europe");
		lista.stream().forEach(System.out::println);
	}
}
