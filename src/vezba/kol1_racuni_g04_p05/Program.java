package vezba.kol1_racuni_g04_p05;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
 * je svaki racun predstavljen jednim stringom bas onako kako bi bio
 * odstampan na fiskalnoj kasi. Za detalje o formatu pokrenuti prog-
 * ram i pogledati kako izgleda svaki od stringova.
 * 
 * Pretvoriti dati tok stringova u tok Racun objekata i ispisati ih.
 * 
 * Drugi deo (5 poena)
 * -------------------
 * 
 * Implementirati metod boolean nemaMilerama(int dan, int mesec, int
 * godina), pozvati ga u glavnom programu i ispisati rezultat.
 * 
 * Metod utvrdjuje da li je za dati dan prodata neka vrsta milerama.
 * Milerami su oni proizvodi koji na pocetku naziva imaju "mileram".
 * 
 * Treci deo (5 poena)
 * -------------------
 * 
 * Implementirati metod void stavke(int brojRacuna), pozvati ga u g-
 * lavnom programu.
 * 
 * Metod ispisuje sve stavke racuna sa zadatim brojem u XML formatu,
 * na sledeci nacin:
 *   <stavka>
 *     <proizvod>Mleko, kesa</proizvod>
 *     <kolicina>1</kolicina>
 *     <cena>67.49</cena>
 *     <stopa>posebna</stopa>
 *   </stavka>
 *   <stavka>
 *     <proizvod>Ekstra kiselo mleko</proizvod>
 *     <kolicina>6</kolicina>
 *     <cena>39.99</cena>
 *     <stopa>opsta</stopa>
 *   </stavka>
 *   ...
 * 
 * Cetvrti deo (5 poena)
 * ---------------------
 * 
 * Za svaki dan 2019. godine ispisati prosecnu visinu racuna, na sl-
 * edeci nacin:
 * 
 *     Datum | Prosek
 * ----------+----------
 *      2.1. |  673.52
 *      3.1. | 1043.31
 *      4.1. |  274.04
 *          ...
 * 
 * Peti deo (5 poena)
 * ------------------
 * 
 * Za svaku godinu ispisati koliko je poreza placeno po svakoj od p-
 * oreskih stopa.
 * 
 *  Godina |    Opsta |  Posebna | Oslobodjen
 * ===========================================
 *   2018  | 23487.54 |  3057.43 |       0.00
 *   2019  | 83753.95 | 10942.04 |       0.00
 *   ....
 * 
 */
public class Program {

	public static void main(String[] args) {

//		Racuni.stringStream(1)
//				.forEach(System.out::println);

//		Racuni.racuniStream(5000)
//				.forEach(System.out::println);
		
		// prvi deo
		List<Racun> racuni = load();
		
		// drugi deo
		System.out.println(drugiDeo(26, 4, 2020) ? "Ima milerama." : "Nema milerama");
		
		// treci deo
		treciDeo(1);
		
		// cetvrti deo
		cetvrtiDeo(2019);
		
		// peti deo
		petiDeo();
	}
	
	
	private static boolean drugiDeo(int dan, int mes, int god) {
		
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == god && r.getVreme().getMonthValue() == mes && r.getVreme().getDayOfMonth() == dan)
				.flatMap(r -> r.getStavke().stream())
				.anyMatch(s -> s.getProizvod().contains("ileram"));
	}


	private static void treciDeo(int broj) {
		
		Racuni.racuniStream(5000)
				.filter(r -> r.getBroj() == broj)
				.map(r -> {
					
					StringBuilder sb = new StringBuilder();
					
					for (Stavka s : r.getStavke()) {
						sb.append("<stavka>\n");
						sb.append("  <proizvod>" + s.getProizvod() + "</proizvod>\n");
						sb.append("  <kolicina>" + s.getKolicina() + "</kolicina>\n");
						sb.append("  <cena>" + s.getCena() + "</cena>\n");
						sb.append("  <stopa>" + s.getStopa().toString().toLowerCase() + "</stopa>\n");
						sb.append("</stavka>\n");
					}
					
					return sb.toString();
					
				}).forEach(System.out::println);
	}


	private static void cetvrtiDeo(int god) {
		/* Za svaki dan 2019. godine ispisati prosecnu visinu racuna, na sl-
		 * edeci nacin:
		 * 
		 *     Datum | Prosek
		 * ----------+----------
		 *      2.1. |  673.52
		 *      3.1. | 1043.31
		 *      4.1. |  274.04
		 */
		
		System.out.println("-----------+---------");
		System.out.println("     DATUM | PROSEK  ");
		System.out.println("-----------+---------");
		
		Racuni.racuniStream(5000)
						.filter(r -> r.getVreme().getYear() == god)
						.collect(Collectors.groupingBy(
								r -> r.getVreme().toLocalDate(), Collectors.averagingDouble(Racun::getVisina)))
				.entrySet().stream()
						.sorted(Map.Entry.comparingByKey())
						.map(e -> String.format("%10s | %6.2f", String.format(" %d.%d.", e.getKey().getDayOfMonth(), e.getKey().getMonthValue()), e.getValue()))
						.forEach(System.out::println);
	}


	private static void petiDeo() {
		
		class Data {
			
			double s0, s1;
			
			public Data() {
				this.s0 = 0;
				this.s1 = 0;
			}
			
			public void add(Racun r) {
				for (Stavka s : r.getStavke())
					if (s.getStopa() == PoreskaStopa.OPSTA)
						s0 += s.getCena() * 0.20;
					else if (s.getStopa() == PoreskaStopa.POSEBNA)
						s1 += s.getCena() * 0.25;
			}
			
			public Data join(Data d) {
				s0 += d.s0;
				s1 += d.s1;
				
				return this;
			}
		}
		
		System.out.println(" Godina |    Opsta   |  Posebna   | Oslobodjen ");
		System.out.println("===============================================");
		
		Racuni.racuniStream(5000)
						.collect(Collectors.groupingBy(
								r -> r.getVreme().getYear(), 
								Collectors.toList()))
				.entrySet().stream()
						.collect(Collectors.toMap(
								Map.Entry::getKey, 
								e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))))
				.entrySet().stream()
						.map(e -> String.format(" %6d | %10.2f | %10.2f | %10.2f ", 
								e.getKey(), e.getValue().s0, e.getValue().s1, 0.0))
						.forEach(System.out::println);
	}


	private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	private static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

	
	private static List<Racun> load() {
		
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
						PoreskaStopa stopa = PoreskaStopa.valueOf(m1.group("stopa").toUpperCase());
						
						stavke.add(new Stavka(proizvod, kol, cena, stopa));
					}
					
					return new Racun(broj, vreme, uplaceno, stavke);
				}).collect(Collectors.toList());
	}
}
