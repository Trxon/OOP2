package vezba.kol1_fudbal_p02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * Zadatak
 * =======
 *
 * Napisati Java aplikaciju koja pomocu tokova podataka i lambda izraza
 * obradjuje i prikazuje podatke o fudbalskom prvenstvu.
 * 
 * Pri pokretanju, aplikacija ucitava podatke iz fajla 2018.txt. Podaci su dati
 * u obicnom tekstualnom formatu. Podaci o jednoj utakmici se nalaze u jednom
 * redu fajla. Na pocetku se nalazi redni broj utakmice na prvenstvu, slede
 * datum i vreme, pa imena zemalja ciji su timovi igrali, sa rezultatom izmedju.
 * U nastavku linije se nalazi lokacija gde je odigrana utakmica. Ako je bilo
 * golova, u narednoj liniji se nalaze podaci o strelcima na utakmici. Takodje,
 * utakmice su odvojene po grupama i fazama takmicenja.
 * 
 * Za cuvanje podataka je potrebno napraviti nabrojivi tip Grupa, koji pred-
 * stavlja grupe prve faze prvenstva: Grupa A, Grupa B, ... Grupa H, kao i gru-
 * pisanje utakmica u plej-of-u: osmina finala, cetvrtina finala, polufinale,
 * trece mesto, finale.
 * 
 * Takodje je potrebno implementirati klasu Strelac za predstavljanje podataka o
 * strelcima na utakmici, koja ima sledeca polja: ime stralca, minut u kojem je
 * postignut gol, boolean indikator da li u pitanju gol iz penala, i indikator
 * da li je u pitanju autogol.
 * 
 * Potom implementirati klasu Rezultat koja ima dva celobrojna polja u kojima se
 * nalazi broj golova na utakmici za svaki od timova.
 * 
 * Na kraju, implementirati klasu Utakmica koja sadrzi sve podatke o jednoj
 * utakmici: grupa, redni broj, datum, vreme, zemlja A, zemlja B, rezultat na
 * poluvremenu, rezultat po isteku vremena, rezultat posle produzetaka, rezultat
 * posle penala, lista strelaca za zemlju A, lista strelaca za zemlju B. Ako
 * nisu igrani produzeci ili penali posle produzetaka, odgovarajuci rezultati su
 * null.
 * 
 * Takodje implementirati metode equals(), hashCode() i toString() kako bi
 * instance ovih klasa mogle da se koriste u kolekcijama.
 * 
 * Podaci se ucitavaju iz fajla i smestaju u jednu veliku listu instanci klase
 * Utakmica. Potom se podaci iz ove liste obradjuju iskljucivo pomocu tokova
 * podataka i lambda izraza.
 * 
 * Implementirati metod String pobednik() koji vraca ime zemlje koja je pobedila
 * na prvenstvu.
 * 
 * Implementirati metod String najviseGolova() koji vraca ime igraca koji je
 * dao najvise golova ukupno na celom prvenstvu.
 * 
 * Pozvati metode iz glavnog programa i ispisati rezultate.
 * 
 * Potom, tabelarno prikazati podatke o svim golovima na prvenstvu, sortirane po
 * datumu i vremenu kada je gol dat.
 * 
 * Svaki red tabele sadrzi podatke o jednom golu. Kolone tabele su redom:
 * datum kada je gol dat, tacno vreme u koje je dat gol, ime igraca koji je
 * dao gol, i ime zemlje za koju je gol dat. Obratiti paznju da brojevi budu
 * uredno poravnati, i takodje prikazati zaglavlje sa imenima kolona.
 */

