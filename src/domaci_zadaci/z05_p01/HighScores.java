package domaci_zadaci.z05_p01;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HighScores {

	private List<GameState> lista = new ArrayList<GameState>();
	
	public int dodaj(GameState igra) {
		
		if (!igra.gameWon()) return -1;
		
		lista.add(igra);
		
		lista.sort(Comparator.comparing(GameState::numAttempts));
		if (lista.size() > 10) lista.remove(10);
		
		return lista.indexOf(igra) + 1;
	}
	
	public String rank(GameState igra) {
		return (lista.indexOf(igra) == -1 ? "NONE" : "" + 1);
	}
}
