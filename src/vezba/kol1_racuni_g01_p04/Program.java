package vezba.kol1_racuni_g01_p04;

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

//		Racuni.xmlStream(1)
//				.forEach(System.out::println);

//		Racuni.racuniStream(5000)
//				.forEach(System.out::println);

		// prvi deo
		List<Racun> racuni = load(Racuni.xmlStream(20));
		
		// peti deo
		petiDeo(2019);
		
		// cetvrti deo
		cetvrtiDeo();
		
//		// treci deo
//		System.out.println("Po visini : " + treciDeo(1, 1, 2019));
//		
//		// drugi deo
//		System.out.println("Ukupno ispod hiljadu : " + drugiDeo(1, 2019));
	}
	
	
	private static long drugiDeo(int mes, int god) {
		/* Implementirati metod long ispodHiljadu(int mesec, int godina), p-
		 * ozvati ga u glavnom programu i ispisati rezultat.
		 * 
		 * Metod vraca ukupan broj racuna, za zadati mesec zadate godine, za
		 * koje je uplaceno strogo manje od hiljadu dinara.
		 */
		
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == god && r.getVreme().getMonthValue() == mes)
				.filter(r -> r.getUplaceno() < 1000)
				.count();
	}


	private static List<Racun> treciDeo(int dan, int mes, int god) {
		/* Implementirati metod List<Racun> sortiraniPoVisini(int dan, int m-
		 * esec, int godina), pozvati ga u glavnom programu i ispisati rezul-
		 * tat.
		 * 
		 * Metod vraca listu racuna izadtih zadatog dana, koji su rastuce so-
		 * rtirani po visini racuna.
		 */
		
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == god && r.getVreme().getMonthValue() == mes && r.getVreme().getDayOfMonth() == dan)
				.sorted(Comparator.comparingDouble(Racun::getVisina))
				.collect(Collectors.toList());
	}


	private static void cetvrtiDeo() {
		/* Za svaki proizvod ispisati koliko je puta prodat (ukupan broj rac-
		 * una na kojima se nalazi taj proizvod), na sledeci nacin:
		 * 
		 *      Proizvod | Prodato puta
		 * --------------+--------------
		 *    Mleko 3,2% |         1245
		 *    Mleko 2,8% |        12876
		 *  Jogurt 1,5kg |         3762
		 *              ...
		 */
		
		Map<String, Long> m0 = Racuni.racuniStream(5000)
				.flatMap(r -> r.getStavke().stream())
				.collect(Collectors.groupingBy(Stavka::getProizvod, Collectors.counting()));
		
		System.out.printf(" %30s | %10s %n", "Proizvod", "Prodato");
		System.out.println("--------------------------------+-----------");
		m0.entrySet().stream()
				.map(e -> String.format(" %30s | %10d", e.getKey(), e.getValue()))
				.forEach(System.out::println);
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
	
	
	private static boolean jeSir(String s) {
		
		String[] sirevi = { "Bjuval RF", "Gauda RF", "Edamer RF", "Tilsiter RF", "Trapist RF" };
		
		for (String sir : sirevi)
			if (sir.equalsIgnoreCase(s))
				return true;
		
		return false;
	}
	
	
	private static void petiDeo(int godina) {
		/* Za svaku vrstu tvrdog sira, ispisati koliko je kilograma prodato u
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
		
		class Data0 {
			
			String p;
			int m;
			double k;
			
			public Data0(String p, int m, double k) {
				this.p = p;
				this.m = m;
				this.k = k;
			}
		}
		
		Map<String, List<Data0>> m0 = Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == godina)
				.flatMap(r -> r.getStavke().stream().map(
						s -> new Data0(s.getProizvod(), r.getVreme().getMonthValue(), s.getKolicina())))
				.filter(d -> jeSir(d.p))
				.collect(Collectors.groupingBy(d -> (String) d.p, Collectors.toList()));
		
		class Data1 {
			
			double[] k;
			
			public Data1() {
				this.k = new double[12];
			}
			
			public void add(Data0 d) {
				
				if (d == null)
					return;
				
				if (this.k == null)
					this.k = new double[12];
				
				this.k[d.m - 1] += d.k;
			}
			
			public Data1 join(Data1 d) {
				
				if (d == null)
					return this;
				
				if (this.k == null)
					this.k = new double[12];
				
				for (int i = 0; i < 12; i++)
					this.k[i] += d.k[i];
				
				return this;
			}
		}
		
		Map<String, Data1> m1 = m0.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().collect(Collector.of(Data1::new, Data1::add, Data1::join))));
		
		
		StringBuilder sb0 = new StringBuilder();
		sb0.append(String.format(" %14s | ", "SIR"));
		
		for (int i = 0; i < 12; i++)
			sb0.append(String.format("%6s | ", monthAsString(i + 1)));
		
		sb0.append(
				"\n==============================================================="
				+ "============================================================== ");
		
		System.out.println(sb0.toString());
		
		m1.entrySet().stream()
				.map(e -> {
					
					StringBuilder sb = new StringBuilder();
					sb.append(String.format(" %14s | ", e.getKey()));
					
					for (int i = 0; i < 12; i++)
						sb.append(String.format("%6.2f | ", e.getValue().k[i]));
					
					return sb.toString();
				}).forEach(System.out::println);
	}


	private static PoreskaStopa stopaFromString(String s) {
		
		for (PoreskaStopa p : PoreskaStopa.values())
			if (p.toString().equalsIgnoreCase(s))
				return p;
		
		return PoreskaStopa.OPSTA;
	}
	

	private static List<Racun> load(Stream<String> xmlStream) {
		
		return xmlStream
				.map(s -> {
					
					Pattern p0 = Pattern.compile("(?sm)<broj>(?<broj>[\\s\\S]*?)<\\/broj>\\s*?<datum>(?<datum>[\\s\\S]*?)<\\/datum>\\s*?<vreme>(?<vreme>[\\s\\S]*?)<\\/vreme>(?<stavke>[\\s\\S]*?)\\s*?<uplaceno>(?<uplaceno>[\\s\\S]*?)<\\/uplaceno>");
					Matcher m0 = p0.matcher(s);
					
					m0.find();
					
					int broj = Integer.parseInt(m0.group("broj").trim());
					
					String[] datumTokens = m0.group("datum").trim().split("\\.");
					String vremeString = m0.group("vreme").trim();
					
					LocalDateTime vreme = LocalDateTime.parse(datumTokens[2] + "-" + datumTokens[1] + "-" + datumTokens[0] + "T" + vremeString);
					
					int uplaceno = Integer.parseInt(m0.group("uplaceno").trim());
					
					String stavkeString = m0.group("stavke");
					List<Stavka> stavke = new ArrayList<Stavka>();
					
					Pattern p1 = Pattern.compile("(?sm)<proizvod>(?<proizvod>[\\s\\S]*?)<\\/proizvod>\\s*?<kolicina>(?<kol>[\\s\\S]*?)<\\/kolicina>\\s*?<cena>(?<cena>[\\s\\S]*?)<\\/cena>\\s*?<stopa>(?<stopa>[\\s\\S]*?)<\\/stopa>");
					Matcher m1 = p1.matcher(stavkeString);
					
					while (m1.find()) {
						
						String proizvod = m1.group("proizvod").trim();
						double kol = Double.parseDouble(m1.group("kol").trim());
						double cena = Double.parseDouble(m1.group("cena").trim());
						PoreskaStopa stopa = stopaFromString(m1.group("stopa").trim());
						
						stavke.add(new Stavka(proizvod, kol, cena, stopa));
					}
					
					return new Racun(broj, vreme, uplaceno, stavke);
				}).collect(Collectors.toList());
	}
}
