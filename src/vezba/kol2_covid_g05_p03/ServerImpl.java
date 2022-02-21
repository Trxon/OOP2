package vezba.kol2_covid_g05_p03;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {


	private static final long serialVersionUID = 1L;

	
	protected ServerImpl() throws RemoteException {
		super();
	}

	
	@Override
	public Sesija novaSesija() throws RemoteException {
		
		Sesija sesija = new SesijaImpl(new Podaci());
		System.out.println("Sesija " + sesija + " pokrenuta...");
		
		return sesija;
	}

}
