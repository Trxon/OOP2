package vezba.kol2_vesanje_rmi_p04;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

public class IgraImpl extends UnicastRemoteObject implements Igra {


	private static final long serialVersionUID = 1L;
	
	
	private String rec;
	private Set<Character> karakteri;
	private int zivoti;

	
	protected IgraImpl() throws RemoteException {
		super();
	}
	
	
	protected IgraImpl(String rec, int zivoti) throws RemoteException {
		super();
		this.rec = rec;
		this.karakteri = new HashSet<Character>();
		this.zivoti = zivoti;
	}

	
	@Override
	public Poruka pogadjaj(String pokusaj) throws RemoteException {
		
		System.out.print("Igra " + this + " -> pokusaj : '" + pokusaj + "'");
		karakteri.add(pokusaj.charAt(0));
		String recTrenutno = recTrenutno();
		System.out.println(" (trenutno : " + recTrenutno + ")");
		
		if (zivoti <= 0)
			return new Poruka(Odgovor.KRAJ, recTrenutno);
		
		zivoti--;
		
		if (!recTrenutno.equals(rec))
			return new Poruka(Odgovor.PROMASAJ, recTrenutno);
		
		return new Poruka(Odgovor.POGODAK, recTrenutno);
	}


	private String recTrenutno() {
		
		String recTrenutno = rec;
		
		for (char c = 'a'; c <= 'z'; c++)
			if (!karakteri.contains(c))
				recTrenutno = recTrenutno.replaceAll(c + "", "-");
		
		return recTrenutno;
	}
	
	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
