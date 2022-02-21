package vezba.kol1_fudbal_p01;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Match {
	
	
	private static final Pattern PATTERN_SCORER = Pattern.compile(
			"(?sm)(?<name>.*?)\\s(?<min>\\d*\\+?\\d*?)'\\s*(\\((?<own>o\\.g\\.)?(?<pen>pen\\.)?\\))?");

	
	private Group group;
	private int num;
	private LocalDate date;
	private LocalTime time;
	private String team1, team2;
	private Result resHalf, resFinal, resOverTime, resPenalty;
	private String location;
	private Scorer[] team1Scorers, team2Scorers;
	
	
	public void setGroup(String group) {
		this.group = Group.fromString(group);
	}
	
	
	public void setNum(String num) { 
		this.num = Integer.parseInt(num); 
	};
	
	
	public void setDate(String month, String day) {
		
		if (month == null || day == null)
			return;
		
		this.date = LocalDate.of(2018, month.equalsIgnoreCase("JUN") ? 6 : 7, Integer.parseInt(day));
	}
	
	
	public void setTime(String time) {
		
		if (time == null)
			return;
		
		this.time = LocalTime.parse(time);
	}
	
	
	public void setTeams(String team1, String team2) {
		
		if (team1 == null || team2 == null)
			return;
		
		this.team1 = team1;
		this.team2 = team2;
	}
	
	
	public void setResults(String res0, String res1, String res2, String res3) {
		
		Result 	resHalf 	= null, 
				resFinal 	= null, 
				resOverTime = null, 
				resPenalty 	= null;
		
		if (res1 != null) {
			
			resHalf 	= Result.fromString(res3);
			resFinal 	= Result.fromString(res2);
			resOverTime = Result.fromString(res1);
			resPenalty 	= Result.fromString(res0);
		} else {
			
			if (res3 != null) {
				
				resHalf 	= Result.fromString(res3);
				resFinal 	= Result.fromString(res2);
				resOverTime = Result.fromString(res0);
				resPenalty 	= null;
			} else {
				
				resHalf 	= Result.fromString(res2);
				resFinal 	= Result.fromString(res0);
				resOverTime = null;
				resPenalty 	= null;
			}
		}
		
		this.resHalf		= resHalf;
		this.resFinal		= resFinal;
		this.resOverTime	= resOverTime;
		this.resPenalty		= resPenalty;
	}
	
	
	public void setLocation(String loc) {
		
		if (loc == null)
			return;
		
		this.location = loc;
	}
	
	
	public void setScorers(String team1, String team2, Result res) {
		this.team1Scorers = setTeamScorers(team1, res.team1());
		this.team2Scorers = setTeamScorers(team2, res.team2());
	}


	private Scorer[] setTeamScorers(String scorers, int count) {

		if (scorers == null || scorers.equals("-")) return null;
		
		Matcher matcherScorer = PATTERN_SCORER.matcher(scorers);
		
		List<Scorer> l = new ArrayList<Scorer>();
		
		while (matcherScorer.find()) {
			
			String 	name 	= matcherScorer.group("name").trim();
			String 	min 	= matcherScorer.group("min").trim();
			boolean own 	= matcherScorer.group("own") != null;
			boolean pen 	= matcherScorer.group("pen") != null;
			
			l.add(new Scorer(name, min, own, pen));
		}
		
		return l.toArray(new Scorer[l.size()]);
	}
	
	
	private String resultsToString() {
		
		StringBuilder sb = new StringBuilder();
		
		if (resPenalty != null) {
			
			sb.append(resPenalty.toString() + " pen. ");
		} 
		
		if (resOverTime != null) {
			
			sb.append(resOverTime.toString() + " a.e.t. ");
		}

		sb.append("(" + resFinal.toString() + ", ");
		sb.append(resHalf.toString() + ")");
		
		return sb.toString();
	}
	
	
	private String scorersToString() {
		
		StringBuilder sb = new StringBuilder();
		
		String t1s = team1Scorers == null ? "-" : 
			Arrays.stream(team1Scorers)
				.map(s1 -> s1.toString())
				.reduce("", (s1, s2) -> s1.equals("") ? s2 : s1 + " " + s2);
		
		String t2s = team2Scorers == null ? "-" : 
			Arrays.stream(team2Scorers)
				.map(s1 -> s1.toString())
				.reduce("", (s1, s2) -> s1.equals("") ? s2 : s1 + " " + s2);
		
		sb.append("[");
		sb.append(t1s);
		sb.append(";");
		sb.append(t2s);
		sb.append("]");
		
		return sb.toString();
	}
	
	
	public String toString() {
		return String.format("%s : (%d) %s/%s %s %s %s %s %n\t%s %n", (group != null ? group.toString() : "-"), num, date, time, team1, resultsToString(), team2, location, scorersToString());
	}


	public Group		group()			{ return group;			}
	public int 			num() 			{ return num; 			}
	public LocalDate 	date() 			{ return date; 			}
	public LocalTime 	time() 			{ return time; 			}
	public String 		team1() 		{ return team1; 		}
	public String 		team2() 		{ return team2; 		}
	public Result 		resHalf() 		{ return resHalf; 		}
	public Result 		resFinal() 		{ return resFinal; 		}
	public Result 		resOverTime() 	{ return resOverTime; 	}
	public Result 		resPenalty() 	{ return resPenalty; 	}
	public String 		location() 		{ return location; 		}
	public Scorer[] 	team1Scorers() 	{ return team1Scorers; 	}
	public Scorer[] 	team2Scorers() 	{ return team2Scorers; 	}
	
	
	public LocalDateTime dateTime() { 
		return LocalDateTime.of(date(), time());
	}
	
	
	public List<Scorer>	team1ScorersAsList() {
		if (team1Scorers == null) return new ArrayList<Scorer>();
		return List.of(team1Scorers);
	}
	
	
	public List<Scorer>	team2ScorersAsList() {
		if (team2Scorers == null) return new ArrayList<Scorer>();
		return List.of(team2Scorers);
	}
	
	
	public List<Scorer> allScorersAsList() {
		return Stream.concat(team1ScorersAsList().stream(), team2ScorersAsList().stream())
			.collect(Collectors.toList());
	}
	
	
	public Result finalResult() {
		return resPenalty != null ? resPenalty : resOverTime != null ? resOverTime : resFinal;
	}
	
	
	public String winner() {
		
		Result r = finalResult(); 
		
		if (r.team1() > r.team2())
			return team1;
		else
			return team2;
	}
	
	
	public boolean isComplete() {
		return group != null
			&& num != 0
			&& date != null
			&& time != null
			&& team1 != null
			&& team2 != null
			&& resHalf != null
			&& resFinal != null
			&& resOverTime != null
			&& resPenalty != null
			&& location != null
			&& team1Scorers != null
			&& team2Scorers != null;
	}
	
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o) return true;
		if (o == null) return false;
		
		if (getClass() != o.getClass())
			return false;
		
		Match m = (Match) o;
		
		if (group 		!= m.group 				||
			num			!= m.num				||
			!date.equals(m.date)				||
			!time.equals(m.time)				||
			!team1.equals(m.team1)				||
			!team2.equals(m.team2)				||
			!resHalf.equals(m.resHalf)			||
			!resFinal.equals(m.resFinal)		||
			!resOverTime.equals(m.resOverTime)	||
			!resPenalty.equals(m.resPenalty)	||
			!location.equals(m.location)	 	 )
			return false;
		
		for (int i = 0; i < team1Scorers.length; i++)
			if (!team1Scorers[i].equals(m.team1Scorers[i]))
				return false;
		
		for (int i = 0; i < team2Scorers.length; i++)
			if (!team2Scorers[i].equals(m.team2Scorers[i]))
				return false;
		
		return true;
	}
	
	
	@Override
	public int hashCode() {
		
		if (!isComplete()) return 0;
		
		final int prime = 31;
		int result = 1;
		
		result = prime * result + group.hashCode();
		result = prime * result + date.hashCode();
		result = prime * result + time.hashCode();
		result = prime * result + team1.hashCode();
		result = prime * result + team2.hashCode();
		result = prime * result + resHalf.hashCode();
		result = prime * result + resFinal.hashCode();
		result = prime * result + resOverTime.hashCode();
		result = prime * result + resPenalty.hashCode();
		result = prime * result + location.hashCode();
		result = prime * result + team1Scorers.hashCode();
		result = prime * result + team2Scorers.hashCode();
		
		return result;
	}
}

