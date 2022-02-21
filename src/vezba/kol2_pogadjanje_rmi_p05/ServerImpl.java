package vezba.kol2_pogadjanje_rmi_p05;

import java.rmi.RemoteException;

public class ServerImpl extends java.rmi.server.UnicastRemoteObject implements Server {

	
	private static final long serialVersionUID = 1L;
	
	
	public static final int MAX_BROJ = 100;
	public static final int ZIVOTI = 15;
	
	
	protected ServerImpl() throws RemoteException {
		super();
	}
	

	@Override
	public Igra novaIgra() throws RemoteException {
		
		int broj = (int) (1 + (Math.random() * MAX_BROJ));
		int zivoti = ZIVOTI;
		
		Igra igra = new IgraImpl(broj, zivoti);
		
		System.out.println("Nova igra " + igra + " pokrenuta.");
		
		return igra;
	}
}
