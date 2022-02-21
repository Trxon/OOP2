package vezba.kol2_vesanje_sockets_p04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Sesija extends Thread {

	
	private static final String[] RECI = { "ananas", "banana", "cvekla", "dinja" };
	private static final int ZIVOTI = 15;
	
	
	private final Socket socket;
	
	private final BufferedReader in;
	private final PrintWriter out;
	
	private final String rec;
	private Set<Character> karakteri;
	private int zivoti;
	
	private String nadimak;
	
	
	public Sesija(Socket klijent) throws IOException {
		
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
			
			String unos = in.readLine();
			this.nadimak = unos;
			
			System.out.println("Nadimak igraca u " + this + " : " + nadimak);
			
			boolean kraj = false;
			
			while (!kraj) {
				
				unos = in.readLine();
				
				System.out.print("Za igraca " + nadimak + " -> pokusaj '" + unos + "'");
				karakteri.add(unos.charAt(0));
				String recTrenutno = recTrenutno();
				System.out.println(" (trenutno stanje : " + recTrenutno + ")");
				
				if (zivoti <= 0) {
					out.println("END " + recTrenutno);
					kraj = true;
				}
				
				zivoti--;
				
				if (!recTrenutno.equals(rec)) {
					out.println("MISS " + recTrenutno);
				} else {
					out.println("WIN " + recTrenutno);
					kraj = true;
				}
			}
			
			System.out.println("Zavrsena igra " + this + " za " + nadimak + ".");
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
