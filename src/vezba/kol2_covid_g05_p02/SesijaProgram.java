package vezba.kol2_covid_g05_p02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class SesijaProgram {
	
	
	private static final String[] CONTINENTS = { "Asia", "Europe", "America", "Africa", "Oceania" };
	

	public static void main(String[] args) throws NotBoundException, IOException {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		
		Server server = (Server) registry.lookup("Sesija");
		Sesija sesija = server.novaSesija();
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		Poruka poruka;
		String unos;
		
		do {
			
			System.out.print("Unos : ");
			unos = stdIn.readLine();
			
			if (jeKontinent(unos.trim())) {
				
				poruka = sesija.posaljiZahtev(unos.trim());
				System.out.println("Najvise umrlih za kontinent : " + ((Zemlja) poruka.getSadrzaj()).getCountry());
			} else if (unos.trim().equalsIgnoreCase("na dan")) {
				
				poruka = sesija.posaljiZahtev(unos.trim());
				Map<LocalDate, List<Zemlja>> m0 = (Map<LocalDate, List<Zemlja>>) poruka.getSadrzaj();
				
				System.out.println("Zemlje po prvim slucajevima zaraze : ");
				m0.entrySet().stream()
						.map(e -> String.format(" %s | %s", e.getKey(), e.getValue().stream().map(Zemlja::getCountry).reduce("", (s1, s2) -> "".equals(s1) ? s2 : s1 + ", " + s2)))
						.forEach(System.out::println);
			} else if (unos.trim().equalsIgnoreCase("tabela")) {
				
				poruka = sesija.posaljiZahtev(unos.trim());
				
				System.out.println((String) poruka.getSadrzaj());
			} else if (unos.trim().equalsIgnoreCase("kraj")){
				
				System.out.println("Kraj sesije.");
			} else {
				
				System.out.println("Pogresan unos.");
			}
			
		} while (!unos.equalsIgnoreCase("kraj"));
	}
	
	
	private static boolean jeKontinent(String s) {
		
		for (String c : CONTINENTS)
			if (c.equalsIgnoreCase(s))
				return true;
		
		return false;
	}
}
