package vezba.kol2_covid_g02_p02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class SesijaProgram { 

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws NotBoundException, IOException {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		
		Server server = (Server) registry.lookup("Sesija");
		Sesija sesija = server.novaSesija();
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String unos;
		
		do {
			
			System.out.print("Unos : ");
			unos = stdIn.readLine();
			
			if (unos.equalsIgnoreCase("kontinenti"))
				System.out.println((List<String>) sesija.posaljiZahtev(unos).getSadrzaj());
			
			else if (	unos.equalsIgnoreCase("africa") || unos.equalsIgnoreCase("america") 	|| 
						unos.equalsIgnoreCase("asia") 	|| unos.equalsIgnoreCase("australia") 	|| 
						unos.equalsIgnoreCase("europe") || unos.equalsIgnoreCase("oceania") 	|| unos.equalsIgnoreCase("other"))
				System.out.println((List<Zemlja>) sesija.posaljiZahtev(unos).getSadrzaj());
			
			else if (unos.equals("kraj"))
				System.out.println("Kraj sesije.");
			
			else
				System.out.println("Pogresan unos.");
			
		} while (!unos.equalsIgnoreCase("kraj"));
	}
}
