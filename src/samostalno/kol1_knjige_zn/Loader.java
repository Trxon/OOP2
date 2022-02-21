package samostalno.kol1_knjige_zn;

import org.svetovid.Svetovid;

public class Loader {

	public static String[] load() {
		return Svetovid.in("src/knjige.csv").readAllLines();
	}
}
