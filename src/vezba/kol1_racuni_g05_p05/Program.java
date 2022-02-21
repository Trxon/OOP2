package vezba.kol1_racuni_g05_p05;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
		
		// prvi deo
		List<Racun> racuni = prviDeo();
		
		petiDeo();
		
		cetvrtiDeo(2020);
		
		treciDeo(1);
		
		System.out.println("Bez jogurta : " + drugiDeo(4, 2020));
	}
	
	
	private static long drugiDeo(int mes, int god) {
		
		Racun racun = Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == god && r.getVreme().getMonthValue() == mes)
				.filter(r -> {
					
					for (Stavka s : r.getStavke())
						if (s.getProizvod().contains("ogurt"))
							return true;
					
					return false;
					
				}).max(Comparator.comparing(Racun::getVisina)).orElse(null);
		
		if (racun == null) 
			return -1;
		else 
			return racun.getBroj();
	}


	private static void treciDeo(int broj) {
		
		Racuni.racuniStream(5000)
				.filter(r -> r.getBroj() == broj)
				.map(r -> {
					
					StringBuilder sb = new StringBuilder();
					
					for (Stavka s : r.getStavke()) {
						
						sb.append("{\n");
						
						sb.append("  \"proizvod\": \"");
						sb.append(s.getProizvod());
						sb.append("\",\n");
						
						sb.append("  \"kolicina\": \"");
						sb.append(s.getKolicina());
						sb.append("\",\n");
						
						sb.append("  \"cena\": \"");
						sb.append(s.getCena());
						sb.append("\",\n");
						
						sb.append("  \"stopa\": \"");
						sb.append(s.getStopa().toString().toLowerCase());
						sb.append("\",\n");
						
						sb.append("}\n");		
					}
					
					return sb.toString();
					
				}).forEach(System.out::println);
	}


	private static void cetvrtiDeo(int god) {
		
		System.out.println("------------+--------");
		System.out.println("    DATUM   | BR. R. ");
		System.out.println("------------+--------");

		Racuni.racuniStream(5000)
						.filter(r -> r.getVreme().getYear() == god)
						.collect(Collectors.groupingBy(
								r -> (LocalDate) r.getVreme().toLocalDate(), 
								Collectors.maxBy((r1, r2) -> r1.getStavke().size() - r2.getStavke().size())))
				.entrySet().stream()
						.sorted(Map.Entry.comparingByKey())
						.map(e -> String.format(" %10s | %6s ", 
								String.format("%d.%d.", e.getKey().getDayOfMonth(), e.getKey().getMonthValue()), 
								e.getValue().get() == null ? "-" : String.format("%06d", e.getValue().get().getBroj())))
						.forEach(System.out::println);
	}


	private static void petiDeo() {
		
		class Data implements Comparable<Data> {
			
			String p;
			LocalDate d;
			
			public Data (String p, LocalDate d) {
				this.p = p;
				this.d = d;
			}
			
			public Data() {
				this.p = null;
				this.d = null;
			}
			
			public void add(Data d) {
				
				if (d.d == null) return;
				
				if (this.d == null || this.d.compareTo(d.d) > 0) {
					this.p = d.p;
					this.d = d.d;
				}
			}
			
			public Data join(Data d) {

				if (d.d == null) return this;
				
				if (this.d == null || this.d.compareTo(d.d) > 0) {
					this.p = d.p;
					this.d = d.d;
				}
				
				return this;
			}

			@Override
			public int compareTo(Data o) {
				return d.compareTo(o.d);
			}
		}
		
		System.out.println("--------------------------------+------------");
		System.out.println("                   Proizvod     |   Datum    ");
		System.out.println("--------------------------------+------------");
		
		Racuni.racuniStream(5000)
						.flatMap(r -> {
							
							List<Data> list = new ArrayList<Data>();
							
							for (Stavka s : r.getStavke())
								list.add(new Data(s.getProizvod(), r.getVreme().toLocalDate()));
							
							return list.stream();
							
						}).collect(Collectors.groupingBy(
								d -> (String) d.p, 
								Collectors.toList()))
				.entrySet().stream()
						.collect(Collectors.toMap(
								Map.Entry::getKey, 
								e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))))
				.entrySet().stream()
						.sorted(Map.Entry.comparingByValue())
						.map(e -> String.format(" %30s | %s", e.getKey(), e.getValue().d))
						.forEach(System.out::println);
	}


	private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	private static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");


	private static List<Racun> prviDeo() {
		
		return Racuni.stringStream(5000)
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
						PoreskaStopa stopa = PoreskaStopa.valueOf(m1.group("stopa").trim().toUpperCase());
						
						stavke.add(new Stavka(proizvod, kolicina, cena, stopa));
					}
					
					return new Racun(broj, vreme, uplaceno, stavke);
					
				}).collect(Collectors.toList());
	}
}
