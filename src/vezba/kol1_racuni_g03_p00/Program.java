package vezba.kol1_racuni_g03_p00;

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
 * Dat je tok stringova u vidu metoda Racuni.jsonStream(), i u njemu
 * je svaki racun predstavljen jednim stringom u JSON formatu. Za d-
 * etalje o formatu pokrenuti program i pogledati kako izgleda svaki
 * od stringova.
 * 
 * Pretvoriti dati tok stringova u tok Racun objekata i ispisati ih.
 * 
 * Drugi deo (5 poena)
 * -------------------
 * 
 * Implementirati metod double prodatoSireva(int dan, int mesec, int
 * godina), pozvati ga u glavnom programu i ispisati rezultat.
 * 
 * Metod vraca koliko je ukupno kilograma svih vrsta mekih sireva p-
 * rodato zadatog dana. Meki sirevi su svi oni proizvodi koji na kr-
 * aju naziva imaju "sir rf".
 * 
 * Treci deo (5 poena)
 * -------------------
 * 
 * Implementirati metod List<String> razlicitiProizvodi(int dan, int
 * mesec, int godina), pozvati ga u glavnom programu i ispisati rez-
 * ultat.
 * 
 * Metod vraca nazive svih proizovda koji su prodati datog dana, so-
 * rtirane abecedno, bez ponavaljanja.
 * 
 * Cetvrti deo (5 poena)
 * ---------------------
 * 
 * Za svaki dan 2018. godine ispisati koliko je litara kozijeg mleka
 * prodato, na sledeci nacin:
 * 
 *     Datum | Prodato
 * ----------+----------
 *      2.1. |   0.40 l
 *      3.1. |  12.53 l
 *      4.1. |   0.00 l
 *          ...
 * 
 * Peti deo (5 poena)
 * ------------------
 * 
 * Pomocu operacije .reduce() generisati dnevni izvestaj za 1.5.2018
 * i smestiti ga u lokalnu promenljivu.
 * 
 * Dnevni izvestaj je izmisljen racun na kojem se nalaze bas sve st-
 * avke sa svih racuna zadatog dana. Redni broj ovakvog racuna je n-
 * ula, vreme izdavanja je podne zadatog dana, a uplaceno je nula d-
 * inara.
 * 
 * Ispisati stavke sa dnevnog izvestaja (naziv proizvoda, kolicinu i
 * ukupnu cenu) na sledeci nacin:
 * Mladi sir RF x 2.300 = 920.00
 * Namaz sa paprikom x 3 = 449.97
 * Kiselo mleko 500g x 1 = 59.99
 * ...
 * 
 */

public class Program {

	
	public static final int BROJ = 500;
	

	public static void main(String[] args) {

//		Racuni.jsonStream(1)
//				.forEach(System.out::println);

//		Racuni.racuniStream(5000)
//				.forEach(System.out::println);

		// prvi deo
//		List<Racun> racuni = load(Racuni.jsonStream(BROJ));
		
		// drugi deo
//		prodatoSireva(25, 4, 2018);
		
		// treci deo
//		System.out.println(razlicitiProizvodi(2, 1, 2018));
		
		// cetvrti deo
//		kozijegMleka(2018);
		
		// peti deo
//		izvestaj(25, 4, 2018);
	}
	
	
//	public static List<Racun> load(Stream<String> lines) {
//		
//	}
	
	
//	public static double prodatoSireva(int dan, int mesec, int godina) {
//		
//	}
	
	
//	public static List<String> razlicitiProizvodi(int dan, int mesec, int godina) {
//		
//	}
	
	
//	public static void kozijegMleka(int godina) {
//		
//	}
	
	
//	public static void izvestaj(int dan, int mesec, int godina) {
//		
//	}
}
