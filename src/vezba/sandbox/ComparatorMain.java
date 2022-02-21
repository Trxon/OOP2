package vezba.sandbox;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ComparatorMain {

	public static void main(String[] args) {
		
		List<Integer> l = new LinkedList<Integer>();
		for (int i = 0; i < 20; i++)
			l.add((int) (Math.random() * 100));
		
		System.out.println(l);
		
		System.out.println(l.stream().sorted((i1, i2) -> i1 - i2).collect(Collectors.toList()));
	}
}
