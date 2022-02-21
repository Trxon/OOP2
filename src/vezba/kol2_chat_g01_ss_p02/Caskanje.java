package vezba.kol2_chat_g01_ss_p02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Caskanje extends Thread {

	
	private Socket socket;
	
	private BufferedReader in;
	private PrintWriter out;
	
	private int phase;
	private List<String> istorija;
	
	public Caskanje(Socket klijent) throws IOException {
		
		this.socket = klijent;
		
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		
		this.phase = 0;
		this.istorija = new ArrayList<String>();
	}
	
	
	@Override
	public void run() {
		
		try {
			
			do {
				
				String unos = in.readLine();
				System.out.println("UNOS : " + unos);
				
				if (phase == 0) {
					
					int hour = LocalTime.now().getHour();
					
					if (
							(unos.equalsIgnoreCase("Dobro jutro!") && (4 <= hour && hour < 12)) || 
							(unos.equalsIgnoreCase("Dobar dan!") && (12 <= hour && hour < 18)) || 
							(unos.equalsIgnoreCase("Dobro vece!") && (18 <= hour || hour < 4))
						) {
											
						phase++;
						String output = "SERVER : " + unos;
						
						istorija.add("Klijent : " + unos);
						istorija.add(output);
						
						out.println(output);
						
					} else {	
						out.println("!");
					}
				} else if (phase == 1) {
					
					if (unos.equals("Kako si?")) {
						
						String output = "SERVER : Nije lose.";
						
						istorija.add("Klijent : " + unos);
						istorija.add(output);
						
						out.println(output);
					} else if (unos.equals("Dovidjenja!")) {
						
						phase++;
						String output = "SERVER : Vidimo se.";
						
						istorija.add("Klijent : " + unos);
						istorija.add(output);
						
						out.println(output);
					} else {
						
						String output = "SERVER : Ne razumem.";
						
						istorija.add("Klijent : " + unos);
						istorija.add(output);
						
						out.println(output);
					}
				}
				
			} while (phase < 2);
			
			out.println("ISTORIJA CASKANJA : ");
			
			for (String s : istorija)
				out.println(s);
			
			out.println();
			
			System.out.println("Caskanje " + this  + " zavrseno.");
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
