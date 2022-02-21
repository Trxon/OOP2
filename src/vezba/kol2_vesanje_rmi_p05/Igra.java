package vezba.kol2_vesanje_rmi_p05;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Igra extends Remote {

	public Poruka pogadjaj(String pokusaj) throws RemoteException;
}
