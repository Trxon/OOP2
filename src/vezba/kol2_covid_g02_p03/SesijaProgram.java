package vezba.kol2_covid_g02_p03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class SesijaProgram {

	public static void main(String[] args) throws NotBoundException, IOException {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1919);
		
		Server server = (Server) registry.lookup("Sesija");
		Sesija sesija = server.novaSesija();
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String unos = null;
		
		List<String> odgovor = sesija.posaljiZahtev("kontinenti");
		
		odgovor.stream().forEach(System.out::println);
		
		System.out.print("Unos : ");
		unos = stdIn.readLine();
		
		odgovor = sesija.posaljiZahtev(unos);
		
		odgovor.stream().forEach(System.out::println);
	}
}
