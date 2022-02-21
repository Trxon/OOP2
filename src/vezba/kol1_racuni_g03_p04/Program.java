package vezba.kol1_racuni_g03_p04;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * Prvi kolokvijum
 * ===============
 * 
 * Napisati Java aplikaciju koja pomocu tokova podataka i lambda i-
 * zraza obradjuje sve podatke o fiskalnim racunima izdatim od str-
 * ane jedne mlekare.
 * 
 * Data je klasa Racun kojom se prestavljaju fiskalni racuni. Svaki
 * racun ima svoj redni broj, datum i vreme kada je izdat, listu s-
 * tavki na racunu, kao i koliko je gotovine uplaceno kada je racun
 * placen.
 * 
 * Data je i klasa Stavka cije instance predstavljaju stavke racuna,
 * a svaka stavka se sastoji od imena proizvoda, kolicine tog proiz-
 * voda, cene po jedinici mere i poreske stope (predstavljene nabro-
 * jivim tipom).
 * 
 * Prvi deo (5 poena)
 * ------------------
 * 
 * Dat je tok stringova u vidu metoda Racuni.jsonStream(), i u njemu
 * je svaki racun predstavljen jednim stringom u JSON formatu. Za d-
 * etalje o formatu pokrenuti program i pogledati kako izgleda svaki
 * od stringova.
 * 
 * Pretvoriti dati tok stringova u tok Racun objekata i ispisati ih.
 * 
 * Drugi deo (5 poena)
 * -------------------
 * 
 * Implementirati metod double prodatoSireva(int dan, int mesec, int
 * godina), pozvati ga u glavnom programu i ispisati rezultat.
 * 
 * Metod vraca koliko je ukupno kilograma svih vrsta mekih sireva p-
 * rodato zadatog dana. Meki sirevi su svi oni proizvodi koji na kr-
 * aju naziva imaju "sir rf".
 * 
 * Treci deo (5 poena)
 * -------------------
 * 
 * Implementirati metod List<String> razlicitiProizvodi(int dan, int
 * mesec, int godina), pozvati ga u glavnom programu i ispisati rez-
 * ultat.
 * 
 * Metod vraca nazive svih proizovda koji su prodati datog dana, so-
 * rtirane abecedno, bez ponavaljanja.
 * 
 * Cetvrti deo (5 poena)
 * ---------------------
 * 
 * Za svaki dan 2018. godine ispisati koliko je litara kozijeg mleka
 * prodato, na sledeci nacin:
 * 
 *     Datum | Prodato
 * ----------+----------
 *      2.1. |   0.40 l
 *      3.1. |  12.53 l
 *      4.1. |   0.00 l
 *          ...
 * 
 * Peti deo (5 poena)
 * ------------------
 * 
 * Pomocu operacije .reduce() generisati dnevni izvestaj za 1.5.2018
 * i smestiti ga u lokalnu promenljivu.
 * 
 * Dnevni izvestaj je izmisljen racun na kojem se nalaze bas sve st-
 * avke sa svih racuna zadatog dana. Redni broj ovakvog racuna je n-
 * ula, vreme izdavanja je podne zadatog dana, a uplaceno je nula d-
 * inara.
 * 
 * Ispisati stavke sa dnevnog izvestaja (naziv proizvoda, kolicinu i
 * ukupnu cenu) na sledeci nacin:
 * Mladi sir RF x 2.300 = 920.00
 * Namaz sa paprikom x 3 = 449.97
 * Kiselo mleko 500g x 1 = 59.99
 * ...
 * 
 */

public class Program {

	
	public static final int BROJ = 500;
	

