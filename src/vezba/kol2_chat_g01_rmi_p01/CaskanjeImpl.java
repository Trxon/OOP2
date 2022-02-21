package vezba.kol2_chat_g01_rmi_p01;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;

public class CaskanjeImpl extends UnicastRemoteObject implements Caskanje {


	private static final long serialVersionUID = 1L;

	
	private int phase;
	private PrintWriter pw;
	
	
	protected CaskanjeImpl() throws IOException {
		super();
		this.phase = 0;
		this.pw = new PrintWriter(new BufferedWriter(new FileWriter("res//log.txt")));
	}
	

	@Override
	public String poruka(String unos) throws RemoteException {
		
		if (phase == 0) {
			
			int h = LocalTime.now().getHour();
			
			if (unos.equalsIgnoreCase("Dobro jutro!") && (4 <= h && h < 12)) {
				
				phase++;
				String output = "SERVER : " + unos;
				
				pw.println("KLIJENT : " + unos);
				pw.println(output);
				
				return output;
				
			} else if (unos.equalsIgnoreCase("Dobar dan!") && (12 <= h && h < 18)) {
				
				phase++;
				String output = "SERVER : " + unos;
				
				pw.println("KLIJENT : " + unos);
				pw.println(output);
				
				return output;
				
			} else if (unos.equalsIgnoreCase("Dobro vece!") && (18 <= h || h < 4)) {
				
				phase++;
				String output = "SERVER : " + unos;
				
				pw.println("KLIJENT : " + unos);
				pw.println(output);
				
				return output;
				
			} else {
				throw new IllegalArgumentException();
			}
		} else if (phase == 1) {
			
			if (unos.equalsIgnoreCase("Kako si?")) {
				
				String output = "SERVER : Dobro sam.";
				
				pw.println("KLIJENT : " + unos);
				pw.println(output);
				
				return output;
			} else if (unos.equalsIgnoreCase("Osecam se dobro.")) {
				
				String output = "SERVER : Blago tebi.";
				
				pw.println("KLIJENT : " + unos);
				pw.println(output);
				
				return output;
			} else if (unos.equalsIgnoreCase("Osecam se lose.")) {
				
				String output = "SERVER : I ja.";
				
				pw.println("KLIJENT : " + unos);
				pw.println(output);
				
				return output;
			} else if (unos.equalsIgnoreCase("Dovidjenja!")) {
				
				phase++;
				String output = "SERVER : Vidimo se!";
				
				pw.println("KLIJENT : " + unos);
				pw.println(output);
				
				return output;
			} else {
				
				String output = "SERVER : Ne razumem...";
				
				pw.println("KLIJENT : " + unos);
				pw.println(output);
				
				return output;
			}
			
		} else {
			
			pw.close();
			
			throw new IllegalArgumentException();
		}
	}
	
	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
