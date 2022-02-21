package vezba.kol2_covid_g04_p02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class Sesija extends Thread {
	
	
	private Socket socket;
	private Podaci podaci;
	
	private BufferedReader in;
	private PrintWriter out;
	

	public Sesija(Socket klijent, Podaci podaci) throws IOException {
		
		this.socket = klijent;
		this.podaci = podaci;
		
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
	}

	
	@Override
	public void run() {
		
		try {
			
			String upit = in.readLine();
			System.out.println(this + " -> upit : " + upit);
			
			List<String> strings = podaci.getStrings(upit);
			
			if (strings == null || strings.size() == 0) {
				
				out.println("Nema podataka ili pogresan unos.");
				out.println();
				
			} else {
			
				out.println(podaci.getZemlja(upit));
				out.println(String.format("%20s %10s %10s", "=date=", "=cases=", "=deaths="));
				
				for (String s : strings)
					out.println(s);
				
				out.println();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
