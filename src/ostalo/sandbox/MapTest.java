package ostalo.sandbox;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapTest {

	public static void main(String[] args) {
		
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		
		hm.put(1, "jedan");
		hm.put(2, "dva");
		
		Iterator it = hm.entrySet().iterator();
		
		while (it.hasNext()) System.out.println(((Map.Entry) it.next()).getKey());
	}
}
