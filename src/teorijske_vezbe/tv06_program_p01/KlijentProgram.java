package teorijske_vezbe.tv06_program_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class KlijentProgram {

	public static void main(String[] args) throws NotBoundException, NumberFormatException, IOException {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		
		Server server = (Server) registry.lookup("Igra");
		Igra igra = server.novaIgra();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		Odgovor odgovor;
		
		do {
			
			System.out.println("Pogodi broj: ");
			int broj = Integer.parseInt(in.readLine());
			odgovor = igra.pogadjaj(broj);
			
			switch (odgovor) {
				case VECI:		System.out.println("Zamisljeni broj je veci");									break;
				case MANJI:		System.out.println("Zamisljeni broj je manji");									break;
				case POGODAK:	System.out.println("Pogodili ste broj");										break;
				case KRAJ:		System.out.println("It's a sad thing that your adventure have ended here...");	break;
			}
			
		} while (odgovor != Odgovor.POGODAK && odgovor != Odgovor.KRAJ);
	}
}
