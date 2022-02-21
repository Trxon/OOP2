package vezba.kol2_vesanje_sockets_p05;

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
			System.out.println("Nova sesija.");
			
			try {
				
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				
				boolean kraj = false;
				
				while (!kraj) {
					
					System.out.print("Unos : ");
					String unos = stdIn.readLine();
					
					out.println(unos);
					String odgovor = in.readLine();
					
					if (odgovor.startsWith("MISS")) {
						System.out.println("PROMASAJ : " + odgovor.substring(5));
					} else if (odgovor.startsWith("END")) {
						kraj = true;
						System.out.println("KRAJ IGRE : " + odgovor.substring(4));
					} else {
						kraj = true;
						System.out.println("POGODAK : " + odgovor.substring(4));
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
