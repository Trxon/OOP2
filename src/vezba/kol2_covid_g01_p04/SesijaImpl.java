package vezba.kol2_covid_g01_p04;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class SesijaImpl extends UnicastRemoteObject implements Sesija {


	private static final long serialVersionUID = 1L;

	
	private Podaci podaci;
	
	private Zemlja selectedCountry;
	private LocalDate selectedDate;
	
	
	protected SesijaImpl() throws RemoteException {
		super();
	}
	
	
	protected SesijaImpl(Podaci podaci) throws RemoteException {
		super();
		this.podaci = podaci;
	}
	

	@Override
	public String posaljiZahtev(String unos) throws RemoteException {
		
		if (unos.charAt(0) == '1') {
			
			List<Zemlja> zemlje = podaci.zemlje();
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("Zemlje za koje su dostupni podaci : \n");
			
			for (Zemlja zemlja : zemlje) {
				sb.append(zemlja);
				sb.append("\n");
			}
			
			return sb.toString();
			
		} else if (unos.charAt(0) == '2') {
			
			String inp = unos.substring(1);
			
			for (Zemlja z : podaci.zemlje())
				if (z.getCountry().equalsIgnoreCase(inp) || z.getGeoId().equalsIgnoreCase(inp) || z.getCode().equalsIgnoreCase(inp)) {
					this.selectedCountry = z;
					break;
				}
			
			if (this.selectedCountry == null) 
				return "!";
			
			List<LocalDate> datumi = podaci.datumi();
			
			StringBuilder sb = new StringBuilder();
			
			sb.append(this.selectedCountry.getCountry() + " SELECTED!\n\nDatumi : \n");
			
			for (LocalDate date : datumi) {
				sb.append(date);
				sb.append("\n");
			}
			
			return sb.toString();
			
		} else if (unos.charAt(0) == '3') {

			LocalDate inp;
			
			try {
				inp = LocalDate.parse(unos.substring(1));
			} catch (DateTimeParseException e) {
				return "!";
			}
			
			for (LocalDate date : podaci.datumi())
				if (date.equals(inp)) {
					this.selectedDate = date;
					break;
				}
			
			if (this.selectedDate == null) 
				return "!";
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("Podaci za " + this.selectedCountry.getCountry() + " na dan " + this.selectedDate + " : \n");
			sb.append(podaci.podaci(selectedCountry, selectedDate));
			
			return sb.toString();
			
		} else {
			
			return "!";
		}
	}
	
	
	@Override
	public String toString() {
		return String.format("#%08X", hashCode());
	}
}
