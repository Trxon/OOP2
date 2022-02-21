package vezba.kol2_pogadjanje_rmi_p06;

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
		Odgovor odgovor = null;
		
		do {
			
			System.out.print("Unos : ");
			unos = stdIn.readLine();
			odgovor = igra.pogadjaj(Integer.parseInt(unos.trim()));
			
			switch (odgovor) {
				case MANJI : 	System.out.println("Broj je veci od " + unos + "..."); 	break;
				case VECI : 	System.out.println("Broj je manji od " + unos + "..."); break;
				case POGODAK : 	System.out.println("Pogodak!"); 						break;
			}
			
		} while (odgovor != Odgovor.POGODAK);
	}
}
