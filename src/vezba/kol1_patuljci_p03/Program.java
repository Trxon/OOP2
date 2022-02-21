package vezba.kol1_patuljci_p03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
 * Napisati program koji racuna statistike o patuljcima.
 * 
 * Podaci o svakom patuljku su dati u fajlu "patuljci.txt" za po j-
 * ednog patuljka u svakom redu. Klasa koja ucitava redove iz fajl-
 * a, kao i klasa koja predstavlja pautljka, su vec date.
 * 
 * Koristeci kolekcije, napraviti klasu koja sadrzi podatke o ucit-
 * anim patuljcima i koja pruza metod void dodaj(Patuljak) za doda-
 * vanje novog pautljka.
 * 
 * Nakon toga pomocu tokova podataka kreirati patuljke koristeci m-
 * etod fromString() i ubaciti ih sve u prethodnu klasu.
 * 
 * Na kraju, takodje pomocu tokova podataka, u datoj klasi impleme-
 * ntirati sledece metode i pozvati ih iz glavnog programa:
 * 
 *   int brPatuljaka()
 *   double prosecnoIskopaliZlata()
 *   double ukupnoIskopaliZlataOniNaSlovo(char)
 *   Patuljak najbogatiji()
 *   int ukupnoUbiliGoblina(Predicate<Patuljak>)
 *   List<Patuljak> opadajuceSortiraniPoBrojuUbijenihGoblina()
 *   Set<Patuljak> patuljciStarijiOd(int)
 *   List<String> imenaPatuljakaRastuceSortiranaPoPrezimenu()
 *   Map<Integer, Set<Patuljak>> patuljciRazvrstaniPoGodiniRodjenja()
 *   boolean postojiPatuljakSaViseZlataOd(double)
 */

public class Program {

	public static void main(String[] args) {
		
		List<Patuljak> patuljci = load();
	}
	
	
	private static long brPatuljaka() {
		return load().stream()
				.collect(Collectors.counting());
	}
	
	
	private static double prosecnoIskopaliZlata() {
		return load().stream()
				.collect(Collectors.averagingDouble(
						Patuljak::getIskopaoZlata));
	}
	
	
	private static double ukupnoIskopaliZlataOniNaSlovo(char c) {
		return load().stream()
				.filter(p -> p.getIme().charAt(0) == c)
				.collect(Collectors.summingDouble(
						Patuljak::getIskopaoZlata));
	}
	
	
	private static Patuljak najbogatiji() {
		return load().stream()
				.max(Comparator.comparing(Patuljak::getIskopaoZlata))
				.orElse(null);
	}
	
	
	private static int ukupnoUbiliGoblina(Predicate<Patuljak> predicate) {
		return load().stream()
				.filter(predicate)
				.collect(Collectors.summingInt(Patuljak::getUbioGoblina));
	}
	
	
	private static List<Patuljak> opadajuceSortiraniPoBrojuUbijenihGoblina() {
		return load().stream()
				.sorted(Comparator.comparingInt(Patuljak::getUbioGoblina).reversed())
				.collect(Collectors.toList());
	}
	
	
	private static Set<Patuljak> patuljciStarijiOd(int god) {
		return load().stream()
				.filter(p -> LocalDate.now().getYear() - p.getGodinaRodjenja() > god)
				.collect(Collectors.toSet());
	}
	
	
	private static List<String> imenaPatuljakaRastuceSortiranaPoPrezimenu() {
		
		class Data implements Comparable<Data> {
			String ime, prezime;

			@Override
			public int compareTo(Data o) {
				return prezime.compareTo(o.prezime);
			}
		}
		
		return load().stream()
				.map(p -> {
					
					Data d = new Data();
					
					String[] tokens = p.getIme().split(" ");
					
					if (tokens.length == 2) {
						d.ime = tokens[0];
						d.prezime = tokens[1];
					} else {
						d.ime = tokens[0];
						d.prezime = "";
					}
					
					return d;
				}).sorted().collect(Collectors.toList())
				.stream()
				.map(d -> String.format("%s %s", 
						d.ime, d.prezime))
				.collect(Collectors.toList());
	}
	
	
	private static Map<Integer, Set<Patuljak>> patuljciRazvrstaniPoGodiniRodjenja() {
		return load().stream()
				.collect(Collectors.groupingBy(
						Patuljak::getGodinaRodjenja, 
						Collectors.toSet()));
	}
	
	
	private static boolean postojiPatuljakSaViseZlataOd(double zlato) {
		return load().stream()
				.anyMatch(p -> p.getIskopaoZlata() > zlato);
	}
	

	private static List<Patuljak> load() {
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Program.class.getResourceAsStream("patuljci.txt")))) {
			
			String line;
			List<Patuljak> patuljci = new ArrayList<Patuljak>();
			
			while ((line = in.readLine()) != null) {
				
				String[] tokens = line.split(";");
				
				patuljci.add(new Patuljak(
						tokens[0].trim(), 
						Integer.parseInt(tokens[1].trim()), 
						Integer.parseInt(tokens[2].trim()), 
						Double.parseDouble(tokens[3].trim())));
			}
			
			return patuljci;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
