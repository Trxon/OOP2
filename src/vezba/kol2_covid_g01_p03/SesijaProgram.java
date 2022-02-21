package vezba.kol2_covid_g01_p03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.util.List;

public class SesijaProgram {

	public static void main(String[] args) throws NotBoundException, IOException {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1919);
		
		Server server = (Server) registry.lookup("Sesija");
		Sesija sesija = server.novaSesija();
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("LISTA ZEMALJA SA DOSTUPNIM PODACIMA : ");
		
		List<Zemlja> zemlje = (List<Zemlja>) (sesija.posaljiZahtev("0").getSadrzaj());
		zemlje.stream().map(z -> String.format("\t%s", z)).forEach(System.out::println);
		
		System.out.print("Unos : ");
		String zemlja = stdIn.readLine();
		
		List<LocalDate> datumi = (List<LocalDate>) (sesija.posaljiZahtev("1").getSadrzaj());
		datumi.stream().map(p -> String.format("\t%s", p)).forEach(System.out::println);
		
		System.out.print("Unos : ");
		String datum = stdIn.readLine();
		
		Poruka poruka = sesija.posaljiZahtev(zemlja + "±" + datum);
		
		if (poruka != null) {
			Podatak p = (Podatak) (sesija.posaljiZahtev(zemlja + "±" + datum).getSadrzaj());
			System.out.printf(" %12s | %10s | %10s || %s %n", "date", "cases", "deaths", "country data");
			System.out.println(p);
		} else {
			System.out.println("Ne postoje podaci.");
		}
	}
}
