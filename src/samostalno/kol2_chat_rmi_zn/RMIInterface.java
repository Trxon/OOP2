package samostalno.kol2_chat_rmi_zn;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {
	public boolean logIn(String username, String password) throws RemoteException;

	public boolean logOut(String username) throws RemoteException;

	public void send(String useraname, String message) throws RemoteException;

	public void registerNewClient(String user, Callback callback) throws RemoteException;

}
