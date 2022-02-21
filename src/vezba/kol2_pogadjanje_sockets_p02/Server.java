package vezba.kol2_pogadjanje_sockets_p02;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		
		
		try {
			
			// kreiranje ServerSocket i RangLista objekata
			ServerSocket ss = new ServerSocket(1234);
			RangLista rl = new RangLista();
			System.err.println("[IGRA POGADJANJA] Server je pokrenut.");
			
			// ovaj try blok se koristi da bi pokretanje ss.close() bilo osigurano
			try {
				
				// sve dok traje glavna nit ocekujemo klijentske konekcije
				while (!Thread.interrupted()) {
					
					// serverski socket prihvata klijentsku konekciju 
					Socket klijent = ss.accept();
					System.err.println("[IGRA POGADJANJA] Uspostavljena je nova konekcija...");
					
					// server kreira novu igru za klijenta i pokrece je
					Igra igra = new Igra(klijent, rl);
					igra.start();
					
					System.err.println("[IGRA POGADJANJA] Pokrenuta je nova igra...");
				}
			} finally {
				ss.close();
			}
			
		} catch (IOException e) {
			// pri kreiranju ServerSocket objekta ponudjeno je postavljanje try-catch bloka
			e.printStackTrace();
		}
	}
}
