package vezba.kol2_vesanje_rmi_p02;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

public class IgraImpl extends UnicastRemoteObject implements Igra {
	
	
	private static final long serialVersionUID = 1L;
	
	
	private String rec;
	private Set<Character> karakteri;
	private int zivoti;

	
	public IgraImpl() throws RemoteException {
		super();
	}
	
	
	public IgraImpl(String rec, int zivoti) throws RemoteException {
		super();
		this.rec = rec;
		this.karakteri = new HashSet<Character>();
		this.zivoti = zivoti;
	}
	

	@Override
	public Poruka pogadjaj(char pokusaj) throws RemoteException {
		
		System.out.print("Igra " + this + " -> pokusaj : '" + pokusaj + "'");	// ISPIS : dodaje se primljen karakter...
		karakteri.add(pokusaj);													// karakter se dodaje u set isprobanih
		String recTrenutno = recTrenutno();										// azurira se trenutno stanje reci
		System.out.println(" (trenutno stanje : " + recTrenutno + " )");		// ISPIS : ...i dodaje se trenutna rec
		
		if (zivoti <= 0)														// provera da li je igrac bez zivota
			return new Poruka(Odgovor.KRAJ, recTrenutno);						// i ako da - kraj igre
		
		zivoti--;																// skidanje jednog zivota
		
		if (!recTrenutno.equalsIgnoreCase(rec))							// provera da li je rec pogodjena
			return new Poruka(Odgovor.PROMASAJ, recTrenutno);					// i ako nije - poruka o promasaju
		
		return new Poruka(Odgovor.POGODAK, recTrenutno);						// ako jeste - poruka o pogotku
	}


	private String recTrenutno() {
		
		String recTrenutno = this.rec;
		
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
