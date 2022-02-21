package teorijske_vezbe.tv03;

import java.time.LocalDate;
import java.util.Random;
import java.util.stream.Stream;

public final class Osobe {

	
	private static final String[] IMENA = new String[] {
			"Per", "Mik", "Đok", "Raj", "Gaj", "Vlaj", "Zlaj",
			"Ac", "Kost", "Nikol", "Vlad",
			"Nad", "An", "Tanj", "Jelen", "Marij", "Tamar", "Nin",
			"Oliver", "Ivan", "Bojan", "Goran", "Zoran", "Dušan", "Vladan"
	};
	
	
	public static final String[] random(int n) {
		
		Random r = new Random(2);
		String[] s = new String[n];
		
		for (int i = 0; i < n; i++)
			s[i] = osoba(r).toString();
		
		return s;
	}
	
	
	public static final Stream<String> stream(int n) {
		Random r = new Random(2);
		return Stream.generate(() -> osoba(r).toString()).limit(n);
	}
	
	
	private static final LocalDate START = LocalDate.of(1960, 1, 1);
	
	
	private static final Osoba osoba(Random r) {
		
		String ime = IMENA[r.nextInt(IMENA.length)] + "a";
		String prezime = IMENA[r.nextInt(IMENA.length)] + "ic";
		LocalDate datum = START.plusDays(r.nextInt(10000));
		int plata = 100 * (200 + r.nextInt(1000));
		
		String[] deca = new String[r.nextInt(5)];
		for (int i = 0; i < deca.length; i++) 
			deca[i] = IMENA[r.nextInt(IMENA.length)] + "a";
		
		return new Osoba(ime, prezime, datum, plata, deca);
	}
}
