package vezba.kol2_covid_g05_p03;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
	public String posaljiZahtev(String unos) throws RemoteException {
		
		System.out.println("Sesija " + this + " ->  " + unos);
		
		if (isContinent(unos)) {
			
			String tabela = podaci.tabelarniPrikaz();
			String novozarazeni = podaci.novozarazeneNaDanAsString();
			
			String najvise = podaci.najviseUmrlihNaKontinentuAsString(unos);
			najvise = najvise.equals("!") ? "Nema podataka ili pogresan unos." : najvise;
			
			StringBuilder sb = new StringBuilder();
			sb.append(tabela);
			sb.append("\n");
			sb.append("\n");
			
			sb.append(novozarazeni);
			sb.append("\n");
			sb.append("\n");
			
			sb.append(najvise);
			sb.append("\n");
			sb.append("\n");
			
			return sb.toString();
		}
		
		return "Nema podataka ili pogresan unos.";
	}
	
	
	public boolean isContinent(String s) {
		
		String[] kontinenti = { "America", "Asia", "Africa", "Europe", "Oceania" };
		
		for (String k : kontinenti)
			if (k.equalsIgnoreCase(s))
				return true;
		
		return false;
	}

	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
