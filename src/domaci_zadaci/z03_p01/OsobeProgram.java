package domaci_zadaci.z03_p01;

import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import domaci_zadaci.z03_p01.Osoba.Pol;

/*
 * Dat je tok osoba u vidu metoda Osobe.osobeStream(). Pomocu dato-
 * g metoda, lambda izraza i operacija nad tokovima podataka imple-
 * mentirati sledece metode:
 * 
 * svi() - stampa id, ime i prezime za sve osobe
 * 
 * punoDece() - stampa ime i prezime svih osoba sa vise od dvoje d-
 * ece
 *
 * istoMesto() - stampa sortirano po imenu mesta podatke za sve os-
 * obe koje zive u istom mestu u kojem su rodjeni, na sledeci naci-
 * n: Ime Prezime (Mesto)
 * 
 * bogateBezDece() - stampa podatke o zenama sa platom preko 75.000
 * koje nemaju dece, sortirano opadajuce po plati, na sledeci naci-
 * n: Ime Prezime (Primanja)
 * 
 * rodjendani() - stampa podatke o osobama koje slave rodjendan ov-
 * og meseca. Stampati dan i mesec, ime i prezime, kao i koliko go-
 * dina osoba puni, na sledeci nacin: DD. MM. Ime Prezime (Puni)
 * 
 * odrasli(String prezime) - stampa ukupan broj osoba sa datim pre-
 * zimenom.
 * 
 * ukupnoDece() - stampa ukupan broj dece svih osoba.
 * 
 * zaPaketice() - stampa imena, prezimena i starost dece koja su d-
 * obila paketice 2010. godine, a uslov za dobijanje paketica je da
 * dete ne bude starije od 10 godina.
 * 
 * imenaPenzionera() - stampa sva razlicita imena penzionera, sort-
 * irana abecedno. Penzioneri su osobe koje imaju preko 65 godina.
 * 
 * procenat() - stampa procenat muskih osoba, ukljucujuci i decu.
 * 
 * trecaPoRedu() - stampa osobu koja je treca po broju muske dece.
 * 
 * najbogatiji(String grad) - stampa ime (bez prezimena), kao i vi-
 * sinu primanja i mesto rodjenja za osobe sa najvecim primanjima i
 * za zadato mesto stanovanja.
 * 
 * josBogatiji(String grad) - stampa podatke za osobe koje imaju v-
 * eca primanja od najbogatije osobe iz zadatog mesta, i to na sle-
 * deci nacin: Ime (primanja) Mesto stanovanja
 */
public class OsobeProgram {
	
	
    /***************************
     * NIKOLA VETNIC 438/19 IT *
     ***************************/
	

	public static void main(String[] args) {
//		svi();
//		punoDece();
//		istoMesto();
//		bogateBezDece();
//		rodjendani();
//		odrasli("Milic");
//		ukupnoDece();
//		zaPaketice();
//		imenaPenzionera();
//		procenatViseProlaza();
//		procenatJedanProlaz();
//		trecaPoRedu();
//		najbogatiji("Zrenjanin");
//		josBogatiji("Zrenjanin");
	}
	

