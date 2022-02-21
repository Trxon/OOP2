package vezba.kol1_racuni_g02_ass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/*
 * Prvi kolokvijum
 * ===============
 * 
 * Napisati Java aplikaciju koja pomocu tokova podataka i lambda izraza
 * obradjuje podatke o fiskalnim racunima izdatim od strane jedne mlekare.
 * 
 * Data je klasa Racun kojom se prestavljaju fiskalni racuni. Svaki racun ima
 * svoj redni broj, datum i vreme kada je izdat, listu stavki na racunu, kao i
 * koliko je gotovine uplaceno kada je racun placen.
 * 
 * Data je i klasa Stavka cije instance predstavljaju stavke racuna, a svaka
 * stavka se sastoji od imena proizvoda, kolicine tog proizvoda, cene po
 * jedinici mere i poreske stope (predstavljene nabrojivim tipom).
 */
public class Grupa2Resenje {

	public static void main(String[] args) {

		System.out.println("Parsiranje");
		parse();

		System.out.println();
		System.out.println("Preko 5");
		System.out.println(prekoPet(3, 2018));
		System.out.println(prekoPet(4, 2018));
		
		System.out.println();
		System.out.println("Razliciti proizvodi");
		System.out.println(razlicitiProizvodi(1, 3, 2018));

		System.out.println();
		tabela1();

		System.out.println();
		tabela2();

	}

	/* 12.26
	 * Prvi deo (5 poena)
	 * ------------------
	 * 
	 * Dat je tok stringova u vidu metoda Racuni.xmlStream(). U njemu je svaki
	 * racun predstavljen jednim stringom u XML formatu. Za detalje o formatu
	 * pokrenuti program i pogledati kako izgleda svaki od stringova.
	 * 
	 * Pretvoriti dati tok stringova u tok Racun objekata i ispisati ih.
	 */
	public static void parse() {
		Racuni.xmlStream(5000)
				.map(s -> parseRacun(s))
				.map(r -> String.format("%4d %20s %s", // Nije se trazilo
						r.getBroj(),
						r.getVreme(),
						r)
				)
				.forEach(System.out::println);
	}

	private static DateTimeFormatter datumFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	private static DateTimeFormatter vremeFormat = DateTimeFormatter.ofPattern("HH:mm");

