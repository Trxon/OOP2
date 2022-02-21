package vezba.kol1_fudbal_ass.postavka_g02;
/*
 * Zadatak
 * =======
 *
 * Napisati Java aplikaciju koja pomocu tokova podataka i lambda izraza
 * obradjuje i prikazuje podatke o fudbalskom prvenstvu.
 * 
 * Pri pokretanju, aplikacija ucitava podatke iz fajla 2018.txt. Podaci su dati
 * u obicnom tekstualnom formatu. Podaci o jednoj utakmici se nalaze u jednom
 * redu fajla. Na pocetku se nalazi redni broj utakmice na prvenstvu, slede
 * datum i vreme, pa imena zemalja ciji su timovi igrali, sa rezultatom izmedju.
 * U nastavku linije se nalazi lokacija gde je odigrana utakmica. Ako je bilo
 * golova, u narednoj liniji se nalaze podaci o strelcima na utakmici. Takodje,
 * utakmice su odvojene po grupama i fazama takmicenja.
 * 
 * Za cuvanje podataka je potrebno napraviti nabrojivi tip Grupa, koji pred-
 * stavlja grupe prve faze prvenstva: Grupa A, Grupa B, ... Grupa H, kao i gru-
 * pisanje utakmica u plej-of-u: osmina finala, cetvrtina finala, polufinale,
 * trece mesto, finale.
 * 
 * Takodje je potrebno implementirati klasu Strelac za predstavljanje podataka o
 * strelcima na utakmici, koja ima sledeca polja: ime stralca, minut u kojem je
 * postignut gol, boolean indikator da li u pitanju gol iz penala, i indikator
 * da li je u pitanju autogol.
 * 
 * Potom implementirati klasu Rezultat koja ima dva celobrojna polja u kojima se
 * nalazi broj golova na utakmici za svaki od timova.
 * 
 * Na kraju, implementirati klasu Utakmica koja sadrzi sve podatke o jednoj
 * utakmici: grupa, redni broj, datum, vreme, zemlja A, zemlja B, rezultat na
 * poluvremenu, rezultat po isteku vremena, rezultat posle produzetaka, rezultat
 * posle penala, lista strelaca za zemlju A, lista strelaca za zemlju B. Ako
 * nisu igrani produzeci ili penali posle produzetaka, odgovarajuci rezultati su
 * null.
 * 
 * Takodje implementirati metode equals(), hashCode() i toString() kako bi
 * instance ovih klasa mogle da se koriste u kolekcijama.
 * 
 * Podaci se ucitavaju iz fajla i smestaju u jednu veliku listu instanci klase
 * Utakmica. Potom se podaci iz ove liste obradjuju iskljucivo pomocu tokova
 * podataka i lambda izraza.
 * 
 * Implementirati metod int brojGolova() koji vraca ukupan broj golova na
 * prvenstvu.
 * 
 * Implementirati metod String najcesciStadion() koji vraca ime Stadiona i grada
 * u kojem se nalazi, a kojem se odigralo najvise utakmica.
 * 
 * Pozvati metode iz glavnog programa i ispisati rezultate.
 * 
 * Potom, tabelarno prikazati podatke o osvojenim poenima u grupnom delu
 * prvenstva. Za svaku grupu prikazati po jednu tabelu.
 * 
 * Svaki red tabele sadrzi podatke o jednoj zemlji, a od kolona sadrzi ime
 * zemlje i broj poena. Redovi u tabeli su sortirani po broju poena.
 * Broj poena se racuna na sledeci nacin: pobeda vredi 3 poena, neresen
 * rezultat nosi po jedan poen svakoj od zemalja, dok zemlja koja je izgubila
 * utakmicu ne dobija poene za tu utakmicu.
 */
public class Program {

}
