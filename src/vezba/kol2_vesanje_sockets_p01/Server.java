package vezba.kol2_vesanje_sockets_p01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		
		try {
			
			ServerSocket ss = new ServerSocket(1234);
			RangLista rl = new RangLista();
			System.err.println("[IGRA VESANJA] Server pokrenut.");
			
			try {
				while (!Thread.interrupted()) {
				
					Socket klijent = ss.accept();
					System.err.println("Uspostavljena je nova konekcija...");
					
					Igra igra = new Igra(klijent, rl);
					igra.start();
					
					System.err.println("Pokrenuta je nova igra...");
				}
			} finally {
				ss.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
