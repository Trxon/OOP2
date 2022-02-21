package vezba.kol2_covid_g03_p03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Podaci {

	
	List<Zemlja> zemlje;
	List<Podatak> podaci;
	
	
	public Podaci() {
		
		zemlje = new ArrayList<Zemlja>();
		podaci = new ArrayList<Podatak>();
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Podaci.class.getResourceAsStream("sars-cov-2.xml")))) {
			
			String line = in.readLine();
			StringBuilder sb = new StringBuilder();
			
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			
			Pattern p0 = Pattern.compile("(?sm)<dateRep>(?<date>.*?)<\\/dateRep>\\s*?<day>(?<day>.*?)<\\/day>\\s*?<month>(?<month>.*?)<\\/month>\\s*?<year>(?<year>.*?)<\\/year>\\s*?<cases>(?<cases>.*?)<\\/cases>\\s*?<deaths>(?<deaths>.*?)<\\/deaths>\\s*?<countriesAndTerritories>(?<country>.*?)<\\/countriesAndTerritories>\\s*?<geoId>(?<geoId>.*?)<\\/geoId>\\s*?<countryterritoryCode>(?<code>.*?)<\\/countryterritoryCode>\\s*?<popData2018>(?<popData>.*?)<\\/popData2018>\\s*?<continentExp>(?<continent>.*?)<\\/continentExp>\\s*?");
			Matcher m0 = p0.matcher(sb.toString());
			
			while (m0.find()) {
				
				String dateString = m0.group("date");
				String casesString = m0.group("cases");
				String deathsString = m0.group("deaths");
				String country = m0.group("country");
				String geoId = m0.group("geoId");
				String code = m0.group("code");
				String popDataString = m0.group("popData");
				String continent = m0.group("continent");
				
				if (continent.equalsIgnoreCase("other"))
					continue;
				
				podaci.add(new Podatak(
						casesString, deathsString, dateString, new Zemlja(
								country, geoId, code, popDataString, continent)));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public Zemlja najkriticnijaZemlja() {
		
		class Data {
			
			int pop, cases;
			
			public void add(Podatak p) {
				pop = p.getCountry().getPopData();
				cases += p.getCases();
			}
			
			public Data join(Data d) {
				pop = d.pop;
				cases += d.cases;
				
				return this;
			}
		}
		
		
		Map<Zemlja, Data> m0 = podaci.stream()
				.filter(p -> p.getCountry().getPopData() != 0)
				.collect(Collectors.groupingBy(Podatak::getCountry, Collector.of(Data::new, Data::add, Data::join)));
		
		return m0.entrySet().stream()
				.max(Comparator.comparingDouble(e -> 100.0 * e.getValue().cases / e.getValue().pop))
				.map(e -> e.getKey()).orElse(null);
	}
	
	
	public Zemlja najkriticnijaZemljaAlt() {
		
		Map<Zemlja, Integer> m0 = podaci.stream()
				.filter(p -> p.getCountry().getPopData() != 0)
				.collect(Collectors.groupingBy(Podatak::getCountry, Collectors.summingInt(Podatak::getCases)));
		
		class Data {
			
			Zemlja c;
			int i;
			
			public double getPercentage() {
				return 100.0 * i / c.getPopData();
			}
		}
		
		Data data = m0.entrySet().stream()
				.map(e -> {
					
					Data d = new Data();
					d.c = e.getKey();
					d.i = e.getValue();
					
					return d;
				})
				.max(Comparator.comparingDouble(Data::getPercentage))
				.orElse(null);
		
		if (data != null)
			return data.c;
		else
			return null;
	}
	
	
	public List<Zemlja> zemljeSaNiskomSmrtnoscu(String kontinent) {
		
		class Data {
			
			int c, d;
			
			public void add(Podatak p) {
				c += p.getCases();
				d += p.getDeaths();
			}
			
			public Data join(Data data) {
				c += data.c;
				d += data.d;
				
				return this;
			}
			
			public double getPercentage() {
				return 100.0 * d / c;
			}
		}
		
		Map<Zemlja, Data> m0 = podaci.stream()
				.filter(p -> p.getCountry().getContinent().equalsIgnoreCase(kontinent))
				.filter(p -> p.getDate().equals(LocalDate.of(2020, 6, 1)))
				.collect(Collectors.groupingBy(Podatak::getCountry, Collector.of(Data::new, Data::add, Data::join)));
		
		return m0.entrySet().stream()
				.filter(e -> 100.0 * e.getValue().d / e.getValue().c < 0.5)
				.sorted(Comparator.comparingDouble(e -> e.getValue().getPercentage()))
				.map(e -> e.getKey()).collect(Collectors.toList());
	}
	
	
	public void tabela() {
		
		class Data {
			
			int cases, deaths;
			LocalDate first;
			
			public void add(Podatak p) {
				cases += p.getCases();
				deaths += p.getDeaths();
				
				if (first == null || first.compareTo(p.getDate()) > 0)
					first = p.getDate();
			}
			
			public Data join(Data d) {
				cases += d.cases;
				deaths += d.deaths;
				
				if (first == null || first.compareTo(d.first) > 0)
					first = d.first;
				
				return this;
			}
			
			public double getPercentage() {
				return cases == 0 ? 0 : 100.0 * deaths / cases;
			}
		}
		
		Map<Zemlja, Data> m0 = podaci.stream()
				.collect(Collectors.groupingBy(Podatak::getCountry, Collector.of(Data::new, Data::add, Data::join)));
		
		System.out.println("+------------------------------------------+------------+------------+------------+----------+------------+");
		System.out.println("|                                   ZEMLJA | STANOVNIKA |  SLUCAJEV  |   UMRLIH   | % UMRLIH | PRVI SLUC. |");
		System.out.println("+------------------------------------------+------------+------------+------------+----------+------------+");
		m0.entrySet().stream()
				.sorted(Comparator.comparingDouble(e -> e.getValue().getPercentage()))
				.map(e -> String.format("| %40s | %10d | %10d | %10d | %8.2f | %s |", 
						e.getKey().getCountry(), e.getKey().getPopData(), e.getValue().cases, e.getValue().deaths, e.getValue().getPercentage(), e.getValue().first))
				.forEach(System.out::println);
		System.out.println("+------------------------------------------+------------+------------+------------+----------+------------+");
	}
	
	
	public static void main(String[] args) {
		
		Podaci p = new Podaci();
		
		System.out.println("NAJKRITICNIJA : " + p.najkriticnijaZemlja());
		System.out.println();
		
		System.out.println("NAJKRITICNIJA : " + p.najkriticnijaZemljaAlt());
		System.out.println();
		
		List<Zemlja> list = p.zemljeSaNiskomSmrtnoscu("Europe");
		System.out.println("ZEMLJE SA NISKOM SMRTNOSCU : ");
		list.stream().forEach(System.out::println);
		System.out.println();
		
		p.tabela();
	}
}
