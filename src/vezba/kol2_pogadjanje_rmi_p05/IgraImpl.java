package vezba.kol2_pogadjanje_rmi_p05;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class IgraImpl extends UnicastRemoteObject implements Igra {


	private static final long serialVersionUID = 1L;
	
	
	private static int broj;
	private static int zivoti;

	
	protected IgraImpl() throws RemoteException {
		super();
	}

	
	protected IgraImpl(int broj, int zivoti) throws RemoteException {
		super();
		this.broj = zivoti;
		this.zivoti = zivoti;
	}

	@Override
	public Odgovor pogadjaj(int pokusaj) throws RemoteException {
		
		System.out.println("Igra " + this + " -> " + pokusaj);
		
		if (pokusaj <= 0)
			return Odgovor.KRAJ;
		
		this.zivoti--;
		
		if (pokusaj > broj)
			return Odgovor.MANJI;
		
		if (pokusaj < broj)
			return Odgovor.VECI;
		
		return Odgovor.POGODAK;
	}
	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
