package vezba.kol1_fudbal_p00;


/*
 * Zadatak
 * =======
 * 
 * Napisati Java aplikaciju koja pomocu tokova podataka i lambda i-
 * zraza obradjuje i prikazuje podatke o fudbalskom prvenstvu.
 * 
 * Pri pokretanju, aplikacija ucitava podatke iz fajla 2018.txt. P-
 * odaci su dati u obicnom tekstualnom formatu. Podaci o jednoj ut-
 * akmici se nalaze u jednom redu fajla. Na pocetku se nalazi redni
 * broj utakmice na prvenstvu, slede datum i vreme, pa imena zemal-
 * ja ciji su timovi igrali, sa rezultatom izmedju. U nastavku lin-
 * ije se nalazi lokacija gde je odigrana utakmica. Ako je bilo go-
 * lova, u narednoj liniji se nalaze podaci o strelcima na utakmici.
 * Takodje, utakmice su odvojene po grupama i fazama takmicenja.
 * 
 * Za cuvanje podataka je potrebno napraviti nabrojivi tip Grupa, k-
 * oji predstavlja grupe prve faze prvenstva: Grupa A, Grupa B... G-
 * rupa H, kao i grupisanje utakmica u plej-of-u: osmina finala, ce-
 * tvrtina finala, polufinale, trece mesto, finale.
 * 
 * Takodje je potrebno implementirati klasu Strelac za predstavljan-
 * je podataka o strelcima na utakmici, i koja ima sledeca polja: i-
 * me stralca, minut u kojem je postignut gol, zatim boolean indika-
 * tor da li u pitanju gol iz penala, i indikator da li je u pitanju
 * autogol.
 * 
 * Potom implementirati klasu Rezultat koja ima dva celobrojna polja
 * u kojima se nalazi broj golova na utakmici za svaki od timova.
 * 
 * Na kraju, implementirati klasu Utakmica koja sadrzi sve podatke o
 * jednoj utakmici: grupa, redni broj, datum, vreme, zemlja A, zeml-
 * ja B, rezultat na poluvremenu, rezultat po isteku vremena, rezul-
 * tat posle produzetaka, rezultat posle penala, zatim lista strela-
 * ca za zemlju A, lista strelaca za zemlju B. Ako nisu igrani prod-
 * uzeci ili penali posle produzetaka, odgovarajuci rezultati su nu-
 * ll.
 * 
 * Takodje implementirati metode equals(), hashCode(), toString() k-
 * ako bi instance ovih klasa mogle da se koriste u kolekcijama.
 * 
 * Podaci se ucitavaju iz fajla i smestaju u jednu veliku listu ins-
 * tanci klase Utakmica. Potom se podaci iz ove liste obradjuju isk-
 * ljucivo pomocu tokova podataka i lambda izraza.
 * 
 * Implementirati metod String pobednik() koji vraca ime zemlje koja
 * je pobedila na prvenstvu.
 * 
 * Implementirati metod String najviseGolova() koji vraca ime igraca
 * koji je dao najvise golova ukupno na celom prvenstvu.
 * 
 * Pozvati metode iz glavnog programa i ispisati rezultate.
 * 
 * Potom tabelarno prikazati podatke o svim golovima na prvenstvu s-
 * ortirane po datumu i vremenu kada je gol dat.
 * 
 * Svaki red tabele sadrzi podatke o jednom golu. Sve kolone su red-
 * om: datum kada je gol dat, tacno vreme u koje je dat gol, ime ig-
 * raca koji je dao gol, i ime zemlje za koju je gol dat. Treba obr-
 * atiti paznju da brojevi budu uredno poravnati, i takodje prikaza-
 * ti zaglavlje sa imenima kolona.
 * 
 * Implementirati metod int brojGolova() koji vraca ukupan broj gol-
 * ova na prvenstvu.
 * 
 * Implementirati metod String najcesciStadion() koji vraca ime Sta-
 * diona i grada u kojem se nalazi, a kojem se odigralo najvise uta-
 * kmica.
 * 
 * Potom, tabelarno prikazati podatke o osvojenim poenima u grupnome
 * delu prvenstva. Za svaku grupu prikazati po jednu tabelu.
 * 
 * Svaki red tabele sadrzi podatke o jednoj zemlji, a od kolona sad-
 * rzi ime zemlje i broj poena. Redovi u tabeli su sortirani po bro-
 * ju poena. Broj poena se racuna na sledeci nacin: pobeda vredi tri
 * poena, neresen rezultat nosi po jedan poen svakoj od zemalja, dok
 * zemlja koja je izgubila utakmicu ne dobija poene za tu utakmicu.
 */


public class Program {

}