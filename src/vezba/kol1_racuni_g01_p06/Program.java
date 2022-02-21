package vezba.kol1_racuni_g01_p06;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

		// peti deo
		petiDeo();
		
		// cetvrti deo
		cetvrtiDeo();
		
		// treci deo
		treciDeo(26, 4, 2020).stream()
				.map(r -> String.format("%.2f", r.getVisina()))
				.forEach(System.out::println);
		
		// drugi deo
		Stream.of(drugiDeo(4, 2020)).forEach(System.out::println);;
	}

	
	private static long drugiDeo(int mes, int god) {
		
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == god)
				.filter(r -> r.getVreme().getMonthValue() == mes)
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
				.filter(r -> r.getVreme().getYear() == god)
				.filter(r -> r.getVreme().getMonthValue() == mes)
				.filter(r -> r.getVreme().getDayOfMonth() == dan)
				.sorted(Comparator.comparingDouble(Racun::getVisina))
				.collect(Collectors.toList());
	}


	private static void cetvrtiDeo() {
		
		Map<String, Long> m0 = Racuni.racuniStream(5000)
				.flatMap(r -> r.getStavke().stream())
				.collect(Collectors.groupingBy(Stavka::getProizvod, Collectors.counting()));
		
		System.out.println(" -------------------------------+--------------");
		System.out.println("                       Proizvod | Prodato puta ");
		System.out.println(" -------------------------------+--------------");
		
		m0.entrySet().stream()
				.map(e -> String.format(" %30s | %12d ", e.getKey(), e.getValue()))
				.forEach(System.out::println);
	}


	private static void petiDeo() {
		
		String[] sirevi = { "Bjuval RF", "Gauda RF", "Edamer RF", "Tilsiter RF", "Trapist RF" };
		
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
				.filter(r -> r.getVreme().getYear() == 2019)
				.flatMap(r -> r.getStavke().stream()
						.filter(s -> {
							for (String sir : sirevi)
								if (s.getProizvod().equalsIgnoreCase(sir))
									return true;
							return false;
						}).map(s -> new Data0(s.getProizvod(), r.getVreme().getMonthValue() - 1, s.getKolicina()))
						.collect(Collectors.toList()).stream())
				.collect(Collectors.groupingBy(d -> (String) d.p, Collectors.toList()));
		
		class Data1 {
			
			String p;
			double[] k;
			
			public Data1() {
				this.p = null;
				this.k = new double[12];
			}
			
			public void add(Data0 d) {
				
				if (d == null) return;
				
				this.p = d.p;
				this.k[d.m] += d.k;
			}
			
			public Data1 join(Data1 d) {
				
				if (d == null) return this;
				
				this.p = d.p;
				
				for (int i = 0; i < 12; i++)
					this.k[i] += d.k[i];
				
				return this;
			}
		}
		
		Map<String, Data1> m1 = m0.entrySet().stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey, 
						e -> e.getValue().stream().collect(Collector.of(Data1::new, Data1::add, Data1::join))));
		
		System.out.print("-------------+---------+---------+---------+---------+---------+---------+");
		System.out.println("---------+---------+---------+---------+---------+---------+");
		
		System.out.print(" Sir         | Januar  | Februar | Mart    | April   | Maj     | Jun     |");
		System.out.println(" Jul     | Avgust  | Sept.   | Oktobar | Novemb. | Decemb. |");
		
		System.out.print("-------------+---------+---------+---------+---------+---------+---------+");
		System.out.println("---------+---------+---------+---------+---------+---------+");
		
		m1.entrySet().stream()
				.map(e -> {
					StringBuilder sb = new StringBuilder();
					
					sb.append(String.format(" %11s |", e.getKey()));
					for (int i = 0; i < 12; i++)
						sb.append(String.format(" %7.2f |", e.getValue().k[i]));
					
					return sb.toString();
				}).forEach(System.out::println);
	}
}
