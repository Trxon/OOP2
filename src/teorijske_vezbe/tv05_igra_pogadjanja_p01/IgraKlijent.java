package teorijske_vezbe.tv05_igra_pogadjanja_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class IgraKlijent {

	public static void main(String[] args) {
		
		try {
			
			// localhost je alias za masinu na kojoj se izvrsava
			Socket s = new Socket("localhost", 1234);
			
			try {
				
				System.out.println("Povezali smo se na server...");
				
				// komunikacija preko mreze
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
				
				// korisnicki unos
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				
				System.out.println("Kako se zoves? ");
				String nadimak = stdIn.readLine();
				
				// saljemo nadimak serveru
				out.println(nadimak);
				
				boolean kraj = false;
				
				while (!kraj) {
					System.out.println("Unesi broj : ");
					String unos = stdIn.readLine();
					
					// saljemo unos serveru
					out.println(unos);
					
					// odgovor servera na unos
					String odgovor = in.readLine();
					if ("=".equals(odgovor)) kraj = true;
					
					System.out.println(odgovor);
				}
				
				// saljemo rang koji se samo ispisuje na kraju igre
				String mojRang = in.readLine();
				System.out.println(mojRang);
				
			} finally {
				s.close();
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
