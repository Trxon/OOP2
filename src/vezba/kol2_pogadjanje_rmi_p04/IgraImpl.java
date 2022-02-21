package vezba.kol2_pogadjanje_rmi_p04;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class IgraImpl extends UnicastRemoteObject implements Igra {

	
	private static final long serialVersionUID = 1L;
	
	
	private int broj;
	private int zivoti;

	
	protected IgraImpl() throws RemoteException {
		super();
	}
	
	
	protected IgraImpl(int broj, int zivoti) throws RemoteException {
		super();
		this.broj = broj;
		this.zivoti = zivoti;
	}
	

	@Override
	public Odgovor pogadjaj(int pokusaj) throws RemoteException {
		
		System.out.println("Igra " + this + " -> " + pokusaj);
		
		if (zivoti <= 0)
			return Odgovor.KRAJ;
		
		zivoti--;
		
		if (pokusaj < broj)
			return Odgovor.VECI;
		
		if (pokusaj > broj)
			return Odgovor.MANJI;
		
		return Odgovor.POGODAK;
	}
	
	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
