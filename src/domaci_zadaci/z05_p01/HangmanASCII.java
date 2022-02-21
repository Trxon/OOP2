package domaci_zadaci.z05_p01;

import java.util.HashMap;
import java.util.Map;

public class HangmanASCII {

	
	public static final Map<Integer, String> hangmanGraphics;
	
	
	static {
		
		hangmanGraphics = new HashMap<Integer, String>();
		
		hangmanGraphics.put(0, 
				"       \n" +
				"       \n" +
				"       \n" +
				"       \n" +
				"       \n" +
				"       \n" +
				"========="
				);
		
		hangmanGraphics.put(1, 
				"      +\n" +
				"      |\n" +
				"      |\n" +
				"      |\n" +
				"      |\n" +
				"      |\n" +
				"========="
				);
		
		hangmanGraphics.put(2, 
				"  +---+\n" +
				"      |\n" +
				"      |\n" +
				"      |\n" +
				"      |\n" +
				"      |\n" +
				"========="
				);
		
		hangmanGraphics.put(3, 
				"  +---+\n" +
				"  |   |\n" +
				"      |\n" +
				"      |\n" +
				"      |\n" +
				"      |\n" +
				"========="
				);
		
		hangmanGraphics.put(4, 
				"  +---+\n" +
				"  |   |\n" +
				"  O   |\n" +
				"      |\n" +
				"      |\n" +
				"      |\n" +
				"========="
				);
		
		hangmanGraphics.put(5, 
				"  +---+\n" +
				"  |   |\n" +
				"  O   |\n" +
				"  |   |\n" +
				"      |\n" +
				"      |\n" +
				"========="
				);
		
		hangmanGraphics.put(6, 
				"  +---+\n" +
				"  |   |\n" +
				"  O   |\n" +
				" /|   |\n" +
				"      |\n" +
				"      |\n" +
				"========="
				);
		
		hangmanGraphics.put(7, 
				"  +---+\n" +
				"  |   |\n" +
				"  O   |\n" +
				" /|\\  |\n" +
				"      |\n" +
				"      |\n" +
				"========="
				);
		
		hangmanGraphics.put(8, 
				"  +---+\n" +
				"  |   |\n" +
				"  O   |\n" +
				" /|\\  |\n" +
				" /    |\n" +
				"      |\n" +
				"========="
				);
		
		hangmanGraphics.put(9, 
				"  +---+\n" +
				"  |   |\n" +
				"  O   |\n" +
				" /|\\  |\n" +
				" / \\  |\n" +
				"      |\n" +
				"========="
				);
	}
}
