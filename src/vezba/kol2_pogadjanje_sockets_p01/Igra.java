package vezba.kol2_pogadjanje_sockets_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Igra extends Thread {

	
	private final Socket socket;
	
	private final BufferedReader in;
	private final PrintWriter out;
	
	private final RangLista rl;
	private final int broj;
	private int brPokusaja;

	private String nadimak;
	
	
	public Igra(Socket klijent, RangLista rl) throws IOException {
		
		this.socket = klijent;
		
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		
		this.rl = rl;
		this.broj = (int) (1.0 + Math.random() * 100.0);
		this.brPokusaja = 0;
	}
	
	
	public int getBrPokusaja() {
		return brPokusaja;
	}
	
	
	@Override
	public void run() {
		
		try {
			
			// MSG-1 RECEIVE nadimak
			this.nadimak = in.readLine();
			System.out.println("Zapoceta igra za : " + this.nadimak);
			
			String linija = null;
			boolean kraj = false;
			
			// MSG-2 RECEIVE pokusaj 
			while ((linija = in.readLine()) != null && !kraj) {
				
				System.out.println("Primljeno '" + linija + "' od " + this.nadimak);
				
				try {
					
					int pokusaj = Integer.parseInt(linija);
					this.brPokusaja++;
					
					if (pokusaj == broj) {
						
						// MSG-3 SEND odgovor (pogodak)
						out.println("=");
						kraj = true;
						
						int rang;
						synchronized (rl) {
							rang = rl.dodaj(this);
						}
						// MSG-4 SEND rang
						out.println(rang);
					
					} else if (pokusaj < broj) {
						
						// MSG-3 SEND odgovor (broj je veci)
						out.println(">");
					} else {
						
						// MSG-3 SEND odgovor (broj je manji)
						out.println("<");
					}
					
					
				} catch (NumberFormatException e) {
					// MSG-3 SEND odgovor (nfe)
					out.println("! " + e.getMessage());
				}
			}
			
			System.out.println("Igra za " + nadimak + " je zavrsena.");
			this.socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
