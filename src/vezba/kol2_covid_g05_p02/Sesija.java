package vezba.kol2_covid_g05_p02;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Sesija extends Remote {

	public Poruka posaljiZahtev(String unos) throws RemoteException;
}
