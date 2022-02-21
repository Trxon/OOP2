package domaci_zadaci.z02_ass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Raspored {

	private final Map<Dan, List<Cas>> casovi;

	public Raspored() {
		casovi = new EnumMap<>(Dan.class);
		for (Dan dan : Dan.values()) {
			casovi.put(dan, new ArrayList<>());
		}
	}

	public List<Cas> getList(Dan dan) {
		return casovi.get(dan);
	}

	public void add(Cas cas) {
		Dan dan = cas.getDan();
		List<Cas> lista = casovi.get(dan);
		lista.add(cas);
		Collections.sort(lista);
	}

	public List<Cas> getList() {
		List<Cas> lista = new ArrayList<>();
		for (List<Cas> cas : casovi.values()) {
			lista.addAll(cas);
		}
		return lista;
	}
}
