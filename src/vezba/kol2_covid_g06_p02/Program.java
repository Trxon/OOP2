package vezba.kol2_covid_g06_p02;

/*
 * Zadatak
 * =======
 * Napisati klijent-server aplikaciju u Javi koja komunicira pomocu Socket-a i
 * prikazuje podatake o trenutnoj pandemiji.
 *
 * Server
 * ------
 * 
 * Pri pokretanju server ucitava podatke iz fajla sars-cov-2.json. Podaci su
 * dati u JSON formatu. Niz records sadrzi po jedan objekat za konkretan datum
 * i konkretnu zemlju, dok polja tog objekta sadrze konkretne podatke.
 * 
 * Za cuvanje podataka je potrebno napraviti klasu Zemlja koja sadrzi podatke o
 * imenu zemlje na engleskom jeziku, kontinentu na kojem se zemlja nalazi i
 * broju stanovnika. Takodje, potrebno je napraviti klasu Podatak koja cuva
 * podatke o broju registrovanih slucajeva, broj umrlih i datumu na koji se
 * podaci odnose.
 * 
 * Server ucitava podatke iz fajla i smesta ih u mapu. Kljucevi mape su imena
 * zemalja a vrednosti su liste instanci klase Podatak vezani za tu zemlju.
 *
 * Server potom kreira soket na portu 1919 i paralelno obradjuje sve klijentske
 * zahteve. 
 *
 * Komunikacija sa klijentom se ostvaruje na sledeci nacin: klijent posalje
 * serveru u jednoj liniji ime zemlje za koju zeli da dobavi podatke a server
 * zatim salje sve podatke iz liste podataka te zemlje, po jedan u svakom redu,
 * sortirano po datumu. Nakon zavrsetka slanja podataka iz liste server salje
 * prazan red klijentu kao indikaciju da je poslao sve podatke, posle cega
 * zatvara konekciju.
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
 * Klijent akumulira podatke koje mu server salje tako sto pamti i ispisuje
 * celu istoriju ukupnog broja zarazenih i umrlih od virusa. Klijent ispisuje
 * na konzolu datum, ukupan broj zarazenih do tog datuma i ukupan broj umrlih
 * od virusa do tog datuma. Klijent zavrsava sa radom nakon sto od servera 
 * dobije prazan red.
 */
public class Program {

}