	public static void main(String[] args) {

		Racuni.jsonStream(1)
				.forEach(System.out::println);

//		Racuni.racuniStream(5000)
//				.forEach(System.out::println);

		// prvi deo
		List<Racun> racuni = load(Racuni.jsonStream(BROJ));
		System.out.println(racuni.size());
		
		// drugi deo
		System.out.println("Prodato sireva : " + prodatoSireva(25, 4, 2018));
		
		// treci deo
		System.out.println(razlicitiProizvodi(2, 1, 2018));
		
		// cetvrti deo
		kozijegMleka(2018);
		
		// peti deo
		izvestaj(25, 4, 2018);
	}
	
	
	public static PoreskaStopa stopaFromString(String s) {
		
		for (PoreskaStopa p : PoreskaStopa.values())
			if (p.toString().equalsIgnoreCase(s))
				return p;
		
		return PoreskaStopa.OPSTA;
	}
	
	
	public static List<Racun> load(Stream<String> lines) {
		
		return lines
				.map(l -> {
					
					Pattern p0 = Pattern.compile("(?sm)\\\"broj\\\":\\s*(?<broj>[\\s\\S]*?),\\s*?\\\"datum\\\":\\s*?\\\"(?<datum>[\\s\\S]*?)\\\",\\s*?\\\"vreme\\\":\\s*?\\\"(?<vreme>[\\s\\S]*?)\\\",\\s*?\\\"stavke\\\":\\s*?\\[(?<stavke>[\\s\\S]*?)\\],\\s*?\\\"uplaceno\\\":\\s*?(?<uplaceno>[\\s\\S]*?)\\s*}");
					Matcher m0 = p0.matcher(l);
					
					m0.find();
					
					int broj = Integer.parseInt(m0.group("broj").trim());
					
					String[] datumTokens = m0.group("datum").trim().split("\\.");
					String vremeString = m0.group("vreme").trim();
					
					LocalDateTime vreme = LocalDateTime.parse(datumTokens[2] + "-" + datumTokens[1] + "-" + datumTokens[0] + "T" + vremeString);
					
					int uplaceno = Integer.parseInt(m0.group("uplaceno").trim());
					
					String stavkeString = m0.group("stavke");
					List<Stavka> stavke = new ArrayList<Stavka>();
					
					Pattern p1 = Pattern.compile("(?sm)\\\"proizvod\\\":\\s*?\\\"(?<proizvod>[\\s\\S]*?)\\\",\\s*?\\\"kolicina\\\":\\s*?(?<kol>[\\s\\S]*?),\\s*?\\\"cena\\\":\\s*?(?<cena>[\\s\\S]*?),\\s*?\\\"stopa\\\":\\s*?\\\"(?<stopa>[\\s\\S]*?)\\\"");
					Matcher m1 = p1.matcher(stavkeString);
					
					while (m1.find()) {
						
						String proizvod = m1.group("proizvod");
						double kolicina = Double.parseDouble(m1.group("kol").trim());
						double cena = Double.parseDouble(m1.group("cena").trim());
						PoreskaStopa stopa = stopaFromString(m1.group("stopa"));
						
						stavke.add(new Stavka(proizvod, kolicina, cena, stopa));
					}
					
					return new Racun(broj, vreme, uplaceno, stavke);
				}).collect(Collectors.toList());
	}
	
	
	public static double prodatoSireva(int dan, int mesec, int godina) {
		
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == godina && r.getVreme().getMonthValue() == mesec && r.getVreme().getDayOfMonth() == dan)
				.flatMap(r -> r.getStavke().stream())
				.filter(s -> s.getProizvod().endsWith("sir RF"))
				.collect(Collectors.summingDouble(Stavka::getKolicina));
	}
	
	
	public static List<String> razlicitiProizvodi(int dan, int mesec, int godina) {
		
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == godina && r.getVreme().getMonthValue() == mesec && r.getVreme().getDayOfMonth() == dan)
				.flatMap(r -> r.getStavke().stream())
				.map(Stavka::getProizvod).distinct().sorted().collect(Collectors.toList());
	}
	
	
	public static void kozijegMleka(int godina) {
		
		class Data {
			
			String p;
			LocalDate d;
			double k;
			
			public Data(String p, LocalDate d, double k) {
				this.p = p;
				this.d = d;
				this.k = k;
			}
		}
		
		Map<LocalDate, Double> m0 = Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == godina)
				.flatMap(r -> r.getStavke().stream().map(s -> new Data(s.getProizvod(), r.getVreme().toLocalDate(), s.getKolicina())))
				.filter(d -> d.p.contains("ozije"))
				.collect(Collectors.groupingBy(d -> (LocalDate) d.d, Collectors.summingDouble(d -> d.k)));
		
		m0.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.map(e -> String.format(" %2d.%2d. | %.2f", e.getKey().getDayOfMonth(), e.getKey().getMonthValue(), e.getValue()))
				.forEach(System.out::println);
				
	}
	
	
	public static void izvestaj(int dan, int mesec, int godina) {
		
		Racun izvestaj = Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == godina && r.getVreme().getMonthValue() == mesec && r.getVreme().getDayOfMonth() == dan)
				.reduce(null, (r1, r2) -> new Racun(
						0, LocalDateTime.of(godina, mesec, dan, 12, 0), 0, 
						Stream.concat(
								r1 == null ? Stream.empty() : r1.getStavke().stream(), 
								r2 == null ? Stream.empty() : r2.getStavke().stream())
						.collect(Collectors.toList())));
		
		class Data {
			
			double k, c;
			
			public void add(Stavka s) {
				this.k += s.getKolicina();
				this.c += s.getCena();
			}
			
			public Data join(Data d) {
				this.k += d.k;
				this.c += d.c;
				
				return this;
			}
		}
		
		Map<String, List<Stavka>> m0 = izvestaj.getStavke().stream()
				.collect(Collectors.groupingBy(Stavka::getProizvod, Collectors.toList()));
		
		Map<String, Data> m1 = m0.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))));
		
		m1.entrySet().stream()
				.map(e -> String.format("%s x %.2f = %.2f", e.getKey(), e.getValue().k, e.getValue().c))
				.forEach(System.out::println);
	}
}
