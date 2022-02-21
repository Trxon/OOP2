package vezba.kol1_racuni_g07_p05;

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
 * Implementirati sledeci metod: boolean prodataPaprika(int dan, int
 * mesec, int godina), pozvati ga u glavnom programu i ispisati rez-
 * ultat.
 * 
 * Dati metod utvrdjuje da li je zadatog dana prodata paprika u pav-
 * laci, bilo ljuta ili ne.
 * 
 * Treci deo (5 poena)
 * -------------------
 * 
 * Implementirati metod List<Double> kusuri() i pozvati ga u glavnom
 * programu i ispisati rezultat.
 * 
 * Metod treba da vraca listu u kojoj se za svaki racun iz toka nal-
 * azi kusur koji je trebalo vratiti za taj racun.
 * 
 * 
 * Cetvrti deo (5 poena)
 * ---------------------
 * 
 * Za svaki mesec 2018. godine ispisati ukupno ostvarenu zaradu pro-
 * date robe, na sledeci nacin:
 * 
 *      | Prodato
 * -----+----------
 *  Jan | 167348.52
 *  Feb | 104300.31
 *  Mar | 274560.04
 *     ...
 * 
 * Peti deo (5 poena)
 * ------------------
 * 
 * Za svaki dan u mesecu martu 2019. godine, ispisati koliko je pak-
 * ovanja kojeg kiselog mleka prodato, u tabelarnom obliku na slede-
 * ci nacin:
 * 
 * Dan       | Kiselo mleko 500g | Kiselo mleko | Ekstra kiselo mleko
 * ==================================================================
 *  01. mart |               125 |          532 |                  2
 *  02. mart |                12 |          125 |               1246
 *  ...
 * 
 */

public class Program {
	
	
	public static final int BROJ = 5000;
	

	public static void main(String[] args) {
		
//		Racuni.stringStream(1)
//			.forEach(System.out::println);
		
//		Racuni.racuniStream(BROJ)
//			.forEach(System.out::println);
		
		// prvi deo
		List<Racun> racuni = prviDeo();
		
		// drugi deo
		System.out.println(drugiDeo(26, 4, 2020) ? "Prodata paprika." : "Nije prodata paprika.");
		
		// treci deo
		treciDeo().stream().forEach(System.out::println);
		
		// cetvrti deo
		cetvrtiDeo(2020);
		
		// peti deo
		petiDeo(3, 2019);
	}
	
	
	private static boolean drugiDeo(int dan, int mes, int god) {
		
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == god && r.getVreme().getMonthValue() == mes && r.getVreme().getDayOfMonth() == dan)
				.flatMap(r -> r.getStavke().stream())
				.anyMatch(s -> s.getProizvod().contains("aprika"));
	}


	private static List<Double> treciDeo() {
		
		return Racuni.racuniStream(5000)
				.map(r -> r.getUplaceno() - r.getVisina())
				.collect(Collectors.toList());
	}


	private static void cetvrtiDeo(int god) {
		
		System.out.println("-----+-----------");
		System.out.println("     | Prodato   ");
		System.out.println("-----+-----------");

		Racuni.racuniStream(5000)
						.filter(r -> r.getVreme().getYear() == god)
						.collect(Collectors.groupingBy(
								r -> (Integer) r.getVreme().getMonthValue(), 
								Collectors.summingDouble(Racun::getVisina)))
				.entrySet().stream()
						.map(e -> String.format(" %3s | %-10.2f ", 
								monthAsString(e.getKey()).substring(0, 3).toUpperCase(), e.getValue()))
						.forEach(System.out::println);
				
	}


	private static String monthAsString(int i) {
		
		switch (i) {
			case 1  : return "januar";
			case 2  : return "februar";
			case 3  : return "mart";
			case 4  : return "april";
			case 5  : return "maj";
			case 6  : return "jun";
			case 7  : return "jul";
			case 8  : return "avgust";
			case 9  : return "septembar";
			case 10 : return "oktobar";
			case 11 : return "novembar";
			case 12 : return "decembar";
			default : return "";
		}
	}
	
	
	private static void petiDeo(int mes, int god) {
		
		class Data {
			
			double k0, k1, k2;
			
			public Data() {
				this.k0 = 0;
				this.k1 = 0;
				this.k2 = 0;
			}
			
			public void add(Racun r) {
				
				for (Stavka s : r.getStavke())
					if (s.getProizvod().equalsIgnoreCase("Kiselo mleko 500g"))
						k0 += s.getKolicina();
					else if (s.getProizvod().equalsIgnoreCase("Kiselo mleko"))
						k1 += s.getKolicina();
					else if (s.getProizvod().equalsIgnoreCase("Ekstra kiselo mleko"))
						k2 += s.getKolicina();
			}
			
			public Data join(Data d) {
				this.k0 += d.k0;
				this.k1 += d.k1;
				this.k2 += d.k2;
				
				return this;
			}
		}
		
		System.out.println("=====================================================================");
		System.out.println(" Dan       | Kiselo mleko 500g | Kiselo mleko | Ekstra kiselo mleko |");
		System.out.println("=====================================================================");

		Racuni.racuniStream(5000)
						.filter(r -> r.getVreme().getYear() == god && r.getVreme().getMonthValue() == mes)
						.collect(Collectors.groupingBy(
								r -> (LocalDate) r.getVreme().toLocalDate(), 
								Collectors.toList()))
				.entrySet().stream()
						.collect(Collectors.toMap(
								Map.Entry::getKey, 
								e -> e.getValue().stream().collect(Collector.of(
										Data::new, Data::add, Data::join))))
				.entrySet().stream()
						.sorted(Map.Entry.comparingByKey())
						.map(e -> String.format(" %02d. %s  | %17.0f | %12.0f | %19.0f | ", 
								e.getKey().getDayOfMonth(), monthAsString(e.getKey().getMonthValue()), 
								e.getValue().k0, e.getValue().k1, e.getValue().k2))
						.forEach(System.out::println);
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
						double kolicina = Double.parseDouble(m1.group("kol").trim());
						double cena = Double.parseDouble(m1.group("cena").trim());
						PoreskaStopa stopa = PoreskaStopa.valueOf(m1.group("stopa").toString().toUpperCase());
						
						stavke.add(new Stavka(proizvod, kolicina, cena, stopa));
					}
					
					return new Racun(broj, vreme, uplaceno, stavke);
					
				}).collect(Collectors.toList());
	}
}
