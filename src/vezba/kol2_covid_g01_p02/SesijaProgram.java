package vezba.kol2_covid_g01_p02;

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

	public static void main(String[] args) throws IOException, NotBoundException {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		
		Server server = (Server) registry.lookup("Sesija");
		Sesija sesija = server.novaSesija();
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		Poruka poruka;
		String unos;
		
		do {
			
			System.out.print("Unos : ");
			unos = stdIn.readLine();
			
			Pattern p0 = Pattern.compile("^\\w+,\\s*?\\d{2}\\/\\d{2}\\/\\d{4}$");
			Matcher m0 = p0.matcher(unos);
			
			if (unos.equalsIgnoreCase("zemlje")) {
				
				poruka = sesija.posaljiZahtev(unos);
				System.out.println((List<Zemlja>) poruka.getSadrzaj());
			} else if (unos.equalsIgnoreCase("podaci")) {
				
				poruka = sesija.posaljiZahtev(unos);
				System.out.println((List<LocalDate>) poruka.getSadrzaj());
			} else if (m0.find()) {
				
				poruka = sesija.posaljiZahtev(unos);
				System.out.println((Podatak) poruka.getSadrzaj());
			} else if (unos.equalsIgnoreCase("kraj")) {
				
				System.out.println("Kraj sesije.");
			} else {
				
				System.out.println("Pogresan unos.");
			}
			
		} while (!unos.equalsIgnoreCase("kraj"));
	}
}
