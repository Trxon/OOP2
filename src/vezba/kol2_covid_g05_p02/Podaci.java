package vezba.kol2_covid_g05_p02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Podaci {
	
	
	private List<Zemlja> zemlje;
	
	
	public Podaci() {
		
		try {
			this.zemlje = parseData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public List<Zemlja> getZemlje() {
		return zemlje;
	}

	
	private static Stream<String> linije() throws IOException {
		return Files.lines(Paths.get(
				"/Users/nikolavetnic/Library/Mobile Documents/com~apple~CloudDocs/Documents/EclipseWorkspace/OOP2/src/vezba/kol2_covid_g05_p00/sars-cov-2.csv"));
	}
	
	
	public List<Zemlja> parseData() throws IOException {
		
		class Pair {
			
			Zemlja z;
			Podatak p;
		}
		
		return linije()
				.filter(s -> !s.equals(
						"dateRep,day,month,year,cases,deaths,countriesAndTerritories,geoId,countryterritoryCode,popData2018,continentExp"))
				.filter(s -> !s.contains("Other"))
				.map(s -> {
					
					String[] tokens = s.split(",");
					
					Pair pair = new Pair();
					pair.z = new Zemlja(tokens[6], tokens[7], tokens[8], tokens[9], tokens[10]);
					pair.p = new Podatak(tokens[0], tokens[4], tokens[5]);
					
					return pair;
					
				}).collect(Collectors.groupingBy(
						p -> (Zemlja) p.z, 
						Collectors.mapping(p -> (Podatak) p.p, Collectors.toList())))
				.entrySet().stream()
				.map(e -> {
					
					Zemlja z = e.getKey();
					z.setPodaci(e.getValue());
					
					return z;
					
				}).collect(Collectors.toList());
	}
	
	
	public Zemlja najviseUmrlihNaKontinentu(String kontinent) {
		
		return zemlje.stream()
				.filter(z -> z.getContinent().equalsIgnoreCase(kontinent))
				.max(Comparator.comparingInt(Zemlja::totalDeaths)).orElse(null);
	}
	
	
	public Map<LocalDate, List<Zemlja>> novozarazeneNaDan() {
		
		return zemlje.stream()
				.collect(Collectors.groupingBy(Zemlja::dateOfFirst, Collectors.toList()));
	}
	
	
	public String tabelarniPrikaz() {
		
		class Data {
			
			int k0, k1, k2;
			
			public void add(Zemlja z) {
				if (z.getDeathPercentage() < 1.0)
					k0++;
				else if (z.getDeathPercentage() < 3.0)
					k1++;
				else
					k2++;
			}
			
			public Data join(Data d) {
				this.k0 += d.k0;
				this.k1 += d.k1;
				this.k2 += d.k2;
				
				return this;
			}
		}
		
		return zemlje.stream()
				.collect(Collectors.groupingBy(
						Zemlja::getContinent, 
						Collectors.toList())).entrySet().stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey, 
						e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join)))).entrySet().stream()
				.map(e -> String.format("| %9s | %6d | %6d | %6d |", e.getKey(), e.getValue().k0, e.getValue().k1, e.getValue().k2))
				.collect(Collectors.joining(
						"\n", 
						"+-----------+--------+--------+--------+\n| CONTINENT | 0 - 1% | 1 - 3% |  > 3%  |\n+-----------+--------+--------+--------+\n", 
						"\n+-----------+--------+--------+--------+"));
	}
}
