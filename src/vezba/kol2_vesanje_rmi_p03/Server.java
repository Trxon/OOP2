package vezba.kol2_vesanje_rmi_p03;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {

	public Igra novaIgra() throws RemoteException;
}
