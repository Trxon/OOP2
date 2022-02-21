package vezba.kol2_covid_g01_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			
			Pattern p0 = Pattern.compile("\\w*?,\\s*?\\d+\\/\\d+\\/\\d{4}");
			Matcher m0 = p0.matcher(unos);
			
			if (unos.equalsIgnoreCase("zemlje")) {
				
				List<Zemlja> zemlje = (List<Zemlja>) poruka.getSadrzaj();
				System.out.println(zemlje);
 			} else if (unos.equalsIgnoreCase("datumi")) {
 				
 				List<LocalDate> datumi = (List<LocalDate>) poruka.getSadrzaj();
				System.out.println(datumi);
 			} else if (m0.find()) {
 				
 				Podatak podatak = (Podatak) poruka.getSadrzaj();
 				System.out.println(podatak);
 			} else {
 				
 				System.out.println("Pogresan unos.");
 			}
			
		} while (!unos.equalsIgnoreCase("kraj"));
	}
}
