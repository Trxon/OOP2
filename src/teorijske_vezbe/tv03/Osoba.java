package teorijske_vezbe.tv03;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class Osoba {

	
	private final String ime;
	private final String prezime;
	private final LocalDate datumRodjenja;
	private final int plata;
	private final List<String> deca;
	
	
	public Osoba(String string) {
		
		String[] podaci = string.split("\\s+");
		
		if (podaci.length < 4)
			throw new IllegalArgumentException();
		
		ime = podaci[0];
		prezime = podaci[1];
		datumRodjenja = LocalDate.parse(podaci[2]);
		
		try {
			plata = Integer.parseInt(podaci[3]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		
		List<String> d = new ArrayList<>();
		for (int i = 4; i < podaci.length; i++)
			d.add(podaci[i]);
		this.deca = Collections.unmodifiableList(d);
	}
	
	
	public Osoba(String ime, String prezime, LocalDate datumRodjenja, int plata, String... deca) {
		
		if (ime == null) throw new IllegalArgumentException();
		this.ime = ime;
		
		if (prezime == null) throw new IllegalArgumentException();
		this.prezime = prezime;
		
		if (datumRodjenja == null) throw new IllegalArgumentException();
		this.datumRodjenja = datumRodjenja;
		
		this.plata = plata;
		
		if (deca == null) throw new IllegalArgumentException();
		List<String> d = new ArrayList<>();
		for (String dete : deca) d.add(dete);
		this.deca = Collections.unmodifiableList(d);
	}


	public String getIme() 				{ return ime; 			}
	public String getPrezime() 			{ return prezime; 		}
	public LocalDate getDatumRodjenja() { return datumRodjenja; }
	public int getPlata() 				{ return plata; 		}
	public List<String> getDeca() 		{ return deca; 			}
	
	
	public String toString() {
		
		StringJoiner joiner = new StringJoiner(" ");
		for (String dete : deca) joiner.add(dete);
		
		return ime + " " + prezime + "\t" + datumRodjenja + "\t" + plata + "\t" + joiner;
	}
}
