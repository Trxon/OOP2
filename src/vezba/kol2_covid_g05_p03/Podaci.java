package vezba.kol2_covid_g05_p03;

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
		load();
	}
	

	private void load() {
		
		try {
			this.zemlje = linije()
					.filter(s -> !s.contains("dateRep") && !s.contains("Other"))
					.map(s -> {
						String[] tokens = s.split(",");
						return new Zemlja(tokens[6], tokens[7], tokens[8], tokens[9], tokens[10], tokens[4], tokens[5], tokens[0]);
					}).collect(Collectors.toList());
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}


	public static Stream<String> linije() throws IOException {
		return Files.lines(Paths.get("/Users/nikolavetnic/Library/Mobile Documents/com~apple~CloudDocs/Documents/EclipseWorkspace/OOP2/src/vezba/kol2_covid_g05_p03/sars-cov-2.csv"));
	}
	
	
	class Data {
		
		String country, continent;
		int cases, deaths;
		LocalDate first;
		
		public void add(Zemlja z) {
			this.country = z.getCountry();
			this.continent = z.getContinent();
			
			this.cases += z.getCases();
			this.deaths += z.getDeaths();
			
			if (this.first == null || this.first.compareTo(z.getDate()) > 0)
				this.first = z.getDate();
		}
		
		public Data join(Data d) {
			this.cases += d.cases;
			this.deaths += d.deaths;
			
			if (this.first == null || this.first.compareTo(d.first) > 0)
				this.first = d.first;
			
			return this;
		}
		
		public double getPercentage() {
			return 100.0 * deaths / cases;
		}
		
		public int getDeaths() {
			return deaths;
		}
		
		@Override
		public String toString() {
			return String.format("%s %s | %d %d %s", country, continent, cases, deaths, first);
		}
	}
	
	
	public List<Data> getData() {
		
		Map<String, List<Zemlja>> m0 = zemlje.stream()
				.collect(Collectors.groupingBy(Zemlja::getCountry, Collectors.toList()));
		
		Map<String, Data> m1 = m0.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().collect(Collector.of(Data::new, Data::add, Data::join))));
		
		List<Data> l0 = m1.entrySet().stream()
				.map(e -> e.getValue()).collect(Collectors.toList());
		
		return l0;
	}
	
	
	public String tabelarniPrikaz() {
		
		List<Data> l0 = getData();
		
		Map<String, List<Data>> m0 = l0.stream()
				.collect(Collectors.groupingBy(d -> d.continent, Collectors.toList()));
		
		class Data1 {
			int k1, k2, k3;
			
			public void add(Data d) {
				if (d.getPercentage() < 1.0)
					k1++;
				else if (d.getPercentage() < 3.0)
					k2++;
				else
					k3++;
			}
			
			public Data1 join(Data1 d) {
				k1 += d.k1;
				k2 += d.k2;
				k3 += d.k3;
				
				return this;
			}
		}
		
		Map<String, Data1> m1 = m0.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().collect(Collector.of(Data1::new, Data1::add, Data1::join))));
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("-----------------------------------\n");
		sb.append("Continent | 0 - 1% | 1 - 3% | >3% |\n");
		sb.append("-----------------------------------\n");
		
		String table = m1.entrySet().stream()
				.map(e -> String.format("%9s | %6d | %6d | %3d |", e.getKey(), e.getValue().k1, e.getValue().k2, e.getValue().k3))
				.reduce("", (s1, s2) -> "".equals(s1) ? s2 : s1 + "\n" + s2);
		
		sb.append(table);
		sb.append("\n");

		sb.append("-----------------------------------");
		
		return sb.toString();
	}
	
	
	public Map<LocalDate, List<Data>> novozarazeneNaDan() {
		
		return getData().stream()
				.collect(Collectors.groupingBy(d -> (LocalDate) d.first, Collectors.toList()));
	}
	
	
	public String novozarazeneNaDanAsString() {
		
		return novozarazeneNaDan().entrySet().stream()
				.map(e -> String.format("%s | %s", e.getKey(), e.getValue().stream().map(d -> d.country).reduce("", (s1, s2) -> "".equals(s1) ? s2 : s1 + ", " + s2)))
				.reduce("", (s1, s2) -> "".equals(s1) ? s2 : s1 + "\n" + s2);
	}
	
	
	public Data najviseUmrlihNaKontinentu(String kontinent) {
		
		return getData().stream().filter(d -> d.continent.equalsIgnoreCase(kontinent))
				.max(Comparator.comparingInt(Data::getDeaths)).orElse(null);
	}
	
	
	public String najviseUmrlihNaKontinentuAsString(String kontinent) {
		
		Data d = najviseUmrlihNaKontinentu(kontinent);
		
		if (d == null) return "!";
		return d.toString();
	}
}
