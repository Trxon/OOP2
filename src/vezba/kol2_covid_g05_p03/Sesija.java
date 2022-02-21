package vezba.kol2_covid_g05_p03;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Sesija extends Remote {

	public String posaljiZahtev(String unos) throws RemoteException;
}
