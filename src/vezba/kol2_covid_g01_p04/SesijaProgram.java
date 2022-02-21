package vezba.kol2_covid_g01_p04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SesijaProgram {

	public static void main(String[] args) throws NotBoundException, IOException {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1919);
		
		Server server = (Server) registry.lookup("Sesija");
		Sesija sesija = server.novaSesija();
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String unos;
		String odgovor = null;
		
		do {
			
			odgovor = sesija.posaljiZahtev("1" + "zemlje");
			System.out.println(odgovor);
			
			System.out.println("Unos : ");
			unos = stdIn.readLine();
			odgovor = sesija.posaljiZahtev("2" + unos);
			
			if (odgovor.charAt(0) != '!') {
				
				System.out.println(odgovor);
				
				System.out.println("Unos : ");
				unos = stdIn.readLine();
				odgovor = sesija.posaljiZahtev("3" + unos);
				
				if (odgovor.charAt(0) != '!')
					System.out.println(odgovor);
				else
					System.err.println("Nema podataka ili pogresan unos!");
				
			} else {
				System.err.println("Nema podataka ili pogresan unos!");
			}
			
		} while ("".equalsIgnoreCase(odgovor));
	}
}
