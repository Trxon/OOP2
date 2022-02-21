package vezba.kol2_pogadjanje_sockets_p05;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RangLista {

	
	private List<Igra> rangLista = new ArrayList<Igra>();
	
	
	public int dodaj(Igra igra) {
		
		rangLista.add(igra);
		rangLista.sort(Comparator.comparing(Igra::getBrPokusaja));
		
		int rang = 1 + rangLista.indexOf(igra);
		
		if (rangLista.size() > 10)
			rangLista.remove(10);
		
		return rang;
	}
}
