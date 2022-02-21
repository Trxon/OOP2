package vezba.kol2_covid_g03_p04;

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
 * Napisati Java aplikaciju koja pomocu tokova podataka i lambda izraza
 * obradjuje i prikazuje podatke o trenutnoj pandemiji.
 * 
 * Pri pokretanju, aplikacija ucitava podatke iz fajla sars-cov-2.xml. Podaci su
 * dati u xml formatu. Svaki record element sadrzi podatke za jedan konkretan
 * datum i jednu konkretnu zemlju, dok podelementi sadrze odgovarajuce podatke.
 * 
 * Za cuvanje podataka je potrebno napraviti klasu Zemlja koja sadrzi podatke o
 * imenu zemlje na engleskom jeziku, dvoslovnoj i troslovnoj oznaci, kao i broju
 * stanovnika i kontinentu na kojem se zemlja nalazi. Takodje je potrebno
 * napraviti klasu Podatak koja sadrzi broj registrovanih slucajeva, broj
 * umrlih, datum i referencu na zemlju za koju se podatak odnosi. Implementirati
 * metode equals() i hashCode() kako bi instance ovih klasa mogle da se koriste
 * u kolekcijama.
 * 
 * Podaci se ucitavaju iz fajla i smestaju u jednu veliku listu instanci
 * klase Podatak, ako za kontinent nemaju vrednost "Other". Potom se podaci iz
 * ove liste obradjuju iskljucivo pomocu tokova podataka i lambda izraza.
 * 
 * Implementirati metod Zemlja najkriticnijaZemlja() koji vraca zemlju sa do
 * danas najvecim procentom potvrdjenih slucajeva u odnosu na ukupno
 * stanovnistvo zemlje.
 * 
 * Pozvati metod iz glavnog programa i ispisati rezultat.
 * 
 * Implementirati metod List<Zemlja> zemljeSaNiskomSmrtnoscu(String kontinent)
 * koji vraca listu zemalja koje imaju manje od pola procenta umrlih u odnosu
 * na broj potvrdjenih slucajeva. Za statistiku uzeti podatke od 1. juna ove
 * godine, i sortirati listu rastuce po procentu. 
 * 
 * U glavnom programu pitati korisnika za kontinent, pozvati metod i ispisati
 * imena zemalja.
 * 
 * Za isti kontinent prikazati tabelarno sledece podatke, takodje sortirane na
 * isti nacin kao u prethodnom slucaju.
 * 
 * Svaki red tabele sadrzi podatke o jednoj zemlji. Kolone tabele su redom:
 * ime zemlje, broj stanovnika, broj potvrdjenih slucajeva, broj umrlih,
 * procenat umrlih, datum prvog registrovanog slucaja. Obratiti paznju da
 * brojevi budu uredno poravnati, i takodje prikazati zaglavlje sa imenima
 * kolona.
 */

public class Program {
	
	
	static List<Podatak> podaci = new ArrayList<Podatak>();
	
	
	private static class Data {
		
		Zemlja zemlja;
		int cases, deaths;
		LocalDate first;
		
		public double getCasesPercentage() {
			if (zemlja.getPopData() == 0) return 0;
			return 100.0 * cases / zemlja.getPopData();
		}
		
		public void add(Podatak p) {
			
			if (p == null) return;
			
			this.zemlja = p.getCountry();
			this.cases += p.getCases();
			this.deaths += p.getDeaths();
			
			if (this.first == null || this.first.compareTo(p.getDate()) > 0)
				this.first = p.getDate();
		}
		
		public Data join(Data d) {
			
			if (d == null) return this;
			
			this.zemlja = d.zemlja;
			this.cases = d.cases;
			this.deaths = d.deaths;
			
			if (this.first == null || this.first.compareTo(d.first) > 0)
				this.first = d.first;
			
			return this;
		}
	}


