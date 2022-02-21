package vezba.kol2_covid_g06_p03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

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
			System.out.println("Stiglo : " + unos);
			
			String rezultat = podaci.podaciZaZemlju(unos);
			
			for (String s : rezultat.split("\n"))
				out.println(s);
			
			out.println("");
			
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
