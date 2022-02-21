package teorijske_vezbe.tv06_program_p01;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {

	// (1) kreiranje interfejsa za server
	
	public Igra novaIgra() throws RemoteException;
}
