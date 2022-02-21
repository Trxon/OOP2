package vezba.kol2_covid_g03_p02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/*
 * Zadatak
 * =======
 * 
 * Napisati Java aplikaciju koja pomocu tokova podataka i lambda i-
 * zraza obradjuje i prikazuje podatke o trenutnoj pandemiji.
 * 
 * Pri pokretanju, aplikacija ucitava podatke iz fajla sars-cov-2.-
 * xml. Podaci su dati u xml formatu. Svaki od record elemenata sa-
 * drzi podatke za jedan konkretan datum i jednu konkretnu zemlju a
 * podelementi sadrze odgovarajuce podatke.
 * 
 * Za cuvanje podataka je potrebno napraviti klasu Zemlja koja sad-
 * rzi podatke o imenu zemlje na engleskom jeziku, dvoslovnoj i tr-
 * oslovnoj oznaci, kao i broju stanovnika i kontinentu na kojem se
 * zemlja nalazi. Takodje je potrebno napraviti i klasu Podatak ko-
 * ja sadrzi broj registrovanih slucajeva, broj umrlih, datum i re-
 * ferencu na zemlju za koju se podatak odnosi. Implementirati met-
 * ode equals() i hashCode() kako bi bilo moguce instance ovih kla-
 * sa koristiti i u kolekcijama.
 * 
 * Podaci se ucitavaju iz fajla i smestaju u jednu veliku listu in-
 * stanci klase Podatak ako kontinent nije "Other". Potom se podaci
 * iz ove liste obradjuju iskljucivo pomocu tokova podataka i lamb-
 * da izraza.
 * 
 * Implementirati metod Zemlja najkriticnijaZemlja() koji vraca ze-
 * mlju sa do danas najvecim procentom potvrdjenih slucajeva u odn-
 * osu na ukupno stanovnistvo zemlje.
 * 
 * Pozvati metod iz glavnog programa i ispisati rezultat.
 * 
 * Implementirati metod List<Zemlja> zemljeSaNiskomSmrtnoscu(String
 * kontinent) koji vraca listu zemalja koje imaju manje od pola pr-
 * ocenta umrlih u odnosu na broj potvrdjenih slucajeva. Za statis-
 * tiku uzeti podatke od 1. juna ove godine, i sortirati listu ras-
 * tuce po procentu. 
 * 
 * U glavnom programu pitati korisnika za kontinent i pozvati metod
 * nakon cega treba ispisati imena zemalja.
 * 
 * Za isti kontinent prikazati tabelarno sledece podatke, sortirane
 * na isti nacin kao u prethodnom slucaju.
 * 
 * Svaki red tabele sadrzi podatke o jednoj zemlji, a kolone tabele
 * su redom: ime zemlje, broj stanovnika, broj potvrdjenih slucaje-
 * va, broj umrlih, procenat umrlih, datum prvog registrovanog slu-
 * caja. Obratiti paznju da brojevi budu uredno poravnati, i takod-
 * je prikazati zaglavlje sa imenima kolona.
 */

public class Program {
	
	
	private static List<Zemlja> zemlje;
	private static List<Podatak> podaci;
	

