package samostalno.kol2_chat_rmi_zn;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CallBackServer extends UnicastRemoteObject implements Callback {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;

	protected CallBackServer(String username) throws RemoteException {
		this.username=username;
	}

	@Override
	public void reciveMessage(String username, String message) throws RemoteException {
		System.out.println("User "+username+" says: "+message);
		
	}

}
