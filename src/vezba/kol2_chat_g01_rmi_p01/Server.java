package vezba.kol2_chat_g01_rmi_p01;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {

	public Caskanje zapocni() throws RemoteException;
}
