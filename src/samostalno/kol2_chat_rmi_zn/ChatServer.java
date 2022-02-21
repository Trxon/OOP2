package samostalno.kol2_chat_rmi_zn;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ChatServer extends UnicastRemoteObject implements RMIInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ChatServer() throws RemoteException {
		initializeUsers();
	}

	private HashMap<String, String> allUsers = new HashMap<>();
	private HashMap<String, String> loggedUsers = new HashMap<>();
	private HashMap<String, Callback> userCallBack = new HashMap<>();

	private void initializeUsers() {
		allUsers.put("zarko", "zarko");
		allUsers.put("aca", "aca");
	}

	@Override
	public boolean logIn(String username, String password) throws RemoteException {
		if (allUsers.containsKey(username) && allUsers.get(username).equals(password)) {
			System.out.println("Users " + username + " logged!");
			if (!loggedUsers.containsKey(username))
				loggedUsers.put(username, password);
			return true;
		}
		System.out.println("user " + username + "could not logged ");
		return false;
	}

	@Override
	public boolean logOut(String username) throws RemoteException {
		if (loggedUsers.containsKey(username)) {
			loggedUsers.remove(username);
			return true;
		}
		return false;
	}

	@Override
	public void send(String useraname, String message) throws RemoteException {
		for (Map.Entry<String, Callback> e : userCallBack.entrySet()) {
			if (!e.getKey().equals(useraname)) {
				e.getValue().reciveMessage(useraname, message);
			}
		}

	}

	@Override
	public void registerNewClient(String user, Callback callback) throws RemoteException {
		if (!loggedUsers.containsKey(user) && !userCallBack.containsKey(callback))
			userCallBack.put(user, callback);

	}
	public static void main(String[] args) {
		try {
			Registry reg=LocateRegistry.createRegistry(1089);
			reg.rebind("server", new ChatServer());
			System.out.println("Server binded succesfuly...");
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
