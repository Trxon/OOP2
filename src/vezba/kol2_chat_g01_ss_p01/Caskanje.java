package vezba.kol2_chat_g01_ss_p01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
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
	
	private PrintWriter pw;
	private int phase;
	
	
	public Caskanje(Socket klijent) throws IOException {
		
		this.socket = klijent;
		
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		
		this.pw = new PrintWriter(new BufferedWriter(new FileWriter("res//log_socket.txt")));
		this.phase = 0;
	}
	
	
	@Override
	public void run() {
		
		try {
			
			do {
				
				String unos = in.readLine();
				System.out.println("Caskanje " + this + " -> " + unos + " (phase : " + phase + ")");
				
				if (phase == 0) {
					
					int hour = LocalTime.now().getHour();
					
					if (unos.equalsIgnoreCase("Dobro jutro!") && (4 <= hour && hour < 12)) {
						
						phase++;
						String output = "SERVER : " + unos;
						
						pw.println("Klijent : " + unos);
						pw.println(output);
						
						out.println(output);
						
					} else if (unos.equalsIgnoreCase("Dobar dan!") && (12 <= hour && hour < 18)) {

						phase++;
						String output = "SERVER : " + unos;
						
						pw.println("Klijent : " + unos);
						pw.println(output);
						
						out.println(output);
						
					} else if (unos.equalsIgnoreCase("Dobro vece!") && (18 <= hour || hour < 4)) {

						phase++;
						String output = "SERVER : " + unos;
						
						pw.println("Klijent : " + unos);
						pw.println(output);
						
						out.println(output);
						
					} else {
						System.out.println("AA");
						out.println("!");
					}
				} else if (phase == 1) {
					
					if (unos.equalsIgnoreCase("Kako si?")) {
						
						String output = "SERVER : Nije lose.";
						
						pw.println("Klijent : " + unos);
						pw.println(output);
						
						out.println(output);
						
					} else if (unos.equalsIgnoreCase("Dovidjenja!")) {
						
						phase++;
						String output = "SERVER : Vidimo se!";
						
						pw.println("Klijent : " + unos);
						pw.println(output);
						
						out.println(output);
						
					} else {
						
						String output = "SERVER : Ne razumem...";

						pw.println("Klijent : " + unos);
						pw.println(output);
						
						out.println(output);
						
					}
				} else {
					
					phase++;
					out.println("!");
				}
			} while (phase < 3);
			
			pw.close();
			
			System.out.println("Caskanje " + this + " zavrsena.");
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
