package vezba.kol2_chat_g02_rmi_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SesijaProgram {

	public static void main(String[] args) throws NotBoundException, IOException {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1234);
		
		Server server = (Server) registry.lookup("Sesija");
		Sesija sesija = server.novaSesija();
		Caskanje caskanje = sesija.zapocni();
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String unos;
				
		try {
			
			System.out.print("Unos : ");
			unos = stdIn.readLine();
			caskanje.poruka(unos);
			System.out.println(unos);
			
			do {
		
				System.out.print("Unos : ");
				unos = stdIn.readLine();
				String odgovor = caskanje.poruka(unos);
				System.out.println(odgovor);
				
			// sesija ce se zavrsiti kad server baci IllegalArgumentException()
			} while (true);	
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
	}
}
