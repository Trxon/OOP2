package vezba.kol1_patuljci_p02;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Patuljci {
	
	
//	private static Pattern PATTERN_LINE = Pattern.compile("^(?<line>[\\s\\S]*?)$");
	private static Pattern PATTERN_LINE = Pattern.compile("(?<line>[\\s\\S]*?)\\n");
	private static Pattern PATTERN_DWARF = Pattern.compile("(?<ime>[^\\;]*?)\\;(?<ubio>[^\\;]*)\\;(?<god>[^\\;]*)\\;(?<zlato>[\\s\\S]*)");

	
	private List<Patuljak> patuljci;
	
	
	public Patuljci() {
		this.patuljci = new LinkedList<Patuljak>();
	}
	
	
	public Patuljci(String text) {
		this();
		parsirajPatuljke(text);
	}
	
	
	public void dodajPatuljka(String ime, String godinaRodjenja, String ubioGoblina, String iskopaoZlata) {
		
		Patuljak p = new Patuljak();
		
		p.setIme(ime);
		p.setGodinaRodjenja(godinaRodjenja);
		p.setUbioGoblina(ubioGoblina);
		p.setIskopaoZlata(iskopaoZlata);
		
		patuljci.add(p);
	}
	
	
	public void parsirajPatuljke(String text) {

		Matcher matcherLine = PATTERN_LINE.matcher(text);
		
		while (matcherLine.find()) {
			
			Matcher matcherDwarf = PATTERN_DWARF.matcher(matcherLine.group("line"));
			
			while (matcherDwarf.find())
				dodajPatuljka(
						matcherDwarf.group("ime"), 
						matcherDwarf.group("god"), 
						matcherDwarf.group("ubio"), 
						matcherDwarf.group("zlato"));
		}
	}
	
	
	public void stampajSve() {
		patuljci.stream().forEach(System.out::println);
	}
	
	
	public int brojPatuljaka() {
		
		int n = (int) patuljci.stream().count();
		
		System.out.println("Broj patuljaka : " + n);
		return n;
	}
	
	
	public double prosecnoIskopaliZlata() {
		
		double d = patuljci.stream().collect(Collectors.averagingDouble(Patuljak::iskopaoZlata));
		
		System.out.printf("Patuljci su prosecno iskopali zlata : %.2f %n", d);
		return d;
	}
	
	
	public double ukupnoIskopaliZlataOniNaSlovo(char c) {
		
		double d = patuljci.stream().filter(p -> p.ime().charAt(0) == c).collect(Collectors.summingDouble(Patuljak::iskopaoZlata));
		
		System.out.printf("Patuljci na slovo '" + c + "' su ukupno iskopali zlata : %.2f %n", d);
		return d;
	}
	
	
	public Patuljak najbogatiji() {
		
		Patuljak p = patuljci.stream().max(Comparator.comparing(Patuljak::iskopaoZlata)).get();
		
		System.out.println("Najbogatiji patuljak : " + p.ime());
		return p;
	}
	
	
	public int ukupnoUbiliGoblina(Predicate<Patuljak> predicate, String desc) {
		
		int n = patuljci.stream().filter(predicate).collect(Collectors.summingInt(Patuljak::ubioGoblina));
		
		System.out.println("Patuljci koji " + (desc != null ? desc : "<FALI OPIS>") + " su ukupno ubili goblina : " + n);
		return n;
	}
	
	
	public List<Patuljak> opadajuceSortiraniPoBrojuUbijenihGoblina() {
		
		List<Patuljak> l = patuljci.stream()
				.sorted(Comparator.comparing(Patuljak::ubioGoblina).reversed()).collect(Collectors.toList());
		
		String ispis = l.stream()
				.map(p -> String.format("%s (ubio : %d)", p.ime(), p.ubioGoblina()))
				.reduce("", (i1, i2) -> "".equals(i1) ? i2 : i1 + "\n" + i2);
		
		System.out.println("Opadajuce sortirani po broju ubijenih goblina : \n" + ispis);
		
		return l;
	}
	
	
	public Set<Patuljak> patuljciStarijiOd(int god) {
		
		Set<Patuljak> s = patuljci.stream().filter(p -> p.godinaRodjenja() < god).collect(Collectors.toSet());
		
		String ispis = s.stream()
				.map(p -> String.format("%s (%d)", p.ime(), p.godinaRodjenja()))
				.reduce("", (s1, s2) -> "".equals(s1) ? s2 : s1 + ", " + s2);
		
		System.out.println("Patuljci rodjeni pre " + god + " : " + ispis);
		return s;
	}
	
	
	public List<String> imenaPatuljakaRastuceSortiranaPoPrezimenu() {
		
		List<String> l = patuljci.stream().map(Patuljak::ime)
				.sorted((s1, s2) -> {
					
					String p1 = s1.split(" ").length == 2 ? s1.split(" ")[1] : "";
					String p2 = s2.split(" ").length == 2 ? s2.split(" ")[1] : "";
					
					return p1.compareTo(p2);
				}).collect(Collectors.toList());
		
		String ispis = l.stream().reduce("", (s1, s2) -> "".equals(s1) ? s2 : s1 + ", " + s2);
		System.out.println("Patuljci sortirani rastuce po prezimenu (prvo idu oni bez prezimena) : " + ispis);
		
		return l;
	}
	
	
	public Map<Integer, Set<Patuljak>> patuljciRazvrstaniPoGodiniRodjenja() {
		
		Map<Integer, Set<Patuljak>> m = patuljci.stream().collect(Collectors.groupingBy(Patuljak::godinaRodjenja, Collectors.toSet()));
		
		System.out.println("Patuljci razvrstani prema godini rodjenja : ");
		System.out.println(m);
		
		return m;
	}
	
	
	public boolean postojiPatuljakSaViseZlataOd(double d) {
		
		boolean b = patuljci.stream().anyMatch(p -> p.iskopaoZlata() > d);
		
		System.out.printf("%sostoji patuljak sa vise zlata od %.2f.", (b ? "P" : "Ne p"), d);
		
		return b;
	}
}
