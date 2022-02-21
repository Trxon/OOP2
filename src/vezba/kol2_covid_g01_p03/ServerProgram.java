package vezba.kol2_covid_g01_p03;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerProgram {

	public static void main(String[] args) throws RemoteException {
		
		Registry registry = LocateRegistry.createRegistry(1919);
		
		Server server = new ServerImpl();
		registry.rebind("Sesija", server);
		
		System.out.println("Server RADI...");
	}
}
