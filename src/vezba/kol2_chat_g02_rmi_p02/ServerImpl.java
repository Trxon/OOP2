package vezba.kol2_chat_g02_rmi_p02;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {


	private static final long serialVersionUID = 1L;

	
	protected ServerImpl() throws RemoteException {
		super();
	}
	

	@Override
	public Caskanje zapocni() throws RemoteException {
		
		Caskanje caskanje = new CaskanjeImpl();
		System.out.println("Caskanje " + caskanje + " zapoceto.");
		
		return caskanje;
	}

}
