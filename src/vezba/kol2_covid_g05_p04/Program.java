package vezba.kol2_covid_g05_p04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * Zadatak
 * =======
 *
 * Napisati klijent-server aplikaciju u Javi koja komunicira pomocu RMI i
 * prikazuje podatake o trenutnoj pandemiji.
 * 
 * Server
 * ------
 * 
 * Pri pokretanju, server ucitava podatke iz fajla sars-cov-2.csv. Podaci su
 * dati u comma-separated values formatu. U prvom redu se nalaze imena kolona
 * dok svaki sledeci red sadrzi podatke za konkretan datum i konkretnu zemlju.

 * Za cuvanje podataka je potrebno napraviti klasu Zemlja koja sadrzi podatke o
 * imenu zemlje na engleskom jeziku, dvoslovnoj i troslovnoj oznaci, kao i broju
 * stanovnika i kontinentu na kojem se zemlja nalazi. Klasa takodje sadrzi polja
 * koja cuvaju podatke o broju registrovanih slucajeva, broju umrlih, i datum. 
 * Implementirati metode equals() i hashCode() kako bi instance ove klase mogle 
 * da se koriste u kolekcijama.
 * 
 * Dat je metod koji ucitava redove iz fajla i pruza ih kao tok linija.
 * 
 * Server na pocetku parsira ovaj tok linija i pretvara ga u tok Zemlja objekata
 * cije elemente potom smesta u listu. Podaci se smestaju u jednu veliku listu
 * instanci klase Zemlja, ako za kontinent nemaju vrednost "Other". Potom se
 * podaci iz ove liste obradjuju iskljucivo pomocu tokova podataka i lambda
 * izraza.
 * 
 * Na serveru implementirati metod Zemlja najviseUmrlihNaKontinentu(String kontinent)
 * koja za prosledjeni naziv kontinenta vraca zemlju sa najvese preminulih osoba.
 *
 * Takodje, implementirati metod Map<LocalDate, List<Zemlja>> novozarazeneNaDan() 
 * koja za svaki datum u bazi podataka kreira listu koja sadrzi sve zemlje ciji
 * je prvi slucaj zaraze registrovan na taj dan.
 *
 * Implementirati metod String tabelarniPrikaz() koji vraca tabelu u obliku
 * stringa cije vrste prestavljaju kontinente, kolone intervale u kojima se moze
 * naci odnos ukupnog broja umrlih i ukupnog broja zarazenih osoba neke drzave.
 * Same vrednosti u tabeli su broj zemalja odredjenog kontinenta ciji odnos
 * umrlih i zarazenih pripada intervalu.
 * 
 * Primer:
 *
 * -----------------------------------
 * Continent | 0 - 1% | 1 - 3% | >3% |
 * -----------------------------------
 * Europe    |      2 |      7 |   0 |
 * -----------------------------------
 *               .
 *               .
 *               . 
 * 
 * Komunikacija sa klijentom se odvija na sledeci nacin: klijent moze da pozove 
 * bilo koju serverovu metodu najviseUmrlihNaKontinentu(String kontinent),
 * novozarazeneNaDan() ili tabelarniPrikaz().
 * 
 * Klijent
 * -------
 * 
 * Po pokretanju, klijent trazi od korisnika da unese kontinent za koji zeli
 * tabelarni prikaz i potom se konektuje na server.
 * 
 * Potom poziva svaki od tri dostupna metoda i prikazuje dobijene podatke
 * korisniku.
 * 
 * Pri pozivu metode najviseUmrlihNaKontinentu(String kontinent) ukoliko za
 * uneti kontinent ne postoje podaci ili dodje do problema u komunikaciji
 * sa serverom, prikazuje se prigodna poruka o tome.
 */
public class Program {
	

