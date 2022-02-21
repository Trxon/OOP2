package vezba.kol1_radio_p02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;
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
	
	
	private static List<Stanica> stanice = readTextFile("res/radio.csv");
	
	
	public static void main(String[] args) {
		
		najviseStanica();
		najslusanijiU("Serbia");
		tabela();
	}
	
	
	private static void tabela() {
		
		System.out.println(	" frekvencija| naziv \n" + 
							"------------+--------");
		
		stanice.stream()
		.filter(s -> s.drzava().equalsIgnoreCase("Serbia"))
		.sorted(Comparator.comparing(Stanica::naziv))
		.sorted(Comparator.comparing(Stanica::frek))
		.forEach(s -> System.out.printf(" %-11.2f| %s %n------------+--------%n", s.frek(), s.naziv()));
	}


	private static Zanr najslusanijiU(String drzava) {
		
		Zanr z = stanice.stream().filter(s -> s.drzava().equalsIgnoreCase(drzava))
				.collect(Collectors.groupingBy(
						Stanica::zanr, 
						Collectors.counting()))
				.entrySet().stream()
				.max(Map.Entry.comparingByValue()).get().getKey();
		
		System.out.println("Najslusaniji zanr u zemlji '" + drzava + "' je : " + z);
		return z;
	}


	private static Zanr najviseStanica() {
		
		Zanr z = stanice.stream()
				.collect(
						Collectors.groupingBy(
								Stanica::zanr, 
								Collectors.counting()))
				.entrySet().stream()
				.max(Map.Entry.comparingByValue()).get().getKey();
		
		System.out.println("Zanr sa najvise stanica je : " + z);
		return z;
	}


	private static List<Stanica> readTextFile(String file) {
		
		Matcher matcherLine = PATTERN_LINE.matcher(load(file));
		List<Stanica> l = new LinkedList<Stanica>();
		
		while (matcherLine.find()) {
			
			Matcher matcherContent = PATTERN_CONTENT.matcher(matcherLine.group("red"));
			
			while (matcherContent.find()) {
				
				Stanica s = new Stanica();
				
				s.setFrek(matcherContent.group("frek"));
				s.setNaziv(matcherContent.group("ime"));
				s.setDrzava(matcherContent.group("zemlja"));
				s.setZanr(matcherContent.group("zanr"));
				
				l.add(s);
			}
		}
		
		return l;
	}


	public static String load(String file) {
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Program.class.getResourceAsStream(file)))) {
			
			String line;
			StringBuilder text = new StringBuilder();
			
			while ((line = in.readLine()) != null) {
				text.append(line);
				text.append("\n");
			}
			
			return text.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
