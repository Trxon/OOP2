package domaci_zadaci.z04_p01;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
 * Dat je tok osoba u vidu metoda Osobe::osobeStream. Uz pomocu da-
 * tog metoda, lambda izraza i operacija nad tokovima podataka imp-
 * lementirati sledece metode i pozvati ih iz glavnog programa. Ko-
 * ristiti iskljucivo terminalnu operaciju .collect().
 *
 * List<Osoba> opadajuceSortiraniPoDatumuRodjenja()
 * Set<Osoba> saVecimPrimanjimaOd(int)
 * double prosecnaPrimanjaUMestu(String)
 * Map<String, Double> prosecnaPrimanjaPoMestu() 
 * 		// Kljuc mape je ime mesta
 * Map<String, Osoba> saNajvecimPrimanjimaZaSvakiGrad() 
 * 		// Kljuc mape je ime mesta
 * Map<Integer, List<Osoba>> razvrstaniPoBrojuDece() 
 * 		// Kljuc mape je broj dece
 * Map<String, Map<Integer, List<Osoba>>> poMestuIBrojuDece()  
 * 		// Kljuc glavne mape je ime mesta, a unutrasnje broj dece
 * String gradSaNajviseDoseljenika()
 * String gradSaNajviseStarosedelaca()
 * String najbogatijiGrad()
 * String najpopularnijeMuskoIme()
 * String najpopularnijeZenskoIme()
 * 
 * U glavnom programu ispisati sledece podatke koriscenjem redukci-
 * ja tokova podataka (terminalna operacija .reduce()).
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
 * U glavnom programu ispisati sledece podatke na zadati nacin. Ra-
 * cunanje podataka realizovati pomocu operacije .collect(). Na po-
 * cetku podatke skupiti u mapu a potom iz mape pustiti novi tok p-
 * odataka i formatirati ispis pomocu metoda String::format. Obrat-
 * iti paznju na format ispisa, velika, mala slova i broj decimala. 
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

public class OsobeProgram2_P01 {
	
	
	private static final int BROJ_OSOBA = 5000;

	
	public static void main(String[] args) {
		
//		svi();
//		System.out.println();
//		System.out.println(prosecnaPrimanjaUMestu("Novi Sad"));
//		System.out.println(prosecnaPrimanjaPoMestu());
//		System.out.println(saNajvecimPrimanjimaZaSvakiGrad());
//		System.out.println(razvrstaniPoBrojuDece());
//		System.out.println(razvrstaniPoMestuIBrojuDece());
//		System.out.println(gradSaNajviseDoseljenika());
//		System.out.println(gradSaNajviseStarosedelaca());
//		System.out.println(najbogatijiGrad());
//		System.out.println(najpopularnijeMuskoIme());
//		System.out.println(najpopularnijeZenskoIme());
		
//		najbogatijaOsoba();
//		muskaImena();
//		godinaRodjenjaNajstarije();
		
//		tabela1();
		tabela2();
		tabela3();
	}


