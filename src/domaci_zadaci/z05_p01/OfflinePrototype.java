package domaci_zadaci.z05_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class OfflinePrototype {
	
	
	private static BufferedReader in;
	
	private static String title;
	private static List<Character> chars;
	
	private static String guess;
	
	
	private static void updateGuess() {
		
		guess = title;
		
		for (char c = 'A'; c <= 'Z'; c++)
			if (!chars.contains(c))
				guess = guess.replaceAll("" + c, "-");
	}

	
	public static void main(String[] args) throws IOException {

		in = new BufferedReader(new InputStreamReader(System.in));
		
		title = "Aliens".toUpperCase();
		chars = new LinkedList<Character>();
		
		updateGuess();
		
		boolean end = false;
		
		while (!end) {
			
			System.out.println(guess);
			
			System.out.print("Unos : ");
			String unos = in.readLine();
			
			unos = unos.toUpperCase();
			System.out.println(unos);
			
			if (!unos.equals(title.toUpperCase())) {
			
				char c0 = unos.charAt(0);
				if (!chars.contains(c0)) chars.add(c0);
				
				updateGuess();
				
				if (guess.equals(title.toUpperCase())) {
					System.out.println(guess);
					end = true;
				}
				
			} else {
				end = true;
			}
		}
	}
}
