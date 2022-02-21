package vezba.kol2_vesanje_sockets_p03;

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
			System.out.println("Konekcija uspostavljena...");
			
			try {
				
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				
				System.out.print("Nadimak : ");
				String nadimak = stdIn.readLine();
				
				// MSG-01
				out.println(nadimak);
				
				boolean kraj = false;
				
				while (!kraj) {
					
					System.out.print("Unos : ");
					String unos = stdIn.readLine();
					
					// MSG-02
					out.println(unos);
					
					// MSG-03
					String odgovor = in.readLine();
					
					if (odgovor == null || odgovor.substring(0, 3).equals("WIN")) {
						
						System.out.println("Rec je otkrivena -> " + odgovor.substring(4));
						
						// MSG-04
						String mojRang = in.readLine();
						System.out.println("Moj rang : " + mojRang);
						
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
