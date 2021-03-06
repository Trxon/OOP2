package vezba.kol2_covid_g02_p04;

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
 * Pri pokretanju, server ucitava podatke iz fajla sars-cov-2.json. Podaci su
 * dati u JSON formatu. Niz records sadrzi po jedan objekat za konkretan datum
 * i konkretnu zemlju, dok polja tog objekta sadrze konkretne podatke.
 * 
 * Za cuvanje podataka je potrebno napraviti klasu Zemlja koja sadrzi podatke o
 * imenu zemlje na engleskom jeziku, kontinentu na kojem se zemlja nalazi,
 * broju stanovnika, broju registrovanih slucajeva, broj umrlih i datumu na koji
 * se podaci odnose.
 * 
 * Server ucitava podatke iz fajla i smesta ih u mapu. Kljucevi mape su imena
 * kontinenata a vrednosti su liste zemalja na konkretnom kontinentu. U mapi se
 * za svaku zemlju cuvaju samo podaci za poslednji datum za koji postoje u fajlu.
 * 
 * Za pristup podacima, server implementira dva metoda koji su klijentu dostupni
 * preko Java RMI.
 * 
 * Metod List<String> kontinenti() vraca listu kontinenata za koje postoje
 * podaci.
 * 
 * Metod List<Zemlja> zemlje(String kontinent) vraca listu zemalja na zatadom
 * kontinentu. Ako u mapi ne postoje podaci za zadati kontinent metod vraca
 * praznu listu.
 * 
 * Klijent
 * -------
 * 
 * Klijentski program se po pokretanju odmah konektuje na server i trazi listu
 * kontinenata. Ako server nije dostupan, prikazuje se prigodna poruka o tome.
 * 
 * Potom, program prikazuje listu kontinenata korisniku i trazi da korisnik
 * izabere jedan od njih.
 * 
 * Na kraju, klijentska aplikacija prikazuje podatke za sve zemlje na izabranom
 * kontinentu, sortirane po procentu zarazenih. Od podataka je potrebno
 * prikazati ime zemlje, broj stanovnika, broj potvrdjenih slucajeva, broj
 * umrlih i procenat zarazenih u odnosu na ukupnu populaciju zemlje.
 */
public class Program {

}