public class Program {
	
	
	private static List<Utakmica> utakmice;

	
	public static void main(String[] args) throws IOException {
		
		String ceoSadrzaj = readFile();
		utakmice = obradiFajl(ceoSadrzaj);
		
//		utakmice.stream().forEach(System.out::println);
		
		System.out.println("Pobednik : " + pobednik());
		
		System.out.println("Najvise golova : " + najviseGolova());
		
		tabela1();
		
		tabela2();
	}
	
	
	private static void tabela2() {
		
		class Poeni {
			
			public final Grupa grupa;
			public final String zemlja;
			public final int poeni;
			
			public Poeni(Grupa grupa, String zemlja, int rez1, int rez2) {
				this.grupa = grupa;
				this.zemlja = zemlja;
				this.poeni = rez1 == rez2 ? 1 : rez1 > rez2 ? 3 : 0;
			}
		}
		
		EnumSet<Grupa> grupe = EnumSet.of(
				Grupa.GRUPA_A, Grupa.GRUPA_B, Grupa.GRUPA_C, Grupa.GRUPA_D, 
				Grupa.GRUPA_E, Grupa.GRUPA_F, Grupa.GRUPA_G, Grupa.GRUPA_H);
		
		Map<Grupa, Map<String, Integer>> m0 = utakmice.stream()
				.filter(u -> grupe.contains(u.grupa))
				.flatMap(u -> Stream.of(
						new Poeni(u.grupa, u.zemljaA, u.naKrajuVremena.timA, u.naKrajuVremena.timB),
						new Poeni(u.grupa, u.zemljaB, u.naKrajuVremena.timB, u.naKrajuVremena.timA)
						))
				.collect(Collectors.groupingBy(
						p -> (Grupa) p.grupa, 
						Collectors.groupingBy(
								p -> p.zemlja,
								Collectors.summingInt(p -> p.poeni)
						)
				));
		
		Map<Grupa, String> m1 = m0.entrySet().stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey, 
						e -> e.getValue().entrySet().stream()
								.sorted(Comparator.comparing(x -> -x.getValue()))
								.map(f -> String.format("| %-12s | %d |", f.getKey(), f.getValue()))
								.collect(Collectors.joining("\n", "o--------------o---o\n", "\no--------------o---o"))));
		
		m1.entrySet().stream()
				.map(e -> String.format("o--------------o---o%n| %-16s |%n%s", e.getKey(), e.getValue()))
				.forEach(System.out::println);
	}


	private static void tabela1() {
		
		class Data {
			
			LocalDate dat;
			LocalTime vre;
			String ime;
			String zemlja;
				
			public Data(LocalDate dat, LocalTime vre, String ime, String zemlja) {
				this.dat = dat;
				this.vre = vre;
				this.ime = ime;
				this.zemlja = zemlja;
			}
		}
		
		System.out.println();
		System.out.println("/--------------------------------------------------------------\\");
		System.out.println("|                            Golovi                            |");
		System.out.println("|                                                              |");
		System.out.println("|      Datum : Vreme                    Strelac : Zemlja       |");
		System.out.println("|------------+----------------------------------+--------------|");
		utakmice.stream()
				.sorted(Comparator.comparing(u -> LocalDateTime.of(u.datum, u.vreme)))
				.flatMap(u -> {
					
					return Stream.concat(
									u.strelciA.stream()
										.map(s -> new Data(u.datum, u.vreme.plusMinutes(s.minut), s.ime, u.zemljaA))
										.collect(Collectors.toList()).stream(),
									u.strelciB.stream()
										.map(s -> new Data(u.datum, u.vreme.plusMinutes(s.minut), s.ime, u.zemljaB))
										.collect(Collectors.toList()).stream())
							.sorted(Comparator.comparing(d -> d.vre))
							.collect(Collectors.toList()).stream();
				}).map(d -> String.format("| %s | %s %26s | %-12s |", d.dat, d.vre, d.ime, d.zemlja))
				.forEach(System.out::println);
		System.out.println("\\------------+----------------------------------+--------------/");
	}


	private static String najviseGolova() {
		
		return utakmice.stream()
				.flatMap(u -> Stream.concat(u.strelciA.stream(), u.strelciB.stream()))
				.collect(Collectors.groupingBy(s -> s.ime, Collectors.counting()))
				.entrySet().stream()
				.max(Map.Entry.comparingByValue())
				.map(e -> e.getKey()).orElse(null);
	}


	private static String pobednik() {
		
		return utakmice.stream()
				.filter(u -> u.grupa == Grupa.FINALE)
				.map(u -> u.naKrajuVremena.timA > u.naKrajuVremena.timB ? u.zemljaA : u.zemljaB)
				.findAny()
				.orElse(null);
	}


	private static List<Utakmica> obradiFajl(String ceoSadrzaj) {
		
		List<Utakmica> utakmice = new ArrayList<>();
		
		String[] poGrupama = ceoSadrzaj.split("(?sm)((Group.*?|Round.*?|Quarter-finals|Semi-finals|Match for.*?|Final)\\n)");
		Grupa[] grupe = Grupa.values();
		
		// indeks kod poGrupama se povecava za 1 jer je prvi element niza nakon splitovanja prazan string
		for (int i = 0; i < grupe.length; i++)
			obradiGrupu(poGrupama[i + 1], grupe[i], utakmice);
		
		return utakmice;
	}


	private static void obradiGrupu(String string, Grupa grupa, List<Utakmica> utakmice) {
		
		DateTimeFormatter datumFormat = DateTimeFormatter.ofPattern("yyyy/MMM/d");
		DateTimeFormatter vremeFormat = DateTimeFormatter.ofPattern("HH:mm");
		
		Pattern p0 = Pattern.compile("\\((?<redniBroj>[0-9]+)\\)\\s\\w{3}\\s(?<datum>\\w{3}\\/[0-9]{1,2})\\s*?(?<vreme>[0-9]+:[0-9]+)\\s*?(?<zemljaA>[A-Z][A-Za-z ]+)\\s*?(?<rezultati>.*?)\\s*?(?<zemljaB>[A-Z][A-Za-z ]+)\\s*?\\@\\s(?<stadion>[\\s\\S]*?)\\s\\(UTC\\+\\d\\)\\s*?\\[(?<strelciA>[\\s\\S]*?)\\;(?<strelciB>[\\s\\S]*?)\\]");
		Matcher m0 = p0.matcher(string);
		
		while (m0.find()) {
			
			int redniBroj = Integer.parseInt(m0.group("redniBroj").trim());
			LocalDate datum = LocalDate.parse("2018/" + m0.group("datum"), datumFormat);
			LocalTime vreme = LocalTime.parse(m0.group("vreme"), vremeFormat);
			String stadion = m0.group("stadion").trim();
			String zemljaA = m0.group("zemljaA").trim();
			String zemljaB = m0.group("zemljaB").trim();
			List<Rezultat> rezultati = obradiRezultate(m0.group("rezultati"));
			List<Strelac> strelciA = obradiStrelce(m0.group("strelciA"));
			List<Strelac> strelciB = obradiStrelce(m0.group("strelciB"));
			
			Utakmica utakmica = new Utakmica(grupa, redniBroj, datum, vreme, stadion, zemljaA, zemljaB, rezultati.get(0), rezultati.get(1), rezultati.get(2), rezultati.get(3), strelciA, strelciB);
			utakmice.add(utakmica);
		}
	}


	private static List<Rezultat> obradiRezultate(String rezultatiString) {
		
		Pattern p0 = Pattern.compile("(?<rez>\\d+-\\d+)");
		Matcher m0 = p0.matcher(rezultatiString);
		
		List<Rezultat> rezultati = new ArrayList<Rezultat>();
		
		while (m0.find()) {
			
			String[] rez = m0.group("rez").trim().split("\\-");
			rezultati.add(new Rezultat(Integer.parseInt(rez[0].trim()), Integer.parseInt(rez[1].trim())));
		}
		
		// dodajemo null za produzetke ako nije bilo produzetaka
		if (rezultati.size() < 3)
			rezultati.add(1, null);
		
		// dodajemo null za penale ako ih nije bilo
		if (rezultati.size() < 4)
			rezultati.add(1, null);
		
		return rezultati;
	}


	private static List<Strelac> obradiStrelce(String strelciString) {
		
		List<Strelac> strelci = new ArrayList<Strelac>();
		
		Pattern p0 = Pattern.compile("((?<ime>[A-ZÁa-záéëíøüšćðž\\.\\- ]+)\\s(?<min>\\d*\\+?\\d*)\\'\\s?((?<own>\\(o.g.\\))|(?<pen>\\(pen.\\)))?|\\-)");
		Matcher m0 = p0.matcher(strelciString);
		
		if (strelciString == null || "-".equals(strelciString.trim()))
			return strelci;
		
		while (m0.find()) {
			
			String ime = m0.group("ime").trim();
			
			String[] minutTokens = m0.group("min").trim().split("\\+");
			int min = 0;
			
			for (String t : minutTokens)
				min += Integer.parseInt(t.trim());
			
			boolean autoGol = m0.group("own") != null;
			boolean izPenala = m0.group("pen") != null;
			
			strelci.add(new Strelac(ime, min, autoGol, izPenala));
		}
		
		return strelci;
	}


	private static String readFile() throws IOException {
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Program.class.getResourceAsStream("2018.txt")))) {
			
			StringBuilder text = new StringBuilder();
			String line;
			
			while ((line = in.readLine()) != null) {
				text.append(line);
				text.append("\n");
			}
			
			return text.toString();
		}
	}
}
