package vezba.kol1_racuni_g05_p06;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
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
		
		// peti deo
		petiDeo();
		
		// cetvrt deo
		cetvrtiDeo();
		
		// treci deo
		treciDeo(1);
		
		// drugi deo
		System.out.println("Najveci bez jogurta : " + drugiDeo(4, 2020));
	}


	private static int drugiDeo(int mes, int god) {
		
		Racun racun = Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == god)
				.filter(r -> r.getVreme().getMonthValue() == mes)
				.filter(r -> r.getStavke().stream().anyMatch(s -> s.getProizvod().contains("ogurt")))
				.findFirst().get();
		
		if (racun == null) 
			return -1;
		
		return racun.getBroj();
	}


	private static void treciDeo(int broj) {
		
		Racuni.racuniStream(5000)
				.filter(r -> r.getBroj() == broj)
				.flatMap(r -> r.getStavke().stream())
				.map(s -> {
					
					StringBuilder sb = new StringBuilder();
					
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
					
					sb.append("}");
					
					return sb.toString();
					
				}).forEach(System.out::println);
	}


	private static void cetvrtiDeo() {
		
		class Data {
			
			int broj;
			int stavki;
			
			public void add(Racun r) {
				if (r == null || stavki > r.getBrStavki()) return;
				broj = r.getBroj();
				stavki = r.getBrStavki();
			}
			
			public Data join(Data d) {
				if (d == null || stavki > d.stavki) return this;
				broj = d.broj;
				stavki = d.stavki;
				return this;
			}
		}

		String title = "      Datum | Racun br.";
		
		System.out.println(title.replaceAll(".", "="));
		System.out.println(title);
		System.out.println(title.replaceAll(".", "="));
		
		Racuni.racuniStream(5000)
						.filter(r -> r.getVreme().getYear() == 2020)
						.collect(Collectors.groupingBy(
								r -> (LocalDate) r.getVreme().toLocalDate(), 
								Collectors.toList()))
				.entrySet().stream()
						.collect(Collectors.toMap(
								Map.Entry::getKey, 
								e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))))
				.entrySet().stream()
						.sorted(Map.Entry.comparingByKey())
						.map(e -> String.format(" %10s |  %06d  ", 
								e.getKey().format(DateTimeFormatter.ofPattern("d.M.")), e.getValue().broj))
						.forEach(System.out::println);
	}


	private static void petiDeo() {
		
		class Data0 {
			
			String p;
			LocalDate f;
			
			public Data0(String p, LocalDate f) {
				this.p = p;
				this.f = f;
			}
			
			public Data0() {
				this.p = null;
				this.f = null;
			}
			
			public void add(Data0 d) {
				if (d == null) return;
				this.p = d.p;
				this.f = this.f == null || this.f.compareTo(d.f) > 0 ? d.f : this.f;
			}
			
			public Data0 join(Data0 d) {
				if (d == null) return this;
				this.p = d.p;
				this.f = this.f == null || this.f.compareTo(d.f) > 0 ? d.f : this.f;
				return this;
			}
		}
		
		String title = "                       Proizvod | Datum       ";
		
		System.out.println(title.replaceAll(".", "="));
		System.out.println(title);
		System.out.println(title.replaceAll(".", "="));
		
		Racuni.racuniStream(5000)
						.flatMap(r -> r.getStavke().stream()
								.map(s -> new Data0(s.getProizvod(), r.getVreme().toLocalDate()))
								.collect(Collectors.toList())
								.stream())
						.collect(Collectors.groupingBy(
								d -> (String) d.p, 
								Collectors.toList()))
				.entrySet().stream()
						.collect(Collectors.toMap(
								Map.Entry::getKey, 
								e -> e.getValue().stream().collect(Collector.of(Data0::new, Data0::add, Data0::join))))
				.entrySet().stream()
						.map(e -> String.format(" %30s | %12s ", 
								e.getKey(), e.getValue().f.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))))
						.forEach(System.out::println);
	}
}
