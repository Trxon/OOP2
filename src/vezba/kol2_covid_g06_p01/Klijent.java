package vezba.kol2_covid_g06_p01;

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
			
			Socket s = new Socket("localhost", 1919);
			
			try {
				
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
				
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				
				System.out.print("Unos : ");
				String unos = stdIn.readLine();
				
				out.println(unos);
				
				String odgovor;
				
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
