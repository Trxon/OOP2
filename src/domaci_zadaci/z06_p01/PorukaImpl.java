package domaci_zadaci.z06_p01;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PorukaImpl extends UnicastRemoteObject implements Poruka {
	
	Odgovor odg;
	String recTrenutno;
	
	public PorukaImpl(Odgovor odgovor, String recTrenutno) throws RemoteException {
		this.odg = odgovor;
		this.recTrenutno = recTrenutno;
	}

	@Override
	public String recTrenutno() {
		return recTrenutno;
	}

	@Override
	public Odgovor odgovor() {
		return odg;
	}
}
