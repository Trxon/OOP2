package vezba.kol1_racuni_g02_p02;

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

	public static void main(String[] args) {

//		Racuni.xmlStream(1)
//				.forEach(System.out::println);
//
//		Racuni.racuniStream(5000)
//				.forEach(System.out::println);
		
		// prvi deo
//		System.out.println(load(Racuni.xmlStream(10)));
		
		// drugi deo
		prekoPet(1, 2018);
		
		// treci deo
		razlicitiProizvodi(2, 1, 2018);
		
		// cetvrti deo
		cetvrtiDeo();
		
		// peti deo
		petiDeo(2, 1, 2018);
	}
	
	
	private static PoreskaStopa stopaFromString(String s) {
		
		for (PoreskaStopa p : PoreskaStopa.values())
			if (p.toString().equalsIgnoreCase(s))
				return p;
		
		return null;
	}
	
	
	public static List<Racun> load(Stream<String> xmlStream) {
		
		return xmlStream.map(s -> {
			
			Pattern p0 = Pattern.compile("(?sm)<racun>\\s*?<broj>(?<broj>[\\s\\S]*?)<\\/broj>\\s*?<datum>(?<datum>[\\s\\S]*?)<\\/datum>\\s*?<vreme>(?<vreme>[\\s\\S]*?)<\\/vreme>\\s*?(?<stavke>[\\s\\S]*?)<uplaceno>(?<uplaceno>[\\s\\S]*?)<\\/uplaceno>");
			Matcher m0 = p0.matcher(s);
			
			m0.find();
			
			int broj = Integer.parseInt(m0.group("broj").trim());
			
			String datumString = m0.group("datum");
			String[] datumTokens = datumString.split("\\.");
			
			String vremeString = m0.group("vreme");
			
			LocalDateTime vreme = LocalDateTime.parse(datumTokens[2] + "-" + datumTokens[1] + "-" + datumTokens[0] + "T" + vremeString);
			
			int uplaceno = Integer.parseInt(m0.group("uplaceno").trim());
			
			String stavkeString = m0.group("stavke");
			
			Pattern p1 = Pattern.compile("(?sm)\\s*?<proizvod>(?<proizvod>[\\s\\S]*?)<\\/proizvod>\\s*?<kolicina>(?<kol>[\\s\\S]*?)<\\/kolicina>\\s*?<cena>(?<cena>[\\s\\S]*?)<\\/cena>\\s*?<stopa>(?<stopa>[\\s\\S]*?)<\\/stopa>");
			Matcher m1 = p1.matcher(stavkeString);
			
			List<Stavka> stavke = new ArrayList<Stavka>();
			
			while (m1.find()) {
				
				String proizvod = m1.group("proizvod");
				double kol = Double.parseDouble(m1.group("kol"));
				double cena = Double.parseDouble(m1.group("cena")); 
				PoreskaStopa stopa = stopaFromString(m1.group("stopa"));
				
				stavke.add(new Stavka(proizvod, kol, cena, stopa));
			}
			
			return new Racun(broj, vreme, uplaceno, stavke);
			
		}).collect(Collectors.toList());
	}
	
	
	public static long prekoPet(int mesec, int godina) {
		
		long l = Racuni.racuniStream(500)
				.filter(r -> r.getVreme().getYear() == godina && r.getVreme().getMonthValue() == mesec)
				.filter(r -> r.getStavke().size() > 5)
				.count();
		
		System.out.println("Racuna sa preko pet stavki : " + l);
		
		return l;
	}
	
	
	public static List<String> razlicitiProizvodi(int dan, int mesec, int godina) {
		
		List<String> l = Racuni.racuniStream(500)
			.filter(r -> r.getVreme().getYear() == godina && r.getVreme().getMonthValue() == mesec && r.getVreme().getDayOfMonth() == dan)
			.flatMap(r -> r.getStavke().stream())
			.map(Stavka::getProizvod)
			.distinct()
			.collect(Collectors.toList());
		
		System.out.println("Razliciti proizvodi na zadati dan : " + l);
		
		return l;
	}
	
	
	public static void cetvrtiDeo() {
		
		Map<String, List<Stavka>> m0 = Racuni.racuniStream(500)
				.flatMap(r -> r.getStavke().stream())
				.collect(Collectors.groupingBy(Stavka::getProizvod, Collectors.toList()));
		
		class Data {
			
			double kol;
			
			public Data() {
				this.kol = 0.0;
			}
			
			public void add(Stavka s) {
				this.kol += s.getKolicina();
			}
			
			public Data join(Data d) {
				this.kol += d.kol;
				
				return this;
			}
		}
		
		Map<String, Data> m1 = m0.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))));
		
	    System.out.println("                      Proizvod | Prodato puta ");
	    System.out.println("-------------------------------+--------------");
		m1.entrySet().stream()
			.map(e -> {
				return String.format("%30s | %12.2f", e.getKey(), e.getValue().kol);
			}).forEach(System.out::println);
	}
	
	
	public static void petiDeo(int dan, int mes, int god) {
		
		Racun izvestaj = Racuni.racuniStream(500)
				.filter(r -> r.getVreme().getYear() == god && r.getVreme().getMonthValue() == mes && r.getVreme().getDayOfMonth() == dan)
				.reduce(
						null, (r0, r1) -> new Racun(0, LocalDateTime.of(god, mes, dan, 12, 00), 0,
									Stream.concat(
											r0 != null ? r0.getStavke().stream() : Stream.empty(), 
											r1 != null ? r1.getStavke().stream() : Stream.empty()).collect(Collectors.toList())));
		
		if (izvestaj == null) return;
		
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
		
		System.out.println("DNEVNI IZVESTAJ : ");
		m1.entrySet().stream()
				.map(e -> {
					return String.format("%s x %.2f = %.2f", e.getKey(), e.getValue().kol, e.getValue().cena);
				}).forEach(System.out::println);
	}
}
