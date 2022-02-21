package vezba.kol2_pogadjanje_rmi_p06;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class IgraImpl extends UnicastRemoteObject implements Igra {


	private static final long serialVersionUID = 1L;
	
	
	private int broj;
	private int brPokusaja;
	

	protected IgraImpl() throws RemoteException {
		super();
	}
	
	
	protected IgraImpl(int broj) throws RemoteException {
		super();
		this.broj = broj;
		this.brPokusaja = 0;
	}
	

	@Override
	public Odgovor pogadjaj(int pokusaj) throws RemoteException {
		
		System.out.println("Igra " + this + " -> " + pokusaj + " (" + brPokusaja + " pokusaja)");
		
		brPokusaja++;
		
		if (pokusaj > broj)
			return Odgovor.VECI;
		else if (pokusaj < broj)
			return Odgovor.MANJI;
		else
			return Odgovor.POGODAK;
	}

	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
