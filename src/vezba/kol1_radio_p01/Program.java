package vezba.kol1_radio_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/*
 * Dat je fajl "radio.csv" koji sadrzi podatke o svetskim radio st-
 * anicama. Dati su podaci o frekvenciji, nazivu radio stanice, dr-
 * zavi u kojoj se emituje, kao i zanru muzike koji se na njemu pu-
 * sta. Zanr moze biti striktno jedan od sledecih zanrova: pop, ro-
 * ck, folk, spiritual ili multi (muzika razlicitih zanrova). Napi-
 * sati program koji ucitava dati fajl, parsira ga i obradjuje pod-
 * atke koji se od njega dobiju. 
 *
 * Program ucitava sve podatke i smesta ih u listu radio stanica. U
 * ovu svrhu potrebno je napraviti klasu Stanica koja treba da cuva
 * podatke o frekvenciji stanice koja je realan broj broj izrazen u
 * MHz-ima, nazivu radio stanice, drzavi i zanru. Sam Zanr predsta-
 * vlja nabrojivi tip podataka. 
 *
 * Glavni program poziva metode u zavisnosti od korisnikovog izbora
 * metoda.
 * 
 * Implementirati sledece metode:
 * - Zanr najviseStanica() koja pronalazi koji zanr ima najvise ra-
 *   dio stanica u datom fajlu
 * - Zanr najslusanijiU(String drzava) koja vraca zanr koje najslu-
 *   saniji u prosledjenoj drzavi
 * - void tabela() koja ispisuje sve domace radio stanice sortirane
 *   prvo po frekvenciji a zatim po nazivu:
 *
 *   frekvencija | naziv 
 *   ---------------------
 *   87.5        | Fokus
 *   ---------------------
 *   87.7        | RNS 1
 */

class Program {
	
	
	private static final Pattern PATTERN_STANICA = Pattern.compile("(?sm)^(?<frek>\\d*?\\.?\\d*?)\\,(?<naziv>.*?)\\,(?<drzava>.*?)\\,(?<zanr>.*?)$");
	
	
	private static List<Stanica> stanice;
	
	
	public static void main(String[] args) throws IOException {
		
		String text = Loader.load("res/radio.csv");
		stanice = readTextFile(text);
		
		// najviseStanica()
		System.out.println("Zanr sa najvise stanica : " + najviseStanica() + "\n");
		
		// najslusanijiU(String drzava)
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Unesite drzavu : ");
		String unos = in.readLine();
		
		try {
			System.out.println("Najslusaniji zanr u drzavi : " + najslusanijiU(unos) + "\n");
		} catch (NoSuchElementException e) {
			System.err.println("Ne postoji drzava '" + unos + "' -> " + e.toString() + "\n");
		}
		
		// tabela()
		tabela();
	}
	
	
	private static void tabela() {
		
		System.out.println(
				" FREKVENCIJA | NAZIV \n" + 
				"-------------+-------"  );
		stanice.stream()
			.filter(s -> s.drzava().equalsIgnoreCase("Serbia"))
			.sorted(Comparator.comparing(Stanica::naziv))
			.sorted(Comparator.comparing(Stanica::frek))
			.forEach(s -> System.out.printf(" %-11.1f | %s %n-------------+------- %n", s.frek(), s.naziv()));
	}
	
	
	private static Zanr najslusanijiU(String drzava) {
		
		return stanice.stream()
				.filter(s -> s.drzava().equalsIgnoreCase(drzava))
				.collect(
						Collectors.groupingBy(
								Stanica::zanr, 
								Collectors.counting()))
				.entrySet().stream()
				.max(Map.Entry.comparingByValue())
				.get().getKey();
	}


	private static Zanr najviseStanica() {
		
		return stanice.stream()
				.collect(
						Collectors.groupingBy(
								Stanica::zanr, 
								Collectors.counting()))
				.entrySet().stream()
				.max(Map.Entry.comparingByValue())
				.get().getKey();
	}


	private static List<Stanica> readTextFile(String text) {
		
		List<Stanica> list = new ArrayList<Stanica>();
		Matcher matcherStanica = PATTERN_STANICA.matcher(text);
		
		while (matcherStanica.find()) {
			
			Stanica s = new Stanica();

			s.setFrek(Double.parseDouble(matcherStanica.group("frek").trim()));
			s.setNaziv(matcherStanica.group("naziv"));
			s.setDrzava(matcherStanica.group("drzava"));
			s.setZanr(matcherStanica.group("zanr").trim());
			
			list.add(s);
		}
		
		return list;
	}
}
