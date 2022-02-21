package vezba.kol2_pogadjanje_sockets_p05;

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
			
			int pokusaj = -1;
			
			do {
				
				String input = in.readLine();
				System.out.println("Igra " + this + " -> " + input + " (" + brPokusaja + " pokusaja do sada)");
				
				brPokusaja++;
				
				try {
					
					pokusaj = Integer.parseInt(input.trim());
					
					if (pokusaj > broj)
						out.println(">");
					else if (pokusaj < broj)
						out.println("<");
					else {
						out.println("=");
						
						int rang = -1;
						synchronized (rl) {
							rang = rl.dodaj(this);
						}
						out.println(rang);
					}
					
				} catch (NumberFormatException e) {
					out.println("!");
				}
			
			} while (pokusaj != broj);
			
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
