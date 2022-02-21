package vezba.kol2_pogadjanje_rmi_p01;

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
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		Odgovor odgovor;
		
		do {
			
			System.out.print("Pogodi broj : ");
			int broj = Integer.parseInt(in.readLine());
			odgovor = igra.pogadjaj(broj);
			
			switch (odgovor) {
				case VECE:		System.out.println("Zamisljeni broj je veci");									break;
				case MANJE:		System.out.println("Zamisljeni broj je manji");									break;
				case POGODAK:	System.out.println("Pogodili ste broj");										break;
				case KRAJ:		System.out.println("It's a sad thing that your adventure have ended here...");	break;
			}
			
		} while (odgovor != Odgovor.POGODAK && odgovor != Odgovor.KRAJ);
	}
}