	private static List<Data> zgodnijiPodaci;
	
	
	public static void main(String[] args) {
		
		load();
		getData();
		
		System.out.println("NAJKRITICNIJA : " + najkriticnijaZemlja());
		System.out.println();
		
		System.out.println("ZEMLJE SA NISKOM SMRTNOSCU : ");
		zemljeSaNiskomSmrtnoscu("Europe").stream().forEach(System.out::println);
		System.out.println();
		
		tabela();
	}
	
	
	private static void tabela() {
		
		System.out.println("+------------------------------------------+------------+------------+------------+------+------------+");
		System.out.println("|                                   ZEMLJA | STANOVNIKA | SLUCAJEVA  | UMRLIH     | % UM | PRVI SLUC. |");
		System.out.println("+------------------------------------------+------------+------------+------------+------+------------+");
		
		zgodnijiPodaci.stream()
			.sorted(Comparator.comparingDouble(Data::getCasesPercentage))
			.map(d -> String.format("| %40s | %10d | %10d | %10d | %4.2f | %10s |", 
					d.zemlja.getCountry(), d.zemlja.getPopData(), d.cases, d.deaths, d.getCasesPercentage(), d.first))
			.forEach(System.out::println);
		
		System.out.println("+------------------------------------------+------------+------------+------------+------+------------+");
	}
	
	
	private static List<Zemlja> zemljeSaNiskomSmrtnoscu(String kontinent) {
		
		return podaci.stream()
				.filter(p -> p.getCountry().getContinent().equalsIgnoreCase(kontinent))
				.filter(p -> p.getDate().equals(LocalDate.of(2020, 6, 1)))
				.filter(p -> {
					
					double d = 100.0 * p.getDeaths() / p.getCases();
					
					if (d < .5) return true;
					return false;
					
				})
				.sorted(Comparator.comparingDouble(Podatak::getPercentage))
				.map(Podatak::getCountry)
				.collect(Collectors.toList());
	}
	
	
	private static Zemlja najkriticnijaZemlja()  {
		
		Data d = zgodnijiPodaci.stream()
				.max(Comparator.comparingDouble(
						Data::getCasesPercentage))
				.orElse(null);
		
		if (d == null)
			return null;
		else
			return d.zemlja;
	}


	private static void load() {
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Program.class.getResourceAsStream("sars-cov-2.xml")))) {
			
			StringBuilder sb = new StringBuilder();
			String line = in.readLine();
			
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			
			Pattern p0 = Pattern.compile("(?sm)<dateRep>(?<dateRep>.*?)<\\/dateRep>\\s*?.*?<cases>(?<cases>.*?)<\\/cases>\\s*?<deaths>(?<deaths>.*?)<\\/deaths>\\s*?<countriesAndTerritories>(?<countriesAndTerritories>.*?)<\\/countriesAndTerritories>\\s*?<geoId>(?<geoId>.*?)<\\/geoId>\\s*?<countryterritoryCode>(?<countryterritoryCode>.*?)<\\/countryterritoryCode>\\s*?<popData2018>(?<popData2018>.*?)<\\/popData2018>\\s*?<continentExp>(?<continentExp>.*?)<\\/continentExp>\\s*?");
			Matcher m0 = p0.matcher(sb.toString());
			
			while (m0.find()) {
				
				String dateRepString = m0.group("dateRep").trim();
				String casesString = m0.group("cases").trim();
				String deathsString = m0.group("deaths").trim();
				String countriesAndTerritories = m0.group("countriesAndTerritories").trim();
				String geoId = m0.group("geoId").trim();
				String countryterritoryCode = m0.group("countryterritoryCode").trim();
				String popData2018String = m0.group("popData2018").trim();
				String continentExp = m0.group("continentExp").trim();
				
				if (continentExp.equalsIgnoreCase("Other")) continue;
				
				podaci.add(new Podatak(
						casesString, deathsString, dateRepString, new Zemlja(
								countriesAndTerritories, geoId, countryterritoryCode, popData2018String, continentExp)));
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private static void getData() {
		
		zgodnijiPodaci = podaci.stream()
						.collect(Collectors.groupingBy(
								Podatak::getCountry, 
								Collectors.toList()))
				.entrySet().stream()
						.collect(Collectors.toMap(
								Map.Entry::getKey, 
								e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))))
				.entrySet().stream()
						.map(e -> e.getValue()).collect(Collectors.toList());
	}
}
