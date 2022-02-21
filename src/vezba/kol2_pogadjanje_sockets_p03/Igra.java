package vezba.kol2_pogadjanje_sockets_p03;

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
	
	private int brPokusaja;
	private int broj;
	private String nadimak;
	
	
	public Igra(Socket klijent, RangLista rl) throws IOException {
		
		this.socket = klijent;
		this.rl = rl;
		
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		
		this.brPokusaja = 0;
		this.broj = (int) (1 + Math.random() * MAX_BROJ);
	}
	
	
	public int getBrojPokusaja() {
		return brPokusaja;
	}
	
	
	@Override
	public void run() {
		
		try {
			
			String nadimak = in.readLine();
			System.out.println(nadimak + " pogadja " + broj);
			
			boolean kraj = false;
			String linija;
			
			while ((linija = in.readLine()) != null && !kraj) {
				
//				System.out.println(linija);
				
				int pokusaj = Integer.parseInt(linija);
				
				brPokusaja++;
				
				if (pokusaj < broj) {
					out.println("<");
				} else if (pokusaj > broj) {
					out.println(">");
				} else if (pokusaj == broj) {
					
					out.println("=");
					kraj = true;
					
					int mojRang;
					synchronized (rl) {
						mojRang = rl.dodaj(this);
					}
					out.println(mojRang);
				}
			}
			
			System.out.println("Gotovo za " + nadimak + ".");
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
