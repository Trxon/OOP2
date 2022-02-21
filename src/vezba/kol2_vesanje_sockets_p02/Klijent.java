package vezba.kol2_vesanje_sockets_p02;

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
			
			Socket s = new Socket("localhost", 1234);
			System.out.println("Uspostavljena je konekcija sa serverom...");
			
			try {
			
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				
				System.out.print("Unesite nadimak : ");
				String nadimak = stdIn.readLine();
				
				out.println(nadimak);
				
				boolean kraj = false;
				
				while (!kraj) {
					
					System.out.print("Unesite karakter : ");
					String pokusaj = stdIn.readLine();
					
					out.println(pokusaj);
					
					String odgovor = in.readLine();
					
					if (odgovor == null || odgovor.substring(0, 3).equals("WIN")) {
						
						System.out.println("Rec je otkrivena -> " + odgovor.substring(4));
						kraj = true;
						
						String mojRang = in.readLine();
						System.out.println("Rang : " + mojRang);
						
					} else if (odgovor.subSequence(0, 4).equals("MISS")) {
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
