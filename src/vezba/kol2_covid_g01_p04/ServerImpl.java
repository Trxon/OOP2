package vezba.kol2_covid_g01_p04;

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
		System.out.println("Nova sesija " + sesija + "...");
		
		return sesija;
	}
}
