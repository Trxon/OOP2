package domaci_zadaci.z05_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class GameState extends Thread {
	
	
	private static final int MAX_ATTEMPTS = 10;
	
	
	private final Socket socket;
	private final BufferedReader in;
	private final PrintWriter out;
	
	private final Movie movie;
	
	private String playerName;
	private final HighScores hs;
	private int numAttempts;
	
	private boolean gameWon;

	
	public GameState(Socket client, HighScores hs) throws IOException {
		
		// saving the socket
		this.socket = client;
		
		// buffered stream (lines) <- character stream <- binary stream 
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		
		// getting a random sci fi movie
		this.movie = Movies.getRandomMovie();
		
		// saving a reference to high scores list
		this.hs = hs;		
	}
	
	
	private void winSequence() {
		
		this.gameWon = true;
		
		System.out.printf("PLAYER %s WINS! %n", playerName);
		
		out.println("correct");
		out.printf("MOVIE title : %s (attempt(s) : %d) %n", movie.titleUpperCase(), numAttempts);
		out.println(movie.consensus());
		int rang = -1;
		
		synchronized(hs) {
			rang = hs.dodaj(this);
		}
		
		out.println(rang);
	}
	
	
	@Override
	public void run() {
		
		try {
			
			// saving the player's name
			playerName = in.readLine();
			
			// server-side information
			System.out.printf("PLAYER %s GUESSES : %s %n", playerName, movie.titleUpperCase());
			out.printf("PLAYER : %s (attempt(s) : %d) %n", playerName, MAX_ATTEMPTS - numAttempts);
			
			String line;
			boolean endGame = false;
			
			// sending the movie hint back to the client
			out.println(movie.guess());
			
			while ((line = in.readLine()) != null && !endGame) {

				// server-side information - do nothing if empty line is received
				System.out.printf("PLAYER %s TYPES : %s %n", playerName, "".equals(line) ? "NULL" : line);
				if ("".equals(line)) continue;
				
				numAttempts++;
				
				// user types in the complete movie title...
				if (line.equalsIgnoreCase(movie.titleUpperCase())) {
					winSequence();
					endGame = true;
				}
				
				// ...and if not we will only check the first char of the input
				movie.addChar(line.charAt(0));
				
				
				if (movie.isGuessCorrect()) {
					
					// the guess is complete with the final char
					winSequence();
					endGame = true;
				} else {
					
					// the guess is not yet complete...
					if (MAX_ATTEMPTS - numAttempts > 0) {
						
						// ...but there are attempts left
						out.println("bad");
						out.println(movie.guess());
						out.println(MAX_ATTEMPTS - numAttempts);
					} else {
						
						// ...and there are no more attempts left
						out.println("end");
						out.println("NO ATTEMPTS left! Solution : " + movie.titleUpperCase());
						endGame = true;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public int numAttempts() { return numAttempts; 	}
	public boolean gameWon() { return gameWon; 		}
}
