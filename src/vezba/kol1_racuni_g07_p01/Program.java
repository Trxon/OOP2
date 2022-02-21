package vezba.kol1_racuni_g07_p01;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
 * Implementirati sledeci metod: boolean prodataPaprika(int dan, int
 * mesec, int godina), pozvati ga u glavnom programu i ispisati rez-
 * ultat.
 * 
 * Dati metod utvrdjuje da li je zadatog dana prodata paprika u pav-
 * laci, bilo ljuta ili ne.
 * 
 * Treci deo (5 poena)
 * -------------------
 * 
 * Implementirati metod List<Double> kusuri() i pozvati ga u glavnom
 * programu i ispisati rezultat.
 * 
 * Metod treba da vraca listu u kojoj se za svaki racun iz toka nal-
 * azi kusur koji je trebalo vratiti za taj racun.
 * 
 * 
 * Cetvrti deo (5 poena)
 * ---------------------
 * 
 * Za svaki mesec 2018. godine ispisati ukupno ostvarenu zaradu pro-
 * date robe, na sledeci nacin:
 * 
 *      | Prodato
 * -----+----------
 *  Jan | 167348.52
 *  Feb | 104300.31
 *  Mar | 274560.04
 *     ...
 * 
 * Peti deo (5 poena)
 * ------------------
 * 
 * Za svaki dan u mesecu martu 2019. godine, ispisati koliko je pak-
 * ovanja kojeg kiselog mleka prodato, u tabelarnom obliku na slede-
 * ci nacin:
 * 
 * Dan       | Kiselo mleko 500g | Kiselo mleko | Ekstra kiselo mleko
 * ==================================================================
 *  01. mart |               125 |          532 |                  2
 *  02. mart |                12 |          125 |               1246
 *  ...
 * 
 */

public class Program {
	
	
	public static final int BROJ = 5000;
	

	public static void main(String[] args) {
		
//		Racuni.stringStream(BROJ)
//			.forEach(System.out::println);
		
//		Racuni.racuniStream(5000)
//			.forEach(System.out::println);
		
		// prvi deo
		List<Racun> racuni = load(Racuni.stringStream(BROJ));
		
		// drugi deo
		prodatProizvodZaDatum(racuni.stream(), "paprika", 2019, 03, 19);
		
		// treci deo
		kusuri(racuni.stream());
		
		// cetvrti deo
		zaradaPoMesecimaZaGodinu(racuni.stream(), 2020);
		
		// peti deo
		kolikoJeProdatoKiselogMleka(racuni.stream(), 2019, 03);
	}


