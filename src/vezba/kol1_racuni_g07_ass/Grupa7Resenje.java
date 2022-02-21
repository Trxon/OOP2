package vezba.kol1_racuni_g07_ass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
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
public class Grupa7Resenje {

	public static void main(String[] args) {

		System.out.println("Parsiranje");
		parse();

		System.out.println();
		System.out.println("ProdataPaprika");
		System.out.println(prodataPaprika(3, 1, 2018));
		System.out.println(prodataPaprika(4, 1, 2018));

		System.out.println();
		System.out.println("Kusuri");
		kusuri().stream()
				.map(d -> String.format("%8.2f", d)) // Nije se trazilo
				.forEach(System.out::println);;

		System.out.println();
		tabela1();
		
		System.out.println();
		tabela2();

	}

	/* 
	 * Prvi deo (5 poena)
	 * ------------------
	 * 
	 * Dat je tok stringova u vidu metoda Racuni.stringStream(). U njemu je svaki
	 * racun predstavljen jednim stringom bas onako kako bi bio odstampan na
	 * fiskalnoj kasi. Za detalje o formatu pokrenuti program i pogledati kako
	 * izgleda svaki od stringova.
	 * 
	 * Pretvoriti dati tok stringova u tok Racun objekata i ispisati ih.
	 */
	public static void parse() {
		Racuni.stringStream(5000)
				.map(s -> parseRacun(s))
				.map(r -> String.format("%4d %20s %s", // Nije se trazilo
						r.getBroj(),
						r.getVreme(),
						r)
				)
				.forEach(System.out::println);
	}

	private static DateTimeFormatter datumPlusVremeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");

	public static Racun parseRacun(String string) {
		String[] delovi = string.split("-+\n");
		String regex = "(?ms)"
				+ ".*"
				+ "UPLACENO +(?<uplaceno>[0-9]+).00\n"
				+ ".*"
				+ "^(?<datum>[0-9:. ]+?) +-\\+- +\n"
				+ ".*"
				+ "BI: +(?<broj>\\d+)"
				+ ".*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(delovi[3]);
		if (matcher.matches()) {
			int broj = Integer.parseInt(matcher.group("broj"));
			LocalDateTime datumPlusVreme = LocalDateTime.parse(matcher.group("datum"), datumPlusVremeFormat);
			List<Stavka> stavke = parseStavke(delovi[1]);
			int uplaceno = Integer.parseInt(matcher.group("uplaceno"));
			return new Racun(broj, datumPlusVreme, uplaceno, stavke);
		} else {
			return null;
		}
	}

