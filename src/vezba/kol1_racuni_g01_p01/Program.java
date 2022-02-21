package vezba.kol1_racuni_g01_p01;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
 * Dat je tok stringova u vidu metoda Racuni.xmlStream(). U njemu je
 * svaki racun predstavljen jednim stringom u XML formatu. Za detal-
 * je o formatu pokrenuti program i pogledati kako format izgleda za
 * svaki od stringova.
 * 
 * Pretvoriti dati tok stringova u tok Racun objekata i ispisati ih.
 * 
 * Drugi deo (5 poena)
 * -------------------
 * 
 * Implementirati metod long ispodHiljadu(int mesec, int godina), p-
 * ozvati ga u glavnom programu i ispisati rezultat.
 * 
 * Metod vraca ukupan broj racuna, za zadati mesec zadate godine, za
 * koje je uplaceno strogo manje od hiljadu dinara.
 * 
 * Treci deo (5 poena)
 * -------------------
 * 
 * Implementirati metod List<Racun> sortiraniPoVisini(int dan, int m-
 * esec, int godina), pozvati ga u glavnom programu i ispisati rezul-
 * tat.
 * 
 * Metod vraca listu racuna izadtih zadatog dana, koji su rastuce so-
 * rtirani po visini racuna.
 * 
 * Cetvrti deo (5 poena)
 * ---------------------
 * 
 * Za svaki proizvod ispisati koliko je puta prodat (ukupan broj rac-
 * una na kojima se nalazi taj proizvod), na sledeci nacin:
 * 
 *      Proizvod | Prodato puta
 * --------------+--------------
 *    Mleko 3,2% |         1245
 *    Mleko 2,8% |        12876
 *  Jogurt 1,5kg |         3762
 *              ...
 * 
 * Peti deo (5 poena)
 * ------------------
 * 
 * Za svaku vrstu tvrdog sira, ispisati koliko je kilograma prodato u
 * svakom mesecu 2019. godine, u tabelarnom obliku na sledeci nacin:
 * 
 * Sir         |  Januar | Februar |    Mart | ...
 * ------------+---------+---------+---------+-----
 * Bjuval RF   |  125.32 |  532.30 |    2.34 |
 * Gauda RF    |   12.43 |  125.44 | 1246.32 |
 * Edamer RF   |     ... |     ... |     ... |
 * Tilsiter RF |     ... |     ... |     ... |
 * Trapist RF  |     ... |     ... |     ... |
 * 
 */

public class Program {
	
	
	public static final int BROJ = 5000;
	

