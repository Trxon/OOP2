package vezba.kol2_chat_g02_rmi_p02;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Caskanje extends Remote {

	public String poruka(String unos) throws RemoteException;
}
