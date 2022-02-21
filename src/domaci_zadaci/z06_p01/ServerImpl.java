package domaci_zadaci.z06_p01;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
