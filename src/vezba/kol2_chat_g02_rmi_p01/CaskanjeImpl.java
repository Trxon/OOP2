package vezba.kol2_chat_g02_rmi_p01;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;

public class CaskanjeImpl extends UnicastRemoteObject implements Caskanje {


	private static final long serialVersionUID = 1L;
	
	
	private LocalTime[] jutro = { LocalTime.of(4, 0), LocalTime.of(11, 59) };
	private LocalTime[] dan = { LocalTime.of(12, 0), LocalTime.of(17, 59) };
	
	
	private boolean dobarPocetak = false;
	private boolean gotovRazgovor = false;


	protected CaskanjeImpl() throws RemoteException {
		super();
	}
	
	
	private boolean dobarPocetak(String unos) {
		
		LocalTime time = LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute());
		
		if (unos.equalsIgnoreCase("Dobro jutro!"))
			if (time.isAfter(jutro[0]) && time.isBefore(jutro[1]))
				return true; 
		
		if (unos.equalsIgnoreCase("Dobar dan!"))
			if (time.isAfter(dan[0]) && time.isBefore(dan[1]))
				return true;
		
		if (unos.equalsIgnoreCase("Dobro vece!"))
			if (time.isAfter(dan[1]) || time.isBefore(jutro[0]))
				return true;
		
		return false;
	}
	

	@Override
	public String poruka(String unos) throws RemoteException, IllegalArgumentException {
		
		if (gotovRazgovor) throw new IllegalArgumentException();
		
		if (!dobarPocetak) {
			
			if (dobarPocetak(unos)) {
				dobarPocetak = true;
				return "SERVER : " + unos;
			}
			else
				throw new IllegalArgumentException();
			
		} else {
			
			if (unos.equalsIgnoreCase("Kako si?")) {
				
				return "SERVER : Hvala na pitanju, odlicno! Kako se ti osecas?";
			} else if (unos.equalsIgnoreCase("Osecam se dobro.")) {
				
				return "SERVER : Drago mi je da to cujem!";
			} else if (unos.equalsIgnoreCase("Osecam se lose.")) {
				
				return "SERVER : Zao mi je da to cujem...";
			} else if (unos.equalsIgnoreCase("Dovidjenja!")) {
				
				gotovRazgovor = true;
				return "SERVER : Hvala na komunikaciji!";
			} else {
				
				return "SERVER : Nisam razumeo poslednju poruku.";
			}
		}
	}
	
	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
