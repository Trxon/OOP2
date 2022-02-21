package vezba.kol1_tvprogram_p01;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TVRaspored {

	
	private Map<String, List<TVEmisija>> map;
	
	
	public TVRaspored() {
		this.map = new HashMap<String, List<TVEmisija>>();
	}
	
	
	public void dodajKanal(String kanal) {
		map.put(kanal.toUpperCase(), new LinkedList<TVEmisija>());
	}
	
	
	public void dodajEmisiju(String kanal, String vreme, String naziv) {
		
		if (!map.containsKey(kanal.toUpperCase())) dodajKanal(kanal.toUpperCase());
		
		TVEmisija e = new TVEmisija();
		e.setNaziv(naziv);
		e.setVreme(vreme);
		
		map.get(kanal.toUpperCase()).add(e);
		map.get(kanal.toUpperCase()).sort(null);
	}
	
	
	public void stampajZaKanal(String kanal) {
		
		if (!map.containsKey(kanal.toUpperCase()))
			throw new IllegalArgumentException();
		
		System.out.printf("Raspored za %s : %n", kanal.toUpperCase());
		
		map.get(kanal.toUpperCase()).stream().forEach(e -> System.out.println("  " + e));
	}
}
