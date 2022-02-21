package vezba.kol2_covid_g02_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

	
	public List<String> kontinenti() {
		return zemlje.stream().map(z -> z.getContinent()).distinct().collect(Collectors.toList());
	}
	
	
	public List<Zemlja> zemlje(String kontinent) {
		return zemlje.stream().filter(z -> z.getContinent().equalsIgnoreCase(kontinent)).collect(Collectors.toList());
	}

	
	private void obradiPodatke(String text) {
		
		Pattern p0 = Pattern.compile("(?sm)\\\"dateRep\\\":\\s*?\\\"(?<datum>[\\s\\S]*?)\\\",.*?\\\"cases\\\":\\s*?\\\"(?<cases>\\d+)\\\",.*?\\\"deaths\\\":\\s*?\\\"(?<deaths>\\d+)\\\",.*?\\\"countriesAndTerritories\\\":\\s*?\\\"(?<country>.*?)\\\",.*?\\\"geoId\\\":\\s*?\\\"(?<geoId>.*?)\\\",.*?\\\"countryterritoryCode\\\":\\s*?\\\"(?<code>.*?)\\\",\\s*?\\\"popData2018\\\":\\s*?\\\"(?<pop>.*?)\\\",\\s*?\\\"continentExp\\\":\\s*?\\\"(?<continent>.*?)\\\"");
		Matcher m0 = p0.matcher(text);
		
		while (m0.find()) {
			
			String datumString = m0.group("datum");
			String casesString = m0.group("cases");
			String deathsString = m0.group("deaths");
			String country = m0.group("country");
			String geoId = m0.group("geoId");
			String countryterritoryCode = m0.group("code");
			String popDataString = m0.group("pop");
			String continent = m0.group("continent");
			
			Zemlja z = new Zemlja(country, geoId, countryterritoryCode, popDataString, continent);
			
			if (!zemlje.contains(z))
				zemlje.add(z);
			
			Zemlja zRef = zemlje.stream()
					.filter(x -> x.getCountry().equals(z.getCountry()))
					.findFirst().orElse(null);
			
			podaci.add(new Podatak(datumString, casesString, deathsString, zRef));
		}
	}


	private static String load() {
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Podaci.class.getResourceAsStream("sars-cov-2.json")))) {
			
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
