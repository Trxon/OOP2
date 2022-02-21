package teorijske_vezbe.tv06_program;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {

	public Igra novaIgra() throws RemoteException;

}