	public static PoreskaStopa poreskaStopaFromString(String s) {
		
		if 		(s.equalsIgnoreCase("OPSTA")) 		return PoreskaStopa.OPSTA;
		else if (s.equalsIgnoreCase("POSEBNA")) 	return PoreskaStopa.POSEBNA;
		else  										return PoreskaStopa.OSLOBODJEN;
	}
	
	
	public static List<Racun> load(Stream<String> lines) {
		
		return lines.map(s -> {
			
			Pattern p0 = Pattern.compile("(?sm)[\\s\\S]*?\\\"broj\\\"\\:\\s*(?<broj>\\d*?)\\,[\\s\\S]*?\\\"datum\\\"\\:\\s*\\\"(?<datum>[\\s\\S]*?)\\\"\\,\\s*?\\\"vreme\\\"\\:\\s*?\\\"(?<vreme>\\d{2}\\:\\d{2})\\\"\\,\\s*?\\\"stavke\\\"\\:\\s*?\\[(?<stavke>[\\s\\S]*?)\\]\\,\\s*?\\\"uplaceno\\\"\\:\\s*?(?<uplaceno>\\d*?)\\n\\s*?\\}");
			Matcher m0 = p0.matcher(s);
			
			m0.find();
			int broj = Integer.parseInt(m0.group("broj").trim());

			String datumString = m0.group("datum");
			String vremeString = m0.group("vreme");
			
			String god = datumString.substring(datumString.length() - 5 , datumString.length() - 1);
			String mes = datumString.substring(datumString.length() - 8 , datumString.length() - 6);
			String dan = datumString.substring(datumString.length() - 11, datumString.length() - 9);
			
			LocalDateTime dateTime = LocalDateTime.parse(god + "-" + mes + "-" + dan + "T" + vremeString + ":00");
			
			String stavkeString = m0.group("stavke");
			
			int uplaceno = Integer.parseInt(m0.group("uplaceno").trim());
			
			Pattern p1 = Pattern.compile("[\\s\\S]*?\\\"proizvod\\\"\\:\\s*\\\"(?<proizvod>[\\s\\S]*?)\\\"\\,\\s*\\\"kolicina\\\"\\:\\s*(?<kol>[\\s\\S]*?)\\,\\s*\\\"cena\\\"\\:\\s*(?<cena>[\\s\\S]*?)\\,\\s*\\\"stopa\\\"\\:\\s*\\\"(?<stopa>[\\s\\S]*?)\\\"\\,");
			Matcher m1 = p1.matcher(stavkeString);
			
			List<Stavka> stavke = new LinkedList<Stavka>();
			
			while (m1.find()) {
				
				String proizvod = m1.group("proizvod").trim();
				double kol = Double.parseDouble(m1.group("kol").trim());
				double cena = Double.parseDouble(m1.group("cena").trim());
				PoreskaStopa ps = poreskaStopaFromString(m1.group("stopa").trim());
				
				stavke.add(new Stavka(proizvod, kol, cena, ps));
			}
			
			return new Racun(broj, dateTime, uplaceno, stavke);
			
		}).collect(Collectors.toList());
	}

	
	public static boolean prodatProizvodZaDatum(Stream<Racun> racuni, String proizvod, int god, int mes, int dan) {
		
		boolean found = racuni
			.filter(r -> r.getVreme().getYear() == god && r.getVreme().getMonthValue() == mes && r.getVreme().getDayOfMonth() == dan)
			.flatMap(r -> r.getStavke().stream())
			.anyMatch(s -> s.getProizvod().equalsIgnoreCase(proizvod));
		
		System.out.println("Za zadati datum " + (found ? "p" : "ne p") + "ostoji trazeni proizvod.");
		return found;
	}
	
	
	public static List<Double> kusuri(Stream<Racun> racuni) {
		
		Map<Integer, Double> m = racuni
				.collect(Collectors.toMap(
						Racun::getBroj, 
						r -> {
					
							double sum = 0.0;
							
							for (Stavka s : r.getStavke())
								sum += s.getCena();
							
							return r.getUplaceno() - sum;
						}
					)
				);
		
		m.entrySet().stream()
				.map(e -> String.format("Broj racuna : %4d ( kusur : %8.2f )", e.getKey(), e.getValue()))
				.forEach(System.out::println);
		
		return m.entrySet().stream()
				.map(e -> e.getValue())
				.collect(Collectors.toList());
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
	
	
	public static void zaradaPoMesecimaZaGodinu(Stream<Racun> racuni, int godina) {
		
		TreeMap<Object, Double> m = racuni
				.filter(r -> r.getVreme().getYear() == godina)
				.collect(Collectors.groupingBy(
						r -> { return r.getVreme().getMonthValue(); },
						TreeMap::new,
						Collectors.mapping(
								r -> {
									
									double sum = 0.0;
									
									for (Stavka s : r.getStavke())
										sum += s.getCena();
									
									return sum;
									
								}, 
								Collectors.summingDouble(d -> d))));
		
		System.out.println("     | Zarada    ");
		System.out.println("-----+-----------");
		m.entrySet().stream()
			.map(e -> String.format(" %s | %.2f", mesecAsString((int) e.getKey()), e.getValue()))
			.forEach(System.out::println);
	}


	private static void kolikoJeProdatoKiselogMleka(Stream<Racun> racuni, int god, int mes) {
		
		class Data {
			
			int kol1;
			int kol2;
			int kol3;
			
			public void add(Stavka s) {
				
				if 		(s.getProizvod().equalsIgnoreCase("Kiselo mleko 500g")) 	kol1++;
				else if (s.getProizvod().equalsIgnoreCase("Kiselo mleko")) 			kol2++;
				else if (s.getProizvod().equalsIgnoreCase("Ekstra kiselo mleko")) 	kol3++;
			}
			
			public Data join(Data a) {
				
				this.kol1 += a.kol1;
				this.kol2 += a.kol2;
				this.kol3 += a.kol3;
				
				return this;
			}
		}
		
		Map<Integer, List<Stavka>> m0 = racuni
			.filter(r -> r.getVreme().getYear() == god && r.getVreme().getMonthValue() == mes)
			.collect(Collectors.groupingBy(
					r -> { return r.getVreme().getDayOfMonth(); },
					Collectors.flatMapping(r -> {
						
						List<Stavka> l = new LinkedList<Stavka>();
						
						for (Stavka s : r.getStavke())
							if (s.getProizvod().contains("iselo mleko"))
								l.add(s);
						
						return l.stream();
						
					}, Collectors.toList())));
		
		Map<Integer, Data> m1 = m0.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, 
				e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))));
		
		System.out.println("| Dan | Vrsta1 | Vrsta2 | Vrsta3 |");
		System.out.println("|=====|========|========|========|");
		m1.entrySet().stream().map(e -> {
			
			return String.format("| %02d  | %6d | %6d | %6d |", e.getKey(), e.getValue().kol1, e.getValue().kol2, e.getValue().kol3);
		}).forEach(System.out::println);
	}
}
