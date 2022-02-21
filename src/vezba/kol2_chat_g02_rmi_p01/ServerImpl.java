package vezba.kol2_chat_g02_rmi_p01;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {


	private static final long serialVersionUID = 1L;

	
	protected ServerImpl() throws RemoteException {
		super();
	}

	
	@Override
	public Sesija novaSesija() throws RemoteException {
		
		Sesija sesija = new SesijaImpl();
		System.out.println("Sesija " + sesija + " zapoceta.");
		
		return sesija;
	}
}
