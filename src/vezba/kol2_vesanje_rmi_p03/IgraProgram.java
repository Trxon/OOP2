package vezba.kol2_vesanje_rmi_p03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class IgraProgram {

	public static void main(String[] args) throws NotBoundException, IOException {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		
		Server server = (Server) registry.lookup("Igra");
		Igra igra = server.novaIgra();
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		Poruka poruka = null;
		
		do {
			
			System.out.print("Unesite karakter : ");
			char pokusaj = stdIn.readLine().charAt(0);
			
			poruka = igra.pogadjaj(pokusaj);
			
			switch (poruka.getOdgovor()) {
				case PROMASAJ:	System.out.println("Delovi reci su jos skriveni -> " + poruka.getRecTrenutno());	break;
				case POGODAK:	System.out.println("Rec je otkrivena -> " + poruka.getRecTrenutno());				break;
				case KRAJ:		System.out.println("It's a sad thing that your adventures have ended here...");		break;
			}
			
		} while (poruka.getOdgovor() == Odgovor.PROMASAJ);
	}
}
