package vezba.kol1_racuni_g06_p05;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
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
		List<Racun> racuni = prviDeo();
		
		// drugi deo
		System.out.println("Prodato mleka : " + drugiDeo(26, 4, 2020));
		
		// treci deo
		treciDeo().stream().forEach(System.out::println);
		
		// cetvrti deo
		cetvrtiDeo(2018);
		
		// peti deo
		petiDeo();
	}
	
	
	private static double drugiDeo(int dan, int mes, int god) {
		
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == god && r.getVreme().getMonthValue() == mes && r.getVreme().getDayOfMonth() == dan)
				.flatMap(r -> r.getStavke().stream())
				.filter(s -> s.getProizvod().contains("leko"))
				.collect(Collectors.summingDouble(Stavka::getCena));
	}


	private static List<LocalDate> treciDeo() {
		
		Stavka maxStavka = Racuni.racuniStream(5000)
				.flatMap(r -> r.getStavke().stream())
				.filter(s -> s.getProizvod().contains("latibor"))
				.max(Comparator.comparing(Stavka::getCena)).orElse(null);
		
		if (maxStavka == null) return null;
		
		return Racuni.racuniStream(5000)
				.filter(r -> {
					
					for (Stavka s : r.getStavke()) {
						if (s.getProizvod().contains("latibor") && s.getCena() < maxStavka.getCena())
							return true;
					}
					
					return false;
					
				}).map(r -> r.getVreme().toLocalDate())
				.distinct().sorted()
				.collect(Collectors.toList());
	}


	private static String monthAsString(int i) {
		
		switch (i) {
			case 1  : return "Jan";
			case 2  : return "Feb";
			case 3  : return "Mar";
			case 4  : return "Apr";
			case 5  : return "Maj";
			case 6  : return "Jun";
			case 7  : return "Jul";
			case 8  : return "Avg";
			case 9  : return "Sep";
			case 10 : return "Okt";
			case 11 : return "Nov";
			case 12 : return "Dec";
			default : return "";
		}
	}
	
	
	private static void cetvrtiDeo(int god) {
		
		System.out.println("     | Izdato ");
		System.out.println("-----+--------");
		
		Racuni.racuniStream(5000)
						.filter(r -> r.getVreme().getYear() == god)
						.filter(r -> {
							
							for (Stavka s : r.getStavke()) {
								if (s.getProizvod().contains("RF"))
									return true;
							}
							
							return false;
							
						})
						.collect(Collectors.groupingBy(r -> r.getVreme().getMonthValue(), Collectors.counting()))
				.entrySet().stream()
						.map(e -> String.format(" %s | %5d ", monthAsString(e.getKey()), e.getValue()))
						.forEach(System.out::println);
	}


	private static void petiDeo() {
		
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
				
				if (this.min > s.getCena())
					this.min = s.getCena();
				
				if (this.max < s.getCena())
					this.max = s.getCena();
				
				this.sum += s.getCena();
				this.cnt += 1;
			}
			
			public Data join(Data d) {
				
				if (this.min > d.min)
					this.min = d.min;
				
				if (this.max < d.max)
					this.max = d.max;
				
				this.sum += d.sum;
				this.cnt += d.cnt;
				
				return this;
			}
			
			public double getAvg() {
				return sum / cnt;
			}
		}
		
		System.out.println("+----------------------+--------+--------+--------+");
		System.out.println("| Namaz                | Min    | Max    | Avg    |");
		System.out.println("+----------------------+--------+--------+--------+");
		
		Racuni.racuniStream(5000)
						.flatMap(r -> r.getStavke().stream())
						.filter(s -> s.getProizvod().contains("amaz"))
						.collect(Collectors.groupingBy(
								Stavka::getProizvod, 
								Collectors.toList()))
				.entrySet().stream()
						.collect(Collectors.toMap(
								Map.Entry::getKey, 
								e -> e.getValue().stream().collect(
										Collector.of(Data::new, Data::add, Data::join))))
				.entrySet().stream()
						.map(e -> String.format("| %20s | %6.2f | %6.2f | %6.2f |", 
								e.getKey(), e.getValue().min, e.getValue().max, e.getValue().getAvg()))
						.forEach(System.out::println);
		
		System.out.println("+----------------------+--------+--------+--------+");
	}


	private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	private static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

	
	private static List<Racun> prviDeo() {
		
		return Racuni.stringStream(50)
				.map(s -> {
					
					Pattern p0 = Pattern.compile("(?sm)\\\"broj\\\":\\s*?(?<broj>.*?),\\s*?\\\"datum\\\":\\s*?\\\"(?<datum>.*?)\\\",\\s*?\\\"vreme\\\":\\s*?\\\"(?<vreme>.*?)\\\",\\s*?\\\"stavke\\\":\\s*?(?<stavke>.*?)\\\"uplaceno\\\":\\s*?(?<uplaceno>.*?)}");
					Matcher m0 = p0.matcher(s);
					
					m0.find();
					
					int broj = Integer.parseInt(m0.group("broj").trim());
					
					LocalDateTime vreme = LocalDateTime.of(
							LocalDate.parse(m0.group("datum").trim(), dateFormat), 
							LocalTime.parse(m0.group("vreme").trim(), timeFormat));
					
					int uplaceno = Integer.parseInt(m0.group("uplaceno").trim());
					
					String stavkeString = m0.group("stavke").trim();
					List<Stavka> stavke = new ArrayList<Stavka>();
					
					Pattern p1 = Pattern.compile("(?sm)\\\"proizvod\\\":\\s*?\\\"(?<proizvod>.*?)\\\",\\s*?\\\"kolicina\\\":\\s*?(?<kol>.*?),\\s*?\\\"cena\\\":\\s*?(?<cena>.*?),\\s*?\\\"stopa\\\":\\s*?\\\"(?<stopa>.*?)\\\"");
					Matcher m1 = p1.matcher(stavkeString);
					
					while (m1.find()) {
						
						String proizvod = m1.group("proizvod").trim();
						double kol = Double.parseDouble(m1.group("kol").trim());
						double cena = Double.parseDouble(m1.group("cena").trim());
						PoreskaStopa stopa = PoreskaStopa.valueOf(m1.group("stopa").toString().toUpperCase());
						
						stavke.add(new Stavka(proizvod, kol, cena, stopa));
					}
					
					return new Racun(broj, vreme, uplaceno, stavke);
				}).collect(Collectors.toList());
	}
}
