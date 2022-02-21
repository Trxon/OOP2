package domaci_zadaci.z04_p01;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

public class OsobeProgram2_P02 {
	
	
    /***************************
     * NIKOLA VETNIC 438/19 IT *
     ***************************/
	
	
	static final private int BROJ_OSOBA = 25;

	
	public static void main(String[] args) {
//		svi();
		
//		System.out.println(opadajuceSortiraniPoDatumuRodjenja());
//		System.out.println(saVecimPrimanjimaOd(100000));
//		System.out.println(prosecnaPrimanjaUMestu("Kikinda"));
//		System.out.println(saNajvecimPrimanjimaZaSvakiGrad1());
//		System.out.println(saNajvecimPrimanjimaZaSvakiGrad2());
//		System.out.println(saNajvecimPrimanjimaZaSvakiGrad3());
//		System.out.println(saNajvecimPrimanjimaZaSvakiGrad4());
//		System.out.println(razvrstaniPoBrojuDece1());
//		System.out.println(razvrstaniPoBrojuDece2());
//		System.out.println(poMestuIBrojuDece1());
//		System.out.println(poMestuIBrojuDece2());
		System.out.println(gradSaNajviseDoseljenika());
		System.out.println(gradSaNajviseStarosedelaca());
//		System.out.println(najbogatijiGrad1());
//		System.out.println(najbogatijiGrad2());
//		System.out.println(najpopularnijeMuskoIme());
//		System.out.println(najpopularnijeIme2(Pol.MUSKI));
//		System.out.println(najpopularnijeIme3(Pol.MUSKI));
//		System.out.println(najpopularnijeZenskoIme());
		
//		najbogatijaOsoba1();
//		najbogatijaOsoba2();
//		muskaImena1();
//		muskaImena2();
//		godinaRodjenjaNajstarije1();
//		godinaRodjenjaNajstarije2();
		
//		prvaTabela1();
//		prvaTabela2();
//		prvaTabela3();
//		prvaTabela4();
//		prvaTabela5();
		
		drugaTabela1();
//		drugaTabela2();
		
//		trecaTabela();
	}
	

