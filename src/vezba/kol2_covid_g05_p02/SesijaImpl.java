package vezba.kol2_covid_g05_p02;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SesijaImpl extends UnicastRemoteObject implements Sesija {
	

	private static final long serialVersionUID = 1L;
	private static final String[] CONTINENTS = { "Asia", "Europe", "America", "Africa", "Oceania" };
	
	private Podaci podaci;

	
	protected SesijaImpl() throws RemoteException {
		super();
	}
	
	
	protected SesijaImpl(Podaci podaci) throws RemoteException {
		super();
		this.podaci = podaci;
	}

	
	@Override
	public Poruka posaljiZahtev(String unos) throws RemoteException {
		
		System.out.println("Sesija " + this + " -> " + unos);
		
		if (jeKontinent(unos))
			return new Poruka(podaci.najviseUmrlihNaKontinentu(unos));
		else if (unos.equalsIgnoreCase("na dan"))
			return new Poruka(podaci.novozarazeneNaDan());
		else if (unos.equals("tabela"))
			return new Poruka(podaci.tabelarniPrikaz());
		else
			return new Poruka(new String("Pogresan unos."));
	}
	
	
	private boolean jeKontinent(String s) {
		
		for (String c : CONTINENTS)
			if (c.equalsIgnoreCase(s))
				return true;
		
		return false;
	}

	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
