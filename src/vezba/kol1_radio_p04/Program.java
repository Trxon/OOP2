package vezba.kol1_radio_p04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
	
	public static void main(String[] args) {
		
		// prvi deo
		load().stream().forEach(System.out::println);
		
		// drugi deo
		System.out.println("Najvise stanica : " + najviseStanica());
		
		// treci deo
		System.out.println("Najslusaniji u Srbiji : " + najslusanijiU("Serbia"));
		
		// cetvrti deo
		tabela();
	}
	
	
	public static void tabela() {
		
		System.out.println("  frekvencija | naziv \n--------------+--------------");
		
		load().stream()
				.filter(s -> s.getDrzava().equalsIgnoreCase("serbia"))
				.map(s -> String.format(" %12.2f | %-12s %n--------------+--------------", 
						s.getFrek(), s.getNaziv()))
				.forEach(System.out::println);
	}
	
	
	private static Zanr najslusanijiU(String drzava) {
		
		return load().stream()
						.filter(s -> s.getDrzava().equalsIgnoreCase(drzava))
						.collect(Collectors.groupingBy(
								Stanica::getZanr, 
								Collectors.counting()))
				.entrySet().stream()
						.max(Map.Entry.comparingByValue())
						.get().getKey();
	}
	
	
	private static Zanr najviseStanica() {
		
		return load().stream()
						.collect(Collectors.groupingBy(
								Stanica::getZanr, 
								Collectors.counting()))
				.entrySet().stream()
						.max(Map.Entry.comparingByValue())
						.get().getKey();
				
	}
	

	private static List<Stanica> load() {
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Program.class.getResourceAsStream("res//radio.csv")))) {
			
			String line;
			List<Stanica> stanice = new ArrayList<Stanica>();
			
			while ((line = in.readLine()) != null) {
				
				String[] tokens = line.split(",");
				
				stanice.add(new Stanica(
						Double.parseDouble(tokens[0].trim()), 
						tokens[1].trim(), tokens[2].trim(), Zanr.fromString(tokens[3].trim())));
			}
			
			return stanice;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
