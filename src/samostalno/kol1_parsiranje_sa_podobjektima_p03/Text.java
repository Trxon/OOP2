package samostalno.kol1_parsiranje_sa_podobjektima_p03;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Text {

	public static void main(String[] args) {
		
		List<String> l = new ArrayList<String>();
		l.add("aa");
		l.add("bb");
		l.add("cc");
		
		List<String> l0 = l.stream().map(s -> {
			if (s.equals("aa")) return null;
			else return s;
		}).collect(Collectors.toList());
		
		System.out.println(l0);
		
		l0.stream().map(s -> {
			if (s == null) return 0;
			else return s.length();
		}).forEach(System.out::println);
		
		String datum = "2021.04.26.21:00";
		datum = datum.replaceAll("\\.", "-");
		datum = datum.substring(0, 10) + "T" + datum.substring(11) + ":00";
		System.out.println(datum);
		LocalDateTime d = LocalDateTime.parse(datum);
		System.out.println(d);
	}
}
