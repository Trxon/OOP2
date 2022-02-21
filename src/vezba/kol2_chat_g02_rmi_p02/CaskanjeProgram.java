package vezba.kol2_chat_g02_rmi_p02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CaskanjeProgram {

	public static void main(String[] args) throws NotBoundException, IOException {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1919);
		
		Server server = (Server) registry.lookup("Caskanje");
		Caskanje caskanje = server.zapocni();
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String unos;
		String odgovor;
		
		try {
			
			do {
				
				System.out.print("KLIJENT : ");
				unos = stdIn.readLine();
				odgovor = caskanje.poruka(unos);
				System.out.println(odgovor);
				
			} while (true);
			
		} catch (IllegalArgumentException e) {
			System.err.println("Illegal argument!");
		}
	}
}
