package vezba.kol2_covid_g04_p03;

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
			
			Socket klijent = new Socket("localhost", 1919);
			System.out.println("Nova sesija.");
			
			try {
				
				BufferedReader in = new BufferedReader(new InputStreamReader(klijent.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(klijent.getOutputStream()), true);
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				
				String unos;
				String odgovor;
				
				System.out.print("Unos : ");
				unos = stdIn.readLine();
				out.println(unos);
				
				while ((odgovor = in.readLine()) != null)
					System.out.println(odgovor);
				
			} finally {
				klijent.close();
			}
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
