package vezba.kol1_fudbal_ass.resenje_g01_g02_norm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * Napisati Java aplikaciju koja pomocu tokova podataka i lambda izraza
 * obradjuje i prikazuje podatke o fudbalskom prvenstvu.
 */
public class Program {

	/*
	 * Pri pokretanju, aplikacija ucitava podatke iz fajla 2018.txt. Podaci su dati
	 * u obicnom tekstualnom formatu. Podaci o jednoj utakmici se nalaze u jednom
	 * redu fajla. Na pocetku se nalazi redni broj utakmice na prvenstvu, slede
	 * datum i vreme, pa imena zemalja ciji su timovi igrali, sa rezultatom izmedju.
	 * U nastavku linije se nalazi lokacija gde je odigrana utakmica. Ako je bilo
	 * golova, u narednoj liniji se nalaze podaci o strelcima na utakmici. Takodje,
	 * utakmice su odvojene po grupama i fazama takmicenja.
	 */
	public static void main(String[] args) throws IOException {

		/*
		 * Podaci se ucitavaju iz fajla i smestaju u jednu veliku listu instanci klase
		 * Utakmica. Potom se podaci iz ove liste obradjuju iskljucivo pomocu tokova
		 * podataka i lambda izraza.
		 */

		String ceoSadrzaj = readFile();
		utakmice = obradiFajl(ceoSadrzaj);

		/*
		 * Pozvati metode iz glavnog programa i ispisati rezultate.
		 */

		System.out.println();
		System.out.println("Pobednik");
		System.out.println(pobednik());

		System.out.println();
		System.out.println("Najvise golova");
		System.out.println(najviseGolova());

		tabela1();

		System.out.println();
		System.out.println("Ukupan broj golova");
		System.out.println(brojGolova());

		System.out.println();
		System.out.println("Najcesci stadion");
		System.out.println(najcesciStadion());

		tabela2();

		utakmice.stream().forEach(System.out::println);
	}

	
	private static List<Utakmica> obradiFajl(String tekst) {

		List<Utakmica> utakmice = new ArrayList<>();

		// Razdvajamo sadrzaj fajla po grupama takmicenja 
		String[] poGrupama = tekst.split("\n(Group .:|Round of 16|Quarter-finals|Semi-finals|Match for third place|Final)\n\n");

		// Obradjujemo grupu po grupu
		// Oslanjamo se na to da su konstante u nabrojivom tipu navedene istim redom kao i grupe u fajlu
		Grupa[] grupe = Grupa.values();
		for (int i = 0; i < grupe.length; i++) { 
			obradiGrupu(poGrupama[i + 1], grupe[i], utakmice);
		}

		return utakmice;

	}

	
	private static final DateTimeFormatter datumFormat = DateTimeFormatter.ofPattern("yyyy/MMM/d");
	private static final DateTimeFormatter vremeFormat = DateTimeFormatter.ofPattern("HH:mm");
	private static final Pattern utakmicaPattern = Pattern.compile(""
			+ " *"
			+ "\\((?<redniBroj>[0-9]+)\\)"
			+ " *"
			+ "[A-Za-z]{3} (?<datum>[A-Za-z]{3}/[0-9]{1,2})"
			+ " *"
			+ "(?<vreme>[0-9]{2}:[0-9]{2})"
			+ " *"
			+ "(?<zemljaA>[A-Z][A-Za-z ]+)"
			+ " *"
			+ "(?<rezultati>.*?)"
			+ " *"
			+ "(?<zemljaB>[A-Z][A-Za-z ]+)"
			+ " *@ *"
			+ "(?<stadion>.*?)"
			+ " *"
			+ "\\(UTC\\+.\\)\n"
			+ " *"
			+ "(?:\\[(?<strelciA>.*?);(?<strelciB>.*?)\\])?"
			+ " *"
	);
	private static void obradiGrupu(String tekst, Grupa grupa, List<Utakmica> utakmice) {

		// Trazimo sve utakmice na klasican nacin
		// Za svaku izvlacimo potrebne podatke regex grupa
		Matcher matcher = utakmicaPattern.matcher(tekst);
		while (matcher.find()) {
			int redniBroj = Integer.parseInt(matcher.group("redniBroj"));
			LocalDate datum = LocalDate.parse("2018/" + matcher.group("datum"), datumFormat);
			LocalTime vreme = LocalTime.parse(matcher.group("vreme"), vremeFormat);
			String stadion = matcher.group("stadion").trim();
			String zemljaA = matcher.group("zemljaA").trim();
			String zemljaB = matcher.group("zemljaB").trim();
			List<Rezultat> rezultati = obradiRezultate(matcher.group("rezultati")); // Ima vise rezultata tako da koristimo pomocni metod
			List<Strelac> strelciA = obradiStrelce(matcher.group("strelciA")); // Moze biti vise strelaca tako da koristimo pomocni metod
			List<Strelac> strelciB = obradiStrelce(matcher.group("strelciB")); // Moze biti vise strelaca tako da koristimo pomocni metod
			Utakmica utakmica = new Utakmica(grupa, redniBroj, datum, vreme, stadion, zemljaA, zemljaB, rezultati.get(0), rezultati.get(1), rezultati.get(2), rezultati.get(3), strelciA, strelciB);
			utakmice.add(utakmica);
		}

	}