	public static void main(String[] args) {
		
		try {
			readFile();
		} catch (IOException e) {
			System.out.println("Greska prilikom citanja -> " + e.getMessage());
		}
		
		System.out.println("Najkriticnija zemlja : " + najkriticnijaZemlja());
		System.out.println();
		
		zemljeSaNiskomSmrtnoscu("europe").stream()
				.forEach(System.out::println);
		System.out.println();
		
		tabela("oceania");
		System.out.println();
	}
	
	
	private static Zemlja najkriticnijaZemlja() {
		
		Map<Zemlja, Data> m0 = mapaZaKontinent("", null);
		
		return m0.entrySet().stream()
				.filter(e -> e.getKey().getPopData() != 0)
				.max(Comparator.comparing(e -> (100.0 * e.getValue().getCases()) / e.getKey().getPopData()))
				.map(e -> e.getKey()).orElse(null);
	}
	
	
	private static List<Zemlja> zemljeSaNiskomSmrtnoscu(String kontinent) {
		
		Map<Zemlja, Data> m0 = mapaZaKontinent(kontinent, LocalDate.parse("01/06/2020", Podatak.dateFormat));
		
		return m0.entrySet().stream()
				.filter(e -> e.getValue().getDeathPercentage() < 0.50)
				.sorted(Comparator.comparingDouble(e -> e.getValue().getDeathPercentage()))
				.map(e -> e.getKey()).collect(Collectors.toList());
	}
	
	
	private static Map<Zemlja, Data> mapaZaKontinent(String kontinent, LocalDate datum) {
		
		Map<Zemlja, List<Podatak>> m0 = podaci.stream()
				.filter(p -> "".equals(kontinent) ? p != null : p.getCountry().getContinent().equalsIgnoreCase(kontinent))
				.filter(p -> datum == null ? p != null : p.getDate().equals(datum))
				.collect(Collectors.groupingBy(Podatak::getCountry, Collectors.toList()));
		
		return m0.entrySet().stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey, 
						e -> e.getValue().stream()
								.collect(Collector.of(Data::new, Data::add, Data::join))));
	}
	
	
	private static void tabela(String kontinent) {
		
		Map<Zemlja, Data> m0 = mapaZaKontinent(kontinent, null);
		
		System.out.println("+------------------------------+------------+------------+------------+------------+------------+");
		System.out.println("|                       ZEMLJA | POPULACIJA |  BR. SLUC. | BR. UMRLIH |  % UMRLIH  | PRVI SLUC. |"); 
		System.out.println("+------------------------------+------------+------------+------------+------------+------------+");
		m0.entrySet().stream()
				.sorted(Comparator.comparingDouble(e -> e.getValue().getDeathPercentage()))
				.map(e -> String.format("| %28s | %10d | %10d | %10d | %10.2f | %s |", 
						e.getKey().getCountry(), e.getKey().getPopData(), e.getValue().getCases(), 
						e.getValue().getDeaths(), e.getValue().getDeathPercentage(), e.getValue().getFirst()))
				.forEach(System.out::println);
		System.out.println("+------------------------------+------------+------------+------------+------------+------------+");
	}


	private static void readFile() throws IOException {
		
		zemlje = new ArrayList<Zemlja>();
		podaci = new ArrayList<Podatak>();
		
		String contents;
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Program.class.getResourceAsStream("sars-cov-2.xml")))) {
			
			StringBuilder sb = new StringBuilder();
			String line;
			
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			
			contents = sb.toString();
		}
		
		Pattern p0 = Pattern.compile("(?sm)<dateRep>(?<date>.*?)<\\/dateRep>.*?<cases>(?<cases>.*?)<\\/cases>.*?<deaths>(?<deaths>.*?)<\\/deaths>.*?<countriesAndTerritories>(?<country>.*?)<\\/countriesAndTerritories>.*?<geoId>(?<geoId>.*?)<\\/geoId>.*?<countryterritoryCode>(?<code>.*?)<\\/countryterritoryCode>.*?<popData2018>(?<pop>.*?)<\\/popData2018>.*?<continentExp>(?<continent>.*?)<\\/continentExp>");
		Matcher m0 = p0.matcher(contents);
		
		while (m0.find()) {
			
			String dateString = m0.group("date");
			String casesString = m0.group("cases");
			String deathsString = m0.group("deaths");
			String country = m0.group("country");
			String geoId = m0.group("geoId");
			String code = m0.group("code");
			String popString = m0.group("pop");
			String continent = m0.group("continent");
			
			if (continent.equalsIgnoreCase("other"))
				continue;
			
			Zemlja zemlja = new Zemlja(country, geoId, code, popString, continent);
			
			if (!zemlje.contains(zemlja))
				zemlje.add(zemlja);
			
			Zemlja zRef = zemlje.stream().filter(z -> z.getCountry().equalsIgnoreCase(country)).findFirst().orElse(null);
			
			podaci.add(new Podatak(dateString, casesString, deathsString, zRef));
		}
	}
}
