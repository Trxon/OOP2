package vezba.kol2_vesanje_sockets_p01;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RangLista {

	
	private List<Igra> lista = new ArrayList<>();
	
	
	public int dodaj(Igra igra) {
		
		lista.add(igra);
		lista.sort(Comparator.comparing(Igra::getZivoti).reversed());
		
		int rang = 1 + lista.indexOf(igra);
		
		if (lista.size() > 10)
			lista.remove(10);
		
		return rang;
	}
}
