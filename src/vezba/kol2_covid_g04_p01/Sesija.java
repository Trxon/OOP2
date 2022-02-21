package vezba.kol2_covid_g04_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Sesija extends Thread {

	
	private Socket socket;
	private Data data;
	
	private BufferedReader in;
	private PrintWriter out;
	
	
	public Sesija(Socket klijent, Data data) throws IOException {
	
		this.socket = klijent;
		this.data = data;
		
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
	}
	
	
	@Override
	public void run() {
		
		try {
			
			String code = in.readLine();
			
			String odgovor = data.printByCode(code);
			
			out.println(odgovor);
			
			this.socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