	public static void main(String[] args) {

//		Racuni.xmlStream(BROJ)
//				.forEach(System.out::println);

//		Racuni.racuniStream(BROJ)
//				.forEach(System.out::println);
		
		// prvi deo
		List<Racun> racuni = load(Racuni.xmlStream(BROJ));
		
		// drugi deo
		ispodHiljadu(2020, 3, 1000);
		
		// treci deo
		sortiraniPoVisini(1, 3, 2019);
		
		// cetvrti deo
		prodatoPuta();
		
		// peti deo
		kolikoSiraPogresno();
		kolikoSiraIspravno();
	}
	
	
	private static PoreskaStopa stopaFromString(String s) {
		switch (s) {
			case "OPSTA"		: return PoreskaStopa.OPSTA;
			case "POSEBNO"		: return PoreskaStopa.POSEBNA;
			case "OSLOBODJEN"	: return PoreskaStopa.OSLOBODJEN;
			default				: return null;
		}
	}
	
	
	public static List<Racun> load(Stream<String> r) {
		
		return r.map(s -> {
			
			Pattern p0 = Pattern.compile("(?sm)[\\s\\S]*?\\<broj\\>(?<broj>\\d*?)\\<\\/[\\s\\S]*?\\<datum\\>(?<datum>[\\s\\S]*?)\\<\\/datum\\>[\\s\\S]*?\\<vreme\\>(?<vreme>\\d{2}\\:\\d{2})\\<\\/vreme\\>[\\s\\S]*?(?<stavke>\\<stavka\\>[\\s\\S]*)\\<uplaceno\\>(?<uplaceno>[\\s\\S]*?)\\<\\/uplaceno\\>");
			Matcher m0 = p0.matcher(s);
			
			m0.find();
			
			int broj = Integer.parseInt(m0.group("broj").trim());
			
			String datumString = m0.group("datum").trim();
			String vremeString = m0.group("vreme").trim();
			
			String[] datumTokens = datumString.split("\\.");
			
			LocalDateTime datumVreme = LocalDateTime.parse(datumTokens[2] + "-" + datumTokens[1] + "-" + datumTokens[0] + "T" + vremeString);
			
			int uplaceno = Integer.parseInt(m0.group("uplaceno").trim());
			
			String stavkeString = m0.group("stavke");
			
			Pattern p1 = Pattern.compile("(?sm)[\\s\\S]*?\\<proizvod\\>(?<proizvod>[\\s\\S]*?)\\<\\/proizvod\\>\\s*?\\<kolicina\\>(?<kolicina>[\\s\\S]*?)\\<\\/kolicina\\>\\s*?\\<cena\\>(?<cena>[\\s\\S]*?)\\<\\/cena\\>\\s*?\\<stopa\\>(?<stopa>[\\s\\S]*?)\\<\\/stopa\\>");
			Matcher m1 = p1.matcher(stavkeString);
			
			List<Stavka> stavke = new LinkedList<Stavka>();
			
			while (m1.find()) {
				
				String proizvod = m1.group("proizvod");
				Double kolicina = Double.parseDouble(m1.group("kolicina").trim());
				double cena = Double.parseDouble(m1.group("cena").trim());
				PoreskaStopa stopa = stopaFromString(m1.group("stopa").toUpperCase());
				
				stavke.add(new Stavka(proizvod, kolicina, cena, stopa));
			}
			
			return new Racun(broj, datumVreme, uplaceno, stavke);
			
		}).collect(Collectors.toList());
	}
	
	
	public static long ispodHiljadu(int mesec, int godina, int limit) {
		
		long l = Racuni.racuniStream(BROJ)
			.filter(r -> r.getVreme().getYear() == godina && r.getVreme().getMonthValue() == mesec)
			.filter(r -> r.getUplaceno() < limit)
			.count();
		
		System.out.println("Broj racuna sa uplacenih strogo manje od 1000 RSD : " + l);
		
		return l;
	}
	
	
	public static List<Racun> sortiraniPoVisini(int dan, int mesec, int godina) {
		
		List<Racun> l = Racuni.racuniStream(BROJ)
			.filter(r -> r.getVreme().getYear() == godina && r.getVreme().getMonthValue() == mesec && r.getVreme().getDayOfMonth() == dan)
			.sorted(Comparator.comparingDouble(Racun::getUplaceno))
			.collect(Collectors.toList());
		
		System.out.println("Racuni sortirani po visini : " + l);
		return l;
	}
	
	
	public static void prodatoPuta() {
		
		System.out.println("                       Proizvod | Prodato puta ");
		System.out.println("--------------------------------+--------------");
		
		Racuni.racuniStream(BROJ)
			.flatMap(r -> r.getStavke().stream())
			.collect(Collectors.groupingBy(Stavka::getProizvod, Collectors.counting()))
			.entrySet().stream()
			.map(e -> String.format(" %30s | %10d ", e.getKey(), e.getValue()))
			.forEach(System.out::println);
	}
	
	
	private static String mesecAsString(int i) {
		switch (i) {
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
	
	
	public static void kolikoSiraPogresno() {
		
		Map<Integer, List<Stavka>> m0 = Racuni.racuniStream(BROJ)
				.collect(Collectors.groupingBy(
						r -> r.getVreme().getMonthValue(),
						Collectors.flatMapping(r -> r.getStavke().stream(), Collectors.toList())));
		
		class Data {
			
			double trapist, edamer, bjuval, gauda, tilsiter;
			
			public void add(Stavka s) {switch (s.getProizvod()) {
					case "Trapist RF"	: trapist 	+= s.getKolicina();
					case "Edamer RF"	: edamer 	+= s.getKolicina();
					case "Bjuval RF"	: bjuval 	+= s.getKolicina();
					case "Gauda RF"		: gauda 	+= s.getKolicina();
					case "Tilsiter RF"	: tilsiter 	+= s.getKolicina();
					default				: return;
				}	
			}
			
			public Data join(Data d) {
				
				this.trapist 	+= d.trapist;
				this.edamer 	+= d.edamer;
				this.bjuval 	+= d.bjuval;
				this.gauda 		+= d.gauda;
				this.tilsiter 	+= d.tilsiter;
				
				return this;
			}
		}
		
		Map<Integer, Data> m1 = m0.entrySet().stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey, 
						e -> e.getValue().stream().collect(
								Collector.of(Data::new, Data::add, Data::join))));
		
		System.out.println(" MESEC | Trapist |  Edamer |  Bjuval |   Gauda | Tilsit. ");
		System.out.println("-------+---------+---------+---------+---------+---------");
		
		m1.entrySet().stream()
			.map(e -> {
				return String.format(" %4s  | %7.2f | %7.2f | %7.2f | %7.2f | %7.2f", 
						mesecAsString(e.getKey()), e.getValue().trapist, e.getValue().edamer, e.getValue().bjuval, e.getValue().gauda, e.getValue().tilsiter);
			})
			.forEach(System.out::println);
	}
	
	
	private static boolean jeTvrdiSir(Stavka s) {
		
		String[] sirevi = { "Trapist RF", "Edamer RF", "Bjuval RF", "Gauda RF", "Tilsiter RF" };
		
		for (String sir : sirevi)
			if (s.getProizvod().equalsIgnoreCase(sir))
				return true;
		
		return false;
	}
	
	
	public static void kolikoSiraIspravno() {
		
		class Data0 {
			
			Stavka s;
			int mesec;
			
			public Data0(Stavka s, int mesec) {
				this.s = s;
				this.mesec = mesec;
			}
		}
		
		Map<String, List<Data0>> m0 = Racuni.racuniStream(BROJ)
				.flatMap(r -> {
					
					List<Data0> l = r.getStavke().stream()
							.map(s -> new Data0(s, r.getVreme().getMonthValue()))
							.collect(Collectors.toList());
					
					return l.stream();
				})
				.filter(d -> jeTvrdiSir(d.s))
				.collect(Collectors.groupingBy(d -> d.s.getProizvod(), Collectors.toList()));
		
		class Data1 {
			
			@SuppressWarnings("unused")
			Stavka s;
			double[] mesec;
			
			public Data1() {
				this.s = null;
				this.mesec = new double[12];
			}
			
			public void add(Data0 d) {
				this.s = d.s;
				this.mesec[d.mesec - 1] += d.s.getKolicina();
			}
			
			public Data1 join(Data1 d) {
				
				for (int i = 0; i < 12; i++)
					this.mesec[i] = d.mesec[i];
				
				return this;
			}
		}
		
		Map<String, Data1> m1 = m0.entrySet().stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey, 
						e -> e.getValue().stream().collect(Collector.of(Data1::new, Data1::add, Data1::join))));
		
		System.out.println(" Sir        |    JAN   |    FEB   |    MAR   |    APR   |    MAJ   |    JUN   |    JUL   |    AVG   |    SEP   |    OKT   |    NOV   |    DEC    ");
		System.out.println("------------+----------+----------+----------+----------+----------+----------+----------+----------+----------+----------+----------+---------- ");
		m1.entrySet().stream()
				.map(e -> {
					return String.format("%11s | %8.2f | %8.2f | %8.2f | %8.2f | %8.2f | %8.2f | %8.2f | %8.2f | %8.2f | %8.2f | %8.2f | %8.2f ", 
							e.getKey(), 
							e.getValue().mesec[0] , e.getValue().mesec[1] , e.getValue().mesec[2] , e.getValue().mesec[3] ,
							e.getValue().mesec[4] , e.getValue().mesec[5] , e.getValue().mesec[6] , e.getValue().mesec[7] ,
							e.getValue().mesec[8] , e.getValue().mesec[9] , e.getValue().mesec[10], e.getValue().mesec[11]);
				})
				.forEach(System.out::println);
	}
}
