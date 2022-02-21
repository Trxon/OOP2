package domaci_zadaci.z05_ass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class KlijentProgram {

	public static void main(String[] args) throws IOException, NumberFormatException {

		// Prvo otvaramo soket prema serveru
		// a potom i tokove za citanje i pisanje
		// Koristimo try-with-resources kako ne bismo morali da brinemo o zatvaranju soketa
		// Otvaramo usput i tok za citanje sa tastature
		try (
				Socket socket = new Socket("localhost", 1234);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
				BufferedReader unos = new BufferedReader(new InputStreamReader(System.in));
		) {

			// Sa servera stizu parovi linija:
			// 1. rec koju pogadjamo
			// 2. broj preostalih pokusaja
			String rec = in.readLine();
			int pokusaji = Integer.parseInt(in.readLine());

			// Igramo se dok ne potrosimo sve pokusaje ili ne pogodimo rec
			// Pogodili smo rec ako nema vise crtica u stringu
			while (pokusaji > 0 && rec.contains("-")) {

				// Prikazujemo stanje igre korisniku i pitamo ga za novi pokusaj
				System.out.println(rec.replaceAll("(.)", "$1 "));
				System.out.printf("Proj pokusaja: %d, unesi slovo:%n", pokusaji);
				String slovo = unos.readLine();

				// Saljemo pokusaj serveru
				out.println(slovo);

				// I citamo njegov odgovor
				rec = in.readLine();
				pokusaji = Integer.parseInt(in.readLine());

			}

			// Proveravamo da li smo pobedili ili izgubili
			if (pokusaji > 0) {
				System.out.println("Bravo, pogodio si rec");
			} else {
				System.out.println("Nazalost, nisi pogodio rec");
			}

			// Ne moramo rucno da zatvaramo tokove i soket,
			// to ce try-with-resources uraditi automatski za nas

		}
	}
}
