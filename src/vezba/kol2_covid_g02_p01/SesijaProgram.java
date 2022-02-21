package vezba.kol2_covid_g02_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class SesijaProgram {

	public static void main(String[] args) throws NotBoundException, IOException {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		
		Server server = (Server) registry.lookup("Sesija");
		Sesija sesija = server.novaSesija();
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String unos;
		Poruka poruka;
		
		do {
			
			System.out.print("Unos : ");
			unos = stdIn.readLine();
			poruka = sesija.posaljiZahtev(unos);
			
			if (!unos.equalsIgnoreCase("kraj"))
				if (unos.equalsIgnoreCase("kontinenti")) {
					
					List<String> kontinenti = (List<String>) poruka.getSadrzaj();
					System.out.println(kontinenti);
				} else {
					
					List<Zemlja> zemlje = (List<Zemlja>) poruka.getSadrzaj();
	 				System.out.println(zemlje);
				}
			
		} while (!unos.equalsIgnoreCase("kraj"));
	}
}
