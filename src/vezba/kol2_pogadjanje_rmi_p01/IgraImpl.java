package vezba.kol2_pogadjanje_rmi_p01;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class IgraImpl extends UnicastRemoteObject implements Igra {

	
	private static final long serialVersionUID = 1L;
	
	
	private int zivoti;
	private int broj;

	
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
		
		System.out.println("Igra " + this + ": pokusaj " + pokusaj);
		
		if (zivoti <= 0)		return Odgovor.KRAJ;
		
		zivoti--;
		
		if (broj > pokusaj)		return Odgovor.VECE;
		if (broj < pokusaj)		return Odgovor.MANJE;
		
		return Odgovor.POGODAK;
	}
	
	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
