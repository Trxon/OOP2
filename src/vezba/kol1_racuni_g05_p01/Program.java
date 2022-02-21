package vezba.kol1_racuni_g05_p01;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
 * Dat je tok stringova u vidu metoda Racuni.stringStream(). U njemu
 * je svaki racun predstavljen jednim stringom bas onako kako bi bio
 * odstampan na fiskalnoj kasi. Za detalje o formatu pokrenuti prog-
 * ram i pogledati kako izgleda svaki od stringova.
 * 
 * Pretvoriti dati tok stringova u tok Racun objekata i ispisati ih.
 * 
 * Drugi deo (5 poena)
 * -------------------
 * 
 * Implementirati metod int najveciRacunBezJogurta(int mesec, int g-
 * odina), pozvati ga u glavnom programu i ispisati rezultat.
 * 
 * Metod vraca redni broj najveceg racuna zadatog meseca zadate god-
 * ine na kojem nema ni jedne vrste jogurta. Jogurti su oni proizvo-
 * di koji na pocetku naziva imaju "jogurt".
 * 
 * Treci deo (5 poena)
 * -------------------
 * 
 * Implementirati metod void stavke(int brojRacuna), pozvati ga u g-
 * lavnom programu.
 * 
 * Metod ispisuje sve stavke racuna sa zadatim brojem u JSON formatu
 * na sledeci nacin:
 *     {
 *       "proizvod": "Pavlaka 1kg",
 *       "kolicina": 2,
 *       "cena": 159.99,
 *       "stopa": "opsta",
 *     }
 *     {
 *       "proizvod": "Tilsiter RF",
 *       "kolicina": 1.02,
 *       "cena": 1200.0,
 *       "stopa": "opsta",
 *     }
 * 
 * Cetvrti deo (5 poena)
 * ---------------------
 * 
 * Za svaki dan 2020. godine treba ispisati redni broj racuna sa na-
 * jvise stavki, na sledeci nacin:
 * 
 *     Datum | Racun br.
 * ----------+----------
 *      2.1. | 000002
 *      3.1. | 000005
 *      4.1. | 000011
 *          ...
 * 
 * Peti deo (5 poena)
 * ------------------
 * 
 * Za svaki proizvod ispisati datum kada je prvi put prodat, u tabe-
 * larnom obliku, sortirano po datumu, na sledeci nacin:
 * 
 * Proizvod     | Datum
 * -------------+-------------
 * Mleko 3,2%   | 02.01.2018.
 * Mleko 2,8%   | 05.01.2018.
 * Jogurt 1,5kg | 02.02.2018.
 * Bjuval RF    | 01.03.2019.
 * ...
 * 
 */

public class Program {
	
	
	public static final int BROJ = 500;
	

	public static void main(String[] args) {

//		Racuni.stringStream(1)
//				.forEach(System.out::println);

//		Racuni.racuniStream(5000)
//				.forEach(System.out::println);
		
//		Racuni.racuniStream(BROJ)
//				.map(r -> r.getStavke().size())
//				.forEach(System.out::println);
		
		// prvi deo
		List<Racun> racuni = load(Racuni.stringStream(BROJ));
		
		// drugi deo
		najveciRacunBezJogurta(2020, 12);
		
		// treci deo
		stavke(1);
		
		// cetvrti deo
		racuniSaNajviseStavki(2018);
		
		// peti deo
		prviPutProdat();
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
	
	
	public static boolean imaJogurta(Racun r) {
		
		return r.getStavke().stream().anyMatch(s -> s.getProizvod().contains("ogurt"));
	}
	
	
	public static int najveciRacunBezJogurta(int mesec, int godina) {
		
		int i = Racuni.racuniStream(BROJ)
			.filter(r -> r.getVreme().getYear() == godina && r.getVreme().getMonthValue() == mesec && !imaJogurta(r))
			.max(Comparator.comparingDouble(r -> r.getStavke().stream().collect(Collectors.summingDouble(Stavka::getCena))))
			.map(Racun::getBroj).orElse(-1);
		
		System.out.printf("Najveci racun bez jogurta je : %d %n", i);
		return i;
	}
	
	
	public static void stavke(int brojRacuna) {
		
		List<Stavka> stavke = Racuni.racuniStream(BROJ)
			.filter(r -> r.getBroj() == brojRacuna)
			.map(Racun::getStavke)
			.findFirst().orElse(new LinkedList<Stavka>());
		
		stavke.stream()
			.map(s -> {
				
				StringBuilder out = new StringBuilder();
				
				out.append("\t{\n");
				
				out.append("\t\t\"proizvod\": \"");
				out.append(s.getProizvod());
				out.append("\",\n");
				
				out.append("\t\t\"kolicina\": \"");
				out.append(s.getKolicina());
				out.append("\",\n");
				
				out.append("\t\t\"cena\": \"");
				out.append(s.getCena());
				out.append("\",\n");

				out.append("\t\t\"stopa\": \"");
				out.append(s.getStopa().toString().toLowerCase());
				out.append("\",\n");
				
				out.append("\t}");
				
				return out.toString();
			})
			.forEach(System.out::println);
	}
	
	
	public static void racuniSaNajviseStavki(int godina) {

		Map<LocalDate, List<Racun>> m = Racuni.racuniStream(BROJ)
				.filter(r -> r.getVreme().getYear() == godina)
				.collect(Collectors.groupingBy(
						r -> r.getVreme().toLocalDate(),
						Collectors.toList()));
		
		System.out.println("    Datum | Racun br. ");
		System.out.println("----------+---------- ");
		m.entrySet().stream()
				.sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
				.map(e -> {
					
					int id = e.getValue().stream()
							.max((r1, r2) -> 
								(int) r1.getStavke().stream().count() - (int) r2.getStavke().stream().count())
							.map(Racun::getBroj).orElse(-1);
					
					return String.format("%9s | %9d", e.getKey().format(DateTimeFormatter.ofPattern("dd.MM.")), id);
				})
				.forEach(System.out::println);
	}
	
	
	private static void prviPutProdat() {
		
		class Data0 implements Comparable<Data0> {
			
			LocalDate date;
			String proizvod;
			
			public Data0(LocalDate date, String proizvod) {
				this.date = date;
				this.proizvod = proizvod;
			}

			@Override
			public int compareTo(Data0 o) {
				return date.compareTo(o.date);
			}
		}
		
		Map<String, List<Data0>> m0 = Racuni.racuniStream(BROJ)
				.flatMap(r -> {
					
					List<Data0> l = new LinkedList<Data0>();
					
					for (Stavka s : r.getStavke())
						l.add(new Data0(r.getVreme().toLocalDate(), s.getProizvod()));
					
					return l.stream();
				})
				.collect(Collectors.groupingBy(
						d -> d.proizvod, 
						Collectors.toList()));
		
		System.out.println("Proizvod                       | Datum       ");
		System.out.println("-------------------------------+-------------");
		m0.entrySet().stream()
			.map(e -> String.format("%-30s | %10s", 
						e.getKey(), 
						e.getValue().stream()
							.max(Comparator
									.naturalOrder()).map(d -> d.date)
							.orElse(LocalDate.of(2021, 4, 1))))
			.forEach(System.out::println);
	}
}
