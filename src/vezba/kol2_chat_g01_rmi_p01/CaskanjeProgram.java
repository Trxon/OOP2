package vezba.kol2_chat_g01_rmi_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CaskanjeProgram {

	public static void main(String[] args) throws IOException, NotBoundException {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1234);
		
		Server server = (Server) registry.lookup("Caskanje");
		Caskanje caskanje = server.zapocni();
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String unos;
		String odgovor;
		
		try {
			
			while (true) {
				
				System.out.print("Klijent : ");
				unos = stdIn.readLine();
				odgovor = caskanje.poruka(unos);
				
				System.out.println(odgovor);
			}
			
		} catch (IllegalArgumentException e) {
			System.err.println("Illegal argument!");
		}
	}
}
