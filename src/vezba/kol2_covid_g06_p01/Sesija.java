package vezba.kol2_covid_g06_p01;

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
			
			String unos = in.readLine();
			System.out.println(unos);
			
			List<Podatak> lista = podaci.getMapa().get(unos.toUpperCase());
			
//			out.println(lista);
			
			out.println(String.format("%10s %5s %5s", "datum", "slucajeva", "umrlih"));
			
			for (Podatak p : lista)
				out.println(p);
			
			out.println();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
