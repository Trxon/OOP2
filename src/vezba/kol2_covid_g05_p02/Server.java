package vezba.kol2_covid_g05_p02;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {

	public Sesija novaSesija() throws RemoteException;
}
