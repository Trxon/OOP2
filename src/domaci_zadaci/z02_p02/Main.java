package domaci_zadaci.z02_p02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import domaci_zadaci.z02_p02.raspored.Raspored;
import domaci_zadaci.z02_p02.time.ICalendar;

/*
 * Napisati program koji ispisuje raspored casova.
 *
 * 1) Napraviti klasu "Cas" koja predstavlja jedan cas. Klasa od o-
 *    sobina treba da ima bar naziv predmeta, ime predavaca, tip c-
 *    asa, vreme i mesto odrzavanja. Takodje, ova klasa implementi-
 *    ra interfejs Comparable kao i metode equals(), hashCode(), t-
 *    oString().
 *
 * 2) Napraviti nabrojivi tip "Dan" koji predstavlja dane u nedelji.
 *
 * 3) Napraviti klasu Raspored koja sadrzi mapu ciji su kljucevi da-
 *    ni u nedelji a vrednosti liste casova koji se odrzavaju tog d-
 *    ana. Ove liste su sortirane po vremenu pocetka casa, a takodje
 *    implementirati i potrebne metode za pristup i izmenu podataka.
 *
 * 4) Pitati korisnika za koju godinu IT smera zeli raspored. Ident-
 *    ifikatori Google kalendara za odgovarajuce godine su:
 *        1 godina: g3khre7jrsih1idp5b5ahgm1f8
 *        2 godina: hu93vkklcv692mikqvm17scnv4
 *        3 godina: 6ovsf0fqb19q10s271b9e59ttc
 *        4 godina: a730slcmbr9c6dii94j9pbdir4
 *
 * 5) Ucitati odgovarajuci raspored casova, izvuci neophodne podatke
 *    u instance klase "Cas" i smestiti ih u jednu instancu klase R-
 *    aspored.
 *
 * 6) Pitati korisnika za koji dan u nedelji zeli raspored i ispisa-
 *    ti casove koji se odrzavaju tog dana.
 *
 * Metod za ucitavanje rasporeda sa interneta je vec dat, kao i kako
 * treba da izgleda URL za Google kalendar sa datim identifikatorom. 
 */

public class Main {
	
	
    /***************************
     * NIKOLA VETNIC 438/19 IT *
     ***************************/
	
	
	private static Raspored raspored;
	
	
	public static void main(String[] args) throws IOException {
		
		try (
				Scanner sc = new Scanner(System.in);
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		) {
			
			raspored = ICalendar.parseCalendar(Util.getCalendar(Util.getId(sc)));
			raspored.stampajZaDan(Util.getDan(sc));
			
		} catch (IOException e) {
			System.err.println("Greska prilikom citanja fajla -> " + e.getMessage());
		}
	}
}
