package vezba.kol1_racuni_g04_p04;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

		Racuni.stringStream(1)
				.forEach(System.out::println);

//		Racuni.racuniStream(5000)
//				.forEach(System.out::println);
		
		// prvi deo
		List<Racun> racuni = load(Racuni.stringStream(10));
		
		// drugi deo
		if (drugiDeo(1, 1, 2019)) System.out.println("Prodat mileram!");
		
		// treci deo
		treciDeo(1);
		
		// cetvrti deo
		cetvrtiDeo(2019);
		
		// peti deo
		petiDeo();
	}


	private static PoreskaStopa stopaFromString(String s) {
		
		for (PoreskaStopa p : PoreskaStopa.values())
			if (p.toString().equalsIgnoreCase(s))
				return p;
		
		return PoreskaStopa.OPSTA;
	}

	
	private static List<Racun> load(Stream<String> stringStream) {
		
		return stringStream
				.map(s -> {
					
					Pattern p0 = Pattern.compile("(?sm)\\\"broj\\\":\\s*?(?<broj>[\\s\\S]*?),\\s*?\\\"datum\\\":\\s*?\\\"(?<datum>[\\s\\S]*?)\\\",\\s*?\\\"vreme\\\":\\s*?\\\"(?<vreme>[\\s\\S]*?)\\\",\\s*?\\\"stavke\\\":\\s*?(?<stavke>[\\s\\S]*?),\\s*?\\\"uplaceno\\\":\\s*?(?<uplaceno>[\\s\\S]*?)}");
					Matcher m0 = p0.matcher(s);
					
					m0.find();
					
					int broj = Integer.parseInt(m0.group("broj").trim());
					
					String[] datumTokens = m0.group("datum").trim().split("\\.");
					String vremeString = m0.group("vreme").trim();
					
					LocalDateTime vreme = LocalDateTime.parse(datumTokens[2] + "-" + datumTokens[1] + "-" + datumTokens[0] + "T" + vremeString);
					
					int uplaceno = Integer.parseInt(m0.group("uplaceno").trim());
					
					String stavkeString = m0.group("stavke").trim();
					List<Stavka> stavke = new ArrayList<Stavka>();
					
					Pattern p1 = Pattern.compile("(?sm)\\\"proizvod\\\":\\s*?\\\"(?<proizvod>[\\s\\S]*?)\\\",\\s*?\\\"kolicina\\\":\\s*?(?<kol>[\\s\\S]*?),\\s*?\\\"cena\\\":\\s*?(?<cena>[\\s\\S]*?),\\s*?\\\"stopa\\\":\\s*?\\\"(?<stopa>[\\s\\S]*?)\\\"");
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
	
	
	private static boolean drugiDeo(int dan, int mes, int god) {
		
		/* Implementirati metod boolean nemaMilerama(int dan, int mesec, int
		 * godina), pozvati ga u glavnom programu i ispisati rezultat.
		 * 
		 * Metod utvrdjuje da li je za dati dan prodata neka vrsta milerama.
		 * Milerami su oni proizvodi koji na pocetku naziva imaju "mileram".
		 */
		
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == god && r.getVreme().getMonthValue() == mes && r.getVreme().getDayOfMonth() == dan)
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
					sb.append("  <proizvod>" + s.getProizvod() + "</proizvod>\n");
					sb.append("  <kolicina>" + s.getKolicina() + "</kolicina>\n");
					sb.append("  <cena>" + s.getCena() + "</cena>\n");
					sb.append("  <stopa>" + s.getStopa().toString().toLowerCase() + "</stopa>\n");
					sb.append("</stavka>");
					
					return sb.toString();
				}).forEach(System.out::println);
	}


	private static void cetvrtiDeo(int godina) {
		
		Map<LocalDate, Double> m0 = Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == godina)
				.collect(Collectors.groupingBy(r -> (LocalDate) r.getVreme().toLocalDate(), Collectors.averagingDouble(Racun::getVisina)));
		
		System.out.println("  Datum | Prosek  ");
		System.out.println("--------+---------");
		m0.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.map(e -> String.format(" %2d.%2d. | %6.2f ", e.getKey().getDayOfMonth(), e.getKey().getMonthValue(), e.getValue()))
				.forEach(System.out::println);
	}


	private static void petiDeo() {
		
		Map<Integer, List<Racun>> m0 = Racuni.racuniStream(5000)
				.collect(Collectors.groupingBy(r -> r.getVreme().getYear(), Collectors.toList()));
		
		class Data {
			
			double k1, k2, k3;
			
			private void add(Racun r) {
				for (Stavka s : r.getStavke())
					switch (s.getStopa()) {
						case OPSTA : 		k1 += s.getCena() * 0.20; break;
						case POSEBNA : 		k2 += s.getCena() * 0.20; break;
						case OSLOBODJEN : 	k3 += s.getCena() * 0.00; break;
					}
			}
			
			private Data join(Data d) {
				this.k1 += d.k1;
				this.k2 += d.k2;
				this.k3 += d.k3;
				
				return this;
			}
		}
		
		Map<Integer, Data> m1 = m0.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))));
		
		System.out.println("  GOD  |    OPSTA   |   POSEBNA  | OSLOBODJEN |");
		System.out.println("+------+------------+------------+------------+");
		m1.entrySet().stream()
				.map(e -> String.format("| %4d | %10.2f | %10.2f | %10.2f |", e.getKey(), e.getValue().k1, e.getValue().k2, e.getValue().k3))
				.forEach(System.out::println);
	}
}
