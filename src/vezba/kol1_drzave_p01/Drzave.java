package vezba.kol1_drzave_p01;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Drzave {

	
	private List<Drzava> drzave;
	
	
	public Drzave() {
		this.drzave = new ArrayList<Drzava>();
	}
	
	
	public void dodajDrzavu(Drzava d) {
		drzave.add(d);
	}
	
	
	public void sve() {
		drzave.stream().forEach(System.out::println);
	}
	
	
	public int brojDrzava() {
		return (int) drzave.stream().count();
	}
	
	
	public double prosecnoBogate() {
		return drzave.stream().mapToDouble(Drzava::budzet).average().getAsDouble();
	}
	
	
	public Drzava najbogatija() {
		return drzave.stream().max(Comparator.comparing(Drzava::budzet)).get();
	}
	
	
	public Drzava najsiromasnija() {
		return drzave.stream().min(Comparator.comparing(Drzava::budzet)).get();
	}
	
	
	public void sortiraniPoImenu(boolean opadajuce) {
		if (opadajuce) 	drzave.stream().sorted(Comparator.comparing(Drzava::ime).reversed())
							.map(Drzava::ime).forEach(System.out::println);
		else			drzave.stream().sorted(Comparator.comparing(Drzava::ime))			
							.map(Drzava::ime).forEach(System.out::println);
	}
	
	
	public void sortiraniPoBrojuStanovnika() {
		drzave.stream().sorted(Comparator.comparing(Drzava::brStanovnika)).map(Drzava::ime).forEach(System.out::println);
	}
	
	
	double prosecanBrojStanovnikaSvihDrzava() {
		return drzave.stream().collect(Collectors.summarizingDouble(Drzava::brStanovnika)).getAverage();
	}
	
	
	public Drzava najbogatijaDrzavaSpramBrojaStanovnikaINovcaUBudzetu() {
		return drzave.stream()
				.reduce((d1, d2) -> d1.budzet() / d1.brStanovnika() > d2.budzet() / d2.brStanovnika() ? d1 : d2).get();
	}
	
	
	public Map<String, Set<Drzava>> drzaveRazvrstanePoKontinentima() {
		return drzave.stream().collect(Collectors.groupingBy(Drzava::kontinent, Collectors.toSet()));
	}
	
	
	public boolean postojiDrzavaSaViseStanovnikaOd(int broj) {
		return drzave.stream().anyMatch(d -> d.brStanovnika() > broj);
	}
	
	
	public boolean postojiDrzavaKojaSeNalaziNaKontinentu(String k) {
		return drzave.stream().anyMatch(d -> d.kontinent().equalsIgnoreCase(k));
	}
	
	
	public List<String> najbogatijeDrzave() {
		return drzave.stream().filter(d -> d.budzet() > najbogatija().budzet() * 0.75).map(Drzava::ime).collect(Collectors.toList());
	}
	
	
	public List<Drzava> opadajuceSortiraniPoBudzetu() {
		return drzave.stream().sorted(Comparator.comparing(Drzava::budzet).reversed()).collect(Collectors.toList());
	}
	
	
	public String najbogatijiKontinent() {
		Map<String, Double> m = drzave.stream().collect(
				Collectors.groupingBy(Drzava::kontinent, Collectors.summingDouble(Drzava::budzet)));
		return m.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey();
	}
}
