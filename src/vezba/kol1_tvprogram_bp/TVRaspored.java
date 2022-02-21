package vezba.kol1_tvprogram_bp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TVRaspored {

	public Map<String, List<TVEmisija>> mapa;
	
	public TVRaspored() {
		mapa = new HashMap<String, List<TVEmisija>>();
	}
	
	public void dodajEmisiju(String kanal, TVEmisija emisija) {
		if (mapa.containsKey(kanal)) {
			mapa.get(kanal).add(emisija);
			Collections.sort(mapa.get(kanal));
		} else {
			ArrayList<TVEmisija> lista = new ArrayList<TVEmisija>();
			lista.add(emisija);
			mapa.put(kanal, lista);
		}
	}
	
	public List<TVEmisija> listaEmisija(String kanal) {
		if (mapa.get(kanal) != null) {
			return mapa.get(kanal);
		}
		return null;
	}
	
}
