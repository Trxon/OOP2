package vezba.kol1_racuni_g04_p06;

import java.time.format.DateTimeFormatter;
import java.util.Map;
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
		
		// peti deo
		petiDeo();
		
		// cetvrti deo
		cetvrtiDeo();
		
		// treci deo
		treciDeo(1);
		
		// drugi deo
		System.out.println(drugiDeo(26, 4, 2020) ? "Ima milerama." : "Nema milerama.");
	}

	
	private static boolean drugiDeo(int dan, int mes, int god) {
		
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == god)
				.filter(r -> r.getVreme().getMonthValue() == mes)
				.filter(r -> r.getVreme().getDayOfMonth() == dan)
				.flatMap(r -> r.getStavke().stream())
				.anyMatch(s -> s.getProizvod().contains("ileram"));
	}


	private static void treciDeo(int broj) {
		
		Racuni.racuniStream(5000)
				.filter(r -> r.getBroj() == broj)
				.flatMap(r -> r.getStavke().stream())
				.map(s -> {
					
					StringBuilder sb = new StringBuilder();
					
					sb.append("<stavka>\n");
					
					sb.append("  <proizvod>");
					sb.append(s.getProizvod());
					sb.append("</proizvod>\n");
					
					sb.append("  <kolicina>");
					sb.append(s.getKolicina());
					sb.append("</kolicina>\n");
					
					sb.append("  <cena>");
					sb.append(s.getCena());
					sb.append("</cena>\n");
					
					sb.append("  <stopa>");
					sb.append(s.getStopa().toString().toLowerCase());
					sb.append("</stopa>\n");
					
					sb.append("</stavka>");
					
					return sb.toString();
					
				}).forEach(System.out::println);
	}


	private static void cetvrtiDeo() {
		
		String title = "  Datum | Prosek  ";
		
		System.out.println(title.replaceAll(".", "="));
		System.out.println(title);
		System.out.println(title.replaceAll(".", "="));
		
		Racuni.racuniStream(5000)
						.filter(r -> r.getVreme().getYear() == 2019)
						.collect(Collectors.groupingBy(
								Racun::getVreme, 
								Collectors.averagingDouble(Racun::getVisina)))
				.entrySet().stream()
						.sorted(Map.Entry.comparingByKey())
						.map(e -> String.format(" %6s | %6.2f", 
								e.getKey().toLocalDate().format(DateTimeFormatter.ofPattern("d.M.")), e.getValue()))
						.forEach(System.out::println);
	}


	private static void petiDeo() {
		
		class Data {
			
			double k0, k1, k2;
			
			public void add(Racun r) {
				
				for (Stavka s : r.getStavke())
					if (s.getStopa() == PoreskaStopa.OPSTA)
						k0 += (s.getKolicina() * s.getCena()) * (100.0 / s.getStopa().getStopa());
					else if (s.getStopa() == PoreskaStopa.POSEBNA)
						k1 += (s.getKolicina() * s.getCena()) * (100.0 / s.getStopa().getStopa());
			}
			
			public Data join(Data d) {
				
				if (d == null)
					return this;
				
				k0 += d.k0;
				k1 += d.k1;
				k2 += d.k2;
				
				return this;
			}
		}
		
		String title = " Godina |    Opsta    |   Posebna  | Oslobodjen ";
		
		System.out.println(title.replaceAll(".", "="));
		System.out.println(title);
		System.out.println(title.replaceAll(".", "="));

		Racuni.racuniStream(5000)
						.collect(Collectors.groupingBy(
								r -> (Integer) r.getVreme().getYear(), 
								Collectors.toList()))
				.entrySet().stream()
						.collect(Collectors.toMap(
								Map.Entry::getKey, 
								e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))))
				.entrySet().stream()
						.map(e -> String.format("  %4d  | %10.2f | %10.2f | %10.2f ", 
								e.getKey(), e.getValue().k0, e.getValue().k1, e.getValue().k2))
						.forEach(System.out::println);
	}
}
