package domaci_zadaci.z06_p01;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
	
	public Igra novaIgra() throws RemoteException;
}
