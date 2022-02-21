package vezba.kol2_chat_g01_rmi_p01;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {


	private static final long serialVersionUID = 1L;

	
	protected ServerImpl() throws RemoteException {
		super();
	}
	

	@Override
	public Caskanje zapocni() throws RemoteException {
		
		try {
			
			Caskanje caskanje = new CaskanjeImpl();
			System.out.println("Novo caskanje " + caskanje + "...");
			
			return caskanje;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
