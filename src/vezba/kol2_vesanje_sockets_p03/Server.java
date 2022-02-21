package vezba.kol2_vesanje_sockets_p03;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		
		try {
			
			ServerSocket ss = new ServerSocket(1234);
			RangLista rl = new RangLista();
			System.out.println("Server RADI...");
			
			try {
				while (!Thread.interrupted()) {
					
					Socket klijent = ss.accept();
					System.out.println("Nova konekcija...");
					
					Igra igra = new Igra(klijent, rl);
					igra.start();
					
					System.out.println("Nova igra...");
				}
			} finally {
				ss.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
