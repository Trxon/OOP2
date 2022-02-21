package vezba.kol2_covid_g01_p01;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;

public class SesijaImpl extends UnicastRemoteObject implements Sesija {


	private static final long serialVersionUID = 1L;
	
	
	private Podaci podaci;
	
	
	protected SesijaImpl() throws RemoteException {
		super();
	}


	public SesijaImpl(Podaci podaci) throws RemoteException {
		super();
		this.podaci = podaci;
	}


	@Override
	public Poruka posaljiZahtev(String unos) throws RemoteException {
		
		System.out.println("Sesija " + this + " -> opcija : " + unos);
		
		if (unos.equalsIgnoreCase("zemlje")) {
			
			return new Poruka(podaci.zemlje());
		} else if (unos.equalsIgnoreCase("datumi")) {
			
			return new Poruka(podaci.datumi());
		} else {
			
			String[] tokens = unos.split(",");
			
			Zemlja z = podaci.zemlje().stream()
					.filter(x -> x.getCountry().equalsIgnoreCase(tokens[0].trim()))
					.findFirst().orElse(null);
			
			if (z != null)
				return new Poruka(podaci.podaci(
						z, LocalDate.parse(tokens[1].trim(), Podatak.dateFormat)));
		}
		
		return null;
	}
	
	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
