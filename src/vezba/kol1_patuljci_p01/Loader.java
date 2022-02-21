package vezba.kol1_patuljci_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Loader {

	
	public static Stream<String> load() {
		
		InputStream i = Loader.class.getResourceAsStream("patuljci.txt");
		BufferedReader in = new BufferedReader(new InputStreamReader(i));
		
		String line;
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			while ((line = in.readLine()) != null)
				list.add(line);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		return list.stream();
	}
}
