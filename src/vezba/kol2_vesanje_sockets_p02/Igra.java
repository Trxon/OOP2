package vezba.kol2_vesanje_sockets_p02;

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
	private static final int ZIVOTI = 15;
	
	
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
		this.rec = RECI[(int) (Math.random() * RECI.length)];
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
			
			while ((linija = in.readLine()) != null && !kraj) {
				
				System.out.print("Igra " + this + " -> pokusaj : '" + linija + "'");
				karakteri.add(linija.charAt(0));
				String recTrenutno = recTrenutno();
				System.out.println(" (trenutno stanje : " + recTrenutno + " )");
				
				if (zivoti <= 0)
					out.println("END " + recTrenutno);
				
					zivoti--;
				
				if (!recTrenutno.equalsIgnoreCase(rec)) {
					out.println("MISS " + recTrenutno);
				} else {
					out.println("WIN " + recTrenutno);
					
					int rang;
					synchronized (rl) {
						rang = rl.dodaj(this);
					}
					
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
		
		String recTrenutno = rec;
		
		for (char c = 'a'; c <= 'z'; c++)
			if (!karakteri.contains(c))
				recTrenutno = recTrenutno.replaceAll(c + "", "-");
		
		return recTrenutno;
	}
	
	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
