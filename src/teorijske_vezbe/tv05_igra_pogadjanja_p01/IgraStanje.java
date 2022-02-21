package teorijske_vezbe.tv05_igra_pogadjanja_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class IgraStanje extends Thread {
	
	
	private final Socket socket;
	private final BufferedReader in;
	private final PrintWriter out;
	private int broj;
	private String nadimak;
	private final RangLista rl;
	private int brPokusaja;

	
	public IgraStanje(Socket klijent, RangLista rl) throws IOException {
		
		// zapamtimo socket...
		this.socket = klijent;
		
		// baferovan tok (nivo linija) <- tekstualni tok (nivo karaktera) <- binarni tok 
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		
		// broj koji se pogadja
		this.broj = (int) (Math.random() * 100) + 1;
		this.brPokusaja = 0;
		
		this.rl = rl;		
	}
	
	
	@Override
	public void run() {
		
		try {
			
			nadimak = in.readLine();
			
			String linija;
			boolean kraj = false;
			
			while ((linija = in.readLine()) != null && !kraj) {
				
				try {
					
					// nakon sto smo procitali vracamo < > ! =
					int pokusaj = Integer.parseInt(linija);
					
					this.brPokusaja++;
					
					if (pokusaj == broj) {
						
						out.println("=");
						kraj = true;
						
						int rang = -1;
						
						synchronized(rl) {
							rang = rl.dodaj(this);
						}
						
						out.println(rang);
						
					} else if (pokusaj < broj) {
						out.println(">");
					} else {
						out.println("<");
					}
					
				} catch (NumberFormatException e) {
					out.println("! " + e.getMessage());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		System.out.println("Igra je zavrsena.");
	}
	
	
	public int brPokusaja() { return brPokusaja; }
}
