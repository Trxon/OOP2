package vezba.kol2_pogadjanje_sockets_p04;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RangLista {

	
	private List<Igra> rangLista = new ArrayList<Igra>();
	
	
	public int dodaj(Igra igra) {
		
		rangLista.add(igra);
		rangLista.sort(Comparator.comparing(Igra::getBrPokusaja));
		
		int rang = rangLista.indexOf(igra) + 1;
		
		if (rangLista.size() > 10)
			rangLista.remove(10);
		
		return rang;
	}
}
