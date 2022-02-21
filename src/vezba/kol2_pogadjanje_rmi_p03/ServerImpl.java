package vezba.kol2_pogadjanje_rmi_p03;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {


	private static final long serialVersionUID = 1L;
	
	
	private static final int MAX_BROJ = 100;
	private static final int ZIVOTI = 10;
	
	
	protected ServerImpl() throws RemoteException {
		super();
	}


	@Override
	public Igra novaIgra() throws RemoteException {
		
		int broj = (int) (1 + Math.random() * 100);
		int zivoti = ZIVOTI;
		
		Igra igra = new IgraImpl(broj, zivoti);
		
		System.out.println("Pokrenuta nova igra : " + igra);
		
		return igra;
	}
}