	private static void svi() {
		Osobe.osobeStream(BROJ_OSOBA)
			.forEach(System.out::println);
	}
	
	
	private static List<Osoba> opadajuceSortiraniPoDatumuRodjenja() {
		return Osobe.osobeStream(BROJ_OSOBA)
				.sorted(Comparator.comparing(Osoba::getDatumRodjenja).reversed())
				.collect(Collectors.toList());
	}
	
	
	private static Set<Osoba> saVecimPrimanjimaOd(int iznos) {
		return Osobe.osobeStream(BROJ_OSOBA)
				.filter(o -> o.getPrimanja() > iznos)
				.collect(Collectors.toSet());
	}
	
	
	private static double prosecnaPrimanjaUMestu(String mesto) {
		return Osobe.osobeStream(BROJ_OSOBA)
				.filter(o -> o.getMestoStanovanja().equalsIgnoreCase(mesto))
				.collect(Collectors.averagingDouble(Osoba::getPrimanja));
	}
	
	
	private static Map<String, Double> prosecnaPrimanjaPoMestu() {
		return Osobe.osobeStream(BROJ_OSOBA)
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja, 
						Collectors.averagingDouble(Osoba::getPrimanja)));
	}
	
	
	private static Map<String, Osoba> saNajvecimPrimanjimaZaSvakiGrad1() {
		return Osobe.osobeStream(BROJ_OSOBA)
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja,
						Collectors.collectingAndThen(
								Collectors.reducing(
										BinaryOperator.maxBy(Comparator.comparing(Osoba::getPrimanja))), 
								Optional::get)));
	}
	
	
	private static Map<String, Osoba> saNajvecimPrimanjimaZaSvakiGrad2() {
		return Osobe.osobeStream(BROJ_OSOBA)
				.collect(Collectors.toMap(
						Osoba::getMestoStanovanja, 
						Function.identity(), 
						(o1, o2) -> o1.getPrimanja() < o2.getPrimanja() ? o2 : o1));
	}
	
	
	private static Map<String, Osoba> saNajvecimPrimanjimaZaSvakiGrad3() {
		return Osobe.osobeStream(BROJ_OSOBA)
				.collect(Collectors.toMap(
						Osoba::getMestoStanovanja, 
						Function.identity(), 
						(o1, o2) -> { 
							Osoba  o = o1.getPrimanja() < o2.getPrimanja() ? o2 : o1;
							return o;
						}));
	}
	
	
	private static Map<String, Osoba> saNajvecimPrimanjimaZaSvakiGrad4() {
		return Osobe.osobeStream(BROJ_OSOBA)
				.collect(Collectors.toMap(
						Osoba::getMestoStanovanja, 
						Function.identity(), 
						BinaryOperator.maxBy(Comparator.comparing(Osoba::getPrimanja))));
	}
	
	
	private static Map<Integer, List<Osoba>> razvrstaniPoBrojuDece1() {
		return Osobe.osobeStream(BROJ_OSOBA)
				.collect(Collectors.groupingBy(
						Osoba::getBrDece, 
						Collectors.toList()));
	}
	
	
	private static Map<Integer, List<Osoba>> razvrstaniPoBrojuDece2() {
		return Osobe.osobeStream(BROJ_OSOBA)
				.collect(Collectors.groupingBy(
						Osoba::getBrDece));
	}
	
	
	private static Map<String, Map<Integer, List<Osoba>>> poMestuIBrojuDece1() {
		return Osobe.osobeStream(BROJ_OSOBA)
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja,
						Collectors.groupingBy(
								Osoba::getBrDece, 
								Collectors.toList())));
	}
	
	
	private static Map<String, Map<Integer, List<Osoba>>> poMestuIBrojuDece2() {
		return Osobe.osobeStream(BROJ_OSOBA)
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja,
						Collectors.groupingBy(
								Osoba::getBrDece)));
	}
	
	
	private static Predicate<Osoba> predicate = o -> o.getMestoStanovanja().equalsIgnoreCase(o.getMestoRodjenja());
	
	
	private static String gradSaNajviseDoseljenika() {
		return gradSaNajvise1(predicate.negate());
	}
	
	
	private static String gradSaNajviseStarosedelaca() {
		return gradSaNajvise1(predicate);
	}
	
	
	private static String gradSaNajvise1(Predicate<Osoba> predicate) {
		return Osobe.osobeStream(BROJ_OSOBA)
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja,
						Collectors.filtering(
								predicate, 
								Collectors.counting())))
				.entrySet().stream().collect(
						Collectors.collectingAndThen(
								Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(Map.Entry::getValue))), 
								o -> o.get().getKey()));
	}
	
	
	private static String gradSaNajvise2(Predicate<Osoba> predicate) {
		return Osobe.osobeStream(BROJ_OSOBA)
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja, 
						Collectors.filtering(predicate, Collectors.counting())))
				.entrySet().stream().reduce((e1, e2) -> e1.getValue() < e2.getValue() ? e2 : e1).get().getKey();
	}
	
	
	private static String gradSaNajvise3(Predicate<Osoba> predicate) {
		return Osobe.osobeStream(BROJ_OSOBA)
				.filter(predicate)
				.collect(Collectors.groupingBy(Osoba::getMestoStanovanja, Collectors.counting()))
				.entrySet().stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey)
				.orElse(null);
	}
	
	
	private static String najbogatijiGrad1() {
		return Osobe.osobeStream(BROJ_OSOBA)
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja,
						Collectors.summingInt(Osoba::getPrimanja)))
				.entrySet().stream().collect(
						Collectors.collectingAndThen(
								Collectors.reducing(
										BinaryOperator.maxBy(Comparator.comparing(Map.Entry::getValue))), 
								o -> o.get().getKey()));
	}
	
	
	private static String najbogatijiGrad2() {
		return Osobe.osobeStream(BROJ_OSOBA)
				.collect(Collectors.groupingBy(Osoba::getMestoStanovanja, Collectors.summingInt(Osoba::getPrimanja)))
				.entrySet().stream().reduce((e1, e2) -> e1.getValue() < e2.getValue() ? e2 : e1).get().getKey();
	}
	
	
	private static ArrayList<Osoba> getSve(Osoba o) {
		
		ArrayList<Osoba> out = new ArrayList<Osoba>();
		
		out.addAll(o.getDeca());
		out.add(o);
		
		return out;
	}
	
	
	private static String najpopularnijeIme1(Pol pol) {
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
	
	
	private static String najpopularnijeIme2(Pol pol) {
		return Osobe.osobeStream(BROJ_OSOBA)
				.flatMap(o -> {
					ArrayList<Osoba> l = new ArrayList<Osoba>();
					l.add(o);
					l.addAll(o.getDeca());
					return l.stream();
				})
				.filter(o -> o.getPol() == pol)
				.collect(Collectors.groupingBy(Osoba::getIme, Collectors.counting()))
				.entrySet().stream().reduce((e1, e2) -> e1.getValue() < e2.getValue() ? e2 : e1).get().getKey();
	}
	
	
	private static String najpopularnijeIme3(Pol pol) {
		return Osobe.osobeStream(BROJ_OSOBA)
				.flatMap(o -> Stream.concat(Stream.of(o), o.getDeca().stream()))
				.filter(o -> o.getPol() == pol)
				.collect(Collectors.groupingBy(Osoba::getIme, Collectors.counting()))
				.entrySet().stream().reduce((e1, e2) -> e1.getValue() < e2.getValue() ? e2 : e1).get().getKey();
	}
	
	
	private static String najpopularnijeMuskoIme() {
		return najpopularnijeIme1(Pol.MUSKI);
	}
	
	
	private static String najpopularnijeZenskoIme() {
		return najpopularnijeIme1(Pol.ZENSKI);
	}
	
	
	private static void najbogatijaOsoba1() {
		System.out.printf("Najbogatija osoba\n-----------------\n%s\n\n", Osobe.osobeStream(BROJ_OSOBA)
				.reduce((o1, o2) -> o1.getPrimanja() < o2.getPrimanja() ? o2 : o1).get());	
	}
	
	
	private static void najbogatijaOsoba2() {
		System.out.printf("Najbogatija osoba\n-----------------\n%s\n\n", Osobe.osobeStream(BROJ_OSOBA)
				.reduce(
					null, 
					(o1, o2) -> {
						if (o1 == null) 							return o2;
						if (o1.getPrimanja() > o2.getPrimanja()) 	return o1;
																	return o2;
					}));
	}
	
	
	private static void muskaImena1() {
		System.out.printf("Muska imena\n-----------\n%s\n\n", Osobe.osobeStream(BROJ_OSOBA)
				.map(o -> getSve(o))
				.flatMap(Collection::stream)
				.filter(o -> o.getPol() == Pol.MUSKI)
				.map(o -> o.getIme())
				.distinct()
				.reduce("", (s1, s2) -> "".equals(s1) ? s2 : s1 + ", " + s2));
	}
	
	
	private static void muskaImena2() {
		System.out.printf("Muska imena\n-----------\n%s\n\n", Osobe.osobeStream(BROJ_OSOBA)
				.flatMap(o -> {
					ArrayList<Osoba> l = new ArrayList<Osoba>();
					l.add(o);
					l.addAll(o.getDeca());
					return l.stream();
				})
				.filter(o -> o.getPol() == Pol.MUSKI)
				.map(Osoba::getIme)
				.distinct()
//				.reduce((s1, s2) -> s1 + " " + s2).get());
				.reduce("", (s1, s2) -> "".equals(s1) ? s2 : s1 + ", " + s2));
	}
	
	
	private static void godinaRodjenjaNajstarije1() {
		System.out.printf("Godina kada je rodjena najstarija osoba\n---------------------------------------\n%s\n\n", 
			Osobe.osobeStream(BROJ_OSOBA)
				.reduce((o1, o2) -> o1.getDatumRodjenja().compareTo(o2.getDatumRodjenja()) < 0 ? 
						 o1: o2).get().getDatumRodjenja().getYear());
	}
	
	
	private static void godinaRodjenjaNajstarije2() {
		System.out.printf("Godina kada je rodjena najstarija osoba\n---------------------------------------\n%s\n\n", 
			Osobe.osobeStream(BROJ_OSOBA)
				.map(Osoba::getDatumRodjenja)
				.mapToInt(LocalDate::getYear)
				.reduce(Integer::min).orElse(-1));
	}
	
	
	private static void prvaTabela1() {
		
		Map<String, DoubleSummaryStatistics> hm = Osobe.osobeStream(BROJ_OSOBA)
			.collect(Collectors.groupingBy(Osoba::getMestoStanovanja, Collectors.summarizingDouble(o -> o.getPrimanja())));
		
		System.out.printf("%10s | %10s | %19s\n-----------+------------+------------------\n", 
				"Grad", "Broj ljudi", "Prosecna primanja");
		
		hm.entrySet().stream()
			.forEach(e -> System.out.printf("%10s | %10d | %17.2f\n", 
				e.getKey().toUpperCase(),
				e.getValue().getCount(),
				e.getValue().getAverage()));
		
		System.out.println();
	}
	
	
	private static void prvaTabela2() {
		
		class Container {
			
			class Data {
				
				int num;
				double sum;
				
				public Data(double sum) { 
					this.num = 1; 
					this.sum = sum; 
				}
			}
			
			HashMap<String, Data> hm = new HashMap<String, Data>();
			
			public void add(String mesto, double primanja) {
				if (hm.containsKey(mesto)) {
					hm.get(mesto).num++;
					hm.get(mesto).sum += primanja;
				} else {
					hm.put(mesto, new Data(primanja));
				}
			}
		}
		
		Container container = new Container();
		
		Osobe.osobeStream(BROJ_OSOBA)
			.forEach(o -> container.add(o.getMestoStanovanja(), o.getPrimanja()));
		
		System.out.printf("%10s | %10s | %19s\n-----------+------------+------------------\n", 
				"Grad", "Broj ljudi", "Prosecna primanja");
		container.hm.entrySet().stream().forEach(e -> System.out.printf(
			"%10s | %10d | %17.2f\n", 
			e.getKey(), 
			e.getValue().num, 
			e.getValue().sum / e.getValue().num));
		System.out.println();
	}
	
	
	private static void prvaTabela3() {
		
		Map<String, List<Osoba>> hm = Osobe.osobeStream(BROJ_OSOBA)
			.collect(Collectors.groupingBy(Osoba::getMestoStanovanja, Collectors.toList()));
			
		System.out.printf("%10s | %10s | %19s\n-----------+------------+------------------\n", 
				"Grad", "Broj ljudi", "Prosecna primanja");
		
		hm.entrySet().stream()
			.forEach(e -> System.out.printf(
					"%10s | %10d | %17.2f\n", 
					e.getKey(), 
					e.getValue().stream().count(), 
					e.getValue().stream().collect(Collectors.averagingDouble(Osoba::getPrimanja))));
		
		System.out.println();
	}
	
	
	private static void prvaTabela4() {
		
		Map<String, List<Osoba>> hm = Osobe.osobeStream(BROJ_OSOBA)
			.collect(Collectors.groupingBy(Osoba::getMestoStanovanja, Collectors.toList()));
		
		System.out.printf("%10s | %10s | %19s\n-----------+------------+------------------\n", 
				"Grad", "Broj ljudi", "Prosecna primanja");
		
		hm.entrySet().stream().forEach(e -> System.out.printf(
				"%-10s | %10d | %17.2f\n", 
				e.getKey(), e.getValue().size(), prosecnaPrimanjaUMestu(e.getKey())));
		
		System.out.println();
	}
	
	
	private static void prvaTabela5() {
		
		Map<String, Long> 	hm1 = Osobe.osobeStream(BROJ_OSOBA)
				.collect(Collectors.groupingBy(Osoba::getMestoStanovanja, Collectors.counting()));
		
		Map<String, Double> hm2 = Osobe.osobeStream(BROJ_OSOBA)
				.collect(Collectors.groupingBy(Osoba::getMestoStanovanja, Collectors.averagingDouble(Osoba::getPrimanja)));
		
		System.out.printf("%10s | %10s | %19s\n-----------+------------+------------------\n", 
				"Grad", "Broj ljudi", "Prosecna primanja");
		
		hm1.entrySet().stream().forEach(e -> System.out.printf(
				"%-10s | %10d | %17.2f\n",
				e.getKey(),
				e.getValue(),
				hm2.get(e.getKey()) / e.getValue()
				));
	}
	
	
	private static void drugaTabela1() {
		
		class Container {
			
			class Data {
				int r;
				int m;
				int z;
				
				public Data(int r, int m, int z) {
					this.r = r;
					this.m = m;
					this.z = z;
				}
			}
			
			HashMap<String, Data> hm = new HashMap<String, Data>();
			
			public void add(Osoba o) {
				
				if (o.getBrDece() == 0) return;
				
				if (hm.containsKey(o.getIme())) {
					hm.get(o.getIme()).r += 1;
					hm.get(o.getIme()).m += o.getBrDece(Pol.MUSKI);
					hm.get(o.getIme()).z += o.getBrDece(Pol.ZENSKI);
				} else {
					hm.put(o.getIme(), new Data(1, o.getBrDece(Pol.MUSKI), o.getBrDece(Pol.ZENSKI)));
				}
			}
			
			public void join(Container c) {
				
				Iterator<String> it = c.hm.keySet().iterator();
				
				while (it.hasNext()) {
					
					String key = it.next();
					
					if (!hm.containsKey(key)) {
						hm.put(key, new Data(c.hm.get(key).r, c.hm.get(key).m, c.hm.get(key).z));
					} else {
						hm.get(key).r += c.hm.get(key).r;
						hm.get(key).m += c.hm.get(key).m;
						hm.get(key).z += c.hm.get(key).z;
					}
				}
			}
		}
		
		Container c = new Container();
		
		Osobe.osobeStream(BROJ_OSOBA)
			.forEach(o -> c.add(o));
		
		System.out.printf(
				"%10s | %11s | %12s | %12s\n-----------+--------------+-----------------+-----------------\n", 
				"Ime", "Br roditelja", "Broj muske dece", "Broj zenske dece");
		
		c.hm.entrySet().stream().forEach(e -> System.out.printf("%10s | %12d | %15d | %16d\n", 
				e.getKey(), e.getValue().r, e.getValue().m, e.getValue().z));
		
		System.out.println();
	}
	
	
	private static void drugaTabela2() {
		
		Map<String, List<Osoba>> hm = Osobe.osobeStream(BROJ_OSOBA)
			.filter(o -> o.getBrDece() != 0)
			.collect(Collectors.groupingBy(Osoba::getIme, Collectors.toList()));
		
		System.out.printf(
				"%10s | %11s | %12s | %12s\n-----------+--------------+-----------------+-----------------\n", 
				"Ime", "Br roditelja", "Broj muske dece", "Broj zenske dece");
		
		hm.entrySet().stream().forEach(e -> System.out.printf("%10s | %12d | %15d | %16d\n", 
				e.getKey(), 
				e.getValue().stream().count(),
				e.getValue().stream().map(o -> o.getBrDece(Pol.MUSKI )).reduce(0, (m, n) -> m + n), 
				e.getValue().stream().map(o -> o.getBrDece(Pol.ZENSKI)).reduce(0, (m, n) -> m + n)));
	}
	
	
	private static void trecaTabela() {
		
		TreeMap<Integer, List<Osoba>> tm = Osobe.osobeStream(BROJ_OSOBA)
				.collect(Collectors.groupingBy(
						o -> Period.between(o.getDatumRodjenja(), LocalDate.now()).getYears(), 
						TreeMap::new, 
						Collectors.toList()));
		
		System.out.printf("%6s | %9s \n%6s | %9s | %9s | %10s | %9s | %10s\n" +
				"-------+-----------+-----------+------------+-----------+-----------\n", 
				"Godine", "Primanja", "zivota", "Najnize", "Najvise", "Ukupno", "Prosek", "Devijacija");
		
		tm.entrySet().stream()
		.forEach(e -> System.out.printf("%6d | %9.2f | %9.2f | %10.2f | %9.2f | %10.2f \n", 
				e.getKey(), 
				e.getValue().stream().collect(Collectors.summarizingDouble(Osoba::getPrimanja)).getMin(),
				e.getValue().stream().collect(Collectors.summarizingDouble(Osoba::getPrimanja)).getMax(),
				e.getValue().stream().collect(Collectors.summarizingDouble(Osoba::getPrimanja)).getSum(),
				e.getValue().stream().collect(Collectors.summarizingDouble(Osoba::getPrimanja)).getAverage(),
				100.0 * Math.sqrt(	// racunato prema formuli : https://sh.wikipedia.org/wiki/Standardna_devijacija
						(1.0 / e.getValue().stream().count()) * e.getValue().stream().collect(
						Collectors.summingDouble(
								o -> Math.pow(
										o.getPrimanja() - 
										e.getValue().stream().collect(Collectors.averagingDouble(Osoba::getPrimanja)), 2))))
						/ e.getValue().stream().mapToDouble(Osoba::getPrimanja).average().getAsDouble()
				));
	}
}
