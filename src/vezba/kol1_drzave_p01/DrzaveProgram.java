package vezba.kol1_drzave_p01;

import java.util.stream.Stream;

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
	
	
	public static void main(String[] args) {
		
		Stream<String> lines = Loader.load();
		Drzave drzave = new Drzave();
		lines.map(s -> Drzava.fromString(s)).forEach(drzave::dodajDrzavu);
		
		System.out.printf("\nUkupno drzava : %d %n", drzave.brojDrzava());
		System.out.printf("\nProsecno bogate : %.2f %n", drzave.prosecnoBogate());
		System.out.printf("\nNajbogatija : %s %n", drzave.najbogatija().ime());
		System.out.printf("\nNajsiromasnija : %s %n", drzave.najsiromasnija().ime());
		
		System.out.println("\nSortirano po imenu : ");
		drzave.sortiraniPoImenu(false);
		System.out.println("\nSortirano po imenu (opadajuce) : ");
		drzave.sortiraniPoImenu(true );
		System.out.println("\nSortirano po broju stanovnika : ");
		drzave.sortiraniPoBrojuStanovnika();
		System.out.printf("\nProsecan broj stanovnika svih drzava : %.2f %n", drzave.prosecanBrojStanovnikaSvihDrzava());
		
		System.out.printf("\nNajbogatija drzava spram broja stanovnika i novca u budzetu : %s %n", 
				drzave.najbogatijaDrzavaSpramBrojaStanovnikaINovcaUBudzetu().ime());
		
		System.out.println("\nDrzave razvrstane po kontinentima : ");
		drzave.drzaveRazvrstanePoKontinentima().entrySet().stream()
				.forEach(e -> {
					System.out.printf("%s : ", e.getKey().toUpperCase());
					System.out.printf("%s %n", e.getValue().stream().map(Drzava::ime).reduce("", (v1, v2) -> "".equals(v1) ? v2 : v1 + ", " + v2));
				});
		
		System.out.printf("\nPostoji drzava sa vise stanovnika od 20,000,000 : %s %n", 
				drzave.postojiDrzavaSaViseStanovnikaOd(20000000) ? "DA" : "NE");
		
		System.out.printf("\nPostoji drzava na kontinentu Antarktik : %s %n", 
				drzave.postojiDrzavaKojaSeNalaziNaKontinentu("Antarktik") ? "DA" : "NE");
		
		System.out.printf("\nNajbogatije drzave : %s %n", 
				drzave.najbogatijeDrzave().stream().reduce("", (s1, s2) -> "".equals(s1) ? s2 : s1 + ", " + s2));
		
		System.out.println("\nOpadajuce sortirane drzave po budzetu : "); 
		drzave.opadajuceSortiraniPoBudzetu().stream().map(Drzava::ime).forEach(s -> System.out.println("  " + s));
		
		System.out.printf("\nNajbogatiji kontinent : %s %n", drzave.najbogatijiKontinent());
	}
}
