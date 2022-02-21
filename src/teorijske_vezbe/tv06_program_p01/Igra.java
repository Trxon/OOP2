package teorijske_vezbe.tv06_program_p01;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Igra extends Remote {
	
	// (2) kreiranje interfejsa za igru

	public Odgovor pogadjaj(int pokusaj) throws RemoteException;
}
