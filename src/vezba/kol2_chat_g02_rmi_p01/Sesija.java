package vezba.kol2_chat_g02_rmi_p01;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Sesija extends Remote {

	public Caskanje zapocni() throws RemoteException;
}
