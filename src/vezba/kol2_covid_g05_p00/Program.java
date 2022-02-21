package vezba.kol2_covid_g05_p00;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/*
 * Zadatak
 * =======
 *
 * Napisati klijent-server aplikaciju u Javi koja komunicira pomocu RMI i
 * prikazuje podatake o trenutnoj pandemiji.
 * 
 * Server
 * ------
 * 
 * Pri pokretanju, server ucitava podatke iz fajla sars-cov-2.csv. Podaci su
 * dati u comma-separated values formatu. U prvom redu se nalaze imena kolona
 * dok svaki sledeci red sadrzi podatke za konkretan datum i konkretnu zemlju.

 * Za cuvanje podataka je potrebno napraviti klasu Zemlja koja sadrzi podatke o
 * imenu zemlje na engleskom jeziku, dvoslovnoj i troslovnoj oznaci, kao i broju
 * stanovnika i kontinentu na kojem se zemlja nalazi. Klasa takodje sadrzi polja
 * koja cuvaju podatke o broju registrovanih slucajeva, broju umrlih, i datum. 
 * Implementirati metode equals() i hashCode() kako bi instance ove klase mogle 
 * da se koriste u kolekcijama.
 * 
 * Dat je metod koji ucitava redove iz fajla i pruza ih kao tok linija.
 * 
 * Server na pocetku parsira ovaj tok linija i pretvara ga u tok Zemlja objekata
 * cije elemente potom smesta u listu. Podaci se smestaju u jednu veliku listu
 * instanci klase Zemlja, ako za kontinent nemaju vrednost "Other". Potom se
 * podaci iz ove liste obradjuju iskljucivo pomocu tokova podataka i lambda
 * izraza.
 * 
 * Na serveru implementirati metod Zemlja najviseUmrlihNaKontinentu(String kontinent)
 * koja za prosledjeni naziv kontinenta vraca zemlju sa najvese preminulih osoba.
 *
 * Takodje, implementirati metod Map<LocalDate, List<Zemlja>> novozarazeneNaDan() 
 * koja za svaki datum u bazi podataka kreira listu koja sadrzi sve zemlje ciji
 * je prvi slucaj zaraze registrovan na taj dan.
 *
 * Implementirati metod String tabelarniPrikaz() koji vraca tabelu u obliku
 * stringa cije vrste prestavljaju kontinente, kolone intervale u kojima se moze
 * naci odnos ukupnog broja umrlih i ukupnog broja zarazenih osoba neke drzave.
 * Same vrednosti u tabeli su broj zemalja odredjenog kontinenta ciji odnos
 * umrlih i zarazenih pripada intervalu.
 * 
 * Primer:
 *
 * -----------------------------------
 * Continent | 0 - 1% | 1 - 3% | >3% |
 * -----------------------------------
 * Europe    |      2 |      7 |   0 |
 * -----------------------------------
 *               .
 *               .
 *               . 
 * 
 * Komunikacija sa klijentom se odvija na sledeci nacin: klijent moze da pozove 
 * bilo koju serverovu metodu najviseUmrlihNaKontinentu(String kontinent),
 * novozarazeneNaDan() ili tabelarniPrikaz().
 * 
 * Klijent
 * -------
 * 
 * Po pokretanju, klijent trazi od korisnika da unese kontinent za koji zeli
 * tabelarni prikaz i potom se konektuje na server.
 * 
 * Potom poziva svaki od tri dostupna metoda i prikazuje dobijene podatke
 * korisniku.
 * 
 * Pri pozivu metode najviseUmrlihNaKontinentu(String kontinent) ukoliko za
 * uneti kontinent ne postoje podaci ili dodje do problema u komunikaciji
 * sa serverom, prikazuje se prigodna poruka o tome.
 */
public class Program {

	public static Stream<String> linije() throws IOException {
		return Files.lines(Paths.get("/Users/nikolavetnic/Library/Mobile Documents/com~apple~CloudDocs/Documents/EclipseWorkspace/OOP2/src/vezba/kol2_covid_g05_p00/sars-cov-2.csv"));
	}
}