	public static List<Stavka> parseStavke(String string) {
		List<Stavka> stavke = new ArrayList<>();
		String regex = "(?ms)^(?<proizvod>.+?) +?\n"
				+ " *"
				+ "(?<kolicina>[0-9.]+)x"
				+ " *"
				+ "(?<cena>[0-9.]+)"
				+ " *"
				+ "[0-9.]+"
				+ " "
				+ "(?<stopa>.)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		while (matcher.find()) {
			String proizvod = matcher.group("proizvod");
			double kolicina = Double.parseDouble(matcher.group("kolicina"));
			double cena = Double.parseDouble(matcher.group("cena"));
			char oznaka = matcher.group("stopa").charAt(0);
			PoreskaStopa stopa = Arrays.stream(PoreskaStopa.values())
					.filter(s -> s.getOznaka() == oznaka)
					.findAny()
					.orElse(null);
			stavke.add(new Stavka(proizvod, kolicina, cena, stopa));
		}
		return stavke;
	}

	/* 2.56
	 * Drugi deo (5 poena)
	 * -------------------
	 * 
	 * Implementirati metod boolean prodataPaprika(int dan, int mesec, int godina),
	 * pozvati ga u glavnom programu i ispisati rezultat.
	 * 
	 * Metod utvrdjuje da li je zadatog dana prodata paprika u pavlaci,
	 * bilo ljuta ili ne.
	 */
	public static boolean prodataPaprika(int dan, int mesec, int godina) {
		LocalDate datum = LocalDate.of(godina, mesec, dan);
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().toLocalDate().equals(datum))
				.flatMap(r -> r.getStavke().stream())
				.anyMatch(s -> s.getProizvod().toLowerCase().contains("paprika"));
	}

	/* 2.34
	 * Treci deo (5 poena)
	 * -------------------
	 * 
	 * Implementirati metod List<Double> kusuri(),
	 * pozvati ga u glavnom programu i ispisati rezultat.
	 * 
	 * Metod vraca listu u kojoj se za svaki racun iz toka nalazi kusur koji
	 * je trebalo vratiti za taj racun.
	 */
	public static List<Double> kusuri() {
		return Racuni.racuniStream(5000)
				.map(r -> r.getUplaceno() - sumaNaRacunu(r))
				.collect(Collectors.toList());
	}

	private static double sumaNaRacunu(Racun racun) {
		return racun.getStavke().stream()
				.mapToDouble(s -> s.getKolicina() * s.getCena())
				.sum();
	}

	/* 4.27
	 * Cetvrti deo (5 poena)
	 * ---------------------
	 * 
	 * Za svaki mesec 2018. godine ispisati ukupno ostvarenu zaradu prodate 
	 * robe, na sledeci nacin:
	 * 
	 *        | Prodato
	 *   -----+----------
	 *    Jan |  470652.57
	 *    Feb |  556020.23
	 *    Mar |  455768.12
	 *       ...
	 */
	public static void tabela1() {
		String[] meseci = {"", "Jan", "Feb", "Mar", "Apr", "Maj", "Jun", "Jul", "Avg", "Sep", "Okt" ,"Nov", "Dec"};
		Map<Month, Double> tabela = Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == 2018)
				.collect(Collectors.groupingBy(
						r -> r.getVreme().getMonth(),
						Collectors.summingDouble(r -> sumaNaRacunu(r))
				));
		System.out.println("     |     Zarada");
		System.out.println("-----+------------");
		tabela.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.map(e -> String.format(" %3s | %10.2f",
						meseci[e.getKey().getValue()], // Moze slicno i sa e.getKey().toString().substring(0, 3)
						e.getValue())
				)
				.forEach(System.out::println);
	}

	/* 6.07
	 * Peti deo (5 poena)
	 * ------------------
	 * 
	 * Za svaku godinu, ispisati koliko je poreza placeno po svakoj od
	 * poreskih stopa.
	 * 
	 *    Godina |      Opsta |    Posebna | Oslobodjen
	 *   ===============================================
	 *     2018  | 1096981.68 |   70635.78 |       0.00
	 *     2019  | 1128519.41 |   71563.39 |       0.00 
	 *     ....
	 */
	public static void tabela2() {

//		Resenje bez pomocne klase i u samo jednom prolasku
//
//		System.out.println(" Godina |      Opsta |    Posebna | Oslobodjen");
// 		System.out.println("===============================================");
//		Racuni.racuniStream(5000)
//				.collect(Collectors.groupingBy(
//						r -> r.getVreme().getYear(),
//						Collectors.flatMapping( // Postoji od Jave 9
//								r -> r.getStavke().stream(),
//								Collectors.groupingBy(
//										s -> s.getStopa(),
//										Collectors.summingDouble(s -> s.getKolicina() * s.getCena() * s.getStopa().getStopa() / 100.0)
//								)
//						)
//				))
//				.entrySet().stream()
//				.map(e -> String.format("  %4d  | %10.2f | %10.2f | %10.2f ",
//						e.getKey(),
//						e.getValue().getOrDefault(PoreskaStopa.OPSTA, 0.0),
//						e.getValue().getOrDefault(PoreskaStopa.POSEBNA, 0.0),
//						e.getValue().getOrDefault(PoreskaStopa.OSLOBODJEN, 0.0)
//				))
//				.forEach(System.out::println);

		class Suma {
			public double opsta;
			public double posebna;
		}

		Map<Integer, Suma> tabela = Racuni.racuniStream(5000)
				.collect(Collectors.groupingBy(r -> r.getVreme().getYear(),
						Collector.of(
								() -> new Suma(),
								(s, r) -> {
									double porezO = r.getStavke().stream()
											.filter(x -> x.getStopa() == PoreskaStopa.OPSTA)
											.mapToDouble(x -> x.getKolicina() * x.getCena())
											.map(x -> x * PoreskaStopa.OPSTA.getStopa() / 100.0)
											.sum();
									double porezP = r.getStavke().stream()
											.filter(x -> x.getStopa() == PoreskaStopa.POSEBNA)
											.mapToDouble(x -> x.getKolicina() * x.getCena())
											.map(x -> x * PoreskaStopa.POSEBNA.getStopa() / 100.0)
											.sum();
									s.opsta += porezO;
									s.posebna += porezP;
								},
								(s1, s2) -> {
									s1.opsta += s2.opsta;
									s1.posebna += s2.posebna;
									return s1;
								}
				)));
		System.out.println(" Godina |      Opsta |    Posebna | Oslobodjen");
 		System.out.println("===============================================");
		tabela.entrySet().stream()
				.map(e -> String.format("  %4d  | %10.2f | %10.2f | %10.2f ",
						e.getKey(),
						e.getValue().opsta,
						e.getValue().posebna,
						0.0))
				.forEach(System.out::println);

	}
}
