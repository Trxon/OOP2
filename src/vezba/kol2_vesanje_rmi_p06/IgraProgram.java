package vezba.kol2_vesanje_rmi_p06;

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
		String unos;
		Poruka poruka = null;
		
		do {
			
			System.out.print("Unos : ");
			unos = stdIn.readLine();
			
			poruka = igra.pogadjaj(unos.charAt(0));
			
			switch (poruka.getOdgovor()) {
				case PROMASAJ : System.out.print("PROMASAJ "); 	break;
				case POGODAK : 	System.out.print("POGODAK "); 	break;
				case KRAJ : 	System.out.print("KRAJ "); 		break;
			}
			
			System.out.println(poruka.getRecTrenutno());
			
		} while (poruka.getOdgovor() == Odgovor.PROMASAJ);
	}
}
