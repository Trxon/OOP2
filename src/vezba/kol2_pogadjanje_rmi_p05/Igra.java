package vezba.kol2_pogadjanje_rmi_p05;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Igra extends Remote {

	public Odgovor pogadjaj(int pokusaj) throws RemoteException;
}
