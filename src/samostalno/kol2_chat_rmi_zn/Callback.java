package samostalno.kol2_chat_rmi_zn;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Callback extends Remote {
	public void reciveMessage(String username, String message) throws RemoteException;
}
