package domaci_zadaci.nagradni01_p01;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		
		class MinMax {
			public long min = Long.MAX_VALUE;
			public long max = Long.MIN_VALUE;
		}
		
		ArrayList<Integer> l = new ArrayList<Integer>();
		for (int i = 0; i < 20; i++) l.add(new Random().nextInt(250));
		
		System.out.println(l);
		
		MinMax mm = l.parallelStream()
				.collect(MinMax::new, 
						(mm1, x) -> {
							if (x < mm1.min)
								mm1.min = x;
							if (x > mm1.max)
								mm1.max = x;
						}, 
						(mm1, mm2) -> {
							mm1.min = mm1.min < mm2.min ? mm1.min : mm2.min;
							mm1.max = mm1.max > mm2.max ? mm1.max : mm2.max;
						});
		System.out.println("Minimalni element : " + mm.min);
		System.out.println("Maksimalni element : " + mm.max);
		
		IntSummaryStatistics s = l.stream().mapToInt(Integer::intValue).summaryStatistics();
		
		System.out.println("Minimalni element (SummaryStatistics) : " + s.getMin());
		System.out.println("Maksimalni element (SummaryStatistics) : " + s.getMax());
	}
}
