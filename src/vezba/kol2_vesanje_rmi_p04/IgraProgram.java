package vezba.kol2_vesanje_rmi_p04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class IgraProgram {

	public static void main(String[] args) throws NotBoundException, IOException {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1234);
		
		Server server = (Server) registry.lookup("Igra");
		Igra igra = server.novaIgra();
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Nova igra : " + igra);
		
		Poruka poruka;
		
		do {
			
			System.out.print("Unos : ");
			String unos = stdIn.readLine();
			
			poruka = igra.pogadjaj(unos);
			
			switch (poruka.getOdgovor()) {
				case PROMASAJ: 	System.out.println("PROMASAJ " + poruka.getRecTrenutno()); break;
				case POGODAK: 	System.out.println("POGODAK " + poruka.getRecTrenutno()); break;
				case KRAJ: 		System.out.println("KRAJ " + poruka.getRecTrenutno()); break;
			}
		} while (poruka.getOdgovor() == Odgovor.PROMASAJ);
	}
}
