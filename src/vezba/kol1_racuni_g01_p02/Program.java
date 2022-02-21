package vezba.kol1_racuni_g01_p02;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
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
	

	public static void main(String[] args) {

//		Racuni.xmlStream(2)
//				.forEach(System.out::println);
//		
//		Racuni.racuniStream(5000)
//				.forEach(System.out::println);
		
		// prvi deo
		List<Racun> racuni = load(Racuni.xmlStream(2));
		
		// drugi deo
		System.out.println("Broj racuna : " + ispodHiljadu(4, 2019));
		
		// treci deo
		System.out.println(sortiraniPoVisini(26, 4, 2019));

		// cetvrti deo
		putaProdat();
		
		// peti deo
		sirevi();
	}
	
	
	private static PoreskaStopa stopaFromString(String s) {
		
		for (PoreskaStopa p : PoreskaStopa.values())
			if (p.toString().equalsIgnoreCase(s))
				return p;
		
		return null;
	}
	
	
	public static List<Racun> load(Stream<String> r) {
		
		return r.map(s -> {
				
				Pattern p0 = Pattern.compile("(?sm)<racun>\\s*?<broj>(?<broj>[\\s\\S]*?)<\\/broj>\\s*?<datum>(?<datum>[\\s\\S]*?)<\\/datum>\\s*<vreme>(?<vreme>[\\s\\S]*?)<\\/vreme>\\s*?(?<stavke>[\\s\\S]*?)<uplaceno>(?<uplaceno>[\\s\\S]*?)<\\/uplaceno>\\s*?<\\/racun>");
				Matcher m0 = p0.matcher(s);
				
				m0.find();
				
				int broj = Integer.parseInt(m0.group("broj").trim());
				
				String datum = m0.group("datum");
				String vreme = m0.group("vreme");
				
				int sat = Integer.parseInt(vreme.substring(0, 2));
				int min = Integer.parseInt(vreme.substring(3, 5));
				
				int god = Integer.parseInt(datum.substring(6, 10));
				int mes = Integer.parseInt(datum.substring(3, 5));
				int dan = Integer.parseInt(datum.substring(0, 2));
				
				LocalDateTime ld = LocalDateTime.of(god, mes, dan, sat, min);
				
				String stavke = m0.group("stavke");
				
				Pattern p1 = Pattern.compile("(?sm)<proizvod>(?<proizvod>[\\s\\S]*?)<\\/proizvod>\\s*?<kolicina>(?<kol>[\\s\\S]*?)<\\/kolicina>\\s*?<cena>(?<cena>[\\s\\S]*?)<\\/cena>\\s*?<stopa>(?<stopa>[\\s\\S]*?)<\\/stopa>");
				Matcher m1 = p1.matcher(stavke);
				
				List<Stavka> stavkeLista = new LinkedList<Stavka>(); 
				
				while (m1.find()) {
					
					String proizvod = m1.group("proizvod");
					double kolicina = Double.parseDouble(m1.group("kol"));
					double cena = Double.parseDouble(m1.group("cena"));
					PoreskaStopa ps = stopaFromString(m1.group("stopa"));
					
					stavkeLista.add(new Stavka(proizvod, kolicina, cena, ps));
				}
				
				int uplaceno = Integer.parseInt(m0.group("uplaceno").trim());
				
				return new Racun(broj, ld, uplaceno, stavkeLista);
				
			}).collect(Collectors.toList());
	}
	
	
	public static long ispodHiljadu(int mesec, int godina) {
		
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == godina && r.getVreme().getMonthValue() == mesec)
				.filter(r -> r.getUplaceno() < 1000)
				.count();
	}
	
	
	public static List<Racun> sortiraniPoVisini(int dan, int mesec, int godina) {
		
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == godina && r.getVreme().getMonthValue() == mesec && r.getVreme().getDayOfMonth() == dan)
				.sorted(Comparator.comparingDouble(Racun::getVisina))
				.collect(Collectors.toList());
	}
	
	
	public static void putaProdat() {
		
		Map<String, Long> m = Racuni.racuniStream(5000)
				.flatMap(r -> r.getStavke().stream())
				.sorted(Comparator.comparing(Stavka::getProizvod))
				.collect(Collectors.groupingBy(Stavka::getProizvod, Collectors.counting()));
		
		System.out.println("                      Proizvod | Prodato puta ");
		System.out.println("-------------------------------+--------------");
		m.entrySet().stream()
				.map(e -> {
					return String.format("%30s | %12d", e.getKey(), e.getValue());
				})
				.forEach(System.out::println);
	}
	
	
	private static boolean jeTvrdiSir(Stavka s) {
		
		String[] sirevi = { "Bjuval RF", "Gauda RF", "Edamer RF", "Tilsiter RF", "Trapist RF" };
		
		for (String sir : sirevi)
			if (s.getProizvod().equalsIgnoreCase(sir))
				return true;
		
		return false;
	}
	
	
	public static void sirevi() {
		
		class Data0 {
			
			Stavka s;
			int mes;
			
			public Data0(Stavka s, int mes) {
				this.s = s;
				this.mes = mes;
			}
		}
		
		Map<String, List<Data0>> m0 = Racuni.racuniStream(5000)
				.flatMap(r -> {
					
					List<Data0> list = new ArrayList<Data0>();
					
					for (Stavka s : r.getStavke())
						list.add(new Data0(s, r.getVreme().getMonthValue()));
					
					return list.stream();
				})
				.filter(d -> jeTvrdiSir(d.s))
				.collect(Collectors.groupingBy(d -> d.s.getProizvod(), Collectors.toList()));
		
		class Data1 {
			
			@SuppressWarnings("unused")
			String s;
			double[] mes;
			
			public Data1() {
				this.s = null;
				this.mes = new double[12];
			}
			
			public void add(Data0 d) {
				this.s = d.s.getProizvod();
				this.mes[d.mes - 1] += d.s.getKolicina();
			}
			
			public Data1 join(Data1 d) {
				for (int i = 0; i < 12; i++)
					this.mes[i] += d.mes[i];
				
				return this;
			}
		}
		
		Map<String, Data1> m1 = m0.entrySet().stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey, e -> e.getValue().stream().collect(Collector.of(Data1::new, Data1::add, Data1::join))));
		
		System.out.println(" Sir        |    JAN   |    FEB   |    MAR   |    APR   |    MAJ   |    JUN   |    JUL   |    AVG   |    SEP   |    OKT   |    NOV   |    DEC    ");
		System.out.println("------------+----------+----------+----------+----------+----------+----------+----------+----------+----------+----------+----------+---------- ");
		m1.entrySet().stream()
				.map(e -> {
					return String.format("%11s | %8.2f | %8.2f | %8.2f | %8.2f | %8.2f | %8.2f | %8.2f | %8.2f | %8.2f | %8.2f | %8.2f | %8.2f ", 
							e.getKey(), 
							e.getValue().mes[0] , e.getValue().mes[1] , e.getValue().mes[2] , e.getValue().mes[3] ,
							e.getValue().mes[4] , e.getValue().mes[5] , e.getValue().mes[6] , e.getValue().mes[7] ,
							e.getValue().mes[8] , e.getValue().mes[9] , e.getValue().mes[10], e.getValue().mes[11]);
				})
				.forEach(System.out::println);
	}
}
