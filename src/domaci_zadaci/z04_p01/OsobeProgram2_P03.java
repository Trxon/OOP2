package domaci_zadaci.z04_p01;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class OsobeProgram2_P03 {
	
	
	static final private int BROJ_OSOBA = 50;
	

	public static void main(String[] args) {
		
//		probniMetod();
		
		sortiraniPoDatumuRodjenja();
	}
	
	
	private static void sortiraniPoDatumuRodjenja() {
		
		System.out.println("Sortirani po datumu rodjenja : ");
		Osobe.osobeStream(BROJ_OSOBA)
			.sorted(Comparator.comparing(Osoba::getDatumRodjenja))
			.map(o -> String.format("%s %s %s", o.getIme(), o.getPrezime(), o.getDatumRodjenja()))
			.forEach(System.out::println);
	}
	

	public static void probniMetod() {
		
		class Data {
			
			int m;
			int z;
			
			public void add(Osoba osoba) {
				this.m += (int) osoba.getDeca().stream().filter(o -> o.getPol() == Pol.MUSKI).count();
				this.z += (int) osoba.getDeca().stream().filter(o -> o.getPol() == Pol.ZENSKI).count();
			}
			
			public Data join(Data d) {
				
				this.m += d.m;
				this.z += d.z;
				
				return this;
			}
			
			@Override
			public String toString() {
				return "[" + m + ", " + z + "]";
			}
		}
		
		Map<Object, List<Osoba>> m0 = Osobe.osobeStream(BROJ_OSOBA)
				.collect(Collectors.groupingBy(
						o -> o.getIme(),
						Collectors.toList()));
		
		Map<Object, Data> m1 = m0.entrySet().stream().collect(
				Collectors.toMap(
						Map.Entry::getKey,
						e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))
				));
		
		System.out.println(m1);
	}
}
