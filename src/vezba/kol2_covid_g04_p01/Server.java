package vezba.kol2_covid_g04_p01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		
		try {
			
			ServerSocket ss = new ServerSocket(1919);
			System.out.println("Server RADI...");
			
			try {
				while (!Thread.interrupted()) {
					
					Socket klijent = ss.accept();
					System.out.println("Uspostavljena nova konekcija...");
					
					Sesija sesija = new Sesija(klijent, new Data());
					sesija.start();
					
					System.out.println("Pokrenuta nova sesija...");
					
				}
			} finally {
				ss.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
