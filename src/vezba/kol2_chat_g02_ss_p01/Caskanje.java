package vezba.kol2_chat_g02_ss_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;

public class Caskanje extends Thread {

	
	private Socket socket;
	
	private BufferedReader in;
	private PrintWriter out;
	
	private int phase;
	
	
	public Caskanje(Socket klijent) throws IOException {
		
		this.socket = klijent;
		
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		
		this.phase = 0;
	}
	
	
	@Override
	public void run() {
		
		try {
			
			do {
				
				String unos = in.readLine();
				System.out.println("Unos : " + unos);
				
				if (phase == 0) {
					
					int hour = LocalTime.now().getHour();
					
					if (unos.equalsIgnoreCase("Dobro jutro!") && (4 <= hour && hour < 12)) {
						phase++;
						out.println("SERVER : " + unos);
					} else if (unos.equalsIgnoreCase("Dobar dan!") && (12 <= hour && hour < 18)) {
						phase++;
						out.println("SERVER : " + unos);
					} else if (unos.equalsIgnoreCase("Dobro vece!") && (18 <= hour || hour < 4)) {
						phase++;
						out.println("SERVER : " + unos);
					} else {
						out.println("!");
					}
					
				} else if (phase == 1) {
					
					if (unos.equalsIgnoreCase("Kako si?")) {
						out.println("SERVER : Dobro sam");
					} else if (unos.equalsIgnoreCase("Osecam se dobro.")) {
						out.println("SERVER : Blago tebi.");
					} else if (unos.equalsIgnoreCase("Osecam se lose.")) {
						out.println("I ja.");
					} else if (unos.equalsIgnoreCase("Dovidjenja!")) {
						out.println("SERVER : Pozdrav!");
						phase++;
					} else {
						out.println("SERVER : Ne razumem...");
					}
					
				} else {
					
					out.println("!");
					phase++;
				}
				
			} while (phase < 3);
			
			System.out.println("Sesija zavrsena.");
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
