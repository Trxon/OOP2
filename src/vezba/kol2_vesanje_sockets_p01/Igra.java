package vezba.kol2_vesanje_sockets_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Igra extends Thread {
	
	
	private static final String[] RECI = { "ananas", "banana", "cvekla", "dinja" };
	private static final int ZIVOTI = 2;
	
	
	private final Socket socket;
	
	private final BufferedReader in;
	private final PrintWriter out;
	
	private final RangLista rl;
	private final String rec;
	private Set<Character> karakteri;
	private int zivoti;
	
	private String nadimak;
	
	
	public Igra(Socket klijent, RangLista rl) throws IOException {
		
		this.socket = klijent;
		
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		
		this.rl = rl;
		this.rec = RECI[(int) (Math.random() * RECI.length)].toUpperCase();
		this.karakteri = new HashSet<Character>();
		this.zivoti = ZIVOTI;
	}
	
	
	public int getZivoti() {
		return zivoti;
	}
	
	
	@Override
	public void run() {
		
		try {
			
			// MSG-1 RECEIVE nadimak
			this.nadimak = in.readLine();
			System.out.println("Zapoceta igra za : " + this.nadimak + " (rec : " + rec + ")");
			
			String linija = null;
			boolean kraj = false;
			
			// MSG-2 RECEIVE pokusaj
			while ((linija = in.readLine()) != null && !kraj) {
				
				System.out.print("Igra " + this + " -> pokusaj : '" + linija + "'");	// ISPIS : dodaje se primljen karakter...
				karakteri.add(linija.toUpperCase().charAt(0));							// karakter se dodaje u set isprobanih
				String recTrenutno = recTrenutno();										// azurira se trenutno stanje reci
				System.out.println(" (trenutno stanje : " + recTrenutno + " )");		// ISPIS : ...i dodaje se trenutna rec
				
				// MSG-3 SEND odgovor (kraj)
				if (zivoti <= 0)														// provera da li je igrac bez zivota
					out.println("END " + recTrenutno);									// i ako da - kraj igre
				
				zivoti--;																// skidanje jednog zivota
				
				// MSG-3 SEND odgovor (promasaj)
				if (!recTrenutno.equalsIgnoreCase(rec)) {								// provera da li je rec pogodjena
					out.println("MISS " + recTrenutno);									// i ako nije - poruka o promasaju
				// MSG-3 SEND odgovor (pogodak)
				} else {																// ako jeste...
					out.println("WIN " + recTrenutno);									// ...poruka o pogotku
					
					int rang;															// racunanje ranga
					synchronized (rl) {
						rang = rl.dodaj(this);
					}
					// MSG-4 SEND rang
					out.println(rang);
				}
			}
			

			System.out.println("Igra za " + nadimak + " je zavrsena.");
			this.socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private String recTrenutno() {
		
		String recTrenutno = this.rec;
		
		for (char c = 'A'; c <= 'Z'; c++)
			if (!karakteri.contains(c))
				recTrenutno = recTrenutno.replaceAll(c + "", "-");
		
		return recTrenutno;
	}
}
