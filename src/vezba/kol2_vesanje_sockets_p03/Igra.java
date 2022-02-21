package vezba.kol2_vesanje_sockets_p03;

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
	
	
	private Socket socket;
	
	private BufferedReader in;
	private PrintWriter out;
	
	private int zivoti;
	private String rec;
	private Set<Character> karakteri;
	
	private RangLista rl;
	
	private String nadimak; 
	

	public Igra(Socket klijent, RangLista rl) throws IOException {
		
		this.socket = klijent;
		
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		
		this.zivoti = ZIVOTI;
		this.rec = RECI[(int) (Math.random() * RECI.length)];
		this.karakteri = new HashSet<Character>();
		
		this.rl = rl;
	}
	

	public int getBrZivota() {
		return zivoti;
	}
	
	
	@Override
	public void run() {
		
		try {
			// MSG-01
			this.nadimak = in.readLine();
			System.out.println("Zapoceta igra za : " + nadimak + " (" + rec + ")");
			
			boolean kraj = false;
			String linija = null;
			
			// MSG-02
			while ((linija = in.readLine()) != null && !kraj) {
				
				System.out.println("Igra " + this + " -> pokusaj '" + linija + "'");
				karakteri.add(linija.charAt(0));
				String recTrenutno = recTrenutno();
				System.out.println(" (trenutno stanje: " + recTrenutno + ")");
				
				// MSG-03
				if (zivoti <= 0) {
					
					out.println("END " + recTrenutno);
					kraj = true;
				} else if (!recTrenutno.equalsIgnoreCase(rec)) {
					
					out.println("MISS " + recTrenutno);
					zivoti--;
				} else {
					
					out.println("WIN " + recTrenutno);
					kraj = true;
					
					// MSG-04
					int mojRang;
					synchronized (rl) {
						mojRang = rl.dodaj(this);
					}
					out.println(mojRang);
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
}
