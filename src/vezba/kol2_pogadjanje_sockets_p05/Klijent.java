package vezba.kol2_pogadjanje_sockets_p05;

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
				String odgovor;
				
				do {
					System.out.print("Unos : ");
					unos = stdIn.readLine();
					out.println(unos);
					odgovor = in.readLine();
					
					System.out.println(odgovor);
					
					if (odgovor.equals("="))
						System.out.println("Moj rang : " + in.readLine());
					
				} while (!odgovor.equals("="));
				
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
