package vezba.kol2_covid_g02_p03;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SesijaImpl extends UnicastRemoteObject implements Sesija {


	private static final long serialVersionUID = 1L;
	
	
	private Podaci podaci;

	
	protected SesijaImpl() throws RemoteException {
		super();
	}
	
	
	protected SesijaImpl(Podaci podaci) throws RemoteException {
		super();
		this.podaci = podaci;
	}

	
	@Override
	public List<String> posaljiZahtev(String upit) throws RemoteException {
		
		System.out.println("Sesija " + this + " -> " + upit);
		
		if (upit.equalsIgnoreCase("kontinenti"))
			return podaci.getMapa().keySet().stream().sorted().collect(Collectors.toList());
		
		String[] kontinenti = { "Africa", "America", "Asia", "Europe", "Oceania" };
		
		for (String s : kontinenti)
			if (s.equalsIgnoreCase(upit))
				return podaci.getMapa().get(upit).stream().map(Zemlja::showData).collect(Collectors.toList());
		
		String s = "Pogresan unos ili nema podataka.";
		List<String> l = new ArrayList<String>();
		l.add(s);
		return l;
	}
	
	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
