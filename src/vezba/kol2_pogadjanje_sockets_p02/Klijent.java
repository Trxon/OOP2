package vezba.kol2_pogadjanje_sockets_p02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Klijent {

	public static void main(String[] args) {
		
		try {
			
			// zna se da je server na "localhost" masini i na portu 1234
			Socket s = new Socket("localhost", 1234);
			System.out.println("[IGRA POGADJANJA] Uspostavljena konekcija sa serverom...");
			
			// ovaj try blok se koristi da bi pokretanje s.close() bilo osigurano
			try {
				
				// ulazna komunikacija sa serverom 
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				// izlazna komunikacija sa serverom (true sluzi za auto flush)
				PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
				// korisnicki unos
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				
				System.out.print("Unesite nadimak : ");
				String nadimak = stdIn.readLine();
				
				// MSG-1 SEND nadimak
				out.println(nadimak);
				
				boolean kraj = false;
				
				while (!kraj) {
					
					System.out.print("Unesite broj : ");
					String pokusaj = stdIn.readLine();
					
					// MSG-2 SEND pokusaj 
					out.println(pokusaj);
					
					// MSG-3 RECEIVE odgovor
					String odgovor = in.readLine();
					if (odgovor == null || odgovor.equals("="))
						kraj = true;
					
					System.out.println(odgovor);
				}
				
				// MSG-4 RECEIVE rang
				String mojRang = in.readLine();
				System.out.println("Rang : " + mojRang);
				
			} finally {
				s.close();
			}
			
		} catch (UnknownHostException e) {
			// pri kreiranju ServerSocket objekta ponudjeno je postavljanje try-catch bloka
			e.printStackTrace();
		} catch (IOException e) {
			// pri kreiranju ServerSocket objekta ponudjeno je postavljanje try-catch bloka
			e.printStackTrace();
		}
	}
}
