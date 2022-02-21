package vezba.kol2_covid_g06_p02;

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
			System.out.println("Zapoceta je nova sesija...");
			
			try {
				
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				
				System.out.print("Unos : ");
				String unos = stdIn.readLine();
				
				out.println(unos);
				
				String odgovor;
				
				while (!"".equals(odgovor = in.readLine()))
					System.out.println(odgovor);
				
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
