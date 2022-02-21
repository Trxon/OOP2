package vezba.kol2_pogadjanje_rmi_p03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class IgraProgram {

	public static void main(String[] args) throws NotBoundException, NumberFormatException, IOException {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		
		Server server = (Server) registry.lookup("Igra");
		Igra igra = server.novaIgra();
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		Odgovor odgovor = null;
		
		do {
			
			System.out.print("Unesite broj : ");
			int pokusaj = Integer.parseInt(stdIn.readLine());
			
			odgovor = igra.pogadjaj(pokusaj);
			
			switch (odgovor) {
				case VECE:		System.out.println("Zamisljeni broj je veci");									break;
				case MANJE:		System.out.println("Zamisljeni broj je manji");									break;
				case POGODAK:	System.out.println("Pogodili ste broj");										break;
				case KRAJ:		System.out.println("It's a sad thing that your adventure have ended here...");	break;
			}
			
		} while (odgovor != Odgovor.KRAJ && odgovor != Odgovor.POGODAK);
	}
}
