package vezba.kol2_vesanje_rmi_p01;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

public class IgraImpl extends UnicastRemoteObject implements Igra {

	
	private int zivoti;					// generise server
	private final String rec; 			// generise server
	private Set<Character> karakteri;
	
	
	public IgraImpl(String rec, int zivoti) throws RemoteException {
		this.zivoti = zivoti;
		this.rec = rec;
		this.karakteri = new HashSet<Character>();
	}


	@Override
	public Poruka pogadjaj(char pokusaj) throws RemoteException {
		
		System.out.print("Igra " + this + " -> pokusaj : '" + pokusaj + "'");
		karakteri.add(pokusaj);
		String recTrenutno = recTrenutno();
		System.out.println(" (trenutno stanje : " + recTrenutno + " )");
		
		if (zivoti <= 0)
			return new Poruka(Odgovor.KRAJ, recTrenutno);
		
		zivoti--;
		
		if (!recTrenutno.equalsIgnoreCase(rec))
			return new Poruka(Odgovor.PROMASAJ, recTrenutno);
		
		return new Poruka(Odgovor.POGODAK, recTrenutno);
	}


	private String recTrenutno() {
		
		String recTrenutno = rec;
		
		for (char c = 'a'; c <= 'z'; c++)
			if (!karakteri.contains(c))
				recTrenutno = recTrenutno.replaceAll("" + c, "-");
		
		return recTrenutno;
	}
	
	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
