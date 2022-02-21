package vezba.kol2_covid_g02_p05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/*
 * Zadatak
 * =======
 *
 * Napisati Java RMI klijent-server aplikaciju za prikaz podataka o trenutnoj
 * pandemiji.
 * 
 * Server
 * ------
 * 
 * Pri pokretanju, server ucitava podatke iz fajla sars-cov-2.json. Podaci su
 * dati u JSON formatu. Niz records sadrzi po jedan objekat za konkretan datum
 * i konkretnu zemlju, dok polja tog objekta sadrze konkretne podatke.
 * 
 * Za cuvanje podataka je potrebno napraviti klasu Zemlja koja sadrzi podatke o
 * imenu zemlje na engleskom jeziku, kontinentu na kojem se zemlja nalazi,
 * broju stanovnika, broju registrovanih slucajeva, broj umrlih i datumu na koji
 * se podaci odnose.
 * 
 * Server ucitava podatke iz fajla i smesta ih u mapu. Kljucevi mape su imena
 * kontinenata a vrednosti su liste zemalja na konkretnom kontinentu. U mapi se
 * za svaku zemlju cuvaju samo podaci za poslednji datum za koji postoje u fajlu.
 * 
 * Za pristup podacima, server implementira dva metoda koji su klijentu dostupni
 * preko Java RMI.
 * 
 * Metod List<String> kontinenti() vraca listu kontinenata za koje postoje
 * podaci.
 * 
 * Metod List<Zemlja> zemlje(String kontinent) vraca listu zemalja na zatadom
 * kontinentu. Ako u mapi ne postoje podaci za zadati kontinent metod vraca
 * praznu listu.
 * 
 * Klijent
 * -------
 * 
 * Klijentski program se po pokretanju odmah konektuje na server i trazi listu
 * kontinenata. Ako server nije dostupan, prikazuje se prigodna poruka o tome.
 * 
 * Potom, program prikazuje listu kontinenata korisniku i trazi da korisnik
 * izabere jedan od njih.
 * 
 * Na kraju, klijentska aplikacija prikazuje podatke za sve zemlje na izabranom
 * kontinentu, sortirane po procentu zarazenih. Od podataka je potrebno
 * prikazati ime zemlje, broj stanovnika, broj potvrdjenih slucajeva, broj
 * umrlih i procenat zarazenih u odnosu na ukupnu populaciju zemlje.
 */

public class Program {
	
	
	// SAMO PARSIRANJE
	
	
	private static Map<String, List<Zemlja>> mapa = new HashMap<String, List<Zemlja>>();
	
	
	public static void main(String[] args) {
		
		load();
		
		kontinenti().stream().forEach(System.out::println);
		zemlje("Europe").stream().forEach(System.out::println);
	}
	
	
	private static List<Zemlja> zemlje(String kontinent) {
		return mapa.entrySet().stream()
				.filter(e -> e.getKey().equalsIgnoreCase(kontinent))
				.flatMap(e -> e.getValue().stream())
				.collect(Collectors.toList());
	}
	
	
	private static List<String> kontinenti() {
		return mapa.keySet().stream().collect(Collectors.toList());
	}


	private static void load() {
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Program.class.getResourceAsStream("sars-cov-2.json")))) {
			
			StringBuilder sb = new StringBuilder();
			String line = in.readLine();
			
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
				
			Pattern p0 = Pattern.compile("(?sm)\\\"dateRep\\\":\\s*?\\\"(?<date>.*?)\\\",\\s*?\\\"day\\\":\\s*?\\\"(?<day>.*?)\\\",\\s*?\\\"month\\\":\\s*?\\\"(?<month>.*?)\\\",\\s*?\\\"year\\\":\\s*?\\\"(?<year>.*?)\\\",\\s*?\\\"cases\\\":\\s*?\\\"(?<cases>.*?)\\\",\\s*?\\\"deaths\\\":\\s*?\\\"(?<deaths>.*?)\\\",\\s*?\\\"countriesAndTerritories\\\":\\s*?\\\"(?<countriesAndTerritories>.*?)\\\",\\s*?\\\"geoId\\\":\\s*?\\\"(?<geoId>.*?)\\\",\\s*?\\\"countryterritoryCode\\\":\\s*?\\\"(?<countryterritoryCode>.*?)\\\",\\s*?\\\"popData2018\\\":\\s*?\\\"(?<popData2018>.*?)\\\",\\s*?\\\"continentExp\\\":\\s*?\\\"(?<continentExp>.*?)\\\"\\s*?");
			Matcher m0 = p0.matcher(sb.toString());
			
			while (m0.find()) {
				
				String country = m0.group("countriesAndTerritories").trim();
				String popDataString = m0.group("popData2018").trim();
				String continent = m0.group("continentExp").trim();
				
				if (continent.equalsIgnoreCase("Other")) continue;
				
				String casesString = m0.group("cases").trim();
				String deathsString = m0.group("deaths").trim();
				String dateString = m0.group("date").trim();
				
				if (!mapa.containsKey(continent))
					mapa.put(continent, new ArrayList<Zemlja>());
				
				Zemlja z = mapa.get(continent).stream().filter(x -> x.getCountry().equalsIgnoreCase(country)).findFirst().orElse(null);	
				
				if (z == null) {
					
					mapa.get(continent).add(
							new Zemlja(country, popDataString, continent, casesString, deathsString, dateString));
				} else {
					
					LocalDate date = LocalDate.parse(dateString, Zemlja.getDateFormat());
					
					if (z.getDate().compareTo(date) < 0) {
						z.setCases(Integer.parseInt(casesString));
						z.setDeaths(Integer.parseInt(deathsString));
						z.setDate(date);
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
