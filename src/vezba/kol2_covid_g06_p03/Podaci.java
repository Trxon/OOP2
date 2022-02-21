package vezba.kol2_covid_g06_p03;

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

	
	private Map<Zemlja, List<Podatak>> mapa;
	
	
	public Podaci() {
		load();
	}


	private void load() {
		
		this.mapa = new HashMap<Zemlja, List<Podatak>>();
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Podaci.class.getResourceAsStream("sars-cov-2.json")))) {
			
			String line;
			StringBuilder sb = new StringBuilder();
			
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			
			Pattern p0 = Pattern.compile("(?sm)\\\"dateRep\\\":\\s*?\\\"(?<date>.*?)\\\".*?\\\"cases\\\":\\s*?\\\"(?<cases>\\d*?)\\\",\\s*?\\\"deaths\\\":\\s*?\\\"(?<deaths>\\d*?)\\\",\\s*?\\\"countriesAndTerritories\\\":\\s*?\\\"(?<country>.*?)\\\",.*?\\\"popData2018\\\":\\s*?\\\"(?<popData>\\d*?)\\\",\\s*?\\\"continentExp\\\":\\s*?\\\"(?<continent>.*?)\\\"");
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
				
				Zemlja zemlja = new Zemlja(country, continent, popDataString);
				Podatak podatak = new Podatak(casesString, deathsString, dateString);
				
				if (!mapa.containsKey(zemlja))
					mapa.put(zemlja, new ArrayList<Podatak>());
				
				mapa.get(zemlja).add(podatak);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public String podaciZaZemlju(String zemlja) {
		
		for (Zemlja z : mapa.keySet())
			if (z.getCountry().equalsIgnoreCase(zemlja)) {
				
				StringBuilder sb = new StringBuilder();
				
				sb.append(String.format("    %4s    | %6s | %6s %n", "DATE", "CASES", "DEATHS"));
				
				sb.append(mapa.get(z).stream()
						.sorted()
						.map(p -> p.toString())
						.reduce("", (s1, s2) -> "".equals(s1) ? s2 : s1 + "\n" + s2));
				
//				sb.append("");
				
				return sb.toString();
			}
		
		return "Nema podataka ili pogresan unos.";
	}
	
	
	public static void main(String[] args) {
		
		Podaci p = new Podaci();
		
		System.out.println(p.podaciZaZemlju("serbia"));
	}
}
