package vezba.kol2_covid_g03_p03;

/*
 * Zadatak
 * =======
 * 
 * Napisati Java aplikaciju koja pomocu tokova podataka i lambda izraza
 * obradjuje i prikazuje podatke o trenutnoj pandemiji.
 * 
 * Pri pokretanju, aplikacija ucitava podatke iz fajla sars-cov-2.xml. Podaci su
 * dati u xml formatu. Svaki record element sadrzi podatke za jedan konkretan
 * datum i jednu konkretnu zemlju, dok podelementi sadrze odgovarajuce podatke.
 * 
 * Za cuvanje podataka je potrebno napraviti klasu Zemlja koja sadrzi podatke o
 * imenu zemlje na engleskom jeziku, dvoslovnoj i troslovnoj oznaci, kao i broju
 * stanovnika i kontinentu na kojem se zemlja nalazi. Takodje je potrebno
 * napraviti klasu Podatak koja sadrzi broj registrovanih slucajeva, broj
 * umrlih, datum i referencu na zemlju za koju se podatak odnosi. Implementirati
 * metode equals() i hashCode() kako bi instance ovih klasa mogle da se koriste
 * u kolekcijama.
 * 
 * Podaci se ucitavaju iz fajla i smestaju u jednu veliku listu instanci
 * klase Podatak, ako za kontinent nemaju vrednost "Other". Potom se podaci iz
 * ove liste obradjuju iskljucivo pomocu tokova podataka i lambda izraza.
 * 
 * Implementirati metod Zemlja najkriticnijaZemlja() koji vraca zemlju sa do
 * danas najvecim procentom potvrdjenih slucajeva u odnosu na ukupno
 * stanovnistvo zemlje.
 * 
 * Pozvati metod iz glavnog programa i ispisati rezultat.
 * 
 * Implementirati metod List<Zemlja> zemljeSaNiskomSmrtnoscu(String kontinent)
 * koji vraca listu zemalja koje imaju manje od pola procenta umrlih u odnosu
 * na broj potvrdjenih slucajeva. Za statistiku uzeti podatke od 1. juna ove
 * godine, i sortirati listu rastuce po procentu. 
 * 
 * U glavnom programu pitati korisnika za kontinent, pozvati metod i ispisati
 * imena zemalja.
 * 
 * Za isti kontinent prikazati tabelarno sledece podatke, takodje sortirane na
 * isti nacin kao u prethodnom slucaju.
 * 
 * Svaki red tabele sadrzi podatke o jednoj zemlji. Kolone tabele su redom:
 * ime zemlje, broj stanovnika, broj potvrdjenih slucajeva, broj umrlih,
 * procenat umrlih, datum prvog registrovanog slucaja. Obratiti paznju da
 * brojevi budu uredno poravnati, i takodje prikazati zaglavlje sa imenima
 * kolona.
 */

public class Program {
	
	
	
}
