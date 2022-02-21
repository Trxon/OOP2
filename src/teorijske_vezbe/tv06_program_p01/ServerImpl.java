package teorijske_vezbe.tv06_program_p01;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {

	// (4) implementacija serverskog programa
	
	public static final int ZIVOTI = 5;
	public static final int MAX_BR = 100;
	
	
	protected ServerImpl() throws RemoteException {
		super();
	}

	
	@Override
	public Igra novaIgra() throws RemoteException {
		
		int broj = 1 + (int) (Math.random() * MAX_BR);
		int zivoti = ZIVOTI;
		
		Igra igra = new IgraImpl(broj, zivoti);
		
		System.out.println("Zapoceta nova igra : " + igra);
		
		return igra;
	}
}
