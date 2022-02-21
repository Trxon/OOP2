package vezba.kol2_vesanje_sockets_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.UnknownHostException;

public class Klijent {

	public static void main(String[] args) {
		
		try {
			
			// zna se da je server na "localhost" masini i na portu 1234
			Socket s = new Socket("localhost", 1234);
			System.out.println("Uspostavljena konekcija sa serverom...");
			
			try {
				
				// ulazna komunikacija sa serverom 
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				// izlazna komunikacija sa serverom
				PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
				// korisnicki unos
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				
				System.out.print("Unesite nadimak : ");
				String nadimak = stdIn.readLine();
				
				// MSG-1 SEND nadimak
				out.println(nadimak);
				
				boolean kraj = false;
				while (!kraj) {
					
					System.out.print("Unesite karakter : ");
					String pokusaj = stdIn.readLine();
					
					// MSG-2 SEND pokusaj 
					out.println(pokusaj);
					
					// MSG-3 RECEIVE odgovor
					String odgovor = in.readLine();
					
					if (odgovor == null || odgovor.substring(0, 3).equals("WIN")) {
						
						System.out.println("Rec je otkrivena -> " + odgovor.substring(4));
						kraj = true;
						
						// MSG-4 RECEIVE rang
						String mojRang = in.readLine();
						System.out.println("Rang : " + mojRang);
						
					} else if (odgovor.substring(0, 4).equals("MISS")) {
						System.out.println("Delovi reci su jos skriveni -> " + odgovor.substring(5));
					} else {
						System.out.println("It's a sad thing that your adventures have ended here...");
						kraj = true;
					}
				}
				
			} finally {
				s.close();
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
