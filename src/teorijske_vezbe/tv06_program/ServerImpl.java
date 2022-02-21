package teorijske_vezbe.tv06_program;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {

	public static final int ZIVOTI = 5;
	public static final int MAX_BR = 100;

	protected ServerImpl() throws RemoteException {
		super();
	}

	@Override
	public Igra novaIgra() throws RemoteException {
		int broj = 1 + (int) (MAX_BR * Math.random());
		int zivoti = ZIVOTI;
		Igra igra = new IgraImpl(broj, zivoti);
		System.out.println("Zapoceta nova igra: " + igra);
		return igra;
	}
}
