package vezba.kol2_covid_g02_p02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Podaci implements Serializable {

	
	private List<Zemlja> zemlje;
	private List<Podatak> podaci;
	
	
	public Podaci() {
		
		zemlje = new ArrayList<Zemlja>();
		podaci = new ArrayList<Podatak>();
		
		obradiPodatke(load());
	}


	private void obradiPodatke(String content) {
		
		Pattern p0 = Pattern.compile("(?sm)\\\"dateRep\\\":\\s*?\\\"(?<date>\\d{2}\\/\\d{2}\\/\\d{4})\\\",.*?\\\"cases\\\":\\s*?\\\"(?<cases>.*?)\\\",\\s*?\\\"deaths\\\":\\s*?\\\"(?<deaths>.*?)\\\",\\s*?\\\"countriesAndTerritories\\\":\\s*?\\\"(?<country>.*?)\\\",\\s*?\\\"geoId\\\":\\s*?\\\"(?<geoId>.*?)\\\",\\s*?\\\"countryterritoryCode\\\":\\s*?\\\"(?<code>.*?)\\\",\\s*?\\\"popData2018\\\":\\s*?\\\"(?<pop>.*?)\\\",\\s*?\\\"continentExp\\\":\\s*?\\\"(?<continent>.*?)\\\"");
		Matcher m0 = p0.matcher(content);
		
		while (m0.find()) {
			
			String dateString = m0.group("date").trim();
			String casesString = m0.group("cases").trim();
			String deathsString = m0.group("deaths").trim();
			String country = m0.group("country").trim();
			String geoId = m0.group("geoId").trim();
			String code = m0.group("code").trim();
			String popString = m0.group("pop").trim();
			String continent = m0.group("continent").trim();
			
			Zemlja zemlja = new Zemlja(country, geoId, code, popString, continent);
			
			if (!zemlje.contains(zemlja))
				zemlje.add(zemlja);
			
			Zemlja zRef = zemlje.stream().filter(z -> z.getCountry().equalsIgnoreCase(country)).findFirst().orElse(null);
			
			podaci.add(new Podatak(dateString, casesString, deathsString, zRef));
		}
	}


	private String load() {
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Podaci.class.getResourceAsStream("sars-cov-2.json")))) {
			
			StringBuilder sb = new StringBuilder();
			String line;
			
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
	
	
	public List<String> kontinenti() {
		return zemlje.stream().map(z -> z.getContinent()).distinct().sorted().collect(Collectors.toList());
	}
	
	
	public List<Zemlja> zemlje(String kontinent) {
		return zemlje.stream().filter(z -> z.getContinent().equalsIgnoreCase(kontinent)).collect(Collectors.toList());
	}
}
