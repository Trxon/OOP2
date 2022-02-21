package vezba.kol2_vesanje_sockets_p03;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RangLista {

	
	private List<Igra> rangLista = new ArrayList<>();
	
	
	public int dodaj(Igra igra) {
		
		rangLista.add(igra);
		rangLista.sort(Comparator.comparing(Igra::getBrZivota));
		
		int mojRang = rangLista.indexOf(igra);
		
		if (rangLista.size() > 10)
			rangLista.remove(10);
		
		return mojRang;
	}
}