	private static final Pattern rezultatPattern = Pattern.compile("([0-9])-([0-9])");
	private static List<Rezultat> obradiRezultate(String tekst) {

		List<Rezultat> rezultati = new ArrayList<>();

		// Trazimo sve rezultate na klasican nacin
		Matcher matcher = rezultatPattern.matcher(tekst);
		while (matcher.find()) {
			Rezultat rezultat = new Rezultat(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
			rezultati.add(rezultat);
		}

		// Dodajemo null za produzetke ako nije bilo produzetaka
		if (rezultati.size() < 3) {
			rezultati.add(1, null);
		}

		// Dodajemo null za penale ako ih nije bilo
		if (rezultati.size() < 4) { 
			rezultati.add(1, null);
		}

		return rezultati;

	}

	private static final Pattern strelacPattern = Pattern.compile(""
			+ " *"
			+ "(?<ime>[^0-9]+)"
			+ " *"
			+ "(?<minut>[0-9]{1,2}(\\+[0-9]{1,2})?)'"
			+ " *"
			+ "((?<penal>\\(pen\\.\\))|(?<autoGol>\\(o\\.g\\.\\)))*"
	);
	private static List<Strelac> obradiStrelce(String tekst) {

		List<Strelac> strelci = new ArrayList<>();

		// Trivijalan slucaj
		if (tekst == null || "-".equals(tekst.trim())) {
			return strelci;
		}

		// Trazimo sve strelce na klasican nacin
		Matcher matcher = strelacPattern.matcher(tekst);
		while (matcher.find()) {
			String ime = matcher.group("ime").trim();
			String[] minutDelovi = matcher.group("minut").split("\\+");
			int minut = 0;
			for (String m : minutDelovi) {
				minut += Integer.parseInt(m);
			}
			boolean izPenala = matcher.group("penal") != null;
			boolean autoGol = matcher.group("autoGol") != null;
			Strelac strelac = new Strelac(ime, minut, izPenala, autoGol);
			strelci.add(strelac);
		}

		return strelci;

	}

	private static String readFile() throws IOException {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Program.class.getResourceAsStream("2018.txt")))) {
			StringBuilder text = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				text.append(line);
				text.append('\n');
			}
			return text.toString();
		}
	}

	private static List<Utakmica> utakmice;

	/* 
	 * Implementirati metod String pobednik() koji vraca ime zemlje koja je pobedila
	 * na prvenstvu.
	 */
	private static String pobednik() {
		return utakmice.stream()
				.filter(u -> u.grupa == Grupa.FINALE)
				.map(u -> u.naKrajuVremena.timA > u.naKrajuVremena.timB ? u.zemljaA : u.zemljaB)
				.findAny()
				.orElse(null);
	}

	/*
	 * Implementirati metod String najviseGolova() koji vraca ime igraca koji je
	 * dao najvise golova ukupno na celom prvenstvu.
	 */
	private static String najviseGolova() {
		return utakmice.stream()
				.flatMap(u -> Stream.concat(u.strelciA.stream(), u.strelciB.stream())) // Predjemo na strelce
				.collect(Collectors.groupingBy(s -> s.ime, Collectors.counting())) // Prebrojimo svakog strelca
				.entrySet().stream()
				.max(Map.Entry.comparingByValue()) // Uzmemo najbrojnijeg
				.map(e -> e.getKey())
				.orElse(null);
	}

	/*
	 * Potom, tabelarno prikazati podatke o svim golovima na prvenstvu, sortirane po
	 * datumu i vremenu kada je gol dat.
	 * 
	 * Svaki red tabele sadrzi podatke o jednom golu. Kolone tabele su redom:
	 * datum kada je gol dat, tacno vreme u koje je dat gol, ime igraca koji je
	 * dao gol, i ime zemlje za koju je gol dat. Obratiti paznju da brojevi budu
	 * uredno poravnati, i takodje prikazati zaglavlje sa imenima kolona.
	 */
	private static void tabela1() {

		class Gol {

			public final LocalDate datum;
			public final LocalTime vreme;
			public final String strelac;
			public final String zemlja;

			public Gol(LocalDate datum, LocalTime vreme, String strelac, String zemlja) {
				this.datum = datum;
				this.vreme = vreme;
				this.strelac = strelac;
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
				.flatMap(u -> Stream.concat( // Izvucemo potrebne podatke, svakog strelca pretvorimo u Gol objekat
						u.strelciA.stream()
								.map(s -> new Gol(u.datum, u.vreme.plusMinutes(s.minut), s.ime, u.zemljaA)), 
						u.strelciB.stream()
								.map(s -> new Gol(u.datum, u.vreme.plusMinutes(s.minut), s.ime, u.zemljaB))
				))
				.sorted( // Sortiramo po datumu i vremenu
						Comparator.comparing((Gol g) -> g.datum)
								.thenComparing((Gol g) -> g.vreme)
				)
				.map(g -> String.format("| %10s | %5s   %24s | %-12s |", // Formatiramo ispis
						g.datum,
						g.vreme,
						g.strelac,
						g.zemlja
				))
				.forEach(System.out::println);
		System.out.println("\\------------+----------------------------------+--------------/");

	}

	/*
	 * Implementirati metod int brojGolova() koji vraca ukupan broj golova na
	 * prvenstvu.
	 */
	private static int brojGolova() {
		return utakmice.stream()
				.mapToInt(u -> u.strelciA.size() + u.strelciB.size())
				.sum();
	}

	/*
	 * Implementirati metod String najcesciStadion() koji vraca ime Stadiona i grada
	 * u kojem se nalazi, na kojem se odigralo najvise utakmica.
	 */
	private static String najcesciStadion() {
		return utakmice.stream()
				.collect(Collectors.groupingBy(u -> u.stadion, Collectors.counting())) // Prebrojimo svaki stadion
				.entrySet().stream()
				.max(Map.Entry.comparingByValue()) // Uzmemo najbrojniji
				.map(e -> e.getKey())
				.orElse(null);
	}

	/*
	 * Potom, tabelarno prikazati podatke o osvojenim poenima u grupnom delu
	 * prvenstva. Za svaku grupu prikazati po jednu tabelu.
	 * 
	 * Svaki red tabele sadrzi podatke o jednoj zemlji, a od kolona sadrzi ime
	 * zemlje i broj poena. Redovi u tabeli su sortirani po broju poena.
	 * Broj poena se racuna na sledeci nacin: pobeda vredi 3 poena, neresen
	 * rezultat nosi po jedan poen svakoj od zemalja, dok zemlja koja je izgubila
	 * utakmicu ne dobija poene za tu utakmicu.
	 */
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

		EnumSet<Grupa> grupe = EnumSet.of(Grupa.GRUPA_A, Grupa.GRUPA_B, Grupa.GRUPA_C, Grupa.GRUPA_D, Grupa.GRUPA_E, Grupa.GRUPA_F, Grupa.GRUPA_G, Grupa.GRUPA_H);
		utakmice.stream()
				.filter(u -> grupe.contains(u.grupa)) // Samo grupni deo takmicenja
				.flatMap(u -> Stream.of(              // Izvucemo potrebne podatke, svaka utakmica ima dve zemlje
						new Poeni(u.grupa, u.zemljaA, u.naKrajuVremena.timA, u.naKrajuVremena.timB),
						new Poeni(u.grupa, u.zemljaB, u.naKrajuVremena.timB, u.naKrajuVremena.timA)
				))
				.collect(Collectors.groupingBy(                     // Grupisemo po grupi takmicenja
						u -> u.grupa,
						Collectors.groupingBy(                      // I onda po zemlji
								p -> p.zemlja,
								Collectors.summingInt(p -> p.poeni) // A poene saberemo
						)
				))
				.entrySet().stream()
				.collect(Collectors.toMap(                                        // Transformisemo mapu
						e -> e.getKey(),                                          // Kljucevi ostaju isti
						e -> e.getValue().entrySet().stream()                     // Listu zemalja pretvorimo u string za stampanje 
								.sorted(Comparator.comparing(x -> -x.getValue())) // Sortiramo po rezultatu (nije se trazilo)
								.map(f -> String.format("| %-12s | %d |", f.getKey(), f.getValue()))
								.collect(Collectors.joining("\n", "o--------------o---o\n", "\no--------------o---o")) // Dodamo okvire oko tabele
				))
				.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())                              // Sortiramo po grupi
				.map(e -> String.format("%no------------------o%n| %-16s |%n%s", // Dodamo zaglavlje grupe
						e.getKey(),
						e.getValue()
				))
				.forEach(System.out::println);

	}
}