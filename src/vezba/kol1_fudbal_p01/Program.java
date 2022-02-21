package vezba.kol1_fudbal_p01;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * Zadatak
 * =======
 * 
 * Napisati Java aplikaciju koja pomocu tokova podataka i lambda i-
 * zraza obradjuje i prikazuje podatke o fudbalskom prvenstvu.
 * 
 * Pri pokretanju, aplikacija ucitava podatke iz fajla 2018.txt. P-
 * odaci su dati u obicnom tekstualnom formatu. Podaci o jednoj ut-
 * akmici se nalaze u jednom redu fajla. Na pocetku se nalazi redni
 * broj utakmice na prvenstvu, slede datum i vreme, pa imena zemal-
 * ja ciji su timovi igrali, sa rezultatom izmedju. U nastavku lin-
 * ije se nalazi lokacija gde je odigrana utakmica. Ako je bilo go-
 * lova, u narednoj liniji se nalaze podaci o strelcima na utakmici.
 * Takodje, utakmice su odvojene po grupama i fazama takmicenja.
 * 
 * Za cuvanje podataka je potrebno napraviti nabrojivi tip Grupa, k-
 * oji predstavlja grupe prve faze prvenstva: Grupa A, Grupa B... G-
 * rupa H, kao i grupisanje utakmica u plej-of-u: osmina finala, ce-
 * tvrtina finala, polufinale, trece mesto, finale.
 * 
 * Takodje je potrebno implementirati klasu Strelac za predstavljan-
 * je podataka o strelcima na utakmici, i koja ima sledeca polja: i-
 * me stralca, minut u kojem je postignut gol, zatim boolean indika-
 * tor da li u pitanju gol iz penala, i indikator da li je u pitanju
 * autogol.
 * 
 * Potom implementirati klasu Rezultat koja ima dva celobrojna polja
 * u kojima se nalazi broj golova na utakmici za svaki od timova.
 * 
 * Na kraju, implementirati klasu Utakmica koja sadrzi sve podatke o
 * jednoj utakmici: grupa, redni broj, datum, vreme, zemlja A, zeml-
 * ja B, rezultat na poluvremenu, rezultat po isteku vremena, rezul-
 * tat posle produzetaka, rezultat posle penala, zatim lista strela-
 * ca za zemlju A, lista strelaca za zemlju B. Ako nisu igrani prod-
 * uzeci ili penali posle produzetaka, odgovarajuci rezultati su nu-
 * ll.
 * 
 * Takodje implementirati metode equals(), hashCode(), toString() k-
 * ako bi instance ovih klasa mogle da se koriste u kolekcijama.
 * 
 * Podaci se ucitavaju iz fajla i smestaju u jednu veliku listu ins-
 * tanci klase Utakmica. Potom se podaci iz ove liste obradjuju isk-
 * ljucivo pomocu tokova podataka i lambda izraza.
 * 
 * Implementirati metod String pobednik() koji vraca ime zemlje koja
 * je pobedila na prvenstvu.
 * 
 * Implementirati metod String najviseGolova() koji vraca ime igraca
 * koji je dao najvise golova ukupno na celom prvenstvu.
 * 
 * Pozvati metode iz glavnog programa i ispisati rezultate.
 * 
 * Potom tabelarno prikazati podatke o svim golovima na prvenstvu s-
 * ortirane po datumu i vremenu kada je gol dat.
 * 
 * Svaki red tabele sadrzi podatke o jednom golu. Sve kolone su red-
 * om: datum kada je gol dat, tacno vreme u koje je dat gol, ime ig-
 * raca koji je dao gol, i ime zemlje za koju je gol dat. Treba obr-
 * atiti paznju da brojevi budu uredno poravnati, i takodje prikaza-
 * ti zaglavlje sa imenima kolona.
 * 
 * Implementirati metod int brojGolova() koji vraca ukupan broj gol-
 * ova na prvenstvu.
 * 
 * Implementirati metod String najcesciStadion() koji vraca ime Sta-
 * diona i grada u kojem se nalazi, a kojem se odigralo najvise uta-
 * kmica.
 * 
 * Potom, tabelarno prikazati podatke o osvojenim poenima u grupnome
 * delu prvenstva. Za svaku grupu prikazati po jednu tabelu.
 * 
 * Svaki red tabele sadrzi podatke o jednoj zemlji, a od kolona sad-
 * rzi ime zemlje i broj poena. Redovi u tabeli su sortirani po bro-
 * ju poena. Broj poena se racuna na sledeci nacin: pobeda vredi tri
 * poena, neresen rezultat nosi po jedan poen svakoj od zemalja, dok
 * zemlja koja je izgubila utakmicu ne dobija poene za tu utakmicu.
 */


