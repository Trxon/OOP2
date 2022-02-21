package vezba.kol2_covid_g02_p03;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Sesija extends Remote {

	public List<String> posaljiZahtev(String upit) throws RemoteException;
}
