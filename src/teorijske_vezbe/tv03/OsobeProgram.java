package teorijske_vezbe.tv03;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class OsobeProgram {

	
	private static final LocalDate GRANICA = LocalDate.of(1970, 1, 1);
	
	
	public static void main(String[] args) {
		
//		Osobe.stream(500);
		String[] nizStringova = Osobe.random(500);
		Arrays.stream(nizStringova)
			
			.map(Osoba::new)
//			Osoba[] nizOsoba = new Osoba[nizStringova.length];
//			for (int i = 0; i < nizStringova.length; i++)
//				nizOsoba[i] = new Osoba(nizStringova[i]);
			
			.sorted(Comparator.comparing(Osoba::getPlata))
//			Arrays.sort(nizOsoba, (x, y) -> x.getPlata() - y.getPlata());
			
			.filter(o -> o.getDatumRodjenja().compareTo(GRANICA) < 0)
//			List<Osoba> pre1970 = new ArrayList<>();
//			for (Osoba osoba : ispodProseka)
//				if (osoba.getDatumRodjenja().compareTo(GRANICA) < 0)
//					pre1970.add(osoba);
			
			.limit(5)
//			List<Osoba> najsiromasniji = new ArrayList<>();
//			for (int i = 0; i < 5; i++)
//				najsiromasniji.add(pre1970.get(i));
			
			.flatMap(o -> o.getDeca().stream())
//			List<String> imena = new ArrayList<>();
//			for (Osoba osoba : najsiromasnsiji)
//				for (String ime : osoba.getDeca())
//					imena.add(ime);
			
			.sorted()
//			Collections.sort(imena);
			
			.distinct()
//			String proslo = null;
//			Iterator<String> iterator = imena.iterator();
//			while (iterator.hasNext()) {
//				String ime = iterator.next();
//				
//				if (ime.equals(proslo))
//					iterator.remove();
//				else
//					proslo = ime;
//			}
			
			.forEach(System.out::print)
//			for (String ime : imena)
//				System.out.println(ime);
		;
	}
}
