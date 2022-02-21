package vezba.kol1_racuni_g06_fv;

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
 * Dat je tok stringova u vidu metoda Racuni.stringStream(). U njemu
 * je svaki racun predstavljen jednim stringom u JSON formatu. Za d-
 * etalje o formatu pokrenuti program i pogledati kako izgleda svaki
 * od stringova.
 * 
 * Pretvoriti dati tok stringova u tok Racun objekata i ispisati ih.
 * 
 * Drugi deo (5 poena)
 * -------------------
 * 
 * Implementirati metod: 
 * 		double prodatoMleka(int dan, int mesec, int godina),
 * pozvati ga u glavnom programu i ispisati rezultat.
 * 
 * Metod vraca koliko je ukupno zaradjeno novca od prodaje svih vrs-
 * ta mleka zadatog dana. Mleka su oni proizvodi koji na pocetku na-
 * ziva imaju "mleko".
 * 
 * Treci deo (5 poena)
 * -------------------
 * 
 * Implementirati metod 
 * 		List<LocalDate> zlatiborskiKajmakNaAkciji(),
 * pozvati ga u glavnom programu i ispisati rezultat.
 * 
 * Metod vraca listu datuma kada je Zlatiborski kajmak prodat dok je
 * bio na popustu. Kajmak je na popustu ako je prodat po ceni manjoj
 * od maksimalne ikada.
 * 
 * Cetvrti deo (5 poena)
 * --------------------
 *
 * Za svaki mesec iz 2018. godine ispisati ukupan broj izdatih racu-
 * na koji sadrze rinfuznu robu, na sledeci nacin:
 * 
 *      | Izdato
 * -----+--------
 *  Jan |    673
 *  Feb |   1043
 *  Mar |    274
 *     ...
 * 
 * Rinfuzna roba su oni proizvodi koji na kraju imena imaju "RF".
 * 
 * Peti deo (5 poena)
 * ------------------
 * 
 * Za svaki namaz ispisati njegovu najnizu, najvisu bas kao i prose-
 * cnu cenu po jedinici mere (komad, kilogram, litar...), u tabelar-
 * nom obliku na sledeci nacin:
 * 
 * | Namaz             |    Min |    Max |    Avg |
 * |===================|========|========|========|
 * | Sirni namaz       |  89.99 | 119.99 | 118.88 |
 * | Namaz sa paprikom | 109.99 | 149.99 | 111.58 |
 * | Namaz sa renom    | 149.99 | 149.99 | 149.99 |
 * | Mali namaz        |  89.99 |  99.99 |  97.34 |
 * | ...
 * 
 * Namazi su oni proizvodi koji u nazivu imaju "namaz".
 * 
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {

	public static void main(String[] args) {

//		Racuni.stringStream(5000)
//				.forEach(System.out::println);
		
//		Racuni.racuniStream(5000)
//				.forEach(System.out::println);
		
		fromString(Racuni.stringStream(5))
				.forEach(System.out::println);
		System.out.println();
		
		System.out.println(prodatoMleka(11, 10, 2018));
		System.out.println();
		
		for(LocalDate ld : zlatiborskiKajmakNaAkciji()) {
			System.out.println(ld);
		}
		
		System.out.println();
		tabela1();
		System.out.println();
		tabela2();

	}
	
	private static Stream<Racun> fromString(Stream<String> stream) {
		return stream.map(s -> {
			String regexRacun = "(?sm).*?\"broj\":\\s*?(?<broj>\\d*?)\\,"
									+ "\\s*?\"datum\":\\s*?\"(?<datum>.*?)\".*?"
									+ "\"vreme\":\\s\"(?<vreme>\\d{2}:\\d{2})\".*?"
									+ "\"stavke\":\\s\\[(?<stavke>.*?)],.*?"
									+ "\"uplaceno\":\\s(?<racun>\\d*?)$";
			
			String regexStavke = "(?sm)\\{.*?\"proizvod\":\\s\"(?<proizvod>.*?)\",.*?"
									+ "\"kolicina\":\\s(?<kolicina>.*?),.*?"
									+ "\"cena\":\\s(?<cena>.*?),.*?"
									+ "\"stopa\":\\s\"(?<stopa>.*?)\",.*?\\}";
			
			Pattern p = Pattern.compile(regexRacun);
			Matcher m = p.matcher(s);
			
			int rb = 0;
			LocalDateTime ldt = null;
			ArrayList<Stavka> l = null;
			int uplata = 0;
			
			while(m.find()) {
				
				rb = Integer.parseInt(m.group("broj"));
				
				String[] tokens = m.group("datum").split("\\.");
				LocalDate ld = LocalDate.of(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[0]));
				LocalTime lt = LocalTime.parse(m.group("vreme"));
				ldt = LocalDateTime.of(ld, lt);
				uplata = Integer.parseInt(m.group("racun"));
				l = new ArrayList<>();
				Pattern p1 = Pattern.compile(regexStavke);
				Matcher m1 = p1.matcher(s);
				
				while (m1.find()) {
					String proizvod = m1.group("proizvod");
					double kolicina = Double.parseDouble(m1.group("kolicina"));
					double cena = Double.parseDouble(m1.group("cena"));
					PoreskaStopa ps = PoreskaStopa.getGrupa(m1.group("stopa"));
						
					l.add(new Stavka(proizvod, kolicina, cena, ps));
				}
			}
			Racun r = new Racun(rb, ldt, uplata, l);
			
			return r;
		});
	}

