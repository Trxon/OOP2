package vezba.kol2_vesanje_rmi_p01;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Igra extends Remote {

	public Poruka pogadjaj(char pokusaj) throws RemoteException;
}
