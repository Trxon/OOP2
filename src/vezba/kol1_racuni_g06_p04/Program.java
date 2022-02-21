package vezba.kol1_racuni_g06_p04;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
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

public class Program {

	public static void main(String[] args) {

		Racuni.stringStream(20)
				.forEach(System.out::println);

		Racuni.racuniStream(5000)
				.forEach(System.out::println);

		// prvi deo
		List<Racun> racuni = load(Racuni.stringStream(20));
		
		// peti deo
		petiDeo();
		
		// cetvrti deo
		cetvrtiDeo(2018);
		
		// treci deo
		System.out.println(treciDeo());
		
		// drugi deo
		System.out.println("Prodato mleka : " + drugiDeo(1, 1, 2019));
	}


	private static PoreskaStopa stopaFromString(String s) {
		
		for (PoreskaStopa p : PoreskaStopa.values())
			if (p.toString().equalsIgnoreCase(s))
				return p;
		
		return null;
	}
	

	private static List<Racun> load(Stream<String> stringStream) {
		
		return stringStream
				.map(s -> {
					
					Pattern p0 = Pattern.compile("(?sm)\\\"broj\\\":\\s*(?<broj>[\\s\\S]*?),\\s*?\\\"datum\\\":\\s*?\\\"(?<datum>[\\s\\S]*?)\\\",\\s*?\\\"vreme\\\":\\s*?\\\"(?<vreme>[\\s\\S]*?)\\\",\\s*?\\\"stavke\\\":\\s*?(?<stavke>[\\s\\S]*?)\\\"uplaceno\\\":\\s*?(?<uplaceno>[\\s\\S]*?)\\s*?}");
					Matcher m0 = p0.matcher(s);
					
					m0.find();
					
					int broj = Integer.parseInt(m0.group("broj").trim());
					
					String datumString = m0.group("datum").trim();
					String[] datumTokens = datumString.split("\\.");
					
					String vremeString = m0.group("vreme").trim();
					
					LocalDateTime vreme = LocalDateTime.parse(datumTokens[2] + "-" + datumTokens[1] + "-" + datumTokens[0] + "T" + vremeString);
					
					int uplaceno = Integer.parseInt(m0.group("uplaceno").trim());
					
					String stavkeString = m0.group("stavke");
					
					Pattern p1 = Pattern.compile("(?sm)\\\"proizvod\\\":\\s*?\\\"(?<proizvod>[\\s\\S]*?)\\\",\\s*?\\\"kolicina\\\":\\s*?(?<kol>[\\s\\S]*?),\\s*?\\\"cena\\\":\\s*?(?<cena>[\\s\\S]*?),\\s*?\\\"stopa\\\":\\s*?\\\"(?<stopa>[\\s\\S]*?)\\\"");
					Matcher m1 = p1.matcher(stavkeString);
					
					List<Stavka> stavke = new ArrayList<Stavka>();
					
					while (m1.find()) {
						
						String proizvod = m1.group("proizvod");
						double kolicina = Double.parseDouble(m1.group("kol").trim());
						double cena = Double.parseDouble(m1.group("cena").trim());
						PoreskaStopa stopa = stopaFromString(m1.group("stopa").trim());
						
						stavke.add(new Stavka(proizvod, kolicina, cena, stopa));
					}
					
					return new Racun(broj, vreme, uplaceno, stavke);
				}).collect(Collectors.toList());
	}
	
	
	private static double drugiDeo(int dan, int mes, int god) {
		
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == god && r.getVreme().getMonthValue() == mes && r.getVreme().getDayOfMonth() == dan)
				.flatMap(r -> r.getStavke().stream())
				.filter(s -> s.getProizvod().contains("leko"))
				.collect(Collectors.summingDouble(Stavka::getCena));
	}


	private static boolean jeKajmakNaAkciji(Racun r, double max) {
		return r.getStavke().stream().filter(s -> s.getProizvod().contains("latiborski")).anyMatch(s -> s.getCena() < max);
	}
	
	
	private static List<LocalDate> treciDeo() {
		
		Stavka maxStavka = Racuni.racuniStream(5000)
				.flatMap(r -> r.getStavke().stream())
				.filter(s -> s.getProizvod().contains("latiborski"))
				.collect(Collectors.maxBy(Comparator.comparing(Stavka::getCena))).orElse(null);
		
		double max = maxStavka != null ? maxStavka.getCena() : Double.MAX_VALUE;
		
		return Racuni.racuniStream(5000)
				.filter(r -> jeKajmakNaAkciji(r, max))
				.map(r -> r.getVreme().toLocalDate())
				.distinct().sorted()
				.collect(Collectors.toList());
	}


	private static boolean sadrziRF(Racun r) {
		return r.getStavke().stream().anyMatch(s -> s.getProizvod().endsWith("RF"));
	}
	
	
	private static String monthAsString(int mes) {
		
		switch (mes) {
			case 1 : return "JAN";
			case 2 : return "FEB";
			case 3 : return "MAR";
			case 4 : return "APR";
			case 5 : return "MAJ";
			case 6 : return "JUN";
			case 7 : return "JUL";
			case 8 : return "AVG";
			case 9 : return "SEP";
			case 10: return "OKT";
			case 11: return "NOV";
			case 12: return "DEC";
			default: return null;
		}
	}
	

	private static void cetvrtiDeo(int god) {
		
		Map<Integer, Long> m0 = Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == god && sadrziRF(r))
				.collect(Collectors.groupingBy(r -> (int) r.getVreme().getMonthValue(), Collectors.counting()));
		
		System.out.println("     | Izdato ");
		System.out.println("-----+--------");
		m0.entrySet().stream()
				.map(e -> String.format(" %s | %6d ", monthAsString(e.getKey()), e.getValue()))
				.forEach(System.out::println);
	}


	private static void petiDeo() {
		
		Map<String, List<Stavka>> m0 = Racuni.racuniStream(5000)
				.flatMap(r -> r.getStavke().stream())
				.filter(s -> s.getProizvod().contains("amaz"))
				.collect(Collectors.groupingBy(Stavka::getProizvod, Collectors.toList()));
		
		class Data {
			
			double min, max, sum;
			int cnt;
			
			public Data() {
				this.min = Double.MAX_VALUE;
				this.max = Double.MIN_VALUE;
				this.sum = 0.0;
				this.cnt = 0;
			}
			
			public void add(Stavka s) {
				this.min = this.min < s.getCena() ? this.min : s.getCena();
				this.max = this.max > s.getCena() ? this.max : s.getCena();
				this.sum += s.getCena();
				this.cnt++;
			}
			
			public Data join(Data d) {
				this.min = this.min < d.min ? this.min : d.min;
				this.max = this.max > d.max ? this.max : d.max;
				this.sum += d.sum;
				this.cnt += d.cnt;
				
				return this;
			}
		}
		
		Map<String, Data> m1 = m0.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))));
		
		System.out.printf("| %30s | %10s | %10s | %10s | %n", "PROIZVOD", "MIN", "MAX", "AVG");
		System.out.println("|================================|============|============|============|");
		m1.entrySet().stream()
				.map(e -> String.format("| %30s | %10.2f | %10.2f | %10.2f |", e.getKey(), e.getValue().min, e.getValue().max, e.getValue().sum / e.getValue().cnt))
				.forEach(System.out::println);
	}
}
