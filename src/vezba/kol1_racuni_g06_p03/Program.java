package vezba.kol1_racuni_g06_p03;

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

//		Racuni.stringStream(1)
//				.forEach(System.out::println);

//		Racuni.racuniStream(5000)
//				.forEach(System.out::println);

		// prvi deo
		List<Racun> racuni = load(Racuni.stringStream(10));
		System.out.println(racuni.size());
		
		// drugi deo
		System.out.println("Ukupno prodato mleka na zadati dan : " + drugiDeo(26, 4, 2019));
		
		// treci deo
		System.out.println(treciDeo());
		
		// cetvrti deo
		cetvrtiDeo();
		
		// peti deo
		petiDeo();
	}
	
	
	private static double drugiDeo(int dan, int mesec, int godina) {
		
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == godina && r.getVreme().getMonthValue() == mesec && r.getVreme().getDayOfMonth() == dan)
				.flatMap(r -> r.getStavke().stream())
				.filter(s -> s.getProizvod().contains("leko"))
				.collect(Collectors.summingDouble(Stavka::getCena));
	}


	private static List<LocalDate> treciDeo() {
		
		Stavka maxStavka = Racuni.racuniStream(5000)
				.flatMap(r -> r.getStavke().stream())
				.filter(s -> s.getProizvod().contains("latiborski kajmak"))
				.collect(Collectors.maxBy(Comparator.comparingDouble(Stavka::getCena))).orElse(null);
		
		if (maxStavka == null) return null;
		
		return Racuni.racuniStream(5000)
				.filter(r -> {
					return r.getStavke().stream()
						.filter(s -> s.getProizvod().contains("latiborski kajmak"))
						.filter(s -> s.getCena() < maxStavka.getCena())
						.findAny().orElse(null) != null;
				})
				.map(r -> r.getVreme().toLocalDate())
				.distinct().sorted().collect(Collectors.toList());
	}


	private static String monthAsString(int mesec) {
		
		switch (mesec) {
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
	

	private static void cetvrtiDeo() {
		
		Map<Integer, Long> m0 = Racuni.racuniStream(5000)
				.filter(r -> r.getStavke().stream().anyMatch(s -> s.getProizvod().contains("RF")))
				.collect(Collectors.groupingBy(r -> (int) r.getVreme().getMonthValue(), Collectors.counting()));
		
		System.out.println("     | Izdato ");
		System.out.println("-----+--------");
		m0.entrySet().stream()
				.map(e -> String.format(" %s | %6d", monthAsString(e.getKey()), e.getValue()))
				.forEach(System.out::println);
	}


	private static void petiDeo() {
		
		Map<String, List<Stavka>> m0 = Racuni.racuniStream(5000)
				.flatMap(r -> r.getStavke().stream())
				.filter(s -> s.getProizvod().contains("amaz"))
				.collect(Collectors.groupingBy(Stavka::getProizvod, Collectors.toList()));
		
		class Data {
			
			double min;
			double max;
			double sum;
			int cnt;
			
			public Data() {
				this.min = Double.MAX_VALUE;
				this.max = Double.MIN_VALUE;
				this.sum = .0;
				this.cnt = 0;
			}
			
			public void add(Stavka s) {
				if (s.getCena() < min)
					min = s.getCena();
				if (s.getCena() > max)
					max = s.getCena();
				sum += s.getCena();
				cnt++;
			}
			
			public Data join(Data d) {
				if (d.min < min)
					min = d.min;
				if (d.max > max)
					max = d.max;
				sum += d.sum;
				cnt += d.cnt;
				
				return this;
			}
		}
		
		Map<String, Data> m1 = m0.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))));
		
		System.out.println("| Namaz              |      Min |      Max |      Avg |");
		System.out.println("|====================|==========|==========|==========|");
		m1.entrySet().stream()
				.map(e -> String.format("| %18s | %8.2f | %8.2f | %8.2f |", e.getKey(), e.getValue().min, e.getValue().max, e.getValue().sum / e.getValue().cnt))
				.forEach(System.out::println);
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
}
