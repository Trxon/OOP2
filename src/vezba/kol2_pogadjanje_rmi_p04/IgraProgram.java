package vezba.kol2_pogadjanje_rmi_p04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class IgraProgram {

	public static void main(String[] args) throws IOException, NotBoundException {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1234);
		
		Server server = (Server) registry.lookup("Igra");
		Igra igra = server.novaIgra();
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String unos;
		Odgovor odgovor;
		
		do {
			
			System.out.print("Unos : ");
			unos = stdIn.readLine();
			
			odgovor = igra.pogadjaj(Integer.parseInt(unos));
			
			switch (odgovor) {
				case VECI:		System.out.println("Zamisljeni broj je veci");									break;
				case MANJI:		System.out.println("Zamisljeni broj je manji");									break;
				case POGODAK:	System.out.println("Pogodili ste broj");										break;
				case KRAJ:		System.out.println("It's a sad thing that your adventure have ended here...");	break;
			}
			
		} while (odgovor != Odgovor.KRAJ && odgovor != Odgovor.POGODAK);
	}
}
