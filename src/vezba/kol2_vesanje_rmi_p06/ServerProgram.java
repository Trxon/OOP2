package vezba.kol2_vesanje_rmi_p06;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerProgram {

	public static void main(String[] args) throws RemoteException {
		
		Registry registry = LocateRegistry.createRegistry(1234);
		
		Server server = new ServerImpl();
		registry.rebind("Igra", server);
		
		System.out.println("Server RADI...");
	}
}
