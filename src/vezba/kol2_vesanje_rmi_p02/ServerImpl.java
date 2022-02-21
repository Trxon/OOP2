package vezba.kol2_vesanje_rmi_p02;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {

	
	private static final long serialVersionUID = 1L;
	
	
	private static final String[] RECI = { "ananas", "banana", "cvekla", "dinja" };
	private static final int ZIVOTI = 15;
	
	
	public ServerImpl() throws RemoteException {
		super();
	}


	@Override
	public Igra novaIgra() throws RemoteException {
		
		String rec = RECI[(int) (Math.random() * RECI.length)];
		int zivoti = ZIVOTI;
		
		Igra igra = new IgraImpl(rec, zivoti);
		
		System.out.println("Zapoceta nova igra [" + igra + "]");
		
		return igra;
	}
}
