package vezba.kol2_pogadjanje_rmi_p06;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {


	private static final long serialVersionUID = 1L;
	
	
	private static final int MAX_BROJ = 100;
	

	protected ServerImpl() throws RemoteException {
		super();
	}

	@Override
	public Igra novaIgra() throws RemoteException {
		
		int broj = (int) (1 + Math.random() * MAX_BROJ);
		Igra igra = new IgraImpl(broj);
		
		System.out.println("Igra " + igra + " pokrenuta...");
		
		return igra;
	}

}
