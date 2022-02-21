package vezba.kol2_covid_g04_p03;

/*
 * Zadatak
 * =======
 *
 * Napisati klijent-server aplikaciju u Javi koja komunicira pomocu soketa i
 * prikazuje podatake o trenutnoj pandemiji.
 * 
 * Server
 * ------
 * 
 * Pri pokretanju, server ucitava podatke iz fajla sars-cov-2.csv. Podaci su
 * dati u comma-separated values formatu. U prvom redu se nalaze imena kolona
 * dok svaki sledeci red sadrzi podatke za konkretan datum i konkretnu zemlju.
 * 
 * Server ucitava redove iz fajla i smesta ih u listu stringova. U ovoj listi
 * se ne nalazi prva linija koja sadrzi nazive kolona.
 * 
 * Server potom kreira soket na portu 1919 i paralelno obradjuje sve klijentske
 * zahteve.
 * 
 * Komunikacija sa klijentom se odvija na sledeci nacin: klijent salje serveru
 * jednu liniju u kojoj se nalazi oznaka (dvoslovna ili troslovna) ili ime
 * zemlje na engleskom jeziku. Server kao svoj odgovor vraca redom sve linije
 * iz liste koje se odnose na datu zemlju, posle cega salje jednu praznu liniju
 * i zatvara konekciju.
 * 
 * Klijent
 * -------
 * 
 * Po pokretanju, klijent trazi od korisnika da unese ime ili oznaku zemlje za
 * koju zeli podatke.
 * 
 * Potom se konektuje na server i od njega trazi zadate podatke po opisanom
 * protokolu.
 * 
 * Na kraju, klijentska aplikacija prikazuje ime zemlje, kontinent i broj
 * stanovnika, a potom za svaki datum u posebnom redu, sortirano po datumu,
 * aplikacija prikazuje datum, broj potvrdjenih slucajeva i broj umrlih.
 * 
 * Ako za trazenu zemlju ne postoje podaci ili dodje do problema u komunikaciji
 * sa serverom, prikazuje se prigodna poruka o tome.
 */
public class Program {

}
