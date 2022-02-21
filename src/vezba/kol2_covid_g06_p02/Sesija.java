package vezba.kol2_covid_g06_p02;

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
			System.out.println("Upit : " + upit);
			
			upit = upit.toUpperCase();
			
			List<Podatak> lista = podaci.getMapa().get(upit);
			
			if (lista == null || lista.size() == 0) {
				
				out.println("Nema podataka ili pogresan unos.");
				out.println();
				
			} else {
			
				out.println("Data for : " + upit);
				out.println(String.format("%10s %10s %10s", "DATE", "CASES", "DEATHS"));
				
				for (Podatak p : lista)
					out.println(p);
				
				out.println();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