	private static void svi() {
		Osobe.osobeStream(5000)
			.forEach(System.out::println);
	}
	
	
	private static void punoDece() {
		Osobe.osobeStream(5000)
			.filter(o -> o.getDeca().size() > 2)
			.forEach(System.out::println);
	}
	
	
	private static void istoMesto() {
		Osobe.osobeStream(5000)
			.filter(o -> o.getMestoRodjenja().equalsIgnoreCase(o.getMestoStanovanja()))
			.sorted(Comparator.comparing(Osoba::getMestoStanovanja))
			.forEach(o -> System.out.printf("%s %s (%s) %n", o.getIme(), o.getPrezime(), o.getMestoStanovanja()));
	}
	
	
	private static void bogateBezDece() {
		Osobe.osobeStream(5000)
			.filter(o -> o.getPol() == Pol.ZENSKI && o.getPrimanja() > 75000 && o.getDeca().size() == 0)
			.sorted(Comparator.comparing(Osoba::getPrimanja).reversed())
			.forEach(o -> System.out.printf("%s %s (%d) %n", o.getIme(), o.getPrezime(), o.getPrimanja()));
	}
	
	
	private static void rodjendani() {
		Osobe.osobeStream(5000)
			.filter(o -> o.getDatumRodjenja().getMonthValue() == 3)
			.forEach(o -> System.out.printf("%02d. %02d. %s %s (puni %d) %n", 
					o.getDatumRodjenja().getDayOfMonth(), 
					o.getDatumRodjenja().getMonthValue(),
					o.getIme(), o.getPrezime(), 
					Integer.parseInt(Year.now().toString()) - o.getDatumRodjenja().getYear()));
	}
	
	
	private static void odrasli(String prezime) {
		System.out.printf("Ukupno ljudi sa prezimenom %s : %d %n", 
			prezime, Osobe.osobeStream(5000)
						.filter(o -> o.getPrezime().equalsIgnoreCase(prezime))
						.count());
	}
	
	
	private static void ukupnoDece() {
		System.out.printf("Ukupan broj dece : %d %n", Osobe.osobeStream(5000)
			.collect(Collectors.summingInt(o -> o.getDeca().size())));
	}
	
	
	private static void zaPaketice() {
		Osobe.osobeStream(5000)
			.flatMap(o -> o.getDeca().stream())
			.filter(o -> 0 <= 2010 - o.getDatumRodjenja().getYear() && 2010 - o.getDatumRodjenja().getYear() <= 10)
			.forEach(o -> System.out.printf("%s %s %s %n",
					o.getIme(), o.getPrezime(), 2010 - o.getDatumRodjenja().getYear()));
	}
	
	
	private static void imenaPenzionera() {
		Osobe.osobeStream(5000)
			.filter(o -> Integer.parseInt(Year.now().toString()) - o.getDatumRodjenja().getYear() >= 65)
			.map(o -> o.getIme())
			.distinct()
			.sorted()
			.forEach(System.out::println);
	}
	
	
	private static void procenatViseProlaza() {
		
		Integer total = Osobe.osobeStream(5000)
			.collect(Collectors.summingInt(o -> o.getDeca().size() + 1));
		
		Integer maleAdults = Osobe.osobeStream(5000)
			.filter(o -> o.getPol() == Pol.MUSKI)
			.collect(Collectors.summingInt(o -> 1));
		
		Integer maleChildren = Osobe.osobeStream(5000)
			.flatMap(o -> o.getDeca().stream())
			.filter(o -> o.getPol() == Pol.MUSKI)
			.collect(Collectors.summingInt(o -> 1));
		
		System.out.printf("Procenat muskih osoba : %.2f %n", (100.0 * (maleAdults + maleChildren) / total));			
	}
	
	
	private static void procenatJedanProlaz() {
		
		class Count {
			public long total;
			public long male;
		}
		
		Count c = Osobe.osobeStream(5000)
				.collect(ArrayList::new,
						(l, o) -> {
							l.addAll(o.getDeca());
							l.add(o);
						},
						(l0, l1) -> {
							l0.addAll(l1);
						})
				.stream()
				.collect(Count::new,
						(c0, o) -> {
							c0.total++;
							
							if (((Osoba) o).getPol() == Osoba.Pol.MUSKI)
								c0.male++;
						}, 
						(c0, c1) -> {
							c0.total = c1.total;
							c0.male = c1.male;
						});
		
		System.out.printf("Procenat muskih osoba : %.2f %n", (100.0 * c.male / c.total));
	}
	
	
	private static void trecaPoRedu() {
		
		List<Osoba> l = Osobe.osobeStream(5000)
			.sorted((o1, o2) -> Integer.compare(brojMuskeDece(o2), brojMuskeDece(o1)))
			.limit(3)
			.collect(Collectors.toList());
		
		System.out.println(l.get(l.size() - 1));
	}


	private static int brojMuskeDece(Osoba osoba) {
		
		List<Osoba> deca = osoba.getDeca();
		int sum = 0;
		
		for (Osoba dete : deca) if (dete.getPol() == Pol.MUSKI) sum++;
		
		return sum;
	}
	
	
	private static void najbogatiji(String grad) {
		
		Osobe.osobeStream(5000)
			.filter(o -> o.getMestoStanovanja().equalsIgnoreCase(grad))
			.sorted((o1, o2) -> o2.getPrimanja() - o1.getPrimanja())
			.forEach(o -> System.out.printf("%s %d %s %n", o.getIme(), o.getPrimanja(), o.getMestoRodjenja()));
	}
	
	
	private static void josBogatiji(String grad) {
		
		List<Osoba> l = Osobe.osobeStream(5000)
			.filter(o -> o.getMestoStanovanja().equalsIgnoreCase(grad))
			.sorted((o1, o2) -> o2.getPrimanja() - o1.getPrimanja())
			.collect(Collectors.toList());
		
		int max = l.get(0).getPrimanja();
		
		Osobe.osobeStream(5000)
			.filter(o -> o.getPrimanja() > max)
			.forEach(o -> System.out.printf("%s (%d) %s %n", o.getIme(), o.getPrimanja(), o.getMestoStanovanja()));
	}
}
