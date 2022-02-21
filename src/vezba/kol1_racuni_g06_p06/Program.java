package vezba.kol1_racuni_g06_p06;

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
 * je svaki racun predstavljen jednim stringom u JSON formatu. Za d-
 * etalje o formatu pokrenuti program i pogledati kako izgleda svaki
 * od stringova.
 * 
 * Pretvoriti dati tok stringova u tok Racun objekata i ispisati ih.
 * 
 * Drugi deo (5 poena)
 * -------------------
 * 
 * Implementirati metod: 
 * 		double prodatoMleka(int dan, int mesec, int godina),
 * pozvati ga u glavnom programu i ispisati rezultat.
 * 
 * Metod vraca koliko je ukupno zaradjeno novca od prodaje svih vrs-
 * ta mleka zadatog dana. Mleka su oni proizvodi koji na pocetku na-
 * ziva imaju "mleko".
 * 
 * Treci deo (5 poena)
 * -------------------
 * 
 * Implementirati metod 
 * 		List<LocalDate> zlatiborskiKajmakNaAkciji(),
 * pozvati ga u glavnom programu i ispisati rezultat.
 * 
 * Metod vraca listu datuma kada je Zlatiborski kajmak prodat dok je
 * bio na popustu. Kajmak je na popustu ako je prodat po ceni manjoj
 * od maksimalne ikada.
 * 
 * Cetvrti deo (5 poena)
 * --------------------
 *
 * Za svaki mesec iz 2018. godine ispisati ukupan broj izdatih racu-
 * na koji sadrze rinfuznu robu, na sledeci nacin:
 * 
 *      | Izdato
 * -----+--------
 *  Jan |    673
 *  Feb |   1043
 *  Mar |    274
 *     ...
 * 
 * Rinfuzna roba su oni proizvodi koji na kraju imena imaju "RF".
 * 
 * Peti deo (5 poena)
 * ------------------
 * 
 * Za svaki namaz ispisati njegovu najnizu, najvisu bas kao i prose-
 * cnu cenu po jedinici mere (komad, kilogram, litar...), u tabelar-
 * nom obliku na sledeci nacin:
 * 
 * | Namaz             |    Min |    Max |    Avg |
 * |===================|========|========|========|
 * | Sirni namaz       |  89.99 | 119.99 | 118.88 |
 * | Namaz sa paprikom | 109.99 | 149.99 | 111.58 |
 * | Namaz sa renom    | 149.99 | 149.99 | 149.99 |
 * | Mali namaz        |  89.99 |  99.99 |  97.34 |
 * | ...
 * 
 * Namazi su oni proizvodi koji u nazivu imaju "namaz".
 * 
 */

public class Program {

	public static void main(String[] args) {

//		Racuni.stringStream(5000)
//				.forEach(System.out::println);

//		Racuni.racuniStream(5000)
//				.forEach(System.out::println);

		// peti deo
		petiDeo();
		
		// cetvrti deo
		cetvrtiDeo();
		
		// treci deo
		treciDeo().forEach(System.out::println);;
		
		// drugi deo
		System.out.println("Prodato mleka : " + drugiDeo(26, 4, 2020));
	}
	
	
	private static double drugiDeo(int dan, int mes, int god) {
		
		return Racuni.racuniStream(5000)
				.filter(r -> r.getVreme().getYear() == god)
				.filter(r -> r.getVreme().getMonthValue() == mes)
				.filter(r -> r.getVreme().getDayOfMonth() == dan)
				.flatMap(r -> r.getStavke().stream())
				.filter(s -> s.getProizvod().contains("leko"))
				.collect(Collectors.summingDouble(Stavka::getCena));
	}


	private static List<LocalDate> treciDeo() {
		
		Stavka stavka = Racuni.racuniStream(5000)
				.flatMap(r -> r.getStavke().stream())
				.filter(s -> s.getProizvod().contains("latibor"))
				.max(Comparator.comparingDouble(Stavka::getCena)).orElse(null);
		
		if (stavka == null)
			return null;
		
		return Racuni.racuniStream(5000)
				.filter(r -> {
					
					for (Stavka s : r.getStavke())
						if (s.getProizvod().contains("latibor") && s.getCena() < stavka.getCena())
							return true;

					return false;
					
				}).map(r -> r.getVreme().toLocalDate())
				.collect(Collectors.toList());
	}


	private static String monthAsString(String s) {
		
		switch (s) {
			case "01": return "Jan";
			case "02": return "Feb";
			case "03": return "Mar";
			case "04": return "Apr";
			case "05": return "Maj";
			case "06": return "Jun";
			case "07": return "Jul";
			case "08": return "Avg";
			case "09": return "Sep";
			case "10": return "Okt";
			case "11": return "Nov";
			case "12": return "Dec";
			default  : return "";
		}
	}
	

	private static void cetvrtiDeo() {
		
		String title = "     | Izdato";
		
		System.out.println(title.replaceAll(".", "-"));
		System.out.println(title);
		System.out.println(title.replaceAll(".", "-"));
		
		Racuni.racuniStream(5000)
						.filter(r -> r.getVreme().getYear() == 2018)
						.filter(r -> r.getStavke().stream()
								.filter(s -> s.getProizvod().endsWith("RF"))
								.findAny()
								.orElse(null) != null)
						.collect(Collectors.groupingBy(
								r -> r.getVreme().format(DateTimeFormatter.ofPattern("MM")), 
								Collectors.counting()))
				.entrySet().stream()
						.sorted(Map.Entry.comparingByKey())
						.map(e -> String.format(" %s | %6d ", 
								monthAsString(e.getKey()), e.getValue()))
						.forEach(System.out::println);
	}


	private static void petiDeo() {
		
		class Data {
			
			double min, max, sum;
			int cnt;
			
			public Data() {
				min = Double.MAX_VALUE;
				max = Double.MIN_NORMAL;
				sum = 0.0;
				cnt = 0;
			}
			
			public void add(Stavka s) {
				if (s.getCena() < min)
					min = s.getCena();
				if (s.getCena() > max)
					max = s.getCena();
				sum += s.getCena();
				cnt += 1;
			}
			
			public Data join(Data d) {
				if (d.min < min)
					min = d.min;
				if (d.max > max)
					max = d.max;
				sum += d.sum;
				cnt += d.cnt;
				return this;
			}
		}

		String title = "| Namaz             |    Min |    Max |    Avg |";
		
		System.out.println(title.replaceAll(".", "-"));
		System.out.println(title);
		System.out.println(title.replaceAll(".", "-"));
		
		Racuni.racuniStream(5000)
						.flatMap(r -> r.getStavke().stream()
								.filter(s -> s.getProizvod().contains("amaz"))
								.collect(Collectors.toList())
								.stream())
						.collect(Collectors.groupingBy(
								Stavka::getProizvod, 
								Collectors.toList()))
				.entrySet().stream()
						.collect(Collectors.toMap(
								Map.Entry::getKey, 
								e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))))
				.entrySet().stream()
						.map(e -> String.format("| %17s | %6.2f | %6.2f | %6.2f |", 
								e.getKey(), e.getValue().min, e.getValue().max, e.getValue().sum / e.getValue().cnt))
						.forEach(System.out::println);
		
		System.out.println(title.replaceAll(".", "-"));
	}
}
