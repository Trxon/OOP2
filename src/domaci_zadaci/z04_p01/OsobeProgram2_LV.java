package domaci_zadaci.z04_p01;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/*
 * Dat je tok osoba u vidu metoda Osobe::osobeStream. Pomocu datog metoda,
 * lambda izraza i operacija nad tokovima podataka implementirati sledece
 * metode i pozvati ih iz glavnog programa. Koristiti iskljucivo terminalnu
 * operaciju .collect().
 *
 * List<Osoba> opadajuceSortiraniPoDatumuRodjenja()
 * Set<Osoba> saVecimPrimanjimaOd(int)
 * double prosecnaPrimanjaUMestu(String)
 * Map<String, Double> prosecnaPrimanjaPoMestu() // Kljuc mape je ime mesta
 * Map<String, Osoba> saNajvecimPrimanjimaZaSvakiGrad() // Kljuc mape je ime mesta
 * Map<Integer, List<Osoba>> razvrstaniPoBrojuDece() // Kljuc mape je broj dece
 * Map<String, Map<Integer, List<Osoba>>> razvrstaniPoMestuIBrojuDece()  // Kljuc glavne mape je ime mesta, a unutrasnje broj dece
 * String gradSaNajviseDoseljenika()
 * String gradSaNajviseStarosedelaca()
 * String najbogatijiGrad()
 * String najpopularnijeMuskoIme()
 * String najpopularnijeZenskoIme()
 * 
 * U glavnom programu ispisati sledece podatke koriscenjem redukcija tokova
 * podataka (terminalna operacija .reduce()).
 *
 * Najbogatija osoba
 * -----------------
 * 01234567 Pera Peric
 *
 * Muska imena
 * -----------
 * Aca, Arsa, Bora, Vlada, ...
 *
 * Godina kada je rodjena najstarija osoba
 * ---------------------------------------
 * 1940
 *
 * U glavnom programu ispisati sledece podatke na zadati nacin. Racunanje podataka
 * realizovati pomocu operacije .collect(). Prvo podatke skupiti u mapu a potom
 * iz mape pustiti novi tok podataka i formatirati ispis pomocu metoda String::format.
 * Obratiti paznju na format ispisa, velika i mala slova i broj decimala. 
 *
 * Grad       | Broj ljudi | Prosecna primanja
 * -----------+------------+------------------
 * NOVI SAD   |        234 |          49867.56
 * BEOGRAD    |        322 |          50072.33
 * KRAGUJEVAC |        225 |          49215.45
 * ...
 *
 * Ime        | Br roditelja | Broj muske dece | Broj zenske dece
 * -----------+--------------+-----------------+-----------------
 * Pera       |          234 |             356 |              297
 * Mika       |          322 |             442 |              443
 * Jelena     |          225 |             295 |              312
 * ...
 *
 * Godine | Primanja
 * zivota | Najnize   | Najvise   | Ukupno     | Prosek    | Devijacija 
 * -------+-----------+-----------+------------+-----------+-----------
 * ...
 * 22     |  12600.00 | 102400.00 | 7652300.00 | 503476.12 |     132.66
 * 23     |  29600.00 |  99700.00 | 6843500.00 | 496456.26 |      98.32
 * 24     |  23400.00 | 123400.00 | 8134800.00 | 512388.43 |     253.01
 * ...  
 */
public class OsobeProgram2_LV {

