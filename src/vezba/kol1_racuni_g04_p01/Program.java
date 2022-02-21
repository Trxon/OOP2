package vezba.kol1_racuni_g04_p01;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	

	public static final int BROJ = 5000;
	

	public static void main(String[] args) {

//		Racuni.stringStream(BROJ)
//				.forEach(System.out::println);

//		Racuni.racuniStream(BROJ)
//				.forEach(System.out::println);
		
		// prvi deo
		List<Racun> racuni = load(Racuni.stringStream(BROJ));
		
		// drugi deo
		nemaMilerama(2020, 3, 1);
		
		// treci deo
		stavke(1);
		
		// cetvrti deo
		prosecnaVisinaRacuna(2018);
		
		// peti deo
		porez();
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
	
	
	public static boolean nemaMilerama(int dan, int mesec, int godina) {
		
		boolean b = Racuni.racuniStream(BROJ)
				.filter(r -> r.getVreme().getYear() == godina && r.getVreme().getMonthValue() == mesec && r.getVreme().getDayOfMonth() == dan)
				.flatMap(r -> r.getStavke().stream())
				.anyMatch(s -> s.getProizvod().contains("ileram"));
		
		System.out.println("Na zadati dan " + (b ? "" : "ni") + "je prodat mileram.");
		return b;
	}
	
	
	public static void stavke(int brojRacuna) {
		
		List<Stavka> stavke = Racuni.racuniStream(BROJ)
			.filter(r -> r.getBroj() == brojRacuna)
			.map(Racun::getStavke)
			.findFirst().orElse(new LinkedList<Stavka>());
		
		stavke.stream()
			.map(s -> {
				
				StringBuilder out = new StringBuilder();
				
				out.append("<stavka>\n");
				
				out.append("\t<proizvod>");
				out.append(s.getProizvod());
				out.append("</proizvod>\n");
				
				out.append("\t<kolicina>");
				out.append(s.getKolicina());
				out.append("</kolicina>\n");
				
				out.append("\t<cena>");
				out.append(s.getCena());
				out.append("</cena>\n");

				out.append("\t<stopa>");
				out.append(s.getStopa().toString().toLowerCase());
				out.append("</stopa>\n");
				
				out.append("</stavka>");
				
				return out.toString();
			})
			.forEach(System.out::println);
	}
	
	
	public static void prosecnaVisinaRacuna(int godina) {
		
		class Data {
			
			int count;
			double sum;
			
			public void add(Racun r) {
				count++;
				sum += r.getStavke().stream().map(Stavka::getCena).reduce(0.0, (d0, d1) -> d0 + d1);
			}
			
			public Data join(Data d) {
				
				count += d.count;
				sum += d.sum;
				
				return this;
			}
			
			public double avg() {
				return sum / count;
			}
		}
		
		Map<LocalDate, Data> m = Racuni.racuniStream(BROJ)
				.filter(r -> r.getVreme().getYear() == godina)
				.collect(Collectors.groupingBy(r -> r.getVreme().toLocalDate(), Collector.of(Data::new, Data::add, Data::join)));
		
		System.out.println("    Datum | Prosek   ");
		System.out.println("----------+----------");
		m.entrySet().stream()
			.map(e -> String.format(" %8s | %.2f", e.getKey().format(DateTimeFormatter.ofPattern("dd.MM.")), e.getValue().avg()))
			.forEach(System.out::println);
	}
	
	
	public static void porez() {
		
		class Data0 {
			
			double ops, pos, osl;
			
			public void add(Racun r) {
				for (Stavka s : r.getStavke())
					switch (s.getStopa()) {
						case OPSTA: 		ops += s.getCena() * 0.20;	// pretpostavimo da se nezavisno od stope placa 20% cene kao porez
						case POSEBNA: 		pos += s.getCena() * 0.20;
						case OSLOBODJEN: 	osl += 0.0;
						default:			return;
					}
			}
		
			public Data0 join(Data0 d) {
				
				this.ops += d.ops;
				this.pos += d.pos;
				this.osl += d.osl;
				
				return this;
			}
		}
		
		Map<Integer, Data0> m0 = Racuni.racuniStream(BROJ)
				.collect(Collectors.groupingBy(
						r -> r.getVreme().getYear(), 
						Collector.of(Data0::new, Data0::add, Data0::join)));
		
		System.out.println(" Godina |    Opsta   |   Posebna  | Oslobodjen  ");
		System.out.println("=============================================== ");
		m0.entrySet().stream()
			.map(e -> String.format("%6d. | %10.2f | %10.2f | %10.2f", e.getKey(), e.getValue().ops, e.getValue().pos, e.getValue().osl))
			.forEach(System.out::println);
	}
}
