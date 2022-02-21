package domaci_zadaci.z06_p01;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Igra extends Remote {

	public Poruka pogadjaj(char pokusaj) throws RemoteException;
}
