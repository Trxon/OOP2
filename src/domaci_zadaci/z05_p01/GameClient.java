package domaci_zadaci.z05_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class GameClient {

	public static void main(String[] args) {
		
		try {
			
			// localhost is the alias of the computer running the application
			Socket s = new Socket("localhost", 1234);
			
			try {
				
				System.out.println("Connection established...");
				
				// network communication
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
				
				// user input
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				
				System.out.print("INPUT player name : ");
				String playerName = stdIn.readLine();
				
				// sending the player's name to the server
				out.println(playerName);
				
				System.out.println(in.readLine());
				System.out.println();
				System.out.println("MOVIE title : " + in.readLine());
				
				boolean endGame = false;
				boolean calculateRank = false;
				
				int hangmanCount = 0;
				
				while (!endGame) {
					
					// player input
					System.out.println();
					System.out.print("INPUT letter OR complete title : ");
					String unos = stdIn.readLine();
					
					// sending the player input
					out.println(unos.toUpperCase());
					
					// empty line input warning
					if ("".equals(unos)) {
						System.err.println("EMPTY LINE is not ALLOWED!");
						continue;
					}
					
					// getting the server respons
					String response = in.readLine();

					System.out.println(HangmanASCII.hangmanGraphics.get(hangmanCount++));
					
					// three possible outcomes
					if (response.equals("correct")) {
						
						// the guess is now complete
						System.out.println(in.readLine());
						System.out.println("RT Critics Consensus :" + in.readLine());
						
						endGame = true;
						
						// rank is only calculated for correct guesses 
						calculateRank = true;
						
					} else if (response.equals("bad")) {
						
						// guess is not yet complete and there are attempts left
						System.out.println("MOVIE title : " + in.readLine());
						System.out.println("ATTEMPTS left : " + in.readLine());
					} else {
						
						// guess is not complete but there are no more attempts left
						System.out.println(in.readLine());
						endGame = true;
					}
				}
				
				// sending the high scores rank which is simply printed out at the end of the game
				if (calculateRank) {
					String mojRang = in.readLine();
					System.out.printf("PLAYER %s RANKS No. %s on our HIGHEST SCORES list! %n", playerName, mojRang);
				}
				
			} finally {
				s.close();
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
