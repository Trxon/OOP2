package samostalno.kol2_chat_rmi_zn;

import java.io.PrintWriter;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
	static String loggedUsername="";
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		  PrintWriter out = new PrintWriter(System.out); 
		
		try {
			Prozor prozor=new Prozor("Aplikacija" , out::println);
			Registry reg=LocateRegistry.getRegistry(1089);
			RMIInterface remote= (RMIInterface) reg.lookup("server");
			boolean logged= loggedUser(remote, sc);
			while(logged) {
				prozor.setVisible(true);
				String line=sc.nextLine();
				if(line.equals("0")) {
					remote.logOut(loggedUsername);
					logged=false;
				}
				else {
					prozor.prikazi(loggedUsername, line);
					remote.send(loggedUsername, line);
				}
				
				
			}
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static boolean loggedUser(RMIInterface remote, Scanner sc) throws RemoteException {
		System.out.println("Wanna login?");
		String login=sc.nextLine();
		if(login.equals("1")) {
			System.out.println("Enter username");
			String username=sc.nextLine();
			System.out.println("Enter pass");
			String pass=sc.nextLine();
			boolean logged=remote.logIn(username, pass);
			if(logged) {
				System.out.println("Succefull");
				loggedUsername=username;
				remote.registerNewClient(username, new CallBackServer(username));
				return true;
			}
			else
				return false;
			
		}
		return false;
		
	}
}