	public static Stream<String> linije() throws IOException {
		return Files.lines(Paths.get("/Users/nikolavetnic/Library/Mobile Documents/com~apple~CloudDocs/Documents/EclipseWorkspace/OOP2/src/vezba/kol2_covid_g05_p04/sars-cov-2.csv"));
	}
	
	
	private static List<Zemlja> zemlje = load();
	
	
	public static void main(String[] args) {
		
		novozarazeneNaDan().entrySet().stream().forEach(System.out::println);
		System.out.println(tabelarniPrikaz());
	}
	
	
	private static String tabelarniPrikaz() {
		
		class Data0 {
			
			Zemlja country;
			int cases, deaths;
			
			public void add(Zemlja z) {
				
				this.country = z.getReduced();
				
				this.cases += z.getCases();
				this.deaths += z.getDeaths();
			}
			
			public Data0 join(Data0 d) {
				
				this.country = d.country;
				
				this.cases += d.cases;
				this.deaths += d.deaths;
				
				return this;
			}
			
			public double getPercentage() {
				return 100.0 * deaths / cases;
			}
			
			public String getContinent() {
				return country.getContinent();
			}
		}
		
		Map<Zemlja, Data0> m0 = zemlje.stream()
				.collect(Collectors.groupingBy(Zemlja::getReduced, Collectors.toList()))
				.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().collect(Collector.of(Data0::new, Data0::add, Data0::join))));
		
		class Data1 {
			
			String continent;
			int k0, k1, k2;
			
			public void add(Data0 d) {
				
				this.continent = d.country.getContinent();
				
				if 		(d.getPercentage() < 1.0) 	k0++;
				else if (d.getPercentage() < 3.0) 	k1++;
				else 								k2++;
			}
			
			public Data1 join(Data1 d) {
				
				this.continent = d.continent;
				
				k0 += d.k0;
				k1 += d.k1;
				k2 += d.k2;
				
				return this;
			}
		}
		
		Map<String, Data1> m1 = m0.entrySet().stream()
						.map(e -> e.getValue())
						.collect(Collectors.toList())
				.stream()
						.collect(Collectors.groupingBy(
								Data0::getContinent, 
								Collectors.toList()))
				.entrySet().stream()
						.collect(Collectors.toMap(
								Map.Entry::getKey, 
								e -> e.getValue()
				.stream()
						.collect(Collector.of(Data1::new, Data1::add, Data1::join))));
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("-----------+--------+--------+-----+\n");
		sb.append(" Continent | 0 - 1% | 1 - 3% | >3% |\n");
		sb.append("-----------+--------+--------+-----+\n");
		
		sb.append(m1.entrySet().stream()
						.map(e -> String.format(" %9s | %6d | %6d | %3d |", 
								e.getKey(), e.getValue().k0, e.getValue().k1, e.getValue().k2))
						.reduce(
								"", 
								(s1, s2) -> "".equals(s1) ? s2 : s1 + "\n" + s2));
		
		sb.append("\n");
		sb.append("-----------+--------+--------+-----+");
		
		return sb.toString();
	}
	
	
	private static Zemlja najviseUmrlihNaKontinentu(String kontinent) {
		
		return (Zemlja) zemlje.stream()
						.filter(z -> z.getContinent().equalsIgnoreCase(kontinent))
						.collect(Collectors.groupingBy(
								Zemlja::getReduced, 
								Collectors.summingInt(Zemlja::getDeaths)))
				.entrySet().stream()
						.max(Map.Entry.comparingByValue())
						.orElse(null);
	}
	
	
	private static Map<LocalDate, List<Zemlja>> novozarazeneNaDan() {
		
		class Data {
			
			Zemlja zemlja;
			LocalDate first;
			
			public void add(Zemlja z) {
				
				if (z == null)
					return;
				
				this.zemlja = z.getReduced();
				
				if (first == null || first.compareTo(z.getDate()) > 0)
					this.first = z.getDate();
			}
			
			public Data join(Data d) {
				
				if (d == null)
					return this;
				
				this.zemlja = d.zemlja;
				
				if (first == null || first.compareTo(d.first) > 0)
					this.first = d.first;
				
				return this;
			}
			
			public Zemlja getZemlja() {
				zemlja.setDate(first);
				return zemlja;
			}
		}
		
		return zemlje.stream()
						.collect(Collectors.groupingBy(
								Zemlja::getReduced, 
								Collectors.toList()))
				.entrySet().stream()
						.collect(Collectors.toMap(
								Map.Entry::getKey, 
								e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))))
				.entrySet().stream()
						.map(e -> e.getValue().getZemlja())
						.collect(Collectors.groupingBy(
								Zemlja::getDate, 
								Collectors.toList()));
	}


	private static List<Zemlja> load() {
		
		try {
			
			return linije().filter(s -> !s.startsWith("dateRep")).map(s -> {
				
				String[] tokens = s.split(",");
				
				return new Zemlja(tokens[6], tokens[7], tokens[8], tokens[9], tokens[10], tokens[4], tokens[5], tokens[0]);
				
			}).filter(z -> !z.getContinent().equalsIgnoreCase("Other"))
			.collect(Collectors.toList());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
