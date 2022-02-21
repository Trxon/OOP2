package vezba.kol2_covid_g05_p01;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SesijaImpl extends UnicastRemoteObject implements Sesija {


	private static final long serialVersionUID = 1L;
	
	
	private List<Zemlja> zemlje;
	private List<Podatak> podaci;
	

	protected SesijaImpl() throws RemoteException {
		super();
	}
	

	protected SesijaImpl(List<Zemlja> zemlje, List<Podatak> podaci) throws RemoteException {
		super();
		this.zemlje = zemlje;
		this.podaci = podaci;
	}
	

	@Override
	public Poruka posaljiZahtev(String unos) throws RemoteException {
		
		System.out.println("Sesija " + this + " -> " + unos);
		
		if (unos.trim().equals("2")) {
			
			return new Poruka(novozarazeneNaDan());
		} else if (unos.trim().equals("3")) {
			
			return new Poruka(tabelarniPrikaz());
		} else {
			
			List<String> continents = Stream.of(
					"Asia", "Europe", "America", "Africa", "Other", "Oceania")
					.collect(Collectors.toList()); 
			
			String[] tokens = unos.trim().split(",");
			
			if (tokens[1].trim().equalsIgnoreCase("1") && continents.contains(tokens[0].trim()))
				return new Poruka("Najvise umrlih : " + najviseUmrlihNaKontinentu(tokens[0].trim()).getCountry());
			else
				return new Poruka(new String("Pogresan unos..."));
		}
	}
	
	
	
	public String tabelarniPrikaz() {
		
		Map<Zemlja, Data> m1 = zbirniPodaci("");
		
		class Temp {
			
			int g0, g1, g2;
			
			public void add(Zemlja z) {
				
				double d = m1.get(z).getPercent();
				
				if (d < 1.0)
					g0++;
				else if (d < 3.0)
					g1++;
				else
					g2++;
			}
			
			public Temp join(Temp t) {
				this.g0 += t.g0;
				this.g1 += t.g1;
				this.g2 += t.g2;
				
				return this;
			}
		}
		
		Map<String, List<Zemlja>> m2 = zemlje.stream()
				.collect(Collectors.groupingBy(Zemlja::getContinent, Collectors.toList()));
		
		Map<String, Temp> m3 = m2.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().collect(Collector.of(Temp::new, Temp::add, Temp::join))));
		
		return m3.entrySet().stream()
				.map(e -> String.format("%10s | %2d | %2d | %2d", e.getKey(), e.getValue().g0, e.getValue().g1, e.getValue().g2))
				.reduce("", (s1, s2) -> s1 == null ? s2 : s1 + "\n" + s2);
	}
	
	
	public Map<LocalDate, List<Zemlja>> novozarazeneNaDan() {
		
		Map<LocalDate, List<Zemlja>> m0 = zemlje.stream()
				.collect(Collectors.groupingBy(z -> {
					
					return this.podaci.stream()
					.filter(p -> p.getCountry().getCountry().equalsIgnoreCase(z.getCountry()))
					.map(p -> p.getDate())
					.sorted()
					.findFirst().get();
					
				}, Collectors.toList()));
		
		return m0.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
	
	
	private Map<Zemlja, Data> zbirniPodaci(String kontinent) {
		
		Map<Zemlja, List<Podatak>> m0 = podaci.stream()
				.filter(p -> "".equals(kontinent) ? p != null : p.getCountry().getContinent().equalsIgnoreCase(kontinent))
				.collect(Collectors.groupingBy(Podatak::getCountry, Collectors.toList()));
		
		return m0.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))));
	}
	
	
	public Zemlja najviseUmrlihNaKontinentu(String kontinent) {
		
		Map<Zemlja, Data> m1 = zbirniPodaci(kontinent);
		
		class Temp {
			
			int d;
			Zemlja c;
		}
		
		return m1.entrySet().stream()
				.map(e -> {
					Temp t = new Temp();
					t.d = e.getValue().getDeaths();
					t.c = e.getKey();
					
					return t;
				}).max(Comparator.comparingInt(t -> t.d))
				.map(t -> t.c).get();
	}

	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
