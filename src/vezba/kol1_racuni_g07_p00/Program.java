package vezba.kol1_racuni_g07_p00;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
 * je svaki racun predstavljen jednim stringom bas onako kako bi bio
 * odstampan na fiskalnoj kasi. Za detalje o formatu pokrenuti prog-
 * ram i pogledati kako izgleda svaki od stringova.
 * 
 * Pretvoriti dati tok stringova u tok Racun objekata i ispisati ih.
 * 
 * Drugi deo (5 poena)
 * -------------------
 * 
 * Implementirati sledeci metod: boolean prodataPaprika(int dan, int
 * mesec, int godina), pozvati ga u glavnom programu i ispisati rez-
 * ultat.
 * 
 * Dati metod utvrdjuje da li je zadatog dana prodata paprika u pav-
 * laci, bilo ljuta ili ne.
 * 
 * Treci deo (5 poena)
 * -------------------
 * 
 * Implementirati metod List<Double> kusuri() i pozvati ga u glavnom
 * programu i ispisati rezultat.
 * 
 * Metod treba da vraca listu u kojoj se za svaki racun iz toka nal-
 * azi kusur koji je trebalo vratiti za taj racun.
 * 
 * 
 * Cetvrti deo (5 poena)
 * ---------------------
 * 
 * Za svaki mesec 2018. godine ispisati ukupno ostvarenu zaradu pro-
 * date robe, na sledeci nacin:
 * 
 *      | Prodato
 * -----+----------
 *  Jan | 167348.52
 *  Feb | 104300.31
 *  Mar | 274560.04
 *     ...
 * 
 * Peti deo (5 poena)
 * ------------------
 * 
 * Za svaki dan u mesecu martu 2019. godine, ispisati koliko je pak-
 * ovanja kojeg kiselog mleka prodato, u tabelarnom obliku na slede-
 * ci nacin:
 * 
 * Dan       | Kiselo mleko 500g | Kiselo mleko | Ekstra kiselo mleko
 * ==================================================================
 *  01. mart |               125 |          532 |                  2
 *  02. mart |                12 |          125 |               1246
 *  ...
 * 
 */

public class Program {
	
	
	public static final int BROJ = 5000;
	

	public static void main(String[] args) {
		
		Racuni.stringStream(BROJ)
			.forEach(System.out::println);
		
		Racuni.racuniStream(BROJ)
			.forEach(System.out::println);
	}
}