//	 * Implementirati metod double prodatoMleka(int dan, int mesec, int godina),
//	 * pozvati ga u glavnom programu i ispisati rezultat.
//	 * 
//	 * Metod vraca koliko je ukupno zaradjeno para prodajom svih vrsta mleka
//	 * zadatog dana. Mleka su oni proizvodi koji na pocetku naziva imaju "mleko".

	private static double prodatoMleka(int dan, int mesec, int godina) {
		return Racuni.racuniStream(5000)
					.filter( r -> r.getVreme().getDayOfMonth() == dan && 
									r.getVreme().getMonthValue() == mesec && 
									r.getVreme().getYear() == godina)
					.flatMap( r -> r.getStavke().stream())
					.filter( s -> s.getProizvod().indexOf("Mleko") == 0)
					.map(Stavka::getCena)
					.reduce(0.0, (a, b) -> a + b);
					
	}
	
//	 * Implementirati metod List<LocalDate> zlatiborskiKajmakNaAkciji(),
//	 * pozvati ga u glavnom programu i ispisati rezultat.
//	 * 
//	 * Metod vraca listu datuma kada je Zlatiborski kajmak prodat dok je
//	 * bio na popustu. Kajmak je na popustu ako je prodat po ceni manjoj od
//	 * maksimalne ikada.
	
	private static List<LocalDate> zlatiborskiKajmakNaAkciji() {
		
		double maxCena = Racuni.racuniStream(5000)
								.flatMap( r -> r.getStavke().stream())
								.filter( s -> s.getProizvod().equalsIgnoreCase("Zlatiborski kajmak RF"))
								.max(Comparator.comparing(Stavka::getCena))
								.map(Stavka::getCena)
								.get();
		
		return Racuni.racuniStream(5000)
								.filter( r -> {
											for (Stavka s : r.getStavke()) {
												if (s.getProizvod().equalsIgnoreCase("Zlatiborski kajmak RF"))
													if (s.getCena() < maxCena)
														return true;
											}
											return false;
										})
								.map( r -> LocalDate.of(r.getVreme().getYear(), r.getVreme().getMonthValue(), r.getVreme().getDayOfMonth()))
								.distinct()
								.collect(Collectors.toList());
	}
	
//	 * Za svaki mesec 2018. godine ispisati ukupan broj izdatih racuna koji 
//	 * sadrze rinfuznu robu, na sledeci nacin:
//	 * 
//	 *      | Izdato
//	 * -----+--------
//	 *  Jan |    673
//	 *  Feb |   1043
//	 *  Mar |    274
//	 *     ...
//	 * 
//	 * Rinfuzna roba su oni proizvodi koji na kraju imena imaju "RF".

	private static void tabela1() {
		
		System.out.println(   "     | Izdato\n"
							+ "-----+--------");
		
		Map<Integer, Long> m = Racuni.racuniStream(5000)
									.filter( r -> r.getVreme().getYear() == 2018)
									.filter(r -> {
											for (Stavka s : r.getStavke()) {
												if (s.getProizvod().indexOf(" RF") != -1)
													return true;
											}
											return false;
										})
									.collect(Collectors.groupingBy(
																	(Racun r) -> r.getVreme().getMonthValue(), 
																	Collectors.counting()));
		
		m.entrySet().stream()
					.map(e -> String.format("%-5s|%5d", Month.of(e.getKey()).toString().substring(0, 3), e.getValue()))
					.forEach(System.out::println);
	}
	
//	 * Za svaki namaz ispisati njegovu najnizu, najvisu i prosecnu cenu po
//	 * jedinici mere (komad, kilogram, litar...), u tabelarnom obliku na
//	 * sledeci nacin:
//	 * 
//	 * | Namaz             |    Min |    Max |    Avg |
//	 * |===================|========|========|========|
//	 * | Sirni namaz       |  89.99 | 119.99 | 118.88 |
//	 * | Namaz sa paprikom | 109.99 | 149.99 | 111.58 |
//	 * | Namaz sa renom    | 149.99 | 149.99 | 149.99 |
//	 * | Mali namaz        |  89.99 |  99.99 |  97.34 |
//	 * | ...
//	 * 
//	 * Namazi su oni proizvodi koji u nazivu imaju "namaz".
	
	private static void tabela2() {
		
		class Stats {
			double min = Double.MAX_VALUE;
			double max = Double.MIN_VALUE;
			int br;
			double ukupno;
		}
		
		Map<String, Stats> m = Racuni.racuniStream(5000)
									.flatMap( r -> r.getStavke().stream())
									.filter( s -> s.getProizvod().indexOf("Namaz") >= 0 || s.getProizvod().indexOf("namaz") >= 0)
									.collect(Collectors.groupingBy(
																	Stavka::getProizvod,
																	Collector.of(
																					Stats::new,
																					(stats, stavka) -> {
																						double cena = stavka.getCena() / stavka.getKolicina();
																						stats.br++;
																						stats.ukupno += cena;
																						stats.min = stats.min < cena ? stats.min : cena;
																						stats.max = stats.max > cena ? stats.max : cena;
																					}, 
																					(s1, s2) -> {
																						s1.br += s2.br;
																						s1.ukupno += s2.ukupno;
																						s1.min = s1.min < s2.min ? s1.min : s2.min;
																						s1.max = s1.max > s2.max ? s1.max : s2.max;
																						return s1;
																					})));
		
		System.out.println(   "| Namaz             |    Min |    Max |    Avg |\n"
							+ "|===================|========|========|========|");
		
		m.entrySet().stream()
				.map( e -> String.format("|%-19s|%8.2f|%8.2f|%8.2f|", e.getKey(), 
																	e.getValue().min,
																	e.getValue().max,
																	e.getValue().ukupno/e.getValue().br))
				.forEach(System.out::println);
		
	}
}
