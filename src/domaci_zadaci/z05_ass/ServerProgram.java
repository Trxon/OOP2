package domaci_zadaci.z05_ass;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerProgram {

	public static void main(String[] args) throws IOException {
		
		// Otvaramo serverski soket za prihvatanje konekcija
		// Koristimo try-with-resources kako ne bismo morali da brinemo o zatvaranju soketa
		try (ServerSocket ss = new ServerSocket(1234)) {
			System.err.println("Server je pokrenut");

			// Glavna nit prihvata konekcije i za svaku pristiglu konekciju kreira novu igru i pokrece je
			// Igra je nit za sebe, tako da glavna nit moze odmah da se vrati na prihvatanje novih konekcija
			// Svaka nit igre ce nezavisno od ostalih obradjivati svoje zahteve i realizovati svoju komunikaciju
			while (!Thread.interrupted()) {
				Socket igrac = ss.accept();
				Igra igra = new Igra(igrac);
				igra.start();
			}

		}

	}
}
