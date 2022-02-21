package vezba.kol2_covid_g02_p04;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SesijaImpl extends UnicastRemoteObject implements Sesija {


	private static final long serialVersionUID = 1L;
	
	
	private Podaci podaci;
	private String selectedContinent;
	

	protected SesijaImpl() throws RemoteException {
		super();
	}
	
	
	protected SesijaImpl(Podaci podaci) throws RemoteException {
		super();
		this.podaci = podaci;
	}
	

	@Override
	public String posaljiZahtev(String unos) throws RemoteException {
		
		if (unos.charAt(0) == '1') {
			
			return podaci.kontinenti().stream().map(s -> "  " + s).reduce("", (s1, s2) -> "".equals(s1) ? s2 : s1 + "\n" + s2);
		} else if (unos.charAt(0) == '2') {
			
			String input = unos.substring(1);
			
			if (isContinent(input)) {
				this.selectedContinent = input;
				return "CONTINENT SELECTED!";
			} else {
				return "!";
			}
		} else if (unos.charAt(0) == '3') {
			
			return podaci.zemlje(selectedContinent).stream().map(s -> "  " + s).reduce("", (s1, s2) -> "".equals(s1) ? s2 : s1 + "\n" + s2);
		}
		
		return null;
	}
	
	
	private boolean isContinent(String continent) {
		
		String[] CONT = { "Africa", "America", "Asia", "Europe", "Oceania" };
		
		for (String s : CONT)
			if (s.equalsIgnoreCase(continent))
				return true;
		
		return false;
	}
	
	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
