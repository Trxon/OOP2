package domaci_zadaci.z03_ass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Stream;

import domaci_zadaci.z03_ass.Osoba.Pol;

/*
 * Dat je tok osoba u vidu metoda Osobe.osobeStream(). Pomocu datog metoda,
 * lambda izraza i operacija nad tokovima podataka implementirati sledece metode:
 * 
 * svi() - Stampa id, ime i prezime za sve osobe
 * 
 * punoDece() - Stampa ime i prezime svih osoba sa vise od dvoje dece
 *
 * istoMesto() - Stampa, sortirano po imenu mesta, podatke za sve osobe koje zive
 *      u istom mestu u kojem su rodjeni, na sledeci nacin:
 *      Ime Prezime (Mesto)
 * 
 * bogateBezDece() - Stampa podatke o zenama sa platom preko 75.000 koje nemaju
 *      dece, sortirano opadajuce po plati, na sledeci nacin:
 *      Ime Prezime Plata
 * 
 * rodjendani() - Stampa podatke o osobama koje slave rodjendan ovog meseca.
 *      Stampati dan i mesec, ime i prezime, kao i koliko godina osoba puni, na
 *      sledeci nacin:
 *      DD. MM. Ime Prezime (Puni)
 * 
 * odrasli(String prezime) - Stampa ukupan broj osoba sa datim prezimenom.
 * 
 * ukupnoDece() - Stampa ukupan broj dece svih osoba.
 * 
 * zaPaketice() - Stampa imena, prezimena i starost dece koja su dobila paketice
 *      2010. godine. Uslov za dobijanje paketica je da dete ne bude starije od
 *      10 godina.
 * 
 * imenaPenzionera() - Stampa sva razlicita imena penzionera, sortirana abecedno.
 *      Penzioneri su osobe koje imaju preko 65 godina.
 * 
 * procenat() - Stampa procenat muskih osoba, ukljucujuci i decu.
 * 
 * trecaPoRedu() - Stampa osobu koja je treca po broju muske dece.
 * 
 * najbogatiji(String grad) - Stampa ime (bez prezimena), visinu primanja i mesto
 *      rodjenja osobe sa najvecim primanjima za zadato mesto stanovanja.
 * 
 * josBogatiji(String grad) - Stampa podatke osoba koje imaju veca primanja od
 *      najbogatije osobe iz zadatog mesta, na sledeci nacin:
 *      Ime (primanja) Mesto stanovanja
 * 
 * Implementirati metod Osoba::fromString i prepraviti sve metode da koriste tok
 * stringova Osobe.stringStream().
 */
public class OsobeProgram {

	public static void main(String[] args) {
		naslov("Svi");
		svi();
		naslov("Vise od dvoje dece");
		punoDece();
		naslov("Isto mesto stanovanja i rodjenja");
		istoMesto();
		naslov("Bogate bez dece");
		bogateBezDece();
		naslov("Bogate bez dece (alternativni pristup)");
		bogateBezDece2();
		naslov("Rodjendani ovog meseca");
		rodjendani();
		naslov("Odrasli sa zadatim prezimenom");
		odrasli("Perovski");
		naslov("Ukupno dece");
		ukupnoDece();
		naslov("Ukupno dece (alternativni pristup)");
		ukupnoDece2();
		naslov("Ukupno dece (alternativni pristup 2)");
		ukupnoDece3();
		naslov("Dobili paketice 2010. godine");
		zaPaketice();
		naslov("Imena penzionera");
		imenaPenzionera();
		naslov("Procenat muskih osoba");
		procenat();
		naslov("Procenat muskih osoba (alternativni pristup)");
		procenat2();
		naslov("Procenat muskih osoba (alternativni pristup 2)");
		procenat3();
		naslov("Treca po broju dece");
		trecaPoRedu();
		naslov("Treca po broju dece (alternativni pristup)");
		trecaPoRedu2();
		naslov("Najbogatiji");
		najbogatiji("Novi Sad");
		naslov("Najbogatiji (alternativni pristup)");
		najbogatiji2("Novi Sad");
		naslov("Jos bogatiji");
		josBogatiji("Novi Sad");
		naslov("Jos bogatiji (alternativni pristup)");
		josBogatiji2("Novi Sad");
	}

	private static void naslov(String naslov) {
		System.out.println();
		System.out.println(naslov);
		System.out.println(naslov.replaceAll(".", "="));
	}

