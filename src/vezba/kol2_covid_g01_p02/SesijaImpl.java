package vezba.kol2_covid_g01_p02;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.List;

public class SesijaImpl extends UnicastRemoteObject implements Sesija {


	private static final long serialVersionUID = 1L;
	
	
	private Podaci podaci;

	
	protected SesijaImpl() throws RemoteException {
		super();
	}

	
	protected SesijaImpl(Podaci podaci) throws RemoteException {
		super();
		this.podaci = podaci;
	}


	@Override
	public Poruka posaljiZahtev(String unos) throws RemoteException {
		
		System.out.println("Sesija " + this + " -> " + unos);
		
		if (unos.equalsIgnoreCase("zemlje")) {
			
			List<Zemlja> zemlje = podaci.zemlje();
			return new Poruka(zemlje);
		} else if (unos.equalsIgnoreCase("podaci")) {
			
			List<LocalDate> datumi = podaci.datumi();
			return new Poruka(datumi);
		} else {
			
			String[] tokens = unos.split(",");
			
//			System.out.println("-" + tokens[0] + "-");
//			System.out.println("-" + tokens[1] + "-");
			
			Podatak p = podaci.podaci(tokens[0].trim(), tokens[1].trim());
			return new Poruka(p);
		}
	}
	
	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
