package vezba.kol1_patuljci_p02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Loader {

	
	public static String load(String file) {
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Loader.class.getResourceAsStream(file)))) {
			
			StringBuilder text = new StringBuilder();
			String line;
			
			while ((line = in.readLine()) != null) {
				text.append(line);
				text.append("\n");
			}
			
			return text.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