	private static void svi() {
		Osobe.osobeStream(5000)
			.forEach(System.out::println);
	}

	private static void punoDece() {
		Osobe.osobeStream(5000)
			.filter(o -> o.getDeca().size() > 2)
			.map(o -> o.getIme() + " " + o.getPrezime())
			.forEach(System.out::println);
	}

	private static void istoMesto() {
		Osobe.osobeStream(5000)
			.filter(o -> o.getMestoStanovanja().equals(o.getMestoRodjenja()))
			.sorted(Comparator.comparing(Osoba::getMestoStanovanja))
			.map(o -> o.getIme() + " " + o.getPrezime() + " (" + o.getMestoStanovanja() + ")")
			.forEach(System.out::println);
	}

	private static void bogateBezDece() {
		Osobe.osobeStream(5000)
			.filter(o -> o.getPol() == Pol.ZENSKI)
			.filter(o -> o.getPrimanja() > 75_000)
			.filter(o -> o.getDeca().isEmpty())
			.sorted(Comparator.comparing(Osoba::getPrimanja).reversed())
			.map(o -> String.format("%s %s (%d)", o.getIme(), o.getPrezime(), o.getPrimanja()))
			.forEach(System.out::println);
	}

	private static void bogateBezDece2() {
		Osobe.osobeStream(5000)
			.filter(o -> (o.getPol() == Pol.ZENSKI)
			          && (o.getPrimanja() > 75_000)
			          && (o.getDeca().isEmpty()))
			.sorted(Comparator.comparing(Osoba::getPrimanja).reversed())
			.map(o -> String.format("%s %s (%d)", o.getIme(), o.getPrezime(), o.getPrimanja()))
			.forEach(System.out::println);
	}

	private static void rodjendani() {
		LocalDate sad = LocalDate.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.");
		Comparator<Osoba> poDanu = Comparator.comparing(o -> o.getDatumRodjenja().getDayOfMonth());
		Comparator<Osoba> poGodini = Comparator.comparing(o -> o.getDatumRodjenja().getYear());
		Comparator<Osoba> poPrezimenu = Comparator.comparing(Osoba::getPrezime);
		Comparator<Osoba> poImenu = Comparator.comparing(Osoba::getIme);
		Comparator<Osoba> poredak = poDanu
			.thenComparing(poGodini.reversed())
			.thenComparing(poPrezimenu)
			.thenComparing(poImenu);
		Osobe.osobeStream(5000)
			.filter(o -> o.getDatumRodjenja().getMonth() == sad.getMonth())
			.sorted(poredak) // Ovo se nije trazilo u zadatku
			.map(o -> String.format("%s %s %s (%d)",
					format.format(o.getDatumRodjenja()),
					o.getIme(),
					o.getPrezime(),
					sad.getYear() - o.getDatumRodjenja().getYear()))
			.forEach(System.out::println);
	}

	private static void odrasli(String prezime) {
		long br = Osobe.osobeStream(5000)
			.filter(o -> o.getPrezime().equals(prezime))
			.count();
		System.out.println(br);
	}

	private static void ukupnoDece() {
		long br = Osobe.osobeStream(5000)
			.mapToInt(o -> o.getDeca().size())
			.sum();
		System.out.println(br);
	}

	private static void ukupnoDece2() {
		long br = Osobe.osobeStream(5000)
			.flatMap(o -> o.getDeca().stream())
			.count();
		System.out.println(br);
	}

	private static void ukupnoDece3() {
		int br = Osobe.osobeStream(5000)
			.map(o -> o.getDeca().size())
			.reduce(0, (x, y) -> x + y);
		System.out.println(br);
	}

	private static void zaPaketice() {
		LocalDate sad = LocalDate.now();
		Osobe.osobeStream(5000)
			.flatMap(o -> o.getDeca().stream())
			.filter(o -> o.getDatumRodjenja().getYear() >= 2000)
			.filter(o -> o.getDatumRodjenja().getYear() <= 2010)
			.map(o -> String.format("%s %s (%d)",
					o.getIme(),
					o.getPrezime(),
					o.getDatumRodjenja().until(sad).getYears()))
			.forEach(System.out::println);
	}

	private static void imenaPenzionera() {
		LocalDate sad = LocalDate.now();
		Osobe.osobeStream(5000)
			.filter(o -> o.getDatumRodjenja().until(sad).getYears() >= 65)
			.map(Osoba::getIme)
			.distinct()
			.sorted()
			.forEach(System.out::println);
	}

