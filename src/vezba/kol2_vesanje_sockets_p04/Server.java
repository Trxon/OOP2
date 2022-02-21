package vezba.kol2_vesanje_sockets_p04;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		
		try {
			
			ServerSocket ss = new ServerSocket(1234);
			System.out.println("Server RADI...");
			
			try {
				
				while (!Thread.interrupted()) {
					
					Socket klijent = ss.accept();
					System.out.println("Nova konjekcija...");
					
					Sesija sesija = new Sesija(klijent);
					sesija.start();
					
					System.out.println("Nova sesija ");
				}
				
			} finally {
				ss.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