public class Program {
	
	
	private static final Pattern PATTERN_MATCH = Pattern.compile(
			"(?sm)^\\s*(?<group>.*?)?\\:?\\s*\\("
			+ "(?<num>\\d*?)\\).*?"
			+ "(?<mon>Jun|Jul)\\/"
			+ "(?<day>\\d{1,2})\\s*?"
			+ "(?<time>\\d{2}:\\d{2})\\s*"
			+ "(?<country1>[a-zA-Z .]*?)\\s*"
			+ "(?<res0>\\d*-\\d*)\\s*"
			+ "(?:pen\\.)?\\s*"
			+ "(?<res1>\\d*-\\d*)?\\s*"
			+ "(?:a\\.e\\.t\\.)?\\s*\\"
			+ "((?<res2>\\d*-\\d*)(?:,\\s*)?(?<res3>\\d*-\\d*)?\\)\\s*"
			+ "(?<country2>[a-zA-Z .]*?)\\s*@\\s*"
			+ "(?<loc>[a-zA-Z-,\\.\\s]*?)\\s\\(.*?\\)\\s*(\\[(?<team1>.*?);\\s*(?<team2>.*?)\\])?$");
	
	
	public static void main(String[] args) {
		
		String text = Loader.load("res/2018.txt");
		List<Match> matches = readTextFile(text);
		
		for (Match m : matches) System.out.println(m);
		
		System.out.println("World Cup Winner : " + pobednik(matches) + "\n");
		
		System.out.println("Top Scorer : " + najviseGolova(matches) + "\n");
		
		System.out.println("Total goals : " + brojGolova(matches));
		
		System.out.println("Most frequently used arena : " + najcesciStadion(matches) + "\n");
		
		scoreTable(matches);
		
		groupTable(matches);
	}
	
	
	private static void groupTable(List<Match> matches) {
		
		class TeamData {
			
			String team;
			int points;
			
			public TeamData(String team) {
				this.team = team;
				this.points = 0;
			}
			
			@Override
			public String toString() {
				return String.format("%s %d", team, points);
			}
			
			@Override
			public boolean equals(Object o) {
				
				if (this == o) return true;
				if (o == null) return false;
				
				if (getClass() != o.getClass())
					return false;
				
				TeamData t = (TeamData) o;
				
				if (!team.equalsIgnoreCase(t.team) || points != t.points) return false;
				
				return true;
			}
			
			@Override
			public int hashCode() {
				
				final int prime = 31;
				int result = 1;
				
				result = result * prime + team.hashCode();
				result = result * prime + points * 23;
				
				return result;
			}
		}
		
		Predicate<Match> gFilter = m -> 
				m.group() == Group.A || 
				m.group() == Group.B ||
				m.group() == Group.C ||
				m.group() == Group.D ||
				m.group() == Group.E ||
				m.group() == Group.F ||
				m.group() == Group.G ||
				m.group() == Group.H  ;
		
		Map<Group, Set<TeamData>> map = matches.stream()
			.filter(gFilter)
			.collect(
					Collectors.groupingBy(
							Match::group, 
							Collectors.mapping(
									e -> new TeamData(e.team1()), 
									Collectors.toSet())));
		
		matches.stream().filter(gFilter)
			.forEach(m -> {
				
				Set<TeamData> s = map.get(m.group());
				
				Result r = m.finalResult();
				boolean team1Wins = false, team2Wins = false;
				
				if (r.team1() > r.team2()) {
					team1Wins = true;
				} else if (r.team1() < r.team2()) {
					team2Wins = true;
				} else {
					team1Wins = true;
					team2Wins = true;
				}
				
				Iterator<TeamData> it = s.iterator();
				
				while (it.hasNext()) {
					
					TeamData t = it.next();
					
					if (t.team.equals(m.team1()))
						if (team1Wins)
							if (team2Wins)
								t.points += 1;
							else
								t.points += 3;
					
					if (t.team.equals(m.team2()))
						if (team2Wins)
							if (team1Wins)
								t.points += 1;
							else
								t.points += 3;
				}
			});
		
		map.entrySet().stream()
			.sorted(Map.Entry.comparingByKey())
			.flatMap(e -> {
				
				ArrayList<TeamData> list = new ArrayList<TeamData>();

				Iterator<TeamData> it = e.getValue().iterator();
				while (it.hasNext()) list.add(it.next());
				list.sort((t1, t2)-> t2.points - t1.points);
				
				HashMap<Group, List<TeamData>> out = new HashMap<Group, List<TeamData>>();
				out.put(e.getKey(), list);
				
				return out.entrySet().stream();
			
			}).forEach(e -> {
			
				System.out.println(e.getKey().toString().toUpperCase());
				for (TeamData t : e.getValue())
					System.out.println("\t" + t);
			});
		
		System.out.println();
	}


