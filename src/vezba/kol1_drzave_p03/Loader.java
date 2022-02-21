package vezba.kol1_drzave_p03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Loader {

	public static String load(String file) {
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Loader.class.getResourceAsStream(file)))) {
			
			String line;
			StringBuilder text = new StringBuilder();
			
			while ((line = in.readLine()) != null) {
				text.append(line);
				text.append("\n");
			}
			
			return text.toString();
		} catch (IOException e) {
			System.err.println("Greska prilikom citanja fajla -> " + e.getMessage());
		}
		
		return null;
	}
}
