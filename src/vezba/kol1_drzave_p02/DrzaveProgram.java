package vezba.kol1_drzave_p02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import vezba.kol1_drzave_p01.Drzava;

public class DrzaveProgram {

	
	/*
	 * Klasa Drzava opisuje jednu drzavu sa sledecim atributima:
	 * - private String ime
	 * - private String kontinent
	 * - private int brStanovnika
	 * - private double budzet
	 * 
	 * Takodje u klasi Drzava implementirati metod:
	 * public static Drzava fromString(String drzava) { ... }
	 * 
	 * Podaci o jednoj drzavi nalaze se u tekstualnom fajlu "drzave.tx-
	 * t". Svi podaci o jednoj drzavi nalaze se u jednom redu i odvoje-
	 * ni su znakom ; . Ucitati podatke o drzavi i smestiti ih u kolek-
	 * ciju List<Drzava>. Nakon toga, koristeci tokove podataka implem-
	 * entirati i sledece metode:
	 * 
	 * int brojDrzava(List<Drzava> lista) { ... }
	 * double prosecnoBogate(List<Drzava> lista) { ... }
	 * najbogatija(List<Drzava> lista) { ... }
	 * sortiraniPoImenu(List<Drzava> lista) { ... }
	 * sortiraniPoImenuOpadajuce(List<Drzava> lista) { ... }
	 * sortiraniPoBrojuStanovnika(List<Drzava> lista) { ... }
	 * double prosecanBrojStanovnikaSvihDrzava(List<Drzava> lista) { ... }
	 * najbogatijaDrzavaSpramBrojaStanovnikaINovcaUBudzetu(List<Drzava> lista) { ... }
	 * 		delimo primanja u budzetu sa brojem stanovnika da bismo vi-
	 * 		deli koja drzava daje najvise novca svojim gradjanima
	 * Map<String, Set<Drzava>> drzaveRazvrstanePoKontinentima(List<Drzava> lista) { ... }
	 * boolean postojiDrzavaSaViseStanovnikaOd(List<Drzava> lista, int broj) { ... }
	 * boolean postojiDrzavaKojaSeNalaziNaKontinentu(List<Drzava> lista, String kontinent) { ... }
	 * List<String> najbogatijeDrzave(List<Drzava> lista) { ... }
	 * 		najbogatije drzave su one koje imaju novca u budzetu za na-
	 * 		jvise 25% manje od trenutno najbogatije:
	 * 		primer - najbogatija je Nemacka sa 1 000 000 000 novca u b-
	 * 		udzetu: Ostale najbogatije ce biti one koje imaju >= 75% p-
	 * 		rimanja koliko i Nemacka
	 * List<Drzava> opadajuceSortiraniPoBudzetu(List<Drzava> lista) { ... }
	 * Drzava najsiromasnija(List<Drzava> lista) { ... }
	 * String najbogatijiKontinent(List<Drzava> lista) { ... }
	 */
	
	
	public static void main(String[] args) {
		
		List<Drzava> drzave = readFile("drzave.txt");
		
		System.out.printf("\nUkupno drzava : %d %n", brojDrzava(drzave));
		System.out.printf("\nProsecno bogate : %.2f %n", prosecnoBogate(drzave));
		System.out.printf("\nNajbogatija : %s %n", najbogatija(drzave).ime());
		System.out.printf("\nNajsiromasnija : %s %n", najsiromasnija(drzave).ime());
		
		System.out.println("\nSortirano po imenu : ");
		sortiraniPoImenu(drzave);
		System.out.println("\nSortirano po imenu (opadajuce) : ");
		sortiraniPoImenuOpadajuce(drzave);
		System.out.println("\nSortirano po broju stanovnika : ");
		sortiraniPoBrojuStanovnika(drzave);
		System.out.printf("\nProsecan broj stanovnika svih drzava : %.2f %n", prosecanBrojStanovnikaSvihDrzava(drzave));
		
		System.out.printf("\nNajbogatija drzava spram broja stanovnika i novca u budzetu : %s %n", 
				najbogatijaDrzavaSpramBrojaStanovnikaINovcaUBudzetu(drzave).ime());
		
		System.out.println("\nDrzave razvrstane po kontinentima : ");
		drzaveRazvrstanePoKontinentima(drzave).entrySet().stream()
				.forEach(e -> {
					System.out.printf("%s : ", e.getKey().toUpperCase());
					System.out.printf("%s %n", e.getValue().stream().map(Drzava::ime).reduce("", (v1, v2) -> "".equals(v1) ? v2 : v1 + ", " + v2));
				});
		
		System.out.printf("\nPostoji drzava sa vise stanovnika od 20,000,000 : %s %n", 
				postojiDrzavaSaViseStanovnikaOd(drzave, 20000000) ? "DA" : "NE");
		
		System.out.printf("\nPostoji drzava na kontinentu Antarktik : %s %n", 
				postojiDrzavaKojaSeNalaziNaKontinentu(drzave, "Antarktik") ? "DA" : "NE");
		
		System.out.printf("\nNajbogatije drzave : %s %n", 
				najbogatijeDrzave(drzave).stream().reduce("", (s1, s2) -> "".equals(s1) ? s2 : s1 + ", " + s2));
		
		System.out.println("\nOpadajuce sortirane drzave po budzetu : "); 
		opadajuceSortiraniPoBudzetu(drzave).stream().map(Drzava::ime).forEach(s -> System.out.println("  " + s));
		
		System.out.printf("\nNajbogatiji kontinent : %s %n", najbogatijiKontinent(drzave));
	}
	
	
	private static int brojDrzava(List<Drzava> drzave) { 
		return (int) drzave.stream().count();
	}
	
	
	private static double prosecnoBogate(List<Drzava> drzave) {
		return drzave.stream().mapToDouble(Drzava::budzet).average().getAsDouble();
	}
	
	
	private static Drzava najbogatija(List<Drzava> drzave) {
		return drzave.stream().max(Comparator.comparing(Drzava::budzet)).get();
	}
	
	
	private static Drzava najsiromasnija(List<Drzava> drzave) {
		return drzave.stream().min(Comparator.comparing(Drzava::budzet)).get();
	}
	
	
	private static void sortiraniPoImenu(List<Drzava> drzave) {
		drzave.stream().sorted(Comparator.comparing(Drzava::ime)).forEach(d -> System.out.printf("  %s %n", d.ime()));
	}
	
	
	private static void sortiraniPoImenuOpadajuce(List<Drzava> drzave) {
		drzave.stream().sorted(Comparator.comparing(Drzava::ime).reversed()).forEach(d -> System.out.printf("  %s %n", d.ime()));
	}
	
	
	private static void sortiraniPoBrojuStanovnika(List<Drzava> drzave) {
		drzave.stream().sorted(Comparator.comparing(Drzava::brStanovnika)).forEach(d -> System.out.printf("  %s %n", d.ime()));
	}
	
	
	private static double prosecanBrojStanovnikaSvihDrzava(List<Drzava> drzave) {
		return drzave.stream().mapToDouble(Drzava::brStanovnika).average().getAsDouble();
	}
	
	
	private static Drzava najbogatijaDrzavaSpramBrojaStanovnikaINovcaUBudzetu(List<Drzava> drzave) {
		return drzave.stream().reduce((d1, d2) -> d1.budzet() / d1.brStanovnika() > d2.budzet() / d2.brStanovnika() ? d1 : d2).get();
	}
	
	
	private static Map<String, Set<Drzava>> drzaveRazvrstanePoKontinentima(List<Drzava> drzave) {
		return drzave.stream().collect(Collectors.groupingBy(Drzava::ime, Collectors.toSet()));
	}
	
	
	private static boolean postojiDrzavaSaViseStanovnikaOd(List<Drzava> drzave, int broj) {
		return drzave.stream().anyMatch(d -> d.brStanovnika() > broj);
	}
	
	
	private static boolean postojiDrzavaKojaSeNalaziNaKontinentu(List<Drzava> drzave, String kontinent) {
		return drzave.stream().anyMatch(d -> d.kontinent().equalsIgnoreCase(kontinent));
	}
	
	
	private static List<String> najbogatijeDrzave(List<Drzava> drzave) {
		return drzave.stream().filter(d -> d.budzet() > najbogatija(drzave).budzet() * 0.75).map(Drzava::ime).collect(Collectors.toList());
	}
	
	
	private static List<Drzava> opadajuceSortiraniPoBudzetu(List<Drzava> drzave) {
		return drzave.stream().sorted(Comparator.comparing(Drzava::budzet)).collect(Collectors.toList());
	}
	
	
	private static String najbogatijiKontinent(List<Drzava> drzave) {
		
		Map<String, Double> m = drzave.stream().collect(
				Collectors.groupingBy(Drzava::kontinent, Collectors.summingDouble(Drzava::budzet)));
		
		return m.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey();
	}
	
	
	private static List<Drzava> readFile(String filename) {
		
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(
						DrzaveProgram.class.getResourceAsStream(filename)))
				) {
			
			List<Drzava> list = new ArrayList<Drzava>();
			String line;
			
			while ((line = in.readLine()) != null) list.add(Drzava.fromString(line));
			
			return list;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
