package samostalno.kol1_json_parse_p00;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Program {
	

	public static void main(String[] args) throws IOException {
		
		URL url = new URL("https://itunes.apple.com/us/rss/topmovies/limit=25/json");
		String text = readURL(url);
		System.out.println(text);
	}
	
	
	private static String readURL(URL url) throws IOException {
		
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
