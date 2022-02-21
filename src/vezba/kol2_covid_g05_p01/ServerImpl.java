package vezba.kol2_covid_g05_p01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServerImpl extends UnicastRemoteObject implements Server {


	private static final long serialVersionUID = 1L;
	
	
	private List<Zemlja> zemlje;
	private List<Podatak> podaci;
	
	
	protected ServerImpl() throws RemoteException {
		super();
	}
	

	public static Stream<String> linije() throws IOException {
		return Files.lines(Paths.get(
				"/Users/nikolavetnic/Library/Mobile Documents/com~apple~CloudDocs/Documents/EclipseWorkspace/OOP2/src/vezba/kol2_covid_g05_p00/sars-cov-2.csv"));
	}


	@Override
	public Sesija novaSesija() throws RemoteException {
		
		try {
			obradiTok();
		} catch (IOException e) {
			System.err.println("Greska prilikom obrade -> " + e.getMessage());
		}
		
		Sesija sesija = new SesijaImpl(zemlje, podaci);
		System.out.println("Nova sesija pokrenuta : " + sesija);
		
		return sesija;
	}


	private void obradiTok() throws IOException {
		
		this.zemlje = linije()
				.filter(s -> !s.equals(
						"dateRep,day,month,year,cases,deaths,countriesAndTerritories,geoId,countryterritoryCode,popData2018,continentExp"))
				.map(s -> {
					
					String[] tokens = s.split(",");
					return new Zemlja(tokens[6], tokens[7], tokens[8], tokens[9], tokens[10]);
					
				}).distinct().collect(Collectors.toList());
		
		this.podaci = linije()
				.filter(s -> !s.equals("dateRep,day,month,year,cases,deaths,countriesAndTerritories,geoId,countryterritoryCode,popData2018,continentExp"))
				.map(s -> {
					
					String[] tokens = s.split(",");
					Zemlja zRef = this.zemlje.stream().filter(x -> x.getCountry().equals(tokens[6].trim())).findFirst().orElse(null);
					
					return new Podatak(tokens[0], tokens[4], tokens[5], zRef);
					
				}).collect(Collectors.toList());
	}
}
