package teorijske_vezbe.tv05_igra_pogadjanja_p01;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RangLista {

	private List<IgraStanje> lista = new ArrayList<IgraStanje>();
	
	public int dodaj(IgraStanje igra) {
		
		lista.add(igra);
		
		lista.sort(Comparator.comparing(IgraStanje::brPokusaja));
		if (lista.size() > 10) lista.remove(10);
		
		return lista.indexOf(igra) + 1;
	}
}