	public static Racun parseRacun(String string) {
		String regex = "(?ms).*"
				+ "<broj>(?<broj>.+?)</broj>"
				+ ".*?"
				+ "<datum>(?<datum>.+?)</datum>"
				+ ".*?"
				+ "<vreme>(?<vreme>.+?)</vreme>"
				+ ".*?"
				+ "(?<stavke><stavka>.+</stavka>)"
				+ ".*?"
				+ "<uplaceno>(?<uplaceno>.+?)</uplaceno>"
				+ ".*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		if (matcher.matches()) {
			int broj = Integer.parseInt(matcher.group("broj"));
			LocalDate datum = LocalDate.parse(matcher.group("datum"), datumFormat);
			LocalTime vreme = LocalTime.parse(matcher.group("vreme"), vremeFormat);
			LocalDateTime datumPlusVreme = LocalDateTime.of(datum, vreme);
			List<Stavka> stavke = parseStavke(matcher.group("stavke"));
			int uplaceno = Integer.parseInt(matcher.group("uplaceno"));
			return new Racun(broj, datumPlusVreme, uplaceno, stavke);
		} else {
			return null;
		}
	}

	public static List<Stavka> parseStavke(String string) {
		List<Stavka> stavke = new ArrayList<>();
		String regex = "(?ms)"
				+ "<proizvod>(?<proizvod>.+?)</proizvod>"
				+ ".*?"
				+ "<kolicina>(?<kolicina>.+?)</kolicina>"
				+ ".*?"
				+ "<cena>(?<cena>.+?)</cena>"
				+ ".*?"
				+ "<stopa>(?<stopa>.+?)</stopa>";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		while (matcher.find()) {
			String proizvod = matcher.group("proizvod");
			double kolicina = Double.parseDouble(matcher.group("kolicina"));
			double cena = Double.parseDouble(matcher.group("cena"));
			PoreskaStopa stopa = PoreskaStopa.valueOf(matcher.group("stopa").toUpperCase());
			stavke.add(new Stavka(proizvod, kolicina, cena, stopa));
		}
		return stavke;
	}

	/* 2.07
	 * Drugi deo (5 poena)
	 * -------------------
	 * 
	 * Implementirati metod long prekoPet(int mesec, int godina),
	 * pozvati ga u glavnom programu i ispisati rezultat.
	 * 
	 * Metod vraca ukupan broj racuna, za zadati mesec zadate godine, na kojima ima
	 * vise od pet stavki.
	 */
	public static long prekoPet(int mesec, int godina) {
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == godina)
				.filter(r -> r.getVreme().getMonthValue() == mesec)
				.filter(r -> r.getStavke().size() > 5)
				.count();
	}

	/* 2.41
	 * Treci deo (5 poena)
	 * -------------------
	 * 
	 * Implementirati metod List<String> razlicitiProizvodi(int dan, int mesec, int godina),
	 * pozvati ga u glavnom programu i ispisati rezultat.
	 * 
	 * Metod vraca nazive svih proizovda prodatih datog dana, sortirane abecedno,
	 * bez ponavaljanja.
	 */
	public static List<String> razlicitiProizvodi(int dan, int mesec, int godina) {
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == godina)
				.filter(r -> r.getVreme().getMonthValue() == mesec)
				.filter(r -> r.getVreme().getDayOfMonth() == dan)
				.flatMap(r -> r.getStavke().stream())
				.map(s -> s.getProizvod())
				.distinct()
				.sorted()
				.collect(Collectors.toList());
	}

	/* 3.37
	 * Cetvrti deo (5 poena)
	 * ---------------------
	 * 
	 * Za svaki proizvod ispisati koliko je ukupno jedinica mere (komada, kilograma,
	 * litara...) prodato, na sledeci nacin:
	 * 
	 *                Proizvod | Prodato puta
	 *   ----------------------+--------------
	 *   Zlatiborski kajmak RF |      1079.71
	 *              Trapist RF |      1199.36
	 *            Jogurt, casa |      2233.00
	 *                        ...
	 */
	public static void tabela1() {
		Map<String, Double> tabela = Racuni.racuniStream(5000)
				.flatMap(r -> r.getStavke().stream())
				.collect(Collectors.groupingBy(
						s -> s.getProizvod(),
						Collectors.summingDouble(s -> s.getKolicina()))
				);
		System.out.println(String.format("%30s | %10s", "Proizvod", "Prodato puta"));
		System.out.println("-------------------------------+--------------");
		tabela.entrySet().stream()
				.sorted(Map.Entry.comparingByKey()) // Nije se trazilo
				.map(e -> String.format("%30s | %12.2f", e.getKey(), e.getValue()))
				.forEach(System.out::println);
	}

	/* 4.42
	 * Peti deo (5 poena)
	 * ------------------
	 * 
	 * Pomocu operacije .reduce() generisati dnevni izvestaj za 1.5.2018. i smestiti
	 * ga u lokalnu promenljivu.
	 * 
	 * Dnevni izvestaj je izmisljen racun na kojem se nalaze sve stavke sa svih racuna
	 * zadatog dana. Redni broj ovakvog "racuna" je nula, vreme izdavanja je podne
	 * zadatog dana, a uplaceno je nula dinara.
	 * 
	 * Ispisati stavke sa dnevnog izvestaja (naziv proizvoda, kolicinu i ukupnu
	 * cenu) na sledeci nacin:
	 * 
	 *   Paprika u pavlaci RF x 1.100 = 550.00
	 *   Jogurt, light x 1.000 = 149.99
	 *   Trapist RF x 0.110 = 110.00
	 *   ...
	 * 
	 */
	public static void tabela2() {
		LocalDateTime datum = LocalDateTime.of(2018, 5, 1, 12, 0);
		Racun izvestaj = Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().toLocalDate().equals(datum.toLocalDate()))
				.reduce(new Racun(0, datum, 0, Collections.emptyList()),
						(r1, r2) -> {
							List<Stavka> stavke = new ArrayList<>();
							stavke.addAll(r1.getStavke());
							stavke.addAll(r2.getStavke());
							return new Racun(0, datum, 0, stavke);
						}
				);
		izvestaj.getStavke().stream()
				.map(s -> String.format("%s x %.3f = %.2f",
						s.getProizvod(),
						s.getKolicina(),
						s.getCena() * s.getKolicina()))
				.forEach(System.out::println);
	}
}
