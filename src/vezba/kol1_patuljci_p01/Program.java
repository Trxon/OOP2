package vezba.kol1_patuljci_p01;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Stream;

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
		
		Stream<String> lines = Loader.load();
		Patuljci patuljci = new Patuljci();
		lines
			.map(s -> Patuljak.fromString(s))
			.forEach(patuljci::dodajPatuljka);
		
		System.out.printf("\nBroj patuljaka : %d %n", patuljci.brPatuljaka1());
		System.out.printf(  "Broj patuljaka : %d %n", patuljci.brPatuljaka2());
		
		System.out.printf("\nProsecno iskopali zlata : %.2f %n", patuljci.prosecnoIskopaliZlata1());
		System.out.printf(  "Prosecno iskopali zlata : %.2f %n", patuljci.prosecnoIskopaliZlata2());
		
		System.out.printf("\nPatuljci na slovo 'G' su iskopali zlata : %.2f %n", patuljci.ukupnoIskopaliZlataOniNaSlovo1('G'));
		System.out.printf(  "Patuljci na slovo 'G' su iskopali zlata : %.2f %n", patuljci.ukupnoIskopaliZlataOniNaSlovo2('G'));
		System.out.printf(  "Patuljci na slovo 'G' su iskopali zlata : %.2f %n", patuljci.ukupnoIskopaliZlataOniNaSlovo3('G'));
		System.out.printf(  "Patuljci na slovo 'G' su iskopali zlata : %.2f %n", patuljci.ukupnoIskopaliZlataOniNaSlovo4('G'));
		
		System.out.printf("\nNajbogatiji patuljak : %s (zlata : %.2f) %n", 
				patuljci.najbogatiji1().ime(), patuljci.najbogatiji1().iskopaoZlata());
		System.out.printf(  "Najbogatiji patuljak : %s (zlata : %.2f) %n", 
				patuljci.najbogatiji2().ime(), patuljci.najbogatiji2().iskopaoZlata());
		
		System.out.printf("\nPatuljci koji su iskopali vise od 100kg zlata su ukupno ubili goblina : %d %n", 
				patuljci.ukupnoUbiliGoblina(p -> p.iskopaoZlata() > 100.0));
		
		System.out.println("\nPatuljci sortirani opadajuce po broju ubijenih goblina : ");
		patuljci.opadajuceSortiraniPoBrojuUbijenihGoblina().stream()
				.forEach(p -> System.out.printf("  %s %d %n", p.ime(), p.ubioGoblina()));
		
		System.out.println("\nPatuljci rodjeni pre godine 1389 : ");
		patuljci.patuljciStarijiOd(1389).stream().forEach(p -> System.out.printf("  %s %d %n", p.ime(), p.godinaRodjenja()));
		
		System.out.println("\nImena patuljaka sortirana po prezimenu : ");
		patuljci.imenaPatuljakaRastuceSortiranaPoPrezimenu().stream().forEach(ime -> System.out.printf("  %s %n", ime));
		
		System.out.println("\nPatuljci razvrstani po godini rodjenja : ");
		patuljci.patuljciRazvrstaniPoGodiniRodjenja().entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey))
				.forEach(e -> System.out.printf("  [%4s] %s %n", e.getKey(), e.getValue()));
		
		System.out.println("\nPatuljci razvrstani po godini rodjenja : ");
		patuljci.patuljciRazvrstaniPoGodiniRodjenja().entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey))
				.forEach(e -> {
					System.out.printf("  [%4s] ", e.getKey());
					System.out.println(e.getValue().stream().map(Patuljak::ime).reduce("", (i1, i2) -> "".equals(i1) ? i2 : i1 + ", " + i2));
				});
		
		if (patuljci.postojiPatuljakSaViseZlataOd1(350.0)) 	System.out.print("\nP");
		else												System.out.print("\nNe p");
		System.out.println("ostoji patuljak koji je iskopao vise od 350.0kg zlata.");
	}
}
