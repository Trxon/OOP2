package vezba.kol1_racuni_g06_p01;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
	
	
	public static final int BROJ = 2000;
	

	public static void main(String[] args) {

//		Racuni.stringStream(BROJ)
//				.forEach(System.out::println);

//		Racuni.racuniStream(5000)
//				.forEach(System.out::println);

		// prvi deo
		List<Racun> racuni = load(Racuni.stringStream(BROJ));
		
		// drugi deo
		prodatoMleka(racuni, 1, 3, 2019);
		
		// treci deo
		zlatiborskiKajmakNaAkciji(racuni);
		
		// cetvrti deo
		izdatihRacunaZaRinfuznuRobu1(racuni, 2018);
		izdatihRacunaZaRinfuznuRobu2(racuni, 2018);
		
		// peti deo
		namazniMetod(racuni);
	}
	
	
	private static PoreskaStopa stopaFromString(String s) {
		
		if (s.equalsIgnoreCase("OPSTA")) 		return PoreskaStopa.OPSTA;
		else if (s.equalsIgnoreCase("POSEBNA")) return PoreskaStopa.POSEBNA;
		else 									return PoreskaStopa.OSLOBODJEN;
	}


	private static List<Racun> load(Stream<String> stringStream) {
		
		return stringStream.map(s -> {
			
			Pattern p0 = Pattern.compile("\\{\\s*\\\"broj\\\"\\:\\s*(?<broj>\\d{1,})\\,\\s*\\\"datum\\\"\\:\\s*\\\"(?<datum>[\\s\\S]*?)\\\"\\,\\s*\\\"vreme\\\"\\:\\s*\\\"(?<vreme>\\d*?\\:\\d*?)\\\"\\,\\s*\\\"stavke\\\"\\:\\s*\\[(?<stavke>[\\s\\S]*?)\\]\\,\\s*\\\"uplaceno\\\"\\:\\s*(?<uplaceno>\\d{1,})\\s*\\}");
			Matcher m0 = p0.matcher(s);
			
			m0.find();
			
			int broj = Integer.parseInt(m0.group("broj").trim());
			
			String datum = m0.group("datum").trim();
			String vreme = m0.group("vreme").trim();
			
			String god = datum.substring(datum.length() - 5 , datum.length() - 1);
			String mes = datum.substring(datum.length() - 8 , datum.length() - 6);
			String dan = datum.substring(datum.length() - 11, datum.length() - 9);
			
			LocalDateTime dateTime = LocalDateTime.parse(god + "-" + mes + "-" + dan + "T" + vreme);
			
			int uplaceno = Integer.parseInt(m0.group("uplaceno").trim());
			
			String stavkeString = m0.group("stavke");
			
			Pattern p1 = Pattern.compile("\\{\\s*\\\"proizvod\\\"\\:\\s*\\\"(?<proizvod>[\\s\\S]*?)\\\"\\,\\s*\\\"kolicina\\\"\\:\\s*(?<kolicina>[\\s\\S]*?)\\,\\s*\\\"cena\\\"\\:\\s*(?<cena>[\\s\\S]*?)\\,\\s*\\\"stopa\\\"\\:\\s*\\\"(?<stopa>[\\s\\S]*?)\\\"\\,\\s*\\}");
			Matcher m1 = p1.matcher(stavkeString);
			
			List<Stavka> stavke = new LinkedList<Stavka>();
			
			while (m1.find()) {
				
				String proizvod = m1.group("proizvod");
				double kolicina = Double.parseDouble(m1.group("kolicina").trim());
				double cena = Double.parseDouble(m1.group("cena").trim());
				PoreskaStopa stopa = stopaFromString(m1.group("stopa").trim());
				
				stavke.add(new Stavka(proizvod, kolicina, cena, stopa));
			}
			
			return new Racun(broj, dateTime, uplaceno, stavke);
			
		}).collect(Collectors.toList());
	}
	
	
	public static double prodatoMleka(List<Racun> racuni, int dan, int mesec, int godina) {
		
		double d = racuni.stream()
			.filter(r -> r.getVreme().getYear() == godina && r.getVreme().getMonthValue() == mesec && r.getVreme().getDayOfMonth() == dan)
			.flatMap(r -> r.getStavke().stream())
			.filter(s -> s.getProizvod().contains("leko"))
			.map(s -> s.getKolicina()).reduce(Double::sum).orElse(0.0);
		
		String date = String.format("%d-%02d-%02d", godina, mesec, dan);
		
		System.out.printf("Prodato mleka na dan %s : %.2f %n", date, d);
		return d;
	}
	
	
	private static boolean proveraZaKajmak(Racun r, double cena) {
		
		for (Stavka s : r.getStavke())
			if (s.getProizvod().equalsIgnoreCase("Zlatiborski kajmak RF") && s.getCena() < cena)
				return true;
		
		return false;
	}
	
	
	public static List<LocalDate> zlatiborskiKajmakNaAkciji(List<Racun> racuni) {
		
		double d = racuni.stream()
			.flatMap(r -> r.getStavke().stream())
			.filter(s -> s.getProizvod().equalsIgnoreCase("Zlatiborski kajmak RF"))
			.map(Stavka::getCena)
			.reduce(Double::max).orElse(Double.MIN_VALUE);
		
		List<LocalDate> l = racuni.stream()
			.filter(r -> proveraZaKajmak(r, d))
			.map(r -> r.getVreme().toLocalDate())
			.distinct()
			.collect(Collectors.toList());
		
		System.out.println("Dani kada je zlatiborski kajmak bio na akciji : ");
		for (LocalDate ld : l) System.out.println(ld);
		
		return l;
	}
	
	
	private static String mesecAsString(int i) {
		
		switch (i) {
			case 1: 	return "Jan";
			case 2:		return "Feb";
			case 3: 	return "Mar";
			case 4:		return "Apr";
			case 5: 	return "Maj";
			case 6:		return "Jun";
			case 7: 	return "Jul";
			case 8:		return "Avg";
			case 9: 	return "Sep";
			case 10:	return "Okt";
			case 11: 	return "Nov";
			case 12:	return "Dec";
		}
		
		return "";
	}
	
	
	private static boolean proveraRinfuzneRobe(Racun r) {
		
		for (Stavka s : r.getStavke())
			if (s.getProizvod().contains("RF"))
				return true;
		
		return false;
	}
	
	
	public static void izdatihRacunaZaRinfuznuRobu1(List<Racun> racuni, int godina) {
		
		class Data {
			
			int num;
			
			public void add(Racun r) {
				this.num++;
			}
			
			public Data join(Data a) {
				this.num += a.num;
				return this;
			}
		}
		
		Map<Integer, List<Racun>> m0 = racuni.stream()
			.filter(r -> r.getVreme().getYear() == godina && proveraRinfuzneRobe(r))
			.collect(Collectors.groupingBy(r -> (int) r.getVreme().getMonthValue(), Collectors.toList()));
		
		Map<Integer, Data> m1 = m0.entrySet().stream()
			.collect(Collectors.toMap(
					Map.Entry::getKey,
					e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))));
		
		System.out.println("     | Izdato ");
		System.out.println("-----+--------");
		m1.entrySet().stream()
			.map(e -> String.format(" %s | %6d", mesecAsString(e.getKey()), e.getValue().num))
			.forEach(System.out::println);
	}
	
	
	public static void izdatihRacunaZaRinfuznuRobu2(List<Racun> racuni, int godina) {
		
		Map<Integer, List<Racun>> m0 = racuni.stream()
			.filter(r -> r.getVreme().getYear() == godina && proveraRinfuzneRobe(r))
			.collect(Collectors.groupingBy(r -> (int) r.getVreme().getMonthValue(), Collectors.toList()));
		
		Map<Object, Object> m1 = m0.entrySet().stream()
			.collect(Collectors.toMap(
					Map.Entry::getKey, 
					e -> e.getValue().stream().count()));
		
		System.out.println("     | Izdato ");
		System.out.println("-----+--------");
		m1.entrySet().stream()
			.map(e -> String.format(" %s | %6d", mesecAsString((int) e.getKey()), e.getValue()))
			.forEach(System.out::println);
	}
	
	
	public static void namazniMetod(List<Racun> racuni) {
		
		class Data {
			
			double min;
			double max;
			double avg;
			
			public void add(Stavka s) {
				this.min = this.min < s.getCena() ? this.min : s.getCena();
				this.max = this.max > s.getCena() ? this.max : s.getCena();
				this.avg = (this.min + this.max) * .5;
			}
			
			public Data join(Data d) {
				
				this.min = this.min < d.min ? this.min : d.min;
				this.max = this.max > d.max ? this.max : d.max;
				this.avg = (this.min + this.max) * .5;
				
				return this;
			}
		}
		
		Map<String, List<Stavka>> m0 = racuni.stream()
			.flatMap(r -> r.getStavke().stream())
			.filter(s -> s.getProizvod().contains("namaz"))
			.collect(Collectors.groupingBy(
					Stavka::getProizvod,
					Collectors.toList()));
		
		Map<String, Data> m1 = m0.entrySet().stream()
			.collect(Collectors.toMap(
					Map.Entry::getKey,
					e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))));
		
		System.out.println("| Namaz             |    Min |    Max |    Avg |");
		System.out.println("|===================|========|========|========|");
		m1.entrySet().stream()
			.map(e -> String.format("| %-17s | %6.2f | %6.2f | %6.2f |", 
					e.getKey(), e.getValue().min, e.getValue().max, e.getValue().avg))
			.forEach(System.out::println);
	}
}
