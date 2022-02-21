package vezba.kol2_covid_g01_p03;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SesijaImpl extends UnicastRemoteObject implements Sesija {


	private static final long serialVersionUID = 1L;
	
	
	private Podaci podaci;

	
	protected SesijaImpl() throws RemoteException {
		super();
	}
	
	
	protected SesijaImpl(Podaci podaci) throws RemoteException {
		this.podaci = podaci;
	}


	@Override
	public Poruka posaljiZahtev(String unos) throws RemoteException {
		
//		System.out.println("STIGLO : " + unos);
		
		char num = unos.charAt(0);
		
		if (num == '0') {
			return new Poruka(podaci.zemlje());
		} else if (num == '1') {
			return new Poruka(podaci.datumi());
		} else {
			
			String[] tokens = unos.split("±");
			Podatak p = podaci.podaci(podaci.zemljaByString(tokens[0]), LocalDate.parse(tokens[1], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			
			if (p == null)
				return null;
			else
				return new Poruka(p);
		}
	}

}
