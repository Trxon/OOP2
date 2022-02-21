package vezba.kol2_chat_g01_ss_p02;

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
			System.out.println("Konekcija na server...");
			
			try {
				
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				
				String unos;
				String odgovor = null;
				
				do {
					
					System.out.print("Klijent : ");
					unos = stdIn.readLine();
					out.println(unos);
					
					odgovor = in.readLine();
					
					if (!odgovor.equals("!"))
						System.out.println(odgovor);
					
				} while (!odgovor.equals("!") && !odgovor.equals("SERVER : Vidimo se."));
				
				do {
					
					odgovor = in.readLine();
					System.out.println(odgovor);
					
				} while (!odgovor.equals(""));
				
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
