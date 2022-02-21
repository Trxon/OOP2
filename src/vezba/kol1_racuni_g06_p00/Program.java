package vezba.kol1_racuni_g06_p00;

/*
 * Prvi kolokvijum
 * ===============
 * 
 * Napisati Java aplikaciju koja pomocu tokova podataka i lambda i-
 * zraza obradjuje sve podatke o fiskalnim racunima izdatim od str-
 * ane jedne mlekare.
 * 
 * Data je klasa Racun kojom se prestavljaju fiskalni racuni. Svaki
 * racun ima svoj redni broj, datum i vreme kada je izdat, listu s-
 * tavki na racunu, kao i koliko je gotovine uplaceno kada je racun
 * placen.
 * 
 * Data je i klasa Stavka cije instance predstavljaju stavke racuna,
 * a svaka stavka se sastoji od imena proizvoda, kolicine tog proiz-
 * voda, cene po jedinici mere i poreske stope (predstavljene nabro-
 * jivim tipom).
 * 
 * Prvi deo (5 poena)
 * ------------------
 * 
 * Dat je tok stringova u vidu metoda Racuni.stringStream(). U njemu
 * je svaki racun predstavljen jednim stringom u JSON formatu. Za d-
 * etalje o formatu pokrenuti program i pogledati kako izgleda svaki
 * od stringova.
 * 
 * Pretvoriti dati tok stringova u tok Racun objekata i ispisati ih.
 * 
 * Drugi deo (5 poena)
 * -------------------
 * 
 * Implementirati metod: 
 * 		double prodatoMleka(int dan, int mesec, int godina),
 * pozvati ga u glavnom programu i ispisati rezultat.
 * 
 * Metod vraca koliko je ukupno zaradjeno novca od prodaje svih vrs-
 * ta mleka zadatog dana. Mleka su oni proizvodi koji na pocetku na-
 * ziva imaju "mleko".
 * 
 * Treci deo (5 poena)
 * -------------------
 * 
 * Implementirati metod 
 * 		List<LocalDate> zlatiborskiKajmakNaAkciji(),
 * pozvati ga u glavnom programu i ispisati rezultat.
 * 
 * Metod vraca listu datuma kada je Zlatiborski kajmak prodat dok je
 * bio na popustu. Kajmak je na popustu ako je prodat po ceni manjoj
 * od maksimalne ikada.
 * 
 * Cetvrti deo (5 poena)
 * --------------------
 *
 * Za svaki mesec iz 2018. godine ispisati ukupan broj izdatih racu-
 * na koji sadrze rinfuznu robu, na sledeci nacin:
 * 
 *      | Izdato
 * -----+--------
 *  Jan |    673
 *  Feb |   1043
 *  Mar |    274
 *     ...
 * 
 * Rinfuzna roba su oni proizvodi koji na kraju imena imaju "RF".
 * 
 * Peti deo (5 poena)
 * ------------------
 * 
 * Za svaki namaz ispisati njegovu najnizu, najvisu bas kao i prose-
 * cnu cenu po jedinici mere (komad, kilogram, litar...), u tabelar-
 * nom obliku na sledeci nacin:
 * 
 * | Namaz             |    Min |    Max |    Avg |
 * |===================|========|========|========|
 * | Sirni namaz       |  89.99 | 119.99 | 118.88 |
 * | Namaz sa paprikom | 109.99 | 149.99 | 111.58 |
 * | Namaz sa renom    | 149.99 | 149.99 | 149.99 |
 * | Mali namaz        |  89.99 |  99.99 |  97.34 |
 * | ...
 * 
 * Namazi su oni proizvodi koji u nazivu imaju "namaz".
 * 
 */

public class Program {

	public static void main(String[] args) {

		Racuni.stringStream(5000)
				.forEach(System.out::println);

		Racuni.racuniStream(5000)
				.forEach(System.out::println);

	}
}
