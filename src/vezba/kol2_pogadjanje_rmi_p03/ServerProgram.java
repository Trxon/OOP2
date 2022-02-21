package vezba.kol2_pogadjanje_rmi_p03;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerProgram {

	public static void main(String[] args) throws RemoteException {
		
		Registry registry = LocateRegistry.createRegistry(1099);
		
		Server server = new ServerImpl();
		registry.rebind("Igra", server);
		
		System.out.println("Server je pokrenut...");
	}
}
