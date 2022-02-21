package vezba.kol1_fudbal_p01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Loader {

	public static String load(String file) {
		
		InputStream i = Loader.class.getResourceAsStream(file);
		BufferedReader in = new BufferedReader(new InputStreamReader(i));
		
		StringBuilder text = new StringBuilder();
		String line;
		
		try {
			while ((line = in.readLine()) != null) {
				text.append(line);
				text.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return text.toString();
	}
}
