package teorijske_vezbe.tv05_igra_pogadjanja_p01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		
		try {
			
			// pravimo odmah jedan serverski socket
			ServerSocket ss = new ServerSocket(1234);
			RangLista rl = new RangLista();
			
			try {
				
				System.out.println("Server je pokrenut!");
				
				// pravimo socket za komunikaciju
				while (!Thread.interrupted()) {
					
					Socket klijent = ss.accept();
					System.out.println("Uspostavljena nova konekcija!");
					
					// nova klasa koja se bavi samom igrom i koja extends Thread - nova nit za novu igru
					IgraStanje igra = new IgraStanje(klijent, rl);
					igra.start();
				}
			} finally {
				// mora se zatvoriti ServerSocket
				ss.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
