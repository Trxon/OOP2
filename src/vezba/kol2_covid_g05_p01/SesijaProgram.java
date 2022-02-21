package vezba.kol2_covid_g05_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class SesijaProgram {

	public static void main(String[] args) throws NotBoundException, IOException {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		
		Server server = (Server) registry.lookup("Sesija");
		Sesija sesija = server.novaSesija();
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		Poruka poruka;
		String unos;
		
		do {
			
			System.out.println("Unos : ");
			unos = stdIn.readLine();
			
			if (unos.equalsIgnoreCase("2")) {
				
				poruka = sesija.posaljiZahtev(unos);
				System.out.println("Novozarazeni na dan : " + (Map<LocalDate, List<Zemlja>>) poruka.getSadrzaj());
			} else if (unos.equalsIgnoreCase("3")) { 
				
				poruka = sesija.posaljiZahtev(unos);
				System.out.println("Tabela \n" + (String) poruka.getSadrzaj());
			} else {
				
				poruka = sesija.posaljiZahtev(unos);
				System.out.println((String) poruka.getSadrzaj());
			}
			
		} while (!unos.equalsIgnoreCase("kraj"));
	}
}
