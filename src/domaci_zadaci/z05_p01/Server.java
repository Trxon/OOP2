package domaci_zadaci.z05_p01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	
    /***************************
     * NIKOLA VETNIC 438/19 IT *
     ***************************/
	

	public static void main(String[] args) {
		
		try {
			
			// create a server socket straight away
			ServerSocket ss = new ServerSocket(1234);
			HighScores hs = new HighScores();
			
			try {
				
				System.out.println("Server is running!");
				
				// creating a communication socket
				while (!Thread.interrupted()) {
					
					Socket klijent = ss.accept();
					System.out.println("New connection established!");
					
					// a custom class which handles the game itself and which extends Thread (a new thread for new game)
					GameState game = new GameState(klijent, hs);
					game.start();
				}
			} finally {
				
				// ServerSocket must be closed
				ss.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
