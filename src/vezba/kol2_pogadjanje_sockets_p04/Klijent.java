package vezba.kol2_pogadjanje_sockets_p04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import domaci_zadaci.z06_p01.Odgovor;

public class Klijent {

	public static void main(String[] args) {
		
		try {
			
			Socket s = new Socket("localhost", 1234);
			System.out.println("Uspostavljena konekcija na server...");
			
			try {
				
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				
				System.out.print("Nadimak : ");
				String unos = stdIn.readLine();
				
				out.println(unos);
				
				boolean kraj = false;
				String odgovor;
				
				while (!kraj) {
					
					System.out.print("Unos : ");
					unos = stdIn.readLine();
					out.println(unos);
					
					odgovor = in.readLine();
					
					if (odgovor.equals(">")) {
						System.out.println("Broj je manji.");
					} else if (odgovor.equals("<")) {
						System.out.println("Broj je veci.");
					} else {
						
						System.out.println("Pogodak!");
						kraj = true;
						
						System.out.println("Moj rang : " + in.readLine());
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
