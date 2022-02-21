package vezba.kol2_pogadjanje_sockets_p03;

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
			System.out.println("Konekcija uspostavljena.");
			
			try {
				
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				
				System.out.print("Nadimak : ");
				String nadimak = stdIn.readLine();
				
				out.println(nadimak);
				
				String unos;
				boolean kraj = false;
				
				while (!kraj) {
					
					System.out.print("Unos : ");
					unos = stdIn.readLine();
					
					out.println(unos);
					
					String odgovor = in.readLine();
					
					if (odgovor.equals(">")) { 
						
						System.out.println("Trazeni broj je MANJI.");
					} else if (odgovor.equals("<")) { 
						
						System.out.println("Trazeni broj je VECI.");
					} else {
						
						System.out.println("Pogodak");
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
