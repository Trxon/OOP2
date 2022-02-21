package vezba.kol2_covid_g04_p01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Data {
	
	
	private List<String> list;

	
	public Data() {
		this.list = load();
	}
	

	public String printByCode(String code) {
		
		String first = null;
		
		for (String s : list)
			if (s.toUpperCase().contains(code.toUpperCase()))
				first = s;
		
		if (first != null) {
			
			String[] firstTokens = first.split(",");
			String header = String.format(" %20s %10s %10s ± %20s %10s %10s", 
					firstTokens[6], firstTokens[10], firstTokens[9], "datum", "zarazeno", "umrlo");
			
			StringBuilder sb = new StringBuilder();
			
			sb.append(header);
			sb.append("±");
			
			for (String s : list)
				if (s.toUpperCase().contains(code.toUpperCase())) {
					
					String[] tokens = s.split(",");
					
					sb.append(String.format(" %20s %10s %10s", tokens[0], tokens[4], tokens[5]));
					sb.append("±");
				}
			
			return sb.toString();
			
		} else {
			return "";
		}
	}


	private List<String> load() {
		
		List<String> list = new ArrayList<String>();
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(Data.class.getResourceAsStream("sars-cov-2.csv")))) {
			
			String line = br.readLine();
			line = null;
			
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
