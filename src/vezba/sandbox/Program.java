package vezba.sandbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

class Program {
	
	
	public static void main(String[] args) throws IOException {
		
		URL url = new URL("https://docs.google.com/spreadsheets/d/1pfDI-zy6iz2APVV1nDVZGIdgvqM3hAjCYiuAJ9rPPqI/edit?usp=sharing");
		String text = load(url);
		
		System.out.println(text);
	}


	public static String load(URL url) throws IOException {
		
		BufferedReader in = new BufferedReader(
				new InputStreamReader(url.openStream()));
		
		StringBuilder sb = new StringBuilder();
		String line;
		
		while ((line = in.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		
		in.close();
		return sb.toString();
	}
}
