package vezba.kol2_chat_g01_rmi_p01;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerProgram {

	public static void main(String[] args) throws RemoteException {
		
		Registry registry = LocateRegistry.createRegistry(1234);
		
		Server server = new ServerImpl();
		registry.rebind("Caskanje", server);
		
		System.out.println("Server RADI...");
	}
}
