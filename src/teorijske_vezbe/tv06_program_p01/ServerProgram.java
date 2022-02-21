package teorijske_vezbe.tv06_program_p01;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerProgram {

	// (6) 
	
	public static void main(String[] args) throws RemoteException {
		
		Registry registry = LocateRegistry.createRegistry(1099);
		
		Server server = new ServerImpl();
		registry.rebind("Igra", server);
		
		System.err.println("Server je pokrenut");
	}
}
