package vezba.kol2_chat_g02_ss_p01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		
		try {
			
			ServerSocket ss = new ServerSocket(1234);
			System.out.println("Server radi...");
			
			try {
				
				while (!Thread.interrupted()) {
					
					Socket klijent = ss.accept();
					System.out.println("Nova konekcija...");
					
					Caskanje caskanje = new Caskanje(klijent);
					caskanje.start();
					System.out.println("Novo caskanje " + caskanje + "...");
				}
				
			} finally {
				ss.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
