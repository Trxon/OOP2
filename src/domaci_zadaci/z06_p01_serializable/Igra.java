package domaci_zadaci.z06_p01_serializable;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Igra extends Remote {

	public PorukaImpl pogadjaj(char pokusaj) throws RemoteException;
}
