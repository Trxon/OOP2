package vezba.kol2_covid_g06_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Podaci {
	
	
	private List<Zemlja> zemlje;
	private List<Podatak> podaci;
	private Map<String, List<Podatak>> mapa;

	
	public static void main(String[] args) {
		
		Podaci p = new Podaci();
		System.out.println(p.mapa);
	}
	
	
	public Podaci() {
		
		String contents = load();
		
		if (contents == null) return;
		
		this.zemlje = new ArrayList<Zemlja>();
		this.podaci = new ArrayList<Podatak>();
		
		this.mapa = new HashMap<String, List<Podatak>>();
		
		Pattern p0 = Pattern.compile("(?sm)\\\"dateRep\\\":\\s*?\\\"(?<datum>.*?)\\\",\\s*?\\\"day\\\":\\s*?\\\"(?<day>.*?)\\\",\\s*?\\\"month\\\":\\s*?\\\"(?<month>.*?)\\\",\\s*?\\\"year\\\":\\s*?\\\"(?<year>.*?)\\\",\\s*?\\\"cases\\\":\\s*?\\\"(?<cases>.*?)\\\",\\s*?\\\"deaths\\\":\\s*?\\\"(?<deaths>.*?)\\\",\\s*?\\\"countriesAndTerritories\\\":\\s*?\\\"(?<country>.*?)\\\",\\s*?\\\"geoId\\\":\\s*?\\\"(?<geoId>.*?)\\\",\\s*?\\\"countryterritoryCode\\\":\\s*?\\\"(?<code>.*?)\\\",\\s*?\\\"popData2018\\\":\\s*?\\\"(?<popData>.*?)\\\",\\s*?\\\"continentExp\\\":\\s*?\\\"(?<continent>.*?)\\\"\\s*?");
		Matcher m0 = p0.matcher(contents);
		
		while (m0.find()) {
			
			Zemlja z = new Zemlja(m0.group("country"), m0.group("geoId"), m0.group("code"), m0.group("popData"), m0.group("continent"));
			Podatak p = new Podatak(m0.group("datum"), m0.group("cases"), m0.group("deaths"));
			
			if (!zemlje.contains(z)) {
				
				zemlje.add(z);
				mapa.put(z.getCountry().toUpperCase(), new ArrayList<Podatak>());
			}
			
			mapa.get(z.getCountry().toUpperCase()).add(p);
			podaci.add(p);
		}
	}

	
	private String load() {
		
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
			
		}
		
		return null;
	}


	public List<Zemlja> getZemlje() {
		return zemlje;
	}


	public List<Podatak> getPodaci() {
		return podaci;
	}


	public Map<String, List<Podatak>> getMapa() {
		return mapa;
	}
	
	
	
}
