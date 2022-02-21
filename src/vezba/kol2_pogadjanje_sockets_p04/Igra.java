package vezba.kol2_pogadjanje_sockets_p04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Igra extends Thread {

	
	private static final int MAX_BROJ = 100;
	
	
	private Socket socket;
	private RangLista rl;
	
	private BufferedReader in;
	private PrintWriter out;
	
	private int broj;
	private int brPokusaja;
	
	private String nadimak;
	
	
	
	public Igra(Socket klijent, RangLista rl) throws IOException {
		
		this.socket = klijent;
		this.rl = rl;
		
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		
		this.broj = (int) (1 + Math.random() * MAX_BROJ);
		this.brPokusaja = 0;
	}
	
	
	public int getBrPokusaja() {
		return brPokusaja;
	}
	
	
	@Override
	public void run() {
		
		try {
			
			String unos = in.readLine();
			this.nadimak = unos;
			
			boolean kraj = false;
			
			while (!kraj) {
				
				unos = in.readLine();

				try {
					int pokusaj = Integer.parseInt(unos.trim());
					
					if (pokusaj < broj) {
						out.println("<");
					} else if (pokusaj > broj) {
						out.println(">");
					} else {
						
						out.println("=");
						kraj = true;
						
						int mojRang;
						synchronized(rl) {
							mojRang = this.rl.dodaj(this);
						}
						
						out.println(mojRang);
					}
				} catch (NumberFormatException e) {
					out.println("!");
				}
			}
			
			System.out.println("Igra " + this + " je zavrsena.");
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
