package vezba.kol1_radio_p03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
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
	
	
	private static Pattern PATTERN_LINE		= Pattern.compile("(?<red>[\\s\\S]*?)\\n"); 
	private static Pattern PATTERN_CONTENT 	= Pattern.compile(
			"(?<frek>[\\s\\S]*?),(?<ime>[\\s\\S]*?),(?<zemlja>[\\s\\S]*?),(?<zanr>[^\\n]*)");
	
	
	public static void main(String[] args) throws IOException {
		
		String text = load("res/radio.csv");
		
		// prvi deo
		List<Stanica> stanice = loadStanice("res/radio.csv");
		for (Stanica s : stanice) System.out.println(s);
		
		// drugi deo
		System.out.println("Najvise stanica : " + najviseStanica(stanice));
		
		// treci deo
		System.out.println("Najslusaniji u Srbiji : " + najslusanijiU("Serbia", stanice));
		
		// cetvrti deo
		tabela(stanice);
	}
	
	
	private static void tabela(List<Stanica> stanice) {
		
		System.out.println("  frekvencija | naziv \n--------------+--------------");
		
		stanice.stream()
				.filter(s -> s.drzava().equalsIgnoreCase("serbia"))
				.map(s -> String.format(" %12.2f | %-12s %n--------------+--------------", 
						s.frek(), s.naziv()))
				.forEach(System.out::println);
	}
	
	
	private static Zanr najslusanijiU(String drzava, List<Stanica> stanice) {
		
		return stanice.stream()
						.filter(s -> s.drzava().equalsIgnoreCase(drzava))
						.collect(Collectors.groupingBy(Stanica::zanr, Collectors.counting()))
				.entrySet().stream()
						.max(Map.Entry.comparingByValue())
						.get().getKey();
	}
	
	
	private static Zanr najviseStanica(List<Stanica> stanice) {
		
		return stanice.stream()
						.collect(Collectors.groupingBy(Stanica::zanr, Collectors.counting()))
				.entrySet().stream()
						.max(Map.Entry.comparingByValue())
						.get().getKey();
	}

	
	private static List<Stanica> loadStanice(String file) {
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Program.class.getResourceAsStream(file)))) {
			
			return in.lines()
				.map(l -> {
					
					Matcher matcherContent = PATTERN_CONTENT.matcher(l);
					matcherContent.find();
					
					Stanica s = new Stanica();
					
					s.setFrek(matcherContent.group("frek"));
					s.setNaziv(matcherContent.group("ime"));
					s.setDrzava(matcherContent.group("zemlja"));
					s.setZanr(matcherContent.group("zanr"));
					
					return s;
				})
				.collect(Collectors.toList());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}


	private static String load(String file) throws IOException {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(Program.class.getResourceAsStream(file)));
		
		String line;
		StringBuilder text = new StringBuilder();
		
		while ((line = in.readLine()) != null) {
			text.append(line);
			text.append("\n");
		}
		
		return text.toString();
	}
}
