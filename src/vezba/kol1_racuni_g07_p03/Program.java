package vezba.kol1_racuni_g07_p03;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
		
//		Racuni.stringStream(BROJ)
//			.forEach(System.out::println);
		
//		Racuni.racuniStream(BROJ)
//			.forEach(System.out::println);
		
		// prvi deo
		List<Racun> racuni = load();
		System.out.println(racuni.size());
		
		// drugi deo
		if (drugiDeo(1, 2, 2019)) System.out.println("Prodata!");
		
		// treci deo
		System.out.println("Kusuri : " + treciDeo());
		
		// cetvrti deo
		cetvrtiDeo(2018);
		
		// peti deo
		petiDeo(2019, 3);
	}
	
	
	private static boolean drugiDeo(int dan, int mesec, int godina) {
		
		return Racuni.racuniStream(BROJ)
				.filter(r -> r.getVreme().getYear() == godina && r.getVreme().getMonthValue() == mesec)
				.anyMatch(r -> r.getStavke().stream().anyMatch(s -> s.getProizvod().contains("aprika")));
	}


	private static List<Double> treciDeo() {
		
		return Racuni.racuniStream(BROJ)
				.map(r -> r.getUplaceno() - r.getVisina())
				.collect(Collectors.toList());
	}


	private static void cetvrtiDeo(int godina) {
		
		Map<Integer, Double> m0 = Racuni.racuniStream(BROJ)
				.filter(r -> r.getVreme().getYear() == godina)
				.collect(Collectors.groupingBy(r -> r.getVreme().getMonthValue(), Collectors.summingDouble(Racun::getVisina)));
		
		System.out.println("     | Prodato    ");
		System.out.println("-----+------------");
		m0.entrySet().stream()
				.map(e -> String.format("%4s | %.2f", e.getKey(), e.getValue()))
				.forEach(System.out::println);
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
			default: return "";
		}
	}
	
	
	private static String datumFormatted(LocalDate date) {
		return String.format("%2d. %s", date.getDayOfMonth(), monthAsString(date.getMonthValue()));
	}


	private static void petiDeo(int godina, int mesec) {
		
		Map<LocalDate, List<Racun>> m0 = Racuni.racuniStream(BROJ)
				.filter(r -> r.getVreme().getYear() == godina && r.getVreme().getMonthValue() == mesec)
				.collect(Collectors.groupingBy(r -> r.getVreme().toLocalDate(), Collectors.toList()));
		
		class Data {
			
			int k1, k2, k3;
			
			public void add(Racun r) {
				
				for (Stavka s : r.getStavke())
					if 		(s.getProizvod() == "Kiselo mleko 500g") 	k1++;
					else if (s.getProizvod() == "Kiselo mleko") 		k2++;
					else if (s.getProizvod() == "Ekstra kiselo mleko") 	k3++;
			}
			
			public Data join(Data d) {
				this.k1 += d.k1;
				this.k2 += d.k2;
				this.k3 += d.k3;
				
				return this;
			}
		}
		
		Map<LocalDate, Data> m1 = m0.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))));
		
		System.out.println("     Dan     | Kiselo mleko 500g  | Kiselo mleko   | Ekstra kiselo mleko ");
		System.out.println("=========================================================================");
		m1.entrySet().stream()
				.map(e -> String.format("  %8s   | %18d | %14d | %18d", datumFormatted(e.getKey()), e.getValue().k1, e.getValue().k2, e.getValue().k3))
				.forEach(System.out::println);
	}


	public static PoreskaStopa stopaFromString(String s) {
		
		for (PoreskaStopa p : PoreskaStopa.values())
			if (p.toString().equalsIgnoreCase(s))
				return p;
		
		return PoreskaStopa.OPSTA;
	}
	
	
	public static List<Racun> load() {
		
		return Racuni.stringStream(BROJ)
				.map(s -> {
					
					Pattern p0 = Pattern.compile("(?sm)\\\"broj\\\":\\s*?(?<broj>[\\s\\S]*?),\\s*?\\\"datum\\\":\\s*?\\\"(?<datum>[\\s\\S]*?)\\\",\\s*?\\\"vreme\\\":\\s*?\\\"(?<vreme>[\\s\\S]*?)\\\",\\s*?\\\"stavke\\\":\\s*?(?<stavke>[\\s\\S]*?)\\\"uplaceno\\\":\\s*?(?<uplaceno>[\\s\\S]*?)\\}");
					Matcher m0 = p0.matcher(s);
					
					m0.find();
					
					int broj = Integer.parseInt(m0.group("broj").trim());
					
					String[] datumTokens = m0.group("datum").trim().split("\\.");
					String vremeString = m0.group("vreme").trim();
					
					LocalDateTime vreme = LocalDateTime.parse(datumTokens[2] + "-" + datumTokens[1] + "-" + datumTokens[0] + "T" + vremeString);
					
					int uplaceno = Integer.parseInt(m0.group("uplaceno").trim());
					
					String stavkeString = m0.group("stavke");
					List<Stavka> stavke = new ArrayList<Stavka>();
					
					Pattern p1 = Pattern.compile("(?sm)\\\"proizvod\\\":\\s*?\\\"(?<proizvod>[\\s\\S]*?)\\\",\\s*?\\\"kolicina\\\":\\s*?(?<kolicina>[\\s\\S]*?),\\s*?\\\"cena\\\":\\s*?(?<cena>[\\s\\S]*?),\\s*?\\\"stopa\\\":\\s*?\\\"(?<stopa>[\\s\\S]*?)\\\"");
					Matcher m1 = p1.matcher(stavkeString);
					
					while (m1.find()) {
						
						String proizvod = m1.group("proizvod");
						double kolicina = Double.parseDouble(m1.group("kolicina").trim());
						double cena = Double.parseDouble(m1.group("cena").trim());
						PoreskaStopa stopa = stopaFromString(m1.group("stopa"));
						
						stavke.add(new Stavka(proizvod, kolicina, cena, stopa));
					}
					
					return new Racun(broj, vreme, uplaceno, stavke);
				}).collect(Collectors.toList());
	}
}
