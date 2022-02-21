package vezba.kol2_vesanje_rmi_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class IgraProgram {

	public static void main(String[] args) throws IOException, NotBoundException {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		
		Server server = (Server) registry.lookup("Igra");
		Igra igra = server.novaIgra();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		Poruka poruka;
		
		do {
			
			System.out.print("Karakter : ");
			char pokusaj = in.readLine().charAt(0);
			poruka = igra.pogadjaj(pokusaj);
			
			switch (poruka.odgovor()) {
				case PROMASAJ:	System.out.println("Delovi reci su jos skriveni -> " + poruka.recTrenutno());	break;
				case POGODAK:	System.out.println("Rec je otkrivena -> " + poruka.recTrenutno());				break;
				case KRAJ:		System.out.println("It's a sad thing that your adventures have ended here...");	break;
			}
			
		} while (poruka.odgovor() != Odgovor.POGODAK && poruka.odgovor() != Odgovor.KRAJ);
	}
}
