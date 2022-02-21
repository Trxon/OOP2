package domaci_zadaci.z02_p03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Raspored {

	
	private final Map<Dan, List<Cas>> casovi;
	
	
	public Raspored() {
		
		casovi = new EnumMap<>(Dan.class);
		
		for (Dan dan : Dan.values())
			casovi.put(dan, new ArrayList<>());
	}
	
	
	public List<Cas> list(Dan dan) {
		return casovi.get(dan);
	}
	
	
	public void add(Cas cas) {
		
		Dan dan = cas.dan();
		List<Cas> l = casovi.get(dan);
		
		l.add(cas);
		Collections.sort(l);
	}
	
	
	public List<Cas> list() {
		
		List<Cas> l = new ArrayList<>();
		for (List<Cas> cas : casovi.values())
			l.addAll(cas);
		
		return l;
	}
}
