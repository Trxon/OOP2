package domaci_zadaci.z05_p01;

import java.util.LinkedList;
import java.util.List;

public class Movie {

	
	private final String title;
	private final String consensus;
	private static List<Character> chars;
	private String guess;
	
	
	public Movie(String title, String consensus) {
		
		this.title = title;
		this.consensus = consensus;
		this.chars = new LinkedList<Character>();
		
		updateGuess();
	}


	public String title() 			{ return title; 				}
	public String consensus() 		{ return consensus; 			}
	public List<Character> chars() 	{ return chars; 				}
	public String guess() 			{ return guess; 				}
	public String titleUpperCase() 	{ return title.toUpperCase(); 	}
	
	
	public boolean isGuessCorrect() {
		
		if (guess.equals(titleUpperCase()))
			return true;
		
		return false;
	}
	
	
	private void updateGuess() {
		
		this.guess = titleUpperCase();
		
		for (char c = 'A'; c <= 'Z'; c++)
			if (!chars.contains(c))
				this.guess = this.guess.replaceAll("" + c, "-");
	}
	
	
	public void addChar(char c) {
		
		if (!chars.contains(c)) chars.add(c);
		
		updateGuess();
	}
	
	
	@Override
	public String toString() {
		return title + ": " + consensus;
	}
}
