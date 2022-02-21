package vezba.kol1_racuni_g02_p01;

import java.time.LocalDateTime;
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
 * Dat je tok stringova u vidu metoda Racuni.xmlStream(). U njemu je
 * svaki racun predstavljen jednim stringom u XML formatu. Za detal-
 * je o formatu pokrenuti program a zatim pogledati kako izgleda sv-
 * aki od stringova.
 * 
 * Pretvoriti dati tok stringova u tok Racun objekata i ispisati ih.
 * 
 * Drugi deo (5 poena)
 * -------------------
 * 
 * Implementirati metod long prekoPet(int mesec, int godina), pozva-
 * ti ga u glavnom programu i ispisati rezultat.
 * 
 * Metod vraca ukupan broj racuna, za zadati mesec zadate godine, na
 * kojima ima vise od pet stavki.
 * 
 * Treci deo (5 poena)
 * -------------------
 * 
 * Implementirati metod List<String> razlicitiProizvodi(int dan, int
 * mesec, int godina), pozvati ga u glavnom programu i ispisati rez-
 * ultat.
 * 
 * Metod vraca nazive svih proizovda prodatih datog dana i sortirane
 * abecedno, bez ponavaljanja.
 * 
 * Cetvrti deo (5 poena)
 * ---------------------
 * 
 * Za svaki proizvod ispisati koliko je ukupno jedinica mere (komada,
 * kilograma, litara...) prodato, na sledeci nacin:
 * 
 *      Proizvod | Prodato puta
 * --------------+--------------
 *    Mleko 3,2% |       1245.52
 *    Mleko 2,8% |      12876.43
 *  Jogurt 1,5kg |       3762.02
 *              ...
 * 
 * Peti deo (5 poena)
 * ------------------
 * 
 * Pomocu operacije .reduce() generisati dnevni izvestaj za 1.5.2018.
 * i smestiti ga u lokalnu promenljivu.
 * 
 * Dnevni izvestaj je izmisljen racun na kojem se mogu naci sve stav-
 * ke sa svih racuna zadatog dana. Redni broj ovakvog "racuna" je nu-
 * la, vreme izdavanja je podne zadatog dana, a uplaceno je nula din-
 * ara.
 * 
 * Ispisati stavke sa dnevnog izvestaja (naziv proizvoda, kolicinu k-
 * ao i ukupnu cenu) na sledeci nacin:
 * Mladi sir RF x 2.300 = 920.00
 * Namaz sa paprikom x 3 = 449.97
 * Kiselo mleko 500g x 1 = 59.99
 * ...
 * 
 */

public class Program {

	
	public static final int BROJ = 5000;
	
	
	public static void main(String[] args) {

//		Racuni.xmlStream(BROJ)
//				.forEach(System.out::println);

//		Racuni.racuniStream(BROJ)
//				.forEach(System.out::println);
		
		// prvi deo
		List<Racun> racuni = load(Racuni.xmlStream(BROJ));
		
		// drugi deo
		prekoPet(1, 2018);
		
		// treci deo
		System.out.println(razlicitiProizvodi(2, 1, 2018));
		
		// cetvrti deo
		prodatoPuta();
		
		// peti deo
		izvestaj(2, 1, 2018);
	}


	public static PoreskaStopa poreskaStopaFromString(String s) {
		
		if 		(s.equalsIgnoreCase("OPSTA")) 		return PoreskaStopa.OPSTA;
		else if (s.equalsIgnoreCase("POSEBNA")) 	return PoreskaStopa.POSEBNA;
		else  										return PoreskaStopa.OSLOBODJEN;
	}


	private static List<Racun> load(Stream<String> xmlStream) {
		
		return xmlStream.map(s -> {
			
			Pattern p0 = Pattern.compile("(?sm)<broj>(?<broj>\\d*?)<\\/broj>\\s*<datum>(?<datum>[\\s\\S]*?)<\\/datum>\\s*?<vreme>(?<vreme>\\d{2}\\:\\d{2})<\\/vreme>\\s*(?<stavke>[\\s\\S]*?)<uplaceno>(?<uplaceno>\\d*?)<\\/uplaceno>");
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
			
			Pattern p1 = Pattern.compile("<proizvod>(?<proizvod>[\\s\\S]*?)<\\/proizvod>\\s*?<kolicina>(?<kolicina>.*?)<\\/kolicina>\\s*?<cena>(?<cena>[\\s\\S]*?)<\\/cena>\\s*<stopa>(?<stopa>[\\s\\S]*?)<\\/stopa>");
			Matcher m1 = p1.matcher(stavkeString);
			
			List<Stavka> stavke = new LinkedList<Stavka>();
			
			while (m1.find()) {
				
				String proizvod = m1.group("proizvod").trim();
				double kol = Double.parseDouble(m1.group("kolicina").trim());
				double cena = Double.parseDouble(m1.group("cena").trim());
				PoreskaStopa ps = poreskaStopaFromString(m1.group("stopa").trim());
				
				stavke.add(new Stavka(proizvod, kol, cena, ps));
			}
			
			return new Racun(broj, dateTime, uplaceno, stavke);
			
		}).collect(Collectors.toList());
	}
	
	
	public static long prekoPet(int mesec, int godina) {
		
		long l = Racuni.racuniStream(BROJ)
				.filter(r -> r.getVreme().getYear() == godina && r.getVreme().getMonthValue() == mesec)
				.filter(r -> r.getStavke().size() > 5)
				.count();
		
		System.out.println("Broj racuna sa preko pet stavki : " + l);
		return l;
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
	
	
	public static void prodatoPuta() {
		
		Map<String, Double> m = Racuni.racuniStream(BROJ)
				.flatMap(r -> r.getStavke().stream())
				.collect(Collectors.groupingBy(Stavka::getProizvod, Collectors.summingDouble(Stavka::getKolicina)));
		
		System.out.println("                   Proizvod | Prodato puta ");
		System.out.println("----------------------------+--------------");
		m.entrySet().stream()
			.sorted(Map.Entry.comparingByKey())
			.map(e -> String.format(" %26s | %12.2f", e.getKey(), e.getValue())).forEach(System.out::println);
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
			
			System.out.println("DNEVNI IZVESTAJ : ");
			
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
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))));
			
			m1.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.map(e -> {
					return String.format("%s x %.2f = %.2f", e.getKey(), e.getValue().kol, e.getValue().cena);
				}).forEach(System.out::println);
		}
	}
}
