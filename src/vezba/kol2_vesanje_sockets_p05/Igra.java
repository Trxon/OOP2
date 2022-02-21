package vezba.kol2_vesanje_sockets_p05;

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
	
	private String rec;
	private Set<Character> karakteri;
	private int zivoti;
	
	
	
	public Igra(Socket klijent) throws IOException {
		
		this.socket = klijent;
		
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		
		this.rec = RECI[(int) (Math.random() * RECI.length)];
		this.karakteri = new HashSet<Character>();
		this.zivoti = ZIVOTI;
	}
	
	
	@Override
	public void run() {
		
		try {
			
			boolean kraj = false;
			
			while (!kraj) {
				
				String unos = in.readLine();

				System.out.print("Igra " + this + " -> '" + unos + "'");
				karakteri.add(unos.charAt(0));
				String recTrenutno = recTrenutno();
				System.out.println(" (trenutno stanje : " + recTrenutno + ")");
				
				if (zivoti <= 0) {
					kraj = true;
					out.println("END " + recTrenutno);
				}
				
				zivoti--;
				
				if (recTrenutno.equalsIgnoreCase(rec)) {
					kraj = true;
					out.println("WIN " + recTrenutno);
				} else {
					out.println("MISS " + recTrenutno);
				}
			}
			
			System.out.println("Igra " + this + " je zavrsena.");
			this.socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private String recTrenutno() {
		
		String recTrenutno = rec;
		
		for (char c = 'a'; c <= 'z'; c++)
			if (!karakteri.contains(c))
				recTrenutno = recTrenutno.replace(c + "", "-");
		
		return recTrenutno;
	}


	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
