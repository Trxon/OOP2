package domaci_zadaci.z04_p02;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collector;
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

public class OsobeProgram2 {
	
	public static void main(String[] args) {
		
//		prosecnaPrimanjaPoMestu();
//		saNajvecimPrimanjimaZaSvakiGrad();
//		razvrstaniPoBrojuDece();
//		poMestuIBrojuDece();

//		Predicate<Osoba> starosedelaca = o -> o.getMestoRodjenja().equals(o.getMestoStanovanja());
//		gradSaNajvise(starosedelaca);

//		Predicate<Osoba> doseljenika = o -> !o.getMestoRodjenja().equals(o.getMestoStanovanja());
//		gradSaNajvise(doseljenika);
		
//		najbogatijiGrad();
//		najpopularnijeIme(Pol.MUSKI);
//		najpopularnijeIme(Pol.ZENSKI);
		
//		najbogatijaOsoba();
//		muskaImena();
//		najstarijaOsoba();
		
//		tabela1();
//		tabela2();
//		tabela3();
	}
	
	
	private static List<Osoba> opadajuceSortiraniPoDatumuRodjenja() {
		
		return Osobe.osobeStream(5000)
				.sorted(Comparator.comparing(Osoba::getDatumRodjenja).reversed())
				.collect(Collectors.toList());
	}
	
	
	private static Set<Osoba> saVecimPrimanjimaOd(int limit) {
		
		return Osobe.osobeStream(5000)
				.filter(o -> o.getPrimanja() > limit)
				.collect(Collectors.toSet());
	}
	
	
	private static double prosecnaPrimanjaUMestu(String mesto) {
		
		return prosecnaPrimanjaPoMestu().get(mesto);
	}
	
	
	private static Map<String, Double> prosecnaPrimanjaPoMestu() {
		
		return Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja,
						Collectors.averagingDouble(Osoba::getPrimanja)));
						
	}
	
	
	private static Map<String, Osoba> saNajvecimPrimanjimaZaSvakiGrad() {
		
		return Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja, 
						Collectors.collectingAndThen(
								Collectors.maxBy(Comparator.comparingDouble(Osoba::getPrimanja)), Optional::get)));
	}
	
	
	private static Map<Integer, List<Osoba>> razvrstaniPoBrojuDece() {
		
		return Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(
						Osoba::getBrDece, 
						Collectors.toList()));
	}
	

	private static Map<String, Map<Integer, List<Osoba>>> poMestuIBrojuDece() {
		
		return Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja, 
						Collectors.groupingBy(
								Osoba::getBrDece, 
								Collectors.toList())));
	}


	private static void gradSaNajvise(Predicate<Osoba> p) {
		
		String grad = Osobe.osobeStream(5000)
				.filter(p)
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja, 
						Collectors.counting()))
		.entrySet().stream()
				.max(Map.Entry.comparingByValue()).get().getKey();
		
		System.out.println("Grad sa najvise : " + grad);
	}


	private static void najbogatijiGrad() {
		
		String grad = Osobe.osobeStream(5000)
						.collect(Collectors.groupingBy(
								Osoba::getMestoStanovanja, 
								Collectors.summingDouble(Osoba::getPrimanja)))
				.entrySet().stream()
						.max(Map.Entry.comparingByValue()).get().getKey();
		
		System.out.println("Najbogatiji grad : " + grad);
	}


	private static void najpopularnijeIme(Pol p) {
		
		String ime = Osobe.osobeStream(5000)
						.flatMap(o -> Stream.concat(Stream.of(o), o.getDeca().stream()))
						.filter(o -> o.getPol() == p)
						.collect(Collectors.groupingBy(Osoba::getIme, Collectors.counting()))
				.entrySet().stream()
						.max(Map.Entry.comparingByValue()).get().getKey();
		
		System.out.println("Najpopularnije ime za pol " + p + " : " + ime);
	}


	private static void najbogatijaOsoba() {
		
		Osoba osoba = Osobe.osobeStream(5000)
				.reduce(null, 
						(o1, o2) -> o1 == null ? o2 : 
							(o1.getPrimanja() - o2.getPrimanja() > 0 ? o1 : o2));
		
		System.out.println(osoba + ", " + osoba.getPrimanja());
	}


	private static void muskaImena() {
		
		String out = Osobe.osobeStream(5000)
				.flatMap(o -> Stream.concat(Stream.of(o), o.getDeca().stream()))
				.filter(o -> o.getPol() == Pol.MUSKI)
				.map(Osoba::getIme)
				.distinct().sorted()
				.reduce("", (s1, s2) -> "".equals(s1) ? s2 : s1 + ", " + s2);
		
		System.out.println("Muska imena : " + out);
	}


	private static void najstarijaOsoba() {
		
		int god = Osobe.osobeStream(5000)
				.max(Comparator.comparingInt(o -> LocalDate.now().getYear() - o.getDatumRodjenja().getYear()))
				.get().getDatumRodjenja().getYear();
		
		Osoba osoba = Osobe.osobeStream(5000)
				.reduce(null, 
						(o1, o2) -> o1 == null ? o2 : 
							(o1.getDatumRodjenja().compareTo(o2.getDatumRodjenja()) < 0 ? o1 : o2));
		
		System.out.println("Najstarija osoba rodjena godine : " + god);
		System.out.println("Najstarija osoba rodjena godine : " + osoba.getDatumRodjenja().getYear());
	}


	private static void tabela1() {
		
		Map<String, List<Osoba>> m0 = Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(Osoba::getMestoStanovanja, Collectors.toList()));
		
		class Data {
			
			int cnt, prm;
			
			public void add(Osoba o) {
				
				cnt += 1;
				prm += o.getPrimanja();
			}
			
			public Data join(Data d) {
				
				cnt += d.cnt;
				prm += d.prm;
				
				return this;
			}
		}
		
		Map<String, Data> m1 = m0.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))));
		
		System.out.println("Grad       | Broj ljudi | Prosecna primanja");
		System.out.println("-----------+------------+------------------");
		
		m1.entrySet().stream()
				.map(e -> String.format("%10s | %10d | %17.2f", e.getKey(), e.getValue().cnt, 1.0 * e.getValue().prm / e.getValue().cnt))
				.forEach(System.out::println);
	}


	private static void tabela2() {
		
		Map<String, List<Osoba>> m0 = Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(o -> (String) o.getIme(), Collectors.toList()));
		
		class Data {
			
			int u, m, z;
			
			public void add(Osoba o) {
				
				u += 1;
				m += o.getBrDece(Pol.MUSKI);
				z += o.getBrDece(Pol.ZENSKI);
			}
			
			public Data join(Data d) {
				
				u += d.u;
				m += d.m;
				z += d.z;
				
				return this;
			}
		}
		
		Map<String, Data> m1 = m0.entrySet().stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey, 
						e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))));
		
		System.out.println("Ime        | Br roditelja | Broj muske dece | Broj zenske dece ");
		System.out.println("-----------+--------------+-----------------+------------------");
		
		m1.entrySet().stream()
				.map(e -> String.format("%10s | %12d | %15d | %16d", 
						e.getKey(), e.getValue().u, e.getValue().m, e.getValue().z))
				.forEach(System.out::println);
	}


	private static void tabela3() {
		
		Map<Integer, List<Osoba>> m0 = Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(
						o -> (Integer) LocalDate.now().getYear() - o.getDatumRodjenja().getYear(), 
						Collectors.toList()));
		
		class Data {
			
			double max, min, sum, cnt;
			List<Integer> lst;
			
			public Data() {
				this.max = Double.MIN_VALUE;
				this.min = Double.MAX_VALUE;
				this.sum = 0;
				this.cnt = 0;
				this.lst = new ArrayList<Integer>();
			}
			
			public void add(Osoba o) {
				
				if (o.getPrimanja() < min)
					min = o.getPrimanja();
				
				if (o.getPrimanja() > max)
					max = o.getPrimanja();
				
				sum += o.getPrimanja();
				cnt += 1;
				lst.add(o.getPrimanja());
			}
			
			public Data join(Data d) {
				
				if (d.min < min)
					min = d.min;
				
				if (d.max > max)
					max = d.max;
				
				sum += d.sum;
				cnt += d.cnt;
				lst.addAll(d.lst);
				
				return this;
			}
			
			public double getDeviation() {
				
				double avg = getAverage();
				
				return Math.sqrt(lst.stream()
						.map(n -> (n - avg) * (n - avg))
						.reduce(0.0, (n0, n1) -> n0 + n1) * (1.0 / cnt));
			}
			
			public double getAverage() {
				return 1.0 * sum / cnt;
			}
		}
		
		Map<Integer, Data> m1 = m0.entrySet().stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey, 
						e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))));
		
		System.out.println("Godine | Primanja  |           |            |           |            ");
		System.out.println("zivota | Najnize   | Najvise   | Ukupno     | Prosek    | Devijacija ");
		System.out.println("-------+-----------+-----------+------------+-----------+------------");
		m1.entrySet().stream()
				.map(e -> String.format("%6d | %9.2f | %9.2f | %9.2f | %9.2f | %9.2f", 
						e.getKey(), e.getValue().min, e.getValue().max, e.getValue().sum, e.getValue().getAverage(), e.getValue().getDeviation()))
				.forEach(System.out::println);
	}
}

