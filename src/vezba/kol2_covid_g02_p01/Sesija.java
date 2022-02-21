package vezba.kol2_covid_g02_p01;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Sesija extends Remote {

	public Poruka posaljiZahtev(String unos) throws RemoteException;
}