	public static void main(String[] args) {
		//svi();
		System.out.println();
		System.out.println("Sortirani po datumu rodjenja: ");
		opadajuceSortiraniPoDatumuRodjenja().stream()
				.map(osoba -> String.format("%s %s (%s)",
						osoba.getIme(),
						osoba.getPrezime(),
						osoba.getDatumRodjenja()))
				.forEach(System.out::println);

		System.out.println();
		System.out.println("Svi sa vecim primanjima od 100 000:");
		saVecimPrimanjima(100000).stream()
				.map(osoba -> String.format("%s %s (%d)",
						osoba.getIme(),
						osoba.getPrezime(),
						osoba.getPrimanja()))
				.forEach(System.out::println);

		System.out.println();
		System.out.println("Prosecna primanja u Novom Sadu: " + prosecnaPrimanjauMestu("Novi Sad"));

		System.out.println();
		System.out.println("Prosecna primanja:");
		prosecnaPrimanjaPoMestu().entrySet().stream()
				.map(e -> String.format("za %-15s %.2f",
						e.getKey(),
						e.getValue()))
				.forEach(System.out::println);

		System.out.println();
		System.out.println("Najbogatiji za svaki grad:");
		saNajvecimPrimanjimaZaSvakiGrad().entrySet().stream()
				.map(e -> String.format("%-15s, %s",
						e.getValue(),
						e.getKey()))
				.forEach(System.out::println);

		System.out.println();
		System.out.println("Po broju dece:");
		razvrstaniPoBrojuDece().entrySet().stream()
				.map(e -> String.format("%d: %s",
						e.getKey(),
						e.getValue()))
				.forEach(System.out::println);

		System.out.println();
		System.out.println("Po mestu i broju dece");
		razvrstaniPoMestuIBrojuDece().entrySet().stream()
				.peek(e -> System.out.println(e.getKey()))
				.flatMap(e -> e.getValue().entrySet().stream())
				.map(e -> String.format("%d: %s",
						e.getKey(),
						e.getValue()))
				.forEach(System.out::println);
		System.out.println();
		System.out.println("Grad sa najvise doseljenika: " + gradSaNajviseDoseljenika());

		System.out.println();
		System.out.println("Grad sa najvise starosedelaca: " + gradSaNajviseStarosedelaca());

		System.out.println();
		System.out.println("Najbogatiji grad: " + najbogatijiGrad());

		System.out.println();
		System.out.println("Najpopularnije musko ime: " + najpopularnijeMuskoIme());

		System.out.println();
		System.out.println("Najpopularnije zensko ime: " + najpopularnijeZenskoIme());

		System.out.println();
		System.out.println("Najbogatija osoba");
		System.out.println("-----------------");
		Osoba najbogatija = Osobe.osobeStream(5000)
				.reduce(null, (osoba1, osoba2) -> {
					if(osoba1 == null)
						return osoba2;
					if(osoba1.getPrimanja() > osoba2.getPrimanja())
						return osoba1;
					return osoba2;
				});
		System.out.println(najbogatija);

		System.out.println();
		System.out.println("Muska imena");
		System.out.println("-----------");
		String imena  = Osobe.osobeStream(5000)
				.filter(osoba -> osoba.getPol() == Pol.MUSKI)
				.map(Osoba::getIme)
				.distinct()
				.sorted()
				.reduce("", (ime1, ime2) -> "".equals(ime1) ? ime2: ime1 + ", " + ime2);
		System.out.println(imena);

		System.out.println();
		System.out.println("Godina kada je rodjena najstarija osoba");
		System.out.println("---------------------------------------");
		OptionalInt godina = Osobe.osobeStream(5000)
				.map(Osoba::getDatumRodjenja)
				.mapToInt(LocalDate::getYear)
				.reduce(Integer::min);
		System.out.println(godina.orElse(-1));

		Map<String, IntSummaryStatistics> podaci1 = Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(Osoba::getMestoStanovanja,
						Collectors.summarizingInt(Osoba::getPrimanja)));

		System.out.println();
		System.out.println("Grad       | Broj ljudi | Prosecna primanja");
		System.out.println("-----------+------------+------------------");
		podaci1.entrySet().stream()
				.map(e -> String.format("%-10s | %10d | %17.2f",
						e.getKey().toUpperCase(),
						e.getValue().getCount(),
						e.getValue().getAverage()))
				.forEach(System.out::println);

		class Brojac {
			public int roditelji;
			public int muska;
			public int zenska;
		}