	private static void svi() {
		Osobe.osobeStream(BROJ_OSOBA)
			.sorted(Comparator.comparing(Osoba::getMestoStanovanja))
			.forEach(System.out::println);
	}
	
	
	private static List<Osoba> opadajuceSortiraniPoDatumuRodjenja() {
		return Osobe.osobeStream(BROJ_OSOBA)
			.sorted(Comparator.comparing(Osoba::getDatumRodjenja).reversed())
			.collect(Collectors.toList());
	}
	
	
	private static double prosecnaPrimanjaUMestu(String mesto) {
		return Osobe.osobeStream(BROJ_OSOBA)
			.filter(o -> o.getMestoStanovanja().equalsIgnoreCase(mesto))
			.collect(Collectors.averagingDouble(Osoba::getPrimanja));
	}
	
	
	private static Map<String, Double> prosecnaPrimanjaPoMestu() {
		return Osobe.osobeStream(BROJ_OSOBA)
			.collect(
	                Collectors.groupingBy(
	                        Osoba::getMestoStanovanja,
	                        Collectors.averagingInt(Osoba::getPrimanja)
	                ));
	}
	
	
	private static Map<String, Osoba> saNajvecimPrimanjimaZaSvakiGrad() {
		return Osobe.osobeStream(BROJ_OSOBA)
			.collect(
					Collectors.groupingBy(
							Osoba::getMestoStanovanja, 
							Collectors.collectingAndThen(
									Collectors.reducing(
											BinaryOperator.maxBy(
													Comparator.comparing(Osoba::getPrimanja))), 
									Optional::get)));
	}
	
	
	private static Map<Integer, List<Osoba>> razvrstaniPoBrojuDece() {
		return Osobe.osobeStream(BROJ_OSOBA)
			.collect(
					Collectors.groupingBy(
							Osoba::getBrDece,
							Collectors.toList()));
	}
	
	
	private static Map<String, Map<Integer, List<Osoba>>> razvrstaniPoMestuIBrojuDece()  { // Kljuc glavne mape je ime mesta, a unutrasnje broj dece
		return Osobe.osobeStream(BROJ_OSOBA)
			.collect(
					Collectors.groupingBy(
							Osoba::getMestoStanovanja, 
							Collectors.groupingBy(
									Osoba::getBrDece, 
									Collectors.toList())));
	}
	
	
	private static String gradSaNajviseStarosedelaca() {
		return gradSaNajvise(o -> o.getMestoRodjenja().equalsIgnoreCase(o.getMestoStanovanja()));
	}
	
	
	private static String gradSaNajviseDoseljenika() {
		return gradSaNajvise(o -> !o.getMestoRodjenja().equalsIgnoreCase(o.getMestoStanovanja()));
	}
	
	
	private static String gradSaNajvise(Predicate<Osoba> p) {
		return Osobe.osobeStream(BROJ_OSOBA)
				.collect(
						Collectors.groupingBy(
								Osoba::getMestoStanovanja,
								Collectors.filtering(
										p, 
										Collectors.counting())))
				.entrySet().stream().collect(
						Collectors.collectingAndThen(
								Collectors.reducing(
										BinaryOperator.maxBy(Comparator.comparing(Map.Entry::getValue))), 
								o -> o.get().getKey()));
	}
	
	
	private static String najbogatijiGrad() {
		return Osobe.osobeStream(BROJ_OSOBA)
			.collect(
					Collectors.groupingBy(
							Osoba::getMestoStanovanja, 
							Collectors.summingInt(Osoba::getPrimanja)))
			.entrySet().stream().collect(
					Collectors.collectingAndThen(
							Collectors.reducing(
									BinaryOperator.maxBy(Comparator.comparing(Map.Entry::getValue))), 
							o -> o.get().getKey()));
	}
	
	
	private static String najpopularnijeMuskoIme() {
		return najpopularnijeIme(Pol.MUSKI);
	}
	
	
	private static String najpopularnijeZenskoIme() {
		return najpopularnijeIme(Pol.ZENSKI);
	}
	
	
	private static String najpopularnijeIme(Pol pol) {
		return Osobe.osobeStream(BROJ_OSOBA)
			.map(o -> getSve(o))
			.flatMap(Collection::stream)
			.filter(o -> o.getPol() == pol)
			.collect(Collectors.groupingBy(Osoba::getIme, Collectors.counting()))
			.entrySet().stream().collect(
				Collectors.collectingAndThen(
						Collectors.reducing(
								BinaryOperator.maxBy(Comparator.comparing(Map.Entry::getValue))), 
						o -> o.get().getKey()));
	}
	
	
	private static ArrayList<Osoba> getSve(Osoba o) {
		
		ArrayList<Osoba> out = new ArrayList<Osoba>();
		
		out.add(o);
		out.addAll(o.getDeca());
		
		return out;
	}

	
	private static void najbogatijaOsoba() {		
		System.out.printf("Najbogatija osoba\n-----------------\n%s\n\n", 
				Osobe.osobeStream(BROJ_OSOBA)
					.reduce((o1, o2) -> o1.getPrimanja() > o2.getPrimanja() ? o1 : o2).get());
	}
	
	
	private static void muskaImena() {
		System.out.printf("Muska imena\n-----------\n%s\n\n", Osobe.osobeStream(BROJ_OSOBA)
				.map(o -> getSve(o))
				.flatMap(Collection::stream)
				.filter(o -> o.getPol() == Pol.MUSKI)
				.map(o -> o.getIme())
				.distinct()
				.reduce((ime1, ime2) -> ime1 + " " + ime2).get());
	}
	
	
	private static void godinaRodjenjaNajstarije() {
		System.out.printf(
				"Godina kada je rodjena najstarija osoba\n" +
				"---------------------------------------\n" +
				"%s\n\n", 
				Osobe.osobeStream(BROJ_OSOBA)
					.reduce((o1, o2) -> o1.getDatumRodjenja().compareTo(o2.getDatumRodjenja()) > 0 ? o2 : o1).get());
	}
	
	
	private static void tabela1() {
		
		// prvi nacin
		class Container {
			
			class Data {
				
				int num;
				double sum;
				
				public Data(double sum) { 
					this.num = 1; 
					this.sum = sum; 
				}
			}
			
			HashMap<String, Data> map = new HashMap<String, Data>();
			
			public void add(String mesto, double primanja) {
				if (map.containsKey(mesto)) {
					map.get(mesto).num++;
					map.get(mesto).sum += primanja;
				} else {
					map.put(mesto, new Data(primanja));
				}
			}
		}
		
		Container container = new Container();
		Osobe.osobeStream(BROJ_OSOBA)
			.forEach(o -> container.add(o.getMestoStanovanja(), o.getPrimanja()));
		
		System.out.printf("%10s | %10s | %19s\n-----------+------------+------------------\n", 
				"Grad", "Broj ljudi", "Prosecna primanja");
		container.map.entrySet().stream().forEach(e -> System.out.printf(
			"%10s | %10d | %17.2f\n", 
			e.getKey(), 
			e.getValue().num, 
			e.getValue().sum / e.getValue().num));
		System.out.println();
		
		// drugi nacin - pretpostavljam da se ovako trazilo u zadatku
		Map<String, List<Osoba>> m1 = Osobe.osobeStream(BROJ_OSOBA)
			.collect(Collectors.groupingBy(Osoba::getMestoStanovanja, Collectors.toList()));
		
		System.out.printf("%10s | %10s | %19s\n-----------+------------+------------------\n", 
				"Grad", "Broj ljudi", "Prosecna primanja");
		m1.entrySet().stream()
			.forEach(e -> System.out.printf(
					"%10s | %10d | %17.2f\n", 
					e.getKey(), 
					e.getValue().stream().count(), 
					e.getValue().stream().collect(Collectors.averagingDouble(Osoba::getPrimanja))));
		System.out.println();
		
		// treci nacin
		Map<String, Long> h1 = Osobe.osobeStream(BROJ_OSOBA)
				.collect(Collectors.groupingBy(Osoba::getMestoStanovanja, Collectors.counting()));
		
		Map<String, Double> h2 = Osobe.osobeStream(BROJ_OSOBA)
				.collect(Collectors.groupingBy(Osoba::getMestoStanovanja, Collectors.summingDouble(Osoba::getPrimanja)));
		
		System.out.printf("%10s | %10s | %19s\n-----------+------------+------------------\n", 
				"Grad", "Broj ljudi", "Prosecna primanja");
		h1.entrySet().stream()
		.forEach(e -> System.out.printf("%10s | %10d | %17.2f\n", e.getKey().toUpperCase(), e.getValue(), h2.get(e.getKey()) / e.getValue()));
		System.out.println();
	}
	
	
	private static void tabela2() {
		
		Map<String, List<Osoba>> m1 = Osobe.osobeStream(BROJ_OSOBA)
				.map(o -> getSve(o))
				.flatMap(Collection::stream)
				.collect(Collectors.groupingBy(Osoba::getIme, Collectors.toList()));
		
		System.out.printf(
				"%10s | %11s | %12s | %12s\n-----------+--------------+-----------------+-----------------\n", 
				"Ime", "Br roditelja", "Broj muske dece", "Broj zenske dece");
		
		m1.entrySet().stream()
			.forEach(e -> System.out.printf("%10s | %12d | %15d | %16d\n",
					e.getKey(),
					e.getValue().stream().filter(o -> o.getBrDece() != 0).count(),
					e.getValue().stream().map(o -> o.getBrDece(Pol.MUSKI)).reduce(0, (a, b) -> a + b),
					e.getValue().stream().map(o -> o.getBrDece(Pol.ZENSKI)).reduce(0, (a, b) -> a + b)
					));
	}
	
	
	private static void tabela3() {
		
		TreeMap<Integer, List<Osoba>> m1 = Osobe.osobeStream(BROJ_OSOBA)
				.collect(Collectors.groupingBy(
						o -> LocalDate.now().getYear() - o.getDatumRodjenja().getYear(),
						TreeMap::new,
						Collectors.toList()
						));
		
		System.out.printf("%6s | %9s \n%6s | %9s | %9s | %10s | %9s | %10s\n-------+-----------+-----------+------------+-----------+-----------\n", 
				"Godine", "Primanja", "zivota", "Najnize", "Najvise", "Ukupno", "Prosek", "Devijacija");
		
		m1.entrySet().stream()
		.forEach(e -> System.out.printf("%6d | %9.2f | %9.2f | %10.2f | %9.2f | %10.2f \n", 
				e.getKey(),
				e.getValue().stream().mapToDouble(Osoba::getPrimanja).reduce((d1, d2) -> d1 < d2 ? d1 : d2).getAsDouble(),
				e.getValue().stream().mapToDouble(Osoba::getPrimanja).reduce((d1, d2) -> d1 > d2 ? d1 : d2).getAsDouble(),
				e.getValue().stream().mapToDouble(Osoba::getPrimanja).reduce(0, Double::sum),
				e.getValue().stream().collect(Collectors.averagingDouble(Osoba::getPrimanja)),
				100.0 * Math.sqrt(	// racunato prema formuli : https://sh.wikipedia.org/wiki/Standardna_devijacija
						1.0 / e.getValue().stream().count() * 
						(e.getValue().stream().collect(
								Collectors.summingDouble(
										o -> Math.pow(o.getPrimanja() - e.getValue().stream().collect(
												Collectors.averagingDouble(Osoba::getPrimanja)), 2))))) / 
												e.getValue().stream().mapToDouble(Osoba::getPrimanja).reduce((d1, d2) -> d1 > d2 ? d1 : d2).getAsDouble()
				));
	}
}

