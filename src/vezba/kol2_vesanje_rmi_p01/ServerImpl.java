package vezba.kol2_vesanje_rmi_p01;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {

	
	public static final String[] RECI = { "ananas", "banana", "cvekla", "dinja" };
	public static final int ZIVOTI = 15;
	
	
	protected ServerImpl() throws RemoteException {
		super();
	}


	@Override
	public Igra novaIgra() throws RemoteException {
		
		String rec = RECI[(int) (Math.random() * RECI.length)];
		int zivoti = ZIVOTI;
		
		Igra igra = new IgraImpl(rec, zivoti);
		
		System.out.println("Zapoceta nova igra : " + igra);
		
		return igra;
	}
}
