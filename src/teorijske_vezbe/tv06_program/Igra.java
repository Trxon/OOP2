package teorijske_vezbe.tv06_program;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Igra extends Remote {

	public Odgovor pogadjaj(int pokusaj) throws RemoteException;
}
