package vezba.kol2_covid_g05_p03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SesijaProgram {

	public static void main(String[] args) throws NotBoundException, IOException {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1919);
		
		Server server = (Server) registry.lookup("Sesija");
		Sesija sesija = server.novaSesija();
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String unos;
		String odgovor;
		
		System.out.print("Kontinent : ");
		unos = stdIn.readLine();		
		odgovor = sesija.posaljiZahtev(unos);
		
		System.out.println();
		System.out.println(odgovor);
	}
}