	private static int brojGolova(List<Match> matches) {
		
		return matches.stream()
			.map(m -> {
				Result r = m.finalResult();
				return r.team1() + r.team2();
			})
			.reduce(0, (n, m) -> n + m);
	}
	
	
	private static String najcesciStadion(List<Match> matches) {
		
		return matches.stream()
			.collect(Collectors.groupingBy(
					Match::location, 
					Collectors.counting()))
			.entrySet().stream()
			.max(Map.Entry.comparingByValue())
			.get().getKey();
	}


	public static void scoreTable(List<Match> matches) {
		
		class DateTeam implements Comparable<DateTeam> {
			
			LocalDateTime date;
			String team;
			
			public DateTeam(LocalDateTime date, String team) {
				this.date = date;
				this.team = team;
			}

			@Override
			public int compareTo(DateTeam o) {
				return date.compareTo(o.date);
			}
		}
		
		Map<DateTeam, List<Scorer>> map1 = matches.stream()
			.collect(Collectors.toMap(
					m -> new DateTeam(m.dateTime(), m.team1()), 
					m -> m.team1ScorersAsList()));
		
		Map<DateTeam, List<Scorer>> map2 = matches.stream()
			.collect(Collectors.toMap(
					m -> new DateTeam(m.dateTime(), m.team2()), 
					m -> m.team2ScorersAsList()));
		
		class Goal {
			
			LocalDateTime date;
			Scorer scorer;
			String team;
			
			public Goal(LocalDateTime date, Scorer scorer, String team) {
				this.date 	= date;
				this.scorer = scorer;
				this.team 	= team;
			}
		}
		
		System.out.print(
				" DATE          | TIME     | SCORER                       | TYPE     |                    \n" +
				"---------------+----------+------------------------------+----------+--------------------\n" );
		Stream.concat(map1.entrySet().stream(), map2.entrySet().stream())
			.sorted(Map.Entry.comparingByKey())
			.flatMap(e -> {
				
				LocalDateTime date = e.getKey().date;
				String team = e.getKey().team;
				List<Scorer> scorers = e.getValue();
				
				List<Goal> goals = new ArrayList<Goal>();
				
				for (Scorer scorer : scorers)
					goals.add(new Goal(date.plusMinutes(scorer.timeAsInt()), scorer, team));
				
				return goals.stream();
			})
			.forEach(goal -> System.out.printf(" %-13s | %-8s | %-28s | %-8s | %-18s %n", 
					goal.date.toLocalDate(), 
					goal.date.toLocalTime(), 
					goal.scorer.name(), 
					goal.scorer.isOwn() ? "(o.g.)" : goal.scorer.isPen() ? "(pen.)" : "", 
					goal.team.toUpperCase()));
		
		System.out.println();
	}
	
	
	public static String pobednik(List<Match> matches) {
		
		return matches.stream()
			.filter(m -> m.group() == Group.FINAL)
			.map(m -> m.winner())
			.collect(Collectors.collectingAndThen(
					Collectors.toList(), 
					list -> {
						return list.get(0);
					}));
	}
	
	
	public static String najviseGolova(List<Match> matches) {
		
		return matches.stream()
			.flatMap(m -> Stream.concat(m.team1ScorersAsList().stream(), m.team2ScorersAsList().stream()))
			.collect(Collectors.groupingBy(Scorer::name, Collectors.counting()))
			.entrySet().stream()
			.reduce((e1, e2) -> e1.getValue() < e2.getValue() ? e2 : e1).get().getKey();		
	}


	private static List<Match> readTextFile(String text) {

		Matcher matchMatcher = PATTERN_MATCH.matcher(Loader.load("res/2018.txt"));
		
		List<Match> matches = new ArrayList<Match>();
		String g = null;
		
		while (matchMatcher.find()) {
			
			Match newMatch = new Match();
			
			String currGroup = matchMatcher.group("group");
			if (g == null || (currGroup != "" && !g.equals(currGroup))) g = currGroup;
			
			newMatch.setGroup(g);
			newMatch.setNum(matchMatcher.group("num"));
			newMatch.setDate(matchMatcher.group("mon"), matchMatcher.group("day"));
			newMatch.setTime(matchMatcher.group("time"));
			newMatch.setTeams(matchMatcher.group("country1"), matchMatcher.group("country2"));
			newMatch.setResults(
					matchMatcher.group("res0"), 
					matchMatcher.group("res1"), 
					matchMatcher.group("res2"), 
					matchMatcher.group("res3"));
			newMatch.setLocation(matchMatcher.group("loc"));
			newMatch.setScorers(matchMatcher.group("team1"), matchMatcher.group("team2"), newMatch.resFinal());
			
			matches.add(newMatch);
		}
		
		return matches;
	}
}