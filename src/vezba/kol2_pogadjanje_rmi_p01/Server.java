package vezba.kol2_pogadjanje_rmi_p01;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {

	public Igra novaIgra() throws RemoteException;
}
