package domaci_zadaci.z03_p02;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import domaci_zadaci.z03_p02.Osoba.Pol;

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
	

	public static void main(String[] args) {
		
//		svi();
//		punoDece();
//		istoMesto();
//		bogateBezDece();
//		rodjendani();
//		odrasli("Zoranovski");
//		ukupnoDece();
//		zaPaketice();
//		imenaPenzionera();
//		procenat();
//		procenatAlt();
//		trecaPoRedu();
//		najbogatiji("Novi Sad");
//		josBogatiji("Novi Sad");
	}
	
	
	private static void josBogatiji(String grad) {
		
		Osoba osoba = Osobe.osobeStream(500)
				.filter(o -> o.getMestoStanovanja().equals(grad))
				.max(Comparator.comparingDouble(Osoba::getPrimanja)).orElseGet(null);
		
		Osobe.osobeStream(500)
				.filter(o -> o.getPrimanja() > osoba.getPrimanja())
				.sorted(Comparator.comparing(Osoba::getPrimanja))
				.map(o -> String.format("%s (%d), %s", o.getIme(), o.getPrimanja(), o.getMestoRodjenja()))
				.forEach(System.out::println);
	}


	private static void najbogatiji(String grad) {
		
		Osoba osoba = Osobe.osobeStream(500)
				.filter(o -> o.getMestoStanovanja().equals(grad))
				.max(Comparator.comparingDouble(Osoba::getPrimanja)).orElseGet(null);
		
		System.out.printf("%s (%d), %s", osoba.getIme(), osoba.getPrimanja(), osoba.getMestoRodjenja());
	}


	private static long brojMuskeDece(Osoba osoba) {
		
		return osoba.getDeca().stream().filter(o -> o.getPol() == Pol.MUSKI).count();
	}
	
	
	private static void trecaPoRedu() {
		 
		Osobe.osobeStream(500)
				.sorted(Comparator.comparingLong(o -> brojMuskeDece(o)))
				.skip(2).findFirst().ifPresent(System.out::println);
	}


	private static void procenatAlt() {
		
		class Data {
			
			int muski, svi;
			
			public void add(Osoba o) {
				
				if (o.getPol() == Pol.MUSKI)
					muski++;
				
				svi++;
			}
			
			public Data join(Data d) {
				
				muski += d.muski;
				svi += d.svi;
				
				return this;
			}
		}
		
		Data data = Osobe.osobeStream(500)
				.flatMap(o -> {
					
					List<Osoba> osobe = new ArrayList<Osoba>();
					
					osobe.add(o);
					osobe.addAll(o.getDeca());
					
					return osobe.stream();
					
				}).collect(Collector.of(Data::new, Data::add, Data::join));
		
		System.out.printf("Procenat muskih : %.2f %n", 100.0 * data.muski / data.svi);
	}
	

	private static void procenat() {
		
		System.out.printf("Procenat muskih : %.2f %n",
				
				100.0 * 
				
				Osobe.osobeStream(500)
						.flatMap(o -> {
							
							List<Osoba> osobe = new ArrayList<Osoba>();
							
							osobe.add(o);
							osobe.addAll(o.getDeca());
							
							return osobe.stream();
							
						}).filter(o -> o.getPol() == Pol.MUSKI).count()
				
				/
				
				Osobe.osobeStream(500)
				.flatMap(o -> {
					
					List<Osoba> osobe = new ArrayList<Osoba>();
					
					osobe.add(o);
					osobe.addAll(o.getDeca());
					
					return osobe.stream();
					
				}).count());
	}


	private static void imenaPenzionera() {
		
		Osobe.osobeStream(500)
				.filter(o -> LocalDate.now().getYear() - o.getDatumRodjenja().getYear() > 65)
				.map(Osoba::getIme)
				.distinct().sorted()
				.forEach(System.out::println);
	}


	private static void zaPaketice() {
		
		Osobe.osobeStream(500)
				.flatMap(o -> o.getDeca().stream())
				.filter(o -> LocalDate.now().getYear() - o.getDatumRodjenja().getYear() <= 10)
				.map(o -> String.format("%s %s (%d)", o.getIme(), o.getPrezime(), LocalDate.now().getYear() - o.getDatumRodjenja().getYear()))
				.forEach(System.out::println);
	}


	private static void ukupnoDece() {
		
		System.out.println(Osobe.osobeStream(500).flatMap(o -> o.getDeca().stream()).count());
	}


	private static void odrasli(String prezime) {
		
		System.out.println(Osobe.osobeStream(500)
				.filter(o -> o.getPrezime().equalsIgnoreCase(prezime))
				.collect(Collectors.counting()));
	}


	private static void rodjendani() {
		
		Osobe.osobeStream(500)
				.filter(o -> o.getDatumRodjenja().getMonthValue() == LocalDate.now().getMonthValue())
				.map(o -> String.format("%02d.%02d. %s %s (puni %d)", 
						o.getDatumRodjenja().getDayOfMonth(), o.getDatumRodjenja().getMonthValue(), o.getIme(), o.getPrezime(), LocalDate.now().getYear() - o.getDatumRodjenja().getYear()))
				.forEach(System.out::println);
	}


	private static void bogateBezDece() {
		
		Osobe.osobeStream(500)
				.filter(o -> o.getPol() == Osoba.Pol.ZENSKI && o.getPrimanja() > 75_000 && o.getDeca().size() == 0)
				.sorted(Comparator.comparing(Osoba::getPrimanja).reversed())
				.map(o -> String.format("%s %s (%d)", o.getIme(), o.getPrezime(), o.getPrimanja()))
				.forEach(System.out::println);
	}


	private static void istoMesto() {
		
		Osobe.osobeStream(500)
				.filter(o -> o.getMestoRodjenja().equals(o.getMestoStanovanja()))
				.sorted(Comparator.comparing(Osoba::getMestoRodjenja))
				.map(o -> String.format("%s %s (%s)", o.getIme(), o.getPrezime(), o.getMestoStanovanja()))
				.forEach(System.out::println);
	}


	private static void punoDece() {
		
		Osobe.osobeStream(500)
				.filter(o -> o.getDeca().size() > 2)
				.forEach(System.out::println);
	}


	private static void svi() {
		
		Osobe.osobeStream(50)
				.flatMap(o -> {
					
					List<Osoba> osobe = new ArrayList<Osoba>();
					
					osobe.add(o);
					osobe.addAll(o.getDeca());
					
					return osobe.stream();
					
				}).forEach(System.out::println);
	}
}
