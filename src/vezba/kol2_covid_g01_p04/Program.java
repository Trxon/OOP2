package vezba.kol2_covid_g01_p04;

/*
 * Zadatak
 * =======
 *
 * Napisati Java RMI klijent-server aplikaciju za prikaz podataka o trenutnoj
 * pandemiji.
 * 
 * Server
 * ------
 * 
 * Pri pokretanju, server ucitava podatke iz fajla sars-cov-2.csv. Podaci su
 * dati u comma-separated values formatu. U prvom redu se nalaze imena kolona
 * dok svaki sledeci red sadrzi podatke za konkretan datum i konkretnu zemlju.
 * 
 * Za cuvanje podataka je potrebno napraviti klasu Zemlja koja sadrzi podatke o
 * imenu zemlje na engleskom jeziku, dvoslovnoj i troslovnoj oznaci, kao i broju
 * stanovnika i kontinentu na kojem se zemlja nalazi. Takodje je potrebno
 * napraviti klasu Podatak koja sdrzi broj registrovanih slucajeva, broj umrlih,
 * datum i referencu na zemlju za koju se podatak odnosi. Implementirati metode
 * equals() i hashCode() kako bi instance ovih klasa mogle da se koriste u
 * kolekcijama.
 * 
 * Server ucitava podatke iz fajla i smesta ih u dve liste. Prva lista sadrzi
 * sve podatke iz fajla za koje kontinent nije "Other", dok druga lista sadrzi
 * sve zemlje na koje se podaci odnose. U listi ne cuvati duplikate, vec svaku
 * zemlju registrovati samo jednom.
 * 
 * Za pristup podacima, server implementira tri metoda koji su klijentu dostupni
 * preko Java RMI.
 * 
 * Metod List<Zemlja> zemlje() vraca listu zemalja za koje postoje podaci.
 * 
 * Metod List<LocalDate> datumi() vraca listu datuma za koje postoje podaci.
 * 
 * Metod Podatak podaci(Zemlja zemlja, LocalDate datum) vraca podatke za zadatu
 * zemlju i datum. Ako u listi na serveru ne postoje podaci za zadatu zemlju i
 * zadati datum, metod vraca null.
 * 
 * Klijent
 * -------
 * 
 * Klijentski program se po pokretanju odmah konektuje na server i trazi listu
 * zemalja i datuma. Ako server nije dostupan, prikazuje se prigodna poruka o
 * tome.
 * 
 * Potom, program prikazuje listu zemalja korisniku i trazi da korisnik izabere
 * jednu od njih. Zemlja se moze izabrati nazivom, ili jednom od oznaka. Potom,
 * prikazuje listu datuma i trazi da korisnik izabere jedan od datuma sa liste.
 * 
 * Na kraju, klijentska aplikacija prikazuje ime zemlje, broj stanovnika, broj
 * potvrdjenih slucajeva i broj umrlih za izabrani datum. Ako podaci ne postoje
 * na serveru, ispisuje se prigodna poruka.
 */
public class Program {

}