		Map<String, Brojac> podaci2 = Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(Osoba::getIme, Collector.of(
						Brojac::new,
						(brojac, osoba) -> {
							brojac.roditelji++;
							brojac.muska += osoba.getBrDece(Pol.MUSKI);
							brojac.zenska += osoba.getBrDece(Pol.ZENSKI);
						},
						(brojac1, brojac2) -> {
							brojac1.roditelji += brojac2.roditelji;
							brojac1.muska += brojac2.muska;
							brojac1.zenska += brojac2.zenska;
							return brojac1;
						})));
		System.out.println();
		System.out.println("Ime        | Broj roditelja | Broj muske dece | Broj zenske dece");
		System.out.println("-----------+----------------+-----------------+-----------------");
		podaci2.entrySet().stream()
				.map(e -> String.format("%-10s | %14d | %15d | %16d",
						e.getKey(),
						e.getValue().roditelji,
						e.getValue().muska,
						e.getValue().zenska))
				.forEach(System.out::println);
	}

	private static void svi() {
		Osobe.osobeStream(5000)
			.forEach(System.out::println);
	}

	private static List<Osoba> opadajuceSortiraniPoDatumuRodjenja() {
		return Osobe.osobeStream(5000)
				.sorted(Comparator.comparing(Osoba::getDatumRodjenja))
				.collect(Collectors.toList());
	}

	private static Set<Osoba> saVecimPrimanjima(int granica) {
		return Osobe.osobeStream(5000)
				.filter(osoba -> osoba.getPrimanja() > granica)
				.collect(Collectors.toSet());
	}

	private static double prosecnaPrimanjauMestu(String mesto) {
		return Osobe.osobeStream(5000)
				.filter(osoba -> mesto.equalsIgnoreCase(osoba.getMestoRodjenja()))
				.collect(Collectors.averagingInt(Osoba::getPrimanja));
	}

	private static Map<String, Double> prosecnaPrimanjaPoMestu() {
		return Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja,
						Collectors.averagingInt(Osoba::getPrimanja)));
	}

	private static Map<String, Osoba> saNajvecimPrimanjimaZaSvakiGrad() {
		return Osobe.osobeStream(5000)
				.collect(Collectors.toMap(Osoba::getMestoStanovanja,
						Function.identity(),
						BinaryOperator.maxBy(Comparator.comparing(Osoba::getPrimanja))));
	}

	private static Map<Integer, List<Osoba>> razvrstaniPoBrojuDece() {
		return Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(Osoba::getBrDece));
	}

	private static Map<String, Map<Integer, List<Osoba>>> razvrstaniPoMestuIBrojuDece() {
		return Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja,
						Collectors.groupingBy(Osoba::getBrDece)));
	}

	private static String gradSaNajviseDoseljenika() {
		return Osobe.osobeStream(5000)
				.filter(osoba -> !osoba.getMestoRodjenja().equals(osoba.getMestoStanovanja()))
				.collect(Collectors.groupingBy(Osoba::getMestoStanovanja, Collectors.counting()))
				.entrySet().stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey)
				.orElse(null);
	}

	private static String gradSaNajviseStarosedelaca() {
		return Osobe.osobeStream(5000)
				.filter(o -> o.getMestoStanovanja().equals(o.getMestoRodjenja()))
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja,
						Collectors.counting()))
				.entrySet().stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey)
				.orElse(null);
	}

	private static String najbogatijiGrad() {
		return Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja,
						Collectors.summingInt(Osoba::getPrimanja)))
				.entrySet().stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey)
				.orElse(null);
	}

	private static String najpopularnijeMuskoIme() {
		return Osobe.osobeStream(5000)
				.filter(osoba -> osoba.getPol() == Pol.MUSKI)
				.map(Osoba::getIme)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
				.entrySet().stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey)
				.orElse(null);
	}

	private static String najpopularnijeZenskoIme() {
		return Osobe.osobeStream(5000)
				.filter(osoba -> osoba.getPol() == Pol.ZENSKI)
				.map(Osoba::getIme)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
				.entrySet().stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey)
				.orElse(null);
	}







}
