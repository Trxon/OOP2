package vezba.kol1_racuni_g03_p06;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
 * Dat je tok stringova u vidu metoda Racuni.jsonStream(), i u njemu
 * je svaki racun predstavljen jednim stringom u JSON formatu. Za d-
 * etalje o formatu pokrenuti program i pogledati kako izgleda svaki
 * od stringova.
 * 
 * Pretvoriti dati tok stringova u tok Racun objekata i ispisati ih.
 * 
 * Drugi deo (5 poena)
 * -------------------
 * 
 * Implementirati metod double prodatoSireva(int dan, int mesec, int
 * godina), pozvati ga u glavnom programu i ispisati rezultat.
 * 
 * Metod vraca koliko je ukupno kilograma svih vrsta mekih sireva p-
 * rodato zadatog dana. Meki sirevi su svi oni proizvodi koji na kr-
 * aju naziva imaju "sir rf".
 * 
 * Treci deo (5 poena)
 * -------------------
 * 
 * Implementirati metod List<String> razlicitiProizvodi(int dan, int
 * mesec, int godina), pozvati ga u glavnom programu i ispisati rez-
 * ultat.
 * 
 * Metod vraca nazive svih proizovda koji su prodati datog dana, so-
 * rtirane abecedno, bez ponavaljanja.
 * 
 * Cetvrti deo (5 poena)
 * ---------------------
 * 
 * Za svaki dan 2018. godine ispisati koliko je litara kozijeg mleka
 * prodato, na sledeci nacin:
 * 
 *     Datum | Prodato
 * ----------+----------
 *      2.1. |   0.40 l
 *      3.1. |  12.53 l
 *      4.1. |   0.00 l
 *          ...
 * 
 * Peti deo (5 poena)
 * ------------------
 * 
 * Pomocu operacije .reduce() generisati dnevni izvestaj za 1.5.2018
 * i smestiti ga u lokalnu promenljivu.
 * 
 * Dnevni izvestaj je izmisljen racun na kojem se nalaze bas sve st-
 * avke sa svih racuna zadatog dana. Redni broj ovakvog racuna je n-
 * ula, vreme izdavanja je podne zadatog dana, a uplaceno je nula d-
 * inara.
 * 
 * Ispisati stavke sa dnevnog izvestaja (naziv proizvoda, kolicinu i
 * ukupnu cenu) na sledeci nacin:
 * Mladi sir RF x 2.300 = 920.00
 * Namaz sa paprikom x 3 = 449.97
 * Kiselo mleko 500g x 1 = 59.99
 * ...
 * 
 */

public class Program {

	
	public static final int BROJ = 500;
	

	public static void main(String[] args) {

//		Racuni.jsonStream(1)
//				.forEach(System.out::println);

//		Racuni.racuniStream(5000)
//				.forEach(System.out::println);

		// prvi deo
//		List<Racun> racuni = load(Racuni.jsonStream(BROJ));
		
		// peti deo
		izvestaj(25, 4, 2018);
		
		// cetvrti deo
		kozijegMleka(2018);
		
		// treci deo
		System.out.println(razlicitiProizvodi(2, 1, 2018));
		
		// drugi deo
		System.out.println(prodatoSireva(25, 4, 2018));
	}
	
	
//	public static List<Racun> load(Stream<String> lines) {
//		
//	}
	
	
	public static double prodatoSireva(int dan, int mesec, int godina) {
		
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == godina)
				.filter(r -> r.getVreme().getMonthValue() == mesec)
				.filter(r -> r.getVreme().getDayOfMonth() == dan)
				.flatMap(r -> r.getStavke().stream())
				.filter(s -> s.getProizvod().contains("sir RF"))
				.collect(Collectors.summingDouble(Stavka::getKolicina));
	}
	
	
	public static List<String> razlicitiProizvodi(int dan, int mesec, int godina) {

		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == godina)
				.filter(r -> r.getVreme().getMonthValue() == mesec)
				.filter(r -> r.getVreme().getDayOfMonth() == dan)
				.flatMap(r -> r.getStavke().stream())
				.map(Stavka::getProizvod)
				.distinct().sorted()
				.collect(Collectors.toList());
	}
	
	
	public static void kozijegMleka(int godina) {
		
		class Data {
			
			String proizvod;
			LocalDate datum;
			double prodato;
			
			public Data(String proizvod, LocalDate datum, double prodato) {
				this.proizvod = proizvod;
				this.datum = datum;
				this.prodato = prodato;
			}
			
			public Data() {
				this.proizvod = null;
				this.datum = null;
				this.prodato = 0.0;
			}
			
			public void add(Data d) {
				
				if (d == null) return;
				
				this.proizvod = d.proizvod;
				this.datum = d.datum;
				this.prodato += d.prodato;
			}
			
			public Data join(Data d) {
				
				if (d == null) return this;
				
				this.proizvod = d.proizvod;
				this.datum = d.datum;
				this.prodato += d.prodato;
				
				return this;
			}
		}
		
		System.out.println("------------+------------");
		System.out.println("      Datum | Prodato    ");
		System.out.println("------------+------------");
		
		Racuni.racuniStream(5000)
						.filter(r -> r.getVreme().getYear() == godina)
						.flatMap(r -> r.getStavke().stream()
								.filter(s -> s.getProizvod().contains("ozije"))
								.map(s -> new Data(s.getProizvod(), r.getVreme().toLocalDate(), s.getKolicina()))
								.collect(Collectors.toList())
								.stream())
						.collect(Collectors.groupingBy(
								d -> (LocalDate) d.datum, 
								Collectors.toList()))
				.entrySet().stream()
						.collect(Collectors.toMap(
								Map.Entry::getKey, 
								e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))))
				.entrySet().stream()
						.sorted(Map.Entry.comparingByKey())
						.map(e -> String.format(" %10s | %10.2f", 
								e.getKey().format(DateTimeFormatter.ofPattern("d.M.")), e.getValue().prodato))
						.forEach(System.out::println);
	}
	
	
	public static void izvestaj(int dan, int mesec, int godina) {
		
		Racun izvestaj = Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == godina)
				.filter(r -> r.getVreme().getMonthValue() == mesec)
				.filter(r -> r.getVreme().getDayOfMonth() == dan)
				.reduce(null, (r1, r2) -> new Racun(0, LocalDateTime.of(godina, mesec, dan, 12, 0), 0, Stream.concat(
						r1 == null ? Stream.empty() : r1.getStavke().stream(),
						r2 == null ? Stream.empty() : r2.getStavke().stream()).collect(Collectors.toList())));
		
		String naslov = "IZVESTAJ ZA " + LocalDate.of(godina, mesec, dan) + " :";
		
		System.out.println(naslov.replaceAll(".", "="));
		System.out.println(naslov);
		System.out.println(naslov.replaceAll(".", "="));
		
		izvestaj.getStavke().stream()
				.map(s -> String.format("%s x %.2f = %.2f", s.getProizvod(), s.getKolicina(), s.getKolicina() * s.getCena()))
				.forEach(System.out::println);
	}
}