	private static void procenat() {
		long brOdraslih = Osobe.osobeStream(5000)
			.count();
		long brOdraslihM = Osobe.osobeStream(5000)
			.filter(o -> o.getPol() == Pol.MUSKI)
			.count();
		long brDece = Osobe.osobeStream(5000)
			.flatMap(o -> o.getDeca().stream())
			.count();
		long brDeceM = Osobe.osobeStream(5000)
			.flatMap(o -> o.getDeca().stream())
			.filter(o -> o.getPol() == Pol.MUSKI)
			.count();
		double procenat = 100.0 * (brOdraslihM + brDeceM) / (brOdraslih + brDece);
		System.out.printf("%8.5f%%%n", procenat);
	}

	private static void procenat2() {
		Stream<Osoba> tok1 = Osobe.osobeStream(5000);
		Stream<Osoba> tok2 = Osobe.osobeStream(5000).flatMap(o -> o.getDeca().stream());
		Stream.concat(tok1, tok2)
				.mapToInt(o -> o.getPol() == Pol.MUSKI ? 1 : 0)
				.average()
				.ifPresent(d -> System.out.printf("%8.5f%%%n", d));
	}

	private static void procenat3() {
		Osobe.osobeStream(5000)
			.flatMap(o -> Stream.concat(
					Stream.of(o),
					o.getDeca().stream()))
			.mapToDouble(o -> o.getPol() == Pol.MUSKI ? 100.0 : 0.0)
			.average()
			.ifPresent(d -> System.out.printf("%8.5f%%%n", d));
	}

	private static void trecaPoRedu() {
		Osobe.osobeStream(5000)
			.sorted(Comparator.comparing((Osoba o) ->
					o.getBrDece(Pol.MUSKI))
					.reversed())
			.skip(2)
			.findFirst()
			.ifPresent(System.out::println);
	}

	private static void trecaPoRedu2() {
		Osobe.osobeStream(5000)
			.sorted(Comparator.comparing((Osoba o) ->
					o.getBrDece(Pol.MUSKI))
					.reversed())
			.limit(3)
			.skip(2)
			.forEach(System.out::println);
	}

	private static void najbogatiji(String grad) {
		Osobe.osobeStream(5000)
			.filter(o -> o.getMestoStanovanja().equals(grad))
			.max(Comparator.comparing(Osoba::getPrimanja))
			.map(o -> String.format("%s %s (%s)",
					o.getIme(),
					o.getPrimanja(),
					o.getMestoRodjenja()))
			.ifPresent(System.out::println);
	}

	private static void najbogatiji2(String grad) {
		Osobe.osobeStream(5000)
			.filter(o -> o.getMestoStanovanja().equals(grad))
			.sorted(Comparator.comparing(Osoba::getPrimanja).reversed())
			.limit(1)
			.map(o -> String.format("%s %s (%s)",
					o.getIme(),
					o.getPrimanja(),
					o.getMestoRodjenja()))
			.forEach(System.out::println);
	}

	private static void josBogatiji(String grad) {
		int granica = Osobe.osobeStream(5000)
			.filter(o -> o.getMestoStanovanja().equals(grad))
			.max(Comparator.comparing(Osoba::getPrimanja))
			.map(Osoba::getPrimanja)
			.orElse(0);
		Osobe.osobeStream(5000)
			.filter(o -> !o.getMestoStanovanja().equals(grad))
			.filter(o -> o.getPrimanja() > granica)
			.sorted(Comparator.comparing(Osoba::getPrimanja))
			.map(o -> String.format("%s (%s) %s",
					o.getIme(),
					o.getPrimanja(),
					o.getMestoStanovanja()))
			.forEach(System.out::println);
	}

	private static void josBogatiji2(String grad) {
		int granica = Osobe.osobeStream(5000)
			.filter(o -> o.getMestoStanovanja().equals(grad))
			.sorted(Comparator.comparing(Osoba::getPrimanja).reversed())
			.mapToInt(Osoba::getPrimanja)
			.limit(1)
			.sum();
		Osobe.osobeStream(5000)
			.filter(o -> !o.getMestoStanovanja().equals(grad))
			.filter(o -> o.getPrimanja() > granica)
			.sorted(Comparator.comparing(Osoba::getPrimanja))
			.map(o -> String.format("%s (%s) %s",
					o.getIme(),
					o.getPrimanja(),
					o.getMestoStanovanja()))
			.forEach(System.out::println);
	}
}
