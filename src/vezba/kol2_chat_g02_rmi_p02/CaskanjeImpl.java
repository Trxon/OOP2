package vezba.kol2_chat_g02_rmi_p02;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;

public class CaskanjeImpl extends UnicastRemoteObject implements Caskanje {

	
	private static final long serialVersionUID = 1L;
	
	
	private int phase = 0;

	
	protected CaskanjeImpl() throws RemoteException {
		super();
	}
	

	@Override
	public String poruka(String unos) throws RemoteException {
		
		System.out.println("Unos : " + unos);
		
		if (phase == 0) {
			
			int hour = LocalTime.now().getHour();
			
			if (unos.equalsIgnoreCase("Dobro jutro!") && 4 <= hour && hour < 12) {
				phase++;
				return "SERVER : " + unos;
			} else if (unos.equalsIgnoreCase("Dobar dan!") && 12 <= hour && hour < 18) {
				phase++;
				return "SERVER : " + unos;
			} else if (unos.equalsIgnoreCase("Dobro vece!") && (18 <= hour || hour < 4)) {
				phase++;
				return "SERVER : " + unos;
			} else {
				throw new IllegalArgumentException();
			}
			
		} else if (phase == 1) {
			
			if (unos.equalsIgnoreCase("Kako si?")) {
				return "SERVER : Dobro sam.";
			} else if (unos.equals("Osecam se dobro.")) {
				return "SERVER : Drago mi je.";
			} else if (unos.equals("Osecam se lose.")) {
				return "SERVER : Zao mi je.";
			} else if (unos.equals("Dovidjenja!")) {
				phase++;
				return "SERVER : Vidimo se!";
			} else {
				return "SERVER : Ne razumem.";
			}
			
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
