package domaci_zadaci.z06_p01;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Poruka extends Remote {

	public String recTrenutno() throws RemoteException;
	public Odgovor odgovor() throws RemoteException;
}
