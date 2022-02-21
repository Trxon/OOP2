package vezba.kol1_racuni_g05_p02;

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
	
	
	public static final int BROJ = 5000;
	

	public static void main(String[] args) {

//		Racuni.stringStream(1)
//				.forEach(System.out::println);

//		Racuni.racuniStream(5000)
//				.forEach(System.out::println);
		
		// prvi deo
		List<Racun> racuni = load(Racuni.stringStream(50));
		
		// peti deo
		petiDeo();
		
		// cetvrti deo
		cetvrtiDeo(2020);
		
		// treci deo
		System.out.println(treciDeo(1));
		
		// drugi deo
		System.out.println("Najveci bez jogurta : " + drugiDeo(3, 2020));
	}


	public static PoreskaStopa stopaFromString(String s) {
		
		for (PoreskaStopa p : PoreskaStopa.values())
			if (p.toString().equalsIgnoreCase(s))
				return p;
		
		return null;
	}
	
	
	public static List<Racun> load(Stream<String> stringStream) {
		
		return stringStream.map(s -> {
			
			Pattern p0 = Pattern.compile("(?sm)\\\"broj\\\":\\s*(?<broj>[\\s\\S]*?),\\s*\\\"datum\\\":\\s*\\\"(?<datum>[\\s\\S]*?)\\\",\\s*\\\"vreme\\\":\\s*\\\"(?<vreme>[\\s\\S]*?)\\\",\\s*\\\"stavke\\\":\\s*(?<stavke>[\\s\\S]*?)\\\"uplaceno\\\":\\s*(?<uplaceno>[\\s\\S]*?)\\s*}");
			Matcher m0 = p0.matcher(s);
			
			m0.find();
			
			int broj = Integer.parseInt(m0.group("broj").trim());
			
			String datumString = m0.group("datum");
			String[] datumTokens = datumString.split("\\.");
			
			String vremeString = m0.group("vreme");
			
			LocalDateTime vreme = LocalDateTime.parse(datumTokens[2] + "-" + datumTokens[1] + "-" + datumTokens[0] + "T" + vremeString);
			
			int uplaceno = Integer.parseInt(m0.group("uplaceno").trim());
			
			String stavkeString = m0.group("stavke");
			
			List<Stavka> stavke = new ArrayList<Stavka>();
			
			Pattern p1 = Pattern.compile("(?sm)\\\"proizvod\\\":\\s*?\\\"(?<proizvod>[\\s\\S]*?)\\\",\\s*?\\\"kolicina\\\":\\s*?(?<kol>[\\s\\S]*?),\\s*?\\\"cena\\\":\\s*(?<cena>[\\s\\S]*?),\\s*?\\\"stopa\\\":\\s*\\\"(?<stopa>[\\s\\S]*?)\\\"");
			Matcher m1 = p1.matcher(stavkeString);
			
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
	
	
	private static boolean imaJogurta(Racun r) {
		
		for (Stavka s : r.getStavke())
			if (s.getProizvod().contains("ogurt"))
				return true;
		
		return false;
	}
	
	
	private static int drugiDeo(int mesec, int godina) {
		
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == godina && r.getVreme().getMonthValue() == mesec)
				.filter(r -> imaJogurta(r))
				.max(Comparator.comparingDouble(r -> r.getStavke().stream().collect(Collectors.summingDouble(Stavka::getCena)))).orElse(null).getBroj();
			
	}


	private static String treciDeo(int brojRacuna) {
		
		return Racuni.racuniStream(5000)
				.filter(r -> r.getBroj() == brojRacuna)
				.flatMap(r -> r.getStavke().stream())
				.map(s -> {
					
					StringBuilder sb = new StringBuilder();
					
					sb.append("{\n");
					
					sb.append("  \"proizvod\": \"" + s.getProizvod() + "\",\n");
					sb.append("  \"kolicina\": \"" + s.getKolicina() + "\",\n");
					sb.append("  \"cena\": \"" + s.getCena() + "\",\n");
					sb.append("  \"stopa\": \"" + s.getStopa().toString().toLowerCase() + "\",\n");
					
					sb.append("}");
					
					return sb.toString();
					
				}).reduce("", (s1, s2) -> "".equals(s1) ? s2 : s1 + "\n" + s2);
	}


	private static void cetvrtiDeo(int godina) {
		
		Map<LocalDate, List<Racun>> m0 = Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == godina)
				.collect(Collectors.groupingBy(r -> r.getVreme().toLocalDate(), Collectors.toList()));
		
		class Data {
			
			Racun r;
			
			public Data() {
				this.r = null;
			}
			
			public void add(Racun rac) {
				if (this.r == null || this.r.getStavke().size() < rac.getStavke().size())
					this.r = rac;
			}
			
			public Data join(Data d) {
				if (this.r == null || this.r.getStavke().size() < d.r.getStavke().size())
					this.r = d.r;
				
				return this;
			}
		}
		
		Map<LocalDate, Data> m1 = m0.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))));
		
		System.out.println("     Datum | Racun br.");
		System.out.println("-----------+----------");
		m1.entrySet().stream()
				.map(e -> {
					return String.format("%s | %05d", e.getKey(), e.getValue().r.getBroj());
				}).forEach(System.out::println);
	}


	private static void petiDeo() {
		
		class Data0 {
			
			String p;
			LocalDate d;
			
			public Data0(String p, LocalDate d) {
				this.p = p;
				this.d = d;
			}
		}
		
		Map<String, List<Data0>> m0 = Racuni.racuniStream(5000)
				.flatMap(r -> {
					
					List<Data0> l = new ArrayList<Data0>();
					
					for (Stavka s : r.getStavke())
						l.add(new Data0(s.getProizvod(), r.getVreme().toLocalDate()));
					
					return l.stream();
				}).collect(Collectors.groupingBy(d -> d.p, Collectors.toList()));
		
		class Data1 {
			
			String p;
			LocalDate d;
			
			public Data1() {
				this.p = null;
				this.d = null;
			}
			
			public void add(Data0 d) {
				
				if (d == null)
					return;

				this.p = d.p;
				
				if (this.d == null || this.d.compareTo(d.d) < 0)
					this.d = d.d;
			}
			
			public Data1 join(Data1 d) {
				this.p = d.p;
				if (this.d.compareTo(d.d) < 0)
					this.d = d.d;
				
				return this;
			}
		}
		
		Map<String, Data1> m1 = m0.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().collect(Collector.of(Data1::new, Data1::add, Data1::join))));
		
		System.out.println(" Proizvod                      | Datum       ");
		System.out.println("-------------------------------+-------------");
		m1.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.map(e -> {
					return String.format("%30s | %10s", e.getKey(), e.getValue().d.toString());
				}).forEach(System.out::println);
	}
}
