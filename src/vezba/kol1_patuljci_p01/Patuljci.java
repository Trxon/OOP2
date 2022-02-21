package vezba.kol1_patuljci_p01;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Patuljci {

	
	private List<Patuljak> patuljci;
	
	
	public Patuljci() {
		this.patuljci = new ArrayList<Patuljak>();
	}
	
	
	public void dodajPatuljka(Patuljak p) {
		patuljci.add(p);
	}
	
	
	public int brPatuljaka1() {
		return (int) patuljci.stream().count();
	}
	
	
	public int brPatuljaka2() {
		return (int) patuljci.stream().collect(Collectors.summarizingInt(p -> 1)).getCount();
	}
	
	
	public double prosecnoIskopaliZlata1() {
		return patuljci.stream().collect(Collectors.averagingDouble(Patuljak::iskopaoZlata));
	}
	
	
	public double prosecnoIskopaliZlata2() {
		return patuljci.stream().collect(Collectors.summarizingDouble(Patuljak::iskopaoZlata)).getAverage();
	}
	
	
	public double ukupnoIskopaliZlataOniNaSlovo1(char c) {
		return patuljci.stream().filter(p -> p.ime().charAt(0) == c)
				.collect(Collectors.summingDouble(Patuljak::iskopaoZlata));
	}
	
	
	public double ukupnoIskopaliZlataOniNaSlovo2(char c) {
		return patuljci.stream().filter(p -> p.ime().charAt(0) == c)
				.collect(Collectors.summarizingDouble(Patuljak::iskopaoZlata)).getSum();
	}
	
	
	public double ukupnoIskopaliZlataOniNaSlovo3(char c) {
		return patuljci.stream().filter(p -> p.ime().charAt(0) == c)
				.map(Patuljak::iskopaoZlata).reduce(0.0, (z1, z2) -> z1 + z2).doubleValue();
	}
	
	
	public double ukupnoIskopaliZlataOniNaSlovo4(char c) {
		return patuljci.stream().filter(p -> p.ime().charAt(0) == c)
				.mapToDouble(Patuljak::iskopaoZlata).sum();
	}
	
	
	public Patuljak najbogatiji1() {
		return patuljci.stream().reduce((p1, p2) -> p1.iskopaoZlata() < p2.iskopaoZlata() ? p2 : p1).get();
	}
	
	
	public Patuljak najbogatiji2() {
		return patuljci.stream().max(Comparator.comparing(Patuljak::iskopaoZlata)).get();
	}
	
	
	public int ukupnoUbiliGoblina(Predicate<Patuljak> predicate) {
		return patuljci.stream().filter(predicate).map(Patuljak::ubioGoblina).reduce(0, (p1, p2) -> p1 + p2);
	}
	
	
	public List<Patuljak> opadajuceSortiraniPoBrojuUbijenihGoblina() {
		return patuljci.stream().sorted(Comparator.comparing(Patuljak::ubioGoblina).reversed()).collect(Collectors.toList());
	}
	
	
	public Set<Patuljak> patuljciStarijiOd(int godina) {
		return patuljci.stream().filter(p -> p.godinaRodjenja() < godina).collect(Collectors.toSet());
	}
	
	
	public List<String> imenaPatuljakaRastuceSortiranaPoPrezimenu() {
		return patuljci.stream().filter(p -> p.ime().contains(" ")).map(Patuljak::ime)
				.sorted(Comparator.comparing(s -> s.split(" ")[1])).collect(Collectors.toList());
	}
	
	
	public Map<Integer, Set<Patuljak>> patuljciRazvrstaniPoGodiniRodjenja() {
		return patuljci.stream().collect(Collectors.groupingBy(Patuljak::godinaRodjenja, Collectors.toSet()));
	}
	
	
	public boolean postojiPatuljakSaViseZlataOd1(double zlato) {
		return patuljci.stream().filter(p -> p.iskopaoZlata() > zlato).findAny() != null;
	}
	
	
	public boolean postojiPatuljakSaViseZlataOd2(double zlato) {
		return patuljci.stream().anyMatch(p -> p.iskopaoZlata() > zlato);
	}
}
