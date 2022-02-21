package domaci_zadaci.z06_p01_serializable;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

public class IgraImpl extends UnicastRemoteObject implements Igra {

	
	private final String rec;
	private Set<Character> karakteri;
	private int zivoti;
	

	public IgraImpl(String rec, int zivoti) throws RemoteException {
		this.rec = rec;
		this.karakteri = new HashSet<Character>();
		this.zivoti = zivoti;
	}


	@Override
	public PorukaImpl pogadjaj(char pokusaj) throws RemoteException {
		
		System.out.print("Igra " + this + " -> pokusaj : '" + pokusaj + "'");
		karakteri.add(pokusaj);
		String trenutno = recTrenutno();
		System.out.println(" (trenutno stanje : " + trenutno + " )");
		
		if (zivoti <= 0)
			return new PorukaImpl(Odgovor.KRAJ, trenutno);
		
		zivoti--;
		
		if (!trenutno.equalsIgnoreCase(rec))
			return new PorukaImpl(Odgovor.PROMASAJ, trenutno);
		
		return new PorukaImpl(Odgovor.POGODAK, trenutno);
	}
	
	
	public String recTrenutno() {
		
		String trenutno = rec;
		
		for (char c = 'a'; c <= 'z'; c++)
			if (!karakteri.contains(c))
				trenutno = trenutno.replaceAll("" + c, "-");
		
		return trenutno;
	}
	
	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
