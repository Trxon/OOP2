package vezba.kol1_drzave_p03;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Drzave {

	
	private List<Drzava> drzave;

	
	public List<Drzava> drzave() 	{ return drzave; 	}
	
	
	public void setDrzave(List<Drzava> drzave) { this.drzave = drzave; }
	
	
	public int brojDrzava() {
		
		int n = (int) drzave.stream().count();
		
		System.out.println("Broj drzava : " + n);
		return n;
	}
	
	
	public double prosecnoBogate() {
		
		double d = drzave.stream().collect(Collectors.averagingDouble(Drzava::budzet));
		
		System.out.printf("Prosek budzeta drzava : %.2f %n", d);
		return d;
	}
	
	
	public Drzava najbogatija() {
		
		Drzava d = drzave.stream().collect(Collectors.maxBy(Comparator.comparing(Drzava::budzet))).get();
		
		System.out.println("Najbogatija drzava : " + d.zemlja());
		return d;
	}
	
	
	public void sortiraniPoImenu(boolean opadajuce) {
		
		Comparator<Drzava> c = opadajuce ? 
				Comparator.comparing(Drzava::zemlja).reversed() : Comparator.comparing(Drzava::zemlja);
		
		System.out.println("Drzave sortirane po imenu " + (opadajuce ? "(opadajuce) : " : ": "));
		drzave.stream()
			.sorted(c)
			.map(Drzava::zemlja)
			.forEach(s -> System.out.println("  " + s));
	}
	
	
	public void sortiraniPoBrojuStanovnika(boolean opadajuce) {
		
		Comparator<Drzava> c = opadajuce ? 
				Comparator.comparing(Drzava::brojStanovnika).reversed() : Comparator.comparing(Drzava::brojStanovnika);
		
		System.out.println("Drzave sortirane po broju stanovnika " + (opadajuce ? "(opadajuce) : " : ": "));
		drzave.stream()
			.sorted(c)
			.map(Drzava::zemlja)
			.forEach(s -> System.out.println("  " + s));
	}
	
	
	public double prosecanBrojStanovnikaSvihDrzava() {
		
		double d = drzave.stream().collect(Collectors.averagingDouble(Drzava::brojStanovnika));
		
		System.out.printf("Prosecan broj stanovnika svih drzava : %.2f %n", d);
		return d;
	}
	
	
	public Drzava najbogatijaDrzavaSpramBrojaStanovnikaINovcaUBudzetu() {
		
		Drzava d = drzave.stream()
				.max(Comparator.comparing(r -> r.budzet() / r.brojStanovnika())).get();
		
		System.out.println("Najbogatija drzava spram broja stanovnika i novca u budzetu : " + d.zemlja());
		return d;
	}
	
	
	public Map<String, Set<Drzava>> drzaveRazvrstanePoKontinentima() {
		
		return drzave.stream().collect(Collectors.groupingBy(Drzava::kontinent, Collectors.toSet()));
	}
	
	
	public boolean postojiDrzavaSaViseStanovnikaOd(int broj) {
		
		boolean b = drzave.stream().anyMatch(d -> d.brojStanovnika() > broj);
		
		System.out.println((b ? "P" : "Ne p") + "ostoji drzava sa vise stanovnika od " + broj + ".");
		return b;
	}
	
	
	public boolean postojiDrzavaKojaSeNalaziNaKontinentu(String kontinent) {
		
		boolean b = drzave.stream().anyMatch(d -> d.kontinent().equalsIgnoreCase(kontinent));
		
		System.out.println((b ? "P" : "Ne p") + "ostoji drzava na kontinentu " + kontinent + ".");
		return b;
	}
	
	
	public List<String> najbogatijeDrzave() {
		
		return drzave.stream()
				.filter(d -> d.budzet() > drzave.stream().max(Comparator.comparing(Drzava::budzet)).get().brojStanovnika() * .75)
				.map(Drzava::zemlja)
				.collect(Collectors.toList());
	}
	
	
	public List<Drzava> opadajuceSortiraniPoBudzetu() {
		
		return drzave.stream().sorted(Comparator.comparing(Drzava::budzet).reversed()).collect(Collectors.toList());
	}
	
	public Drzava najsiromasnija() {
		
		Drzava d = drzave.stream().max(Comparator.comparing(Drzava::budzet).reversed()).get();
		
		System.out.println("Najsiromasnija drzava : " + d.zemlja());
		return d;
	}
	
	
	public String najbogatijiKontinent() {
		
		String s = drzave.stream()
				.collect(Collectors.groupingBy(
						Drzava::kontinent, 
						Collectors.summingDouble(Drzava::budzet)))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue()).get().getKey();
		
		System.out.println("Najbogatiji kontinent : " + s);
		return s;
	}
}
