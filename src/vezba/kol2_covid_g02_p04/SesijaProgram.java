package vezba.kol2_covid_g02_p04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SesijaProgram {

	public static void main(String[] args) throws NotBoundException, IOException {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1919);
		
		try {
			
			Server server = (Server) registry.lookup("Sesija");
			Sesija sesija = server.novaSesija();
			
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			String unos;
			String odgovor;
			
			// lista kontinenata
			odgovor = sesija.posaljiZahtev("1");
			System.out.println("LISTA KONTINENATA : \n" + odgovor + "\n");
			
			System.out.print("Unos : ");
			unos = stdIn.readLine();
			odgovor = sesija.posaljiZahtev("2" + unos);
			
			if (odgovor.charAt(0) != '!') {
				
				System.out.println(odgovor + "\n");
				
				odgovor = sesija.posaljiZahtev("3");
				System.out.println("LISTA ZEMALJA : \n" + odgovor + "\n");
				
			} else {
				System.err.println("Pogresan unos ili ne postoje podaci.");
			}
			
		} catch (ConnectException e) {
			System.err.println("NEMA SERVERA -> " + e.getMessage());
		}
	}
}
