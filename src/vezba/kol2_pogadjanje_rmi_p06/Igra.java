package vezba.kol2_pogadjanje_rmi_p06;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Igra extends Remote {

	public Odgovor pogadjaj(int pokusaj) throws RemoteException;
}
