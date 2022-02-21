package vezba.kol1_drzave_p04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

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
		
		load().stream().forEach(System.out::println);
	}
	
	
	public static String najbogatijiKontinent(List<Drzava> lista) {
		
		return lista.stream()
				.collect(Collectors.groupingBy(
						Drzava::getKontinent, 
						Collectors.summingDouble(Drzava::getBudzet)))
				.entrySet().stream()
				.max(Map.Entry.comparingByValue())
				.get().getKey();
	}
	
	
	public static Drzava najsiromasnija(List<Drzava> lista) {
		return lista.stream().min(Comparator.comparing(Drzava::getBudzet)).orElse(null);
	}
	
	
	public static List<Drzava> opadajuceSortiraniPoBudzetu(List<Drzava> lista) {
		return lista.stream().sorted(Comparator.comparing(Drzava::getBudzet).reversed()).collect(Collectors.toList());
	}
	
	
	public static List<String> najbogatijeDrzave(List<Drzava> lista) {
		
		Drzava naj = lista.stream().max(Comparator.comparing(Drzava::getBudzet)).orElse(null);
		if (naj == null) return null;
		
		return lista.stream().filter(d -> d.getBudzet() > naj.getBudzet() * .75).map(Drzava::getIme).collect(Collectors.toList());
	}
	
	
	public static boolean postojiDrzavaKojaSeNalaziNaKontinentu(List<Drzava> lista, String kontinent) {
		return lista.stream().anyMatch(d -> d.getKontinent().equalsIgnoreCase(kontinent));
	}
	
	
	public static boolean postojiDrzavaSaViseStanovnikaOd(List<Drzava> lista, int broj) {
		return lista.stream().anyMatch(d -> d.getBrStanovnika() > broj);
	}
	
	
	public static Map<String, Set<Drzava>> drzaveRazvrstanePoKontinentima(List<Drzava> lista) {
		return lista.stream().collect(Collectors.groupingBy(Drzava::getKontinent, Collectors.toSet()));
	}
	
	
	public static Drzava najbogatijaDrzavaSpramBrojaStanovnikaINovcaUBudzetu(List<Drzava> lista) {
		return lista.stream().max(Comparator.comparingDouble(d -> 1.0 * d.getBudzet() / d.getBrStanovnika())).orElse(null);
	}
	
	
	public static double prosecanBrojStanovnikaSvihDrzava(List<Drzava> lista) {
		return lista.stream().collect(Collectors.averagingDouble(Drzava::getBrStanovnika));
	}
	
	
	public static void sortiraniPoBrojuStanovnika(List<Drzava> lista) {
		load().stream().sorted(Comparator.comparing(Drzava::getBrStanovnika)).forEach(System.out::println);
	}
	
	
	public static void sortiraniPoImenuOpadajuce(List<Drzava> lista) {
		load().stream().sorted(Comparator.comparing(Drzava::getIme).reversed()).forEach(System.out::println);
	}
	
	
	public static void sortiraniPoImenu(List<Drzava> lista) {
		load().stream().sorted(Comparator.comparing(Drzava::getIme)).forEach(System.out::println);
	}
	
	
	public static Drzava najbogatija(List<Drzava> lista) {
		return lista.stream().max(Comparator.comparing(Drzava::getBudzet)).orElse(null);
	}
	
	
	public static double prosecnoBogate(List<Drzava> lista) { 
		return lista.stream().collect(Collectors.averagingDouble(Drzava::getBudzet));
	}
	
	
	public static long brojDrzava(List<Drzava> lista) {
		return lista.stream().collect(Collectors.counting());
	}
	

	private static List<Drzava> load() {
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(DrzaveProgram.class.getResourceAsStream("drzave.txt")))) {
			
			String line;
			List<Drzava> drzave = new ArrayList<Drzava>();
			
			while ((line = in.readLine()) != null)
				drzave.add(Drzava.fromString(line));
			
			return drzave;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
