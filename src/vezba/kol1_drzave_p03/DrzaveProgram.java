package vezba.kol1_drzave_p03;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DrzaveProgram {

	
	/*
	 * Klasa Drzava opisuje jednu drzavu sa sledecim atributima:
	 * - private String ime
	 * - private String kontinent
	 * - private int brStanovnika
	 * - private double budzet
	 * 
	 * Takodje u klasi Drzava implementirati metod:
	 * public static Drzava fromString(String drzava) { ... }
	 * 
	 * Podaci o jednoj drzavi nalaze se u tekstualnom fajlu "drzave.tx-
	 * t". Svi podaci o jednoj drzavi nalaze se u jednom redu i odvoje-
	 * ni su znakom ; . Ucitati podatke o drzavi i smestiti ih u kolek-
	 * ciju List<Drzava>. Nakon toga, koristeci tokove podataka implem-
	 * entirati i sledece metode:
	 * 
	 * int brojDrzava(List<Drzava> lista) { ... }
	 * double prosecnoBogate(List<Drzava> lista) { ... }
	 * najbogatija(List<Drzava> lista) { ... }
	 * sortiraniPoImenu(List<Drzava> lista) { ... }
	 * sortiraniPoImenuOpadajuce(List<Drzava> lista) { ... }
	 * sortiraniPoBrojuStanovnika(List<Drzava> lista) { ... }
	 * double prosecanBrojStanovnikaSvihDrzava(List<Drzava> lista) { ... }
	 * najbogatijaDrzavaSpramBrojaStanovnikaINovcaUBudzetu(List<Drzava> lista) { ... }
	 * 		delimo primanja u budzetu sa brojem stanovnika da bismo vi-
	 * 		deli koja drzava daje najvise novca svojim gradjanima
	 * Map<String, Set<Drzava>> drzaveRazvrstanePoKontinentima(List<Drzava> lista) { ... }
	 * boolean postojiDrzavaSaViseStanovnikaOd(List<Drzava> lista, int broj) { ... }
	 * boolean postojiDrzavaKojaSeNalaziNaKontinentu(List<Drzava> lista, String kontinent) { ... }
	 * List<String> najbogatijeDrzave(List<Drzava> lista) { ... }
	 * 		najbogatije drzave su one koje imaju novca u budzetu za na-
	 * 		jvise 25% manje od trenutno najbogatije:
	 * 		primer - najbogatija je Nemacka sa 1 000 000 000 novca u b-
	 * 		udzetu: Ostale najbogatije ce biti one koje imaju >= 75% p-
	 * 		rimanja koliko i Nemacka
	 * List<Drzava> opadajuceSortiraniPoBudzetu(List<Drzava> lista) { ... }
	 * Drzava najsiromasnija(List<Drzava> lista) { ... }
	 * String najbogatijiKontinent(List<Drzava> lista) { ... }
	 */
	
	
	private static Pattern PATTERN_LINE = Pattern.compile("^(?<zemlja>[^;]+)\\;(?<kontinent>[^;]+);(?<stanovnici>[^;]+);(?<budzet>[\\s\\S]+?)$"); 
	

	public static void main(String[] args) {
		
		String text = Loader.load("drzave.txt");
		
		Drzave drzave = new Drzave();
		drzave.setDrzave(readTextFileAlt(text));
		
		drzave.brojDrzava();
		drzave.prosecnoBogate();
		drzave.najbogatija();
		drzave.najsiromasnija();
		drzave.sortiraniPoImenu(false);
		drzave.sortiraniPoImenu(true );
		drzave.sortiraniPoBrojuStanovnika(false);
		drzave.sortiraniPoBrojuStanovnika(true );
		drzave.prosecanBrojStanovnikaSvihDrzava();
		drzave.najbogatijaDrzavaSpramBrojaStanovnikaINovcaUBudzetu();
		
		Map<String, Set<Drzava>> poKontinentima = drzave.drzaveRazvrstanePoKontinentima();
		
		drzave.postojiDrzavaSaViseStanovnikaOd(500000000);
		drzave.postojiDrzavaKojaSeNalaziNaKontinentu("Antarktik");
		
		List<String> najbogatije = drzave.najbogatijeDrzave();
		
		List<Drzava> poBudzetu = drzave.opadajuceSortiraniPoBudzetu();
		
		drzave.najbogatijiKontinent();
	}


	private static List<Drzava> readTextFile(String text) {
		
		List<Drzava> list = new ArrayList<Drzava>();
		String[] lines = text.split("\n");
		
		for (String line : lines) {
			
			Matcher matcherLine = PATTERN_LINE.matcher(line);
			
			if (matcherLine.find()) {
				
				Drzava d = new Drzava();
				
				d.setZemlja(matcherLine.group("zemlja"));
				d.setKontinent(matcherLine.group("kontinent"));
				d.setBrojStanovnika(matcherLine.group("stanovnici"));
				d.setBudzet(matcherLine.group("budzet"));
				
				list.add(d);
			}
		}
		
		return list;
	}
	
	
	private static List<Drzava> readTextFileAlt(String text) {
		
		List<Drzava> list = new ArrayList<Drzava>();
		String[] lines = text.split("\n");
		
		for (String line : lines)
			list.add(Drzava.fromString(line));
		
		return list;
	}
}
