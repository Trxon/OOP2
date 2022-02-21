package vezba.kol2_vesanje_sockets_p04;

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
			System.out.println("Nova sesija...");
			
			try {
				
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				
				System.out.print("Nadimak : ");
				String unos = stdIn.readLine();
				
				out.println(unos);
				
				boolean kraj = false;
				
				while (!kraj) {
					
					System.out.print("Unos : ");
					unos = stdIn.readLine();
					
					out.println(unos);
					String odgovor = in.readLine();
					
					if (odgovor.substring(0, 3).equals("END")) {
						System.out.println("Kraj igre.");
						kraj = true;
					} else if (odgovor.substring(0, 4).equals("MISS")) {
						System.out.println("Neotkriven deo : " + odgovor.substring(5));
					} else {
						System.out.println("Pobeda!");
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
