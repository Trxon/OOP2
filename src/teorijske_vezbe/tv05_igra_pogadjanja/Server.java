package teorijske_vezbe.tv05_igra_pogadjanja;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		
		try {

			ServerSocket ss = new ServerSocket(1234);
			RangLista rl = new RangLista(); 
			System.out.println("Server je pokrenut");
			try {

				while (!Thread.interrupted()) {
					Socket klijent = ss.accept();
					System.out.println("Uspostavljena je nova konekcija");
					Igra igra = new Igra(klijent, rl);
					igra.start();
					System.out.println("Pokrenuta je nova igra");
				}

			} finally {
				ss.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
