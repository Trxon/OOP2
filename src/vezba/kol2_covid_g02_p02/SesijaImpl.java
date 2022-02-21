package vezba.kol2_covid_g02_p02;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
	public Poruka posaljiZahtev(String unos) throws RemoteException {
		
		System.out.println("Sesija " + this + " -> " + unos);
		
		if (unos.trim().equalsIgnoreCase("kontinenti"))
			return new Poruka(podaci.kontinenti());
		else
			return new Poruka(podaci.zemlje(unos.trim()));
	}
	
	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
