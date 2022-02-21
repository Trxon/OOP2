package vezba.kol1_racuni_g03_p01;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
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

//		Racuni.jsonStream(1)
//				.forEach(System.out::println);

//		Racuni.racuniStream(5000)
//				.forEach(System.out::println);

		// prvi deo
		List<Racun> racuni = load(Racuni.jsonStream(BROJ));
		
		// drugi deo
		prodatoSireva(25, 4, 2018);
		
		// treci deo
		System.out.println(razlicitiProizvodi(2, 1, 2018));
		
		// cetvrti deo
		kozijegMleka(2018);
		
		// peti deo
		izvestaj(25, 4, 2018);
	}


	public static PoreskaStopa poreskaStopaFromString(String s) {
		
		if 		(s.equalsIgnoreCase("OPSTA")) 		return PoreskaStopa.OPSTA;
		else if (s.equalsIgnoreCase("POSEBNA")) 	return PoreskaStopa.POSEBNA;
		else  										return PoreskaStopa.OSLOBODJEN;
	}
	
	
	public static List<Racun> load(Stream<String> lines) {
		
		return lines.map(s -> {
			
			Pattern p0 = Pattern.compile("(?sm)[\\s\\S]*?\\\"broj\\\"\\:\\s*(?<broj>\\d*?)\\,[\\s\\S]*?\\\"datum\\\"\\:\\s*\\\"(?<datum>[\\s\\S]*?)\\\"\\,\\s*?\\\"vreme\\\"\\:\\s*?\\\"(?<vreme>\\d{2}\\:\\d{2})\\\"\\,\\s*?\\\"stavke\\\"\\:\\s*?\\[(?<stavke>[\\s\\S]*?)\\]\\,\\s*?\\\"uplaceno\\\"\\:\\s*?(?<uplaceno>\\d*?)\\n\\s*?\\}");
			Matcher m0 = p0.matcher(s);
			
			m0.find();
			int broj = Integer.parseInt(m0.group("broj").trim());

			String datumString = m0.group("datum");
			String vremeString = m0.group("vreme");
			
			String god = datumString.substring(datumString.length() - 5 , datumString.length() - 1);
			String mes = datumString.substring(datumString.length() - 8 , datumString.length() - 6);
			String dan = datumString.substring(datumString.length() - 11, datumString.length() - 9);
			
			LocalDateTime dateTime = LocalDateTime.parse(god + "-" + mes + "-" + dan + "T" + vremeString + ":00");
			
			String stavkeString = m0.group("stavke");
			
			int uplaceno = Integer.parseInt(m0.group("uplaceno").trim());
			
			Pattern p1 = Pattern.compile("[\\s\\S]*?\\\"proizvod\\\"\\:\\s*\\\"(?<proizvod>[\\s\\S]*?)\\\"\\,\\s*\\\"kolicina\\\"\\:\\s*(?<kol>[\\s\\S]*?)\\,\\s*\\\"cena\\\"\\:\\s*(?<cena>[\\s\\S]*?)\\,\\s*\\\"stopa\\\"\\:\\s*\\\"(?<stopa>[\\s\\S]*?)\\\"\\,");
			Matcher m1 = p1.matcher(stavkeString);
			
			List<Stavka> stavke = new LinkedList<Stavka>();
			
			while (m1.find()) {
				
				String proizvod = m1.group("proizvod").trim();
				double kol = Double.parseDouble(m1.group("kol").trim());
				double cena = Double.parseDouble(m1.group("cena").trim());
				PoreskaStopa ps = poreskaStopaFromString(m1.group("stopa").trim());
				
				stavke.add(new Stavka(proizvod, kol, cena, ps));
			}
			
			return new Racun(broj, dateTime, uplaceno, stavke);
			
		}).collect(Collectors.toList());
	}
	
	
	public static double prodatoSireva(int dan, int mesec, int godina) {
		
		double d = Racuni.racuniStream(BROJ)
				.filter(r -> r.getVreme().getYear() == godina && r.getVreme().getMonthValue() == mesec && r.getVreme().getDayOfMonth() == dan)
				.flatMap(r -> r.getStavke().stream())
				.filter(s -> s.getProizvod().contains("sir RF"))
				.collect(Collectors.summingDouble(Stavka::getKolicina));
		
		System.out.printf("Ukupno prodato mekih sireva : %.2f %n", d);
		return d;
	}
	
	
	public static List<String> razlicitiProizvodi(int dan, int mesec, int godina) {
		
		return Racuni.racuniStream(BROJ)
				.filter(r -> r.getVreme().getYear() == godina && r.getVreme().getMonthValue() == mesec && r.getVreme().getDayOfMonth() == dan)
				.flatMap(r -> r.getStavke().stream())
				.map(Stavka::getProizvod)
				.collect(Collectors.toSet())
				.stream()
				.sorted()
				.collect(Collectors.toList());
	}
	
	
	public static void kozijegMleka(int godina) {
		
		class Data0 {
			
			double kol;
			
			public void add(Racun r) {
				for (Stavka s : r.getStavke())
					if (s.getProizvod().equalsIgnoreCase("Mleko, kozije"))
						kol += s.getKolicina();
			}
			
			public Data0 join(Data0 d) {
				kol += d.kol;
				return this;
			}
		}
		
		Map<LocalDate, Data0> m = Racuni.racuniStream(BROJ)
				.filter(r -> r.getVreme().getYear() == godina)
				.collect(Collectors.groupingBy(
						r -> r.getVreme().toLocalDate(),
						Collector.of(Data0::new, Data0::add, Data0::join)));
		
		if (m != null) {
			
			System.out.println("    Datum | Prodato  ");
			System.out.println("----------+----------");
			
			m.entrySet().stream()
					.map(e -> String.format(" %8s | %8.2f", e.getKey().format(DateTimeFormatter.ofPattern("dd.MM.")), e.getValue().kol))
					.forEach(System.out::println);
		}
	}
	
	
	public static void izvestaj(int dan, int mesec, int godina) {
		
		Racun izvestaj = Racuni.racuniStream(BROJ)
				.filter(r -> r.getVreme().getYear() == godina && r.getVreme().getMonthValue() == mesec && r.getVreme().getDayOfMonth() == dan)
				.reduce(
						null, 
						(r0, r1) -> new Racun(
								0, LocalDateTime.of(godina, mesec, dan, 12, 00), 0, 
								Stream.concat(
										r0 != null ? r0.getStavke().stream() : Stream.empty(), 
										r1 != null ? r1.getStavke().stream() : Stream.empty())
								.collect(Collectors.toList())));
		
		if (izvestaj != null) {
			
			class Data {
				
				double kol;
				double cena;
				
				public Data() {
					this.kol = 0.0;
					this.cena = 0.0;
				}
				
				public void add(Stavka s) {
					this.kol += s.getKolicina();
					this.cena += s.getCena();
				}
				
				public Data join(Data d) {
					this.kol += d.kol;
					this.cena += d.cena;
					
					return this;
				}
			}
			
			Map<String, List<Stavka>> m0 = izvestaj.getStavke().stream()
					.collect(Collectors.groupingBy(Stavka::getProizvod, Collectors.toList()));
			
			Map<String, Data> m1 = m0.entrySet().stream()
					.collect(
							Collectors.toMap(
									Map.Entry::getKey, 
									e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))));
			
			System.out.println("DNEVNI IZVESTAJ : ");
			
			m1.entrySet().stream()
				.map(e -> String.format("%s x %.2f = %.2f", e.getKey(), e.getValue().kol, e.getValue().cena))
				.forEach(System.out::println);
		}
	}
}
