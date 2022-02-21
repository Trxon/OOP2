package vezba.kol2_chat_g02_rmi_p01;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SesijaImpl extends UnicastRemoteObject implements Sesija {


	private static final long serialVersionUID = 1L;

	
	protected SesijaImpl() throws RemoteException {
		super();
	}
	

	@Override
	public Caskanje zapocni() throws RemoteException {
		
		return new CaskanjeImpl();
	}

	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
