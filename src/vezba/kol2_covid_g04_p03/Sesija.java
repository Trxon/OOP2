package vezba.kol2_covid_g04_p03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class Sesija extends Thread {

	
	private Socket socket;
	
	private BufferedReader in;
	private PrintWriter out;
	
	private Podaci podaci;
	
	
	public Sesija(Socket klijent, Podaci podaci) throws IOException {
		
		this.socket = klijent;
		
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		
		this.podaci = podaci;
	}
	
	
	@Override
	public void run() {
		
		try {
			
			String unos = in.readLine();
			System.out.println("Stiglo : " + unos);
			
			List<String> lista = podaci.podaciZaZemlju(unos);
			
			if (lista == null) {
				System.out.println("Greska.");
				out.println("Ne postoje podaci ili pogresan unos.\n");
				return;
			}
			
			for (String s : lista)
				out.println(s);
			
			System.out.println("Sesija " + this + " zavrsena.");
			this.socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
