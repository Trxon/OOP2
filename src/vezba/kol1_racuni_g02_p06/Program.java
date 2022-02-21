package vezba.kol1_racuni_g02_p06;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
 * je o formatu pokrenuti program a zatim pogledati kako izgleda sv-
 * aki od stringova.
 * 
 * Pretvoriti dati tok stringova u tok Racun objekata i ispisati ih.
 * 
 * Drugi deo (5 poena)
 * -------------------
 * 
 * Implementirati metod long prekoPet(int mesec, int godina), pozva-
 * ti ga u glavnom programu i ispisati rezultat.
 * 
 * Metod vraca ukupan broj racuna, za zadati mesec zadate godine, na
 * kojima ima vise od pet stavki.
 * 
 * Treci deo (5 poena)
 * -------------------
 * 
 * Implementirati metod List<String> razlicitiProizvodi(int dan, int
 * mesec, int godina), pozvati ga u glavnom programu i ispisati rez-
 * ultat.
 * 
 * Metod vraca nazive svih proizovda prodatih datog dana i sortirane
 * abecedno, bez ponavaljanja.
 * 
 * Cetvrti deo (5 poena)
 * ---------------------
 * 
 * Za svaki proizvod ispisati koliko je ukupno jedinica mere (komada,
 * kilograma, litara...) prodato, na sledeci nacin:
 * 
 *      Proizvod | Prodato puta
 * --------------+--------------
 *    Mleko 3,2% |       1245.52
 *    Mleko 2,8% |      12876.43
 *  Jogurt 1,5kg |       3762.02
 *              ...
 * 
 * Peti deo (5 poena)
 * ------------------
 * 
 * Pomocu operacije .reduce() generisati dnevni izvestaj za 1.5.2018.
 * i smestiti ga u lokalnu promenljivu.
 * 
 * Dnevni izvestaj je izmisljen racun na kojem se mogu naci sve stav-
 * ke sa svih racuna zadatog dana. Redni broj ovakvog "racuna" je nu-
 * la, vreme izdavanja je podne zadatog dana, a uplaceno je nula din-
 * ara.
 * 
 * Ispisati stavke sa dnevnog izvestaja (naziv proizvoda, kolicinu k-
 * ao i ukupnu cenu) na sledeci nacin:
 * Mladi sir RF x 2.300 = 920.00
 * Namaz sa paprikom x 3 = 449.97
 * Kiselo mleko 500g x 1 = 59.99
 * ...
 * 
 */

public class Program {

	public static void main(String[] args) {

//		Racuni.xmlStream(1)
//				.forEach(System.out::println);

//		Racuni.racuniStream(5000)
//				.forEach(System.out::println);
		
		// peti deo
		petiDeo(1, 5, 2018);
		
		// cetvrti deo
		cetvrtiDeo();
		
		// treci deo
		treciDeo(26, 4, 2020).stream().forEach(System.out::println);
		
		// drugi deo
		System.out.println("Preko pet : " + drugiDeo(4, 2020));
	}
	

	private static long drugiDeo(int mes, int god) {
		
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == god)
				.filter(r -> r.getVreme().getMonthValue() == mes)
				.filter(r -> r.getStavke().size() > 5)
				.count();
	}


	private static List<String> treciDeo(int dan, int mes, int god) {
		
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == god)
				.filter(r -> r.getVreme().getMonthValue() == mes)
				.filter(r -> r.getVreme().getDayOfMonth() == dan)
				.flatMap(r -> r.getStavke().stream().map(Stavka::getProizvod))
				.distinct().sorted()
				.collect(Collectors.toList());
	}


	private static void cetvrtiDeo() {
		
		String title = String.format(" %30s | %10s ", "PROIZVOD", "PRODATO");
		
		System.out.println(title.replaceAll(".", "="));
		System.out.println(title);
		System.out.println(title.replaceAll(".", "="));
		
		Racuni.racuniStream(5000)
				.flatMap(r -> r.getStavke().stream())
				.collect(Collectors.groupingBy(Stavka::getProizvod, Collectors.summingDouble(Stavka::getKolicina)))
				.entrySet().stream()
				.map(e -> String.format(" %30s | %10.2f", e.getKey(), e.getValue()))
				.forEach(System.out::println);
	}


	private static void petiDeo(int dan, int mes, int god) {
		
		Racun izvestaj = Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == god)
				.filter(r -> r.getVreme().getMonthValue() == mes)
				.filter(r -> r.getVreme().getDayOfMonth() == dan)
				.reduce(null, (r1, r2) -> new Racun(0, LocalDateTime.of(god, mes, dan, 12, 0), 0, Stream.concat(
						r1 == null ? Stream.empty() : r1.getStavke().stream(), 
						r2 == null ? Stream.empty() : r2.getStavke().stream()).collect(Collectors.toList())));
		
		String title = String.format("IZVESTAJ ZA " + LocalDate.of(god, mes, dan) + " : ");
		
		System.out.println(title.replaceAll(".", "="));
		System.out.println(title);
		System.out.println(title.replaceAll(".", "="));
		
		izvestaj.getStavke().stream()
				.map(s -> String.format("%s x %.2f = %.2f", s.getProizvod(), s.getKolicina(), s.getKolicina() * s.getCena()))
				.forEach(System.out::println);
	}
}
