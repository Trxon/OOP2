package vezba.kol2_covid_g01_p04;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {

	public Sesija novaSesija() throws RemoteException;
}
