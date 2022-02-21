package vezba.kol1_patuljci_p02;

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
		
		Patuljci patuljci = new Patuljci(Loader.load("patuljci.txt"));
		patuljci.stampajSve();
		
		patuljci.brojPatuljaka();
		patuljci.prosecnoIskopaliZlata();
		patuljci.ukupnoIskopaliZlataOniNaSlovo('O');
		patuljci.najbogatiji();
		patuljci.ukupnoUbiliGoblina(p -> p.godinaRodjenja() > 1700, "mladji od 300 godina");
		patuljci.opadajuceSortiraniPoBrojuUbijenihGoblina();
		patuljci.patuljciStarijiOd(950);
		patuljci.imenaPatuljakaRastuceSortiranaPoPrezimenu();
		patuljci.patuljciRazvrstaniPoGodiniRodjenja();
		patuljci.postojiPatuljakSaViseZlataOd(500.0);
	}
}
