package vezba.kol2_covid_g01_p01;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {


	private static final long serialVersionUID = 1L;
	
	
	protected ServerImpl() throws RemoteException {
		super();
	}


	@Override
	public Sesija novaSesija() throws RemoteException {
		
		Podaci podaci = new Podaci();
		Sesija sesija = new SesijaImpl(podaci);
		
		System.out.println("Zapoceta nova sesija : " + sesija);
		
		return sesija;
	}
}
