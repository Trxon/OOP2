package vezba.sandbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUKUS {
	
	
	private static final String[] INPUTS = { 
			"2\n" + 
			"the odour coming out of the left over food was intolerable\n" +
			"ammonia has a very pungent odor\n" +
			"1\n" +
			"odour",
	};
	
	
	private static final String[] EXPECTED_OUTPUTS = {
			"2"
	};
	
   
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new StringReader(INPUTS[0]));
		int n = Integer.parseInt(br.readLine());
		
		Pattern p;
		Matcher m;
		
		StringBuilder 	uk,
						str = new StringBuilder();
		
		
		for (int i = 0; i < n; i++) str.append(br.readLine()+ " ");	// get all words from input into a single string
		str.toString();
		
		int t = Integer.parseInt(br.readLine());
		int count;
		
		for (int i = 0; i < t; i++) {								// iterate through UK words after the second int in input
			
			uk = new StringBuilder(br.readLine());					// get current word into StringBuilder object
			uk.insert((uk.indexOf("our")+2),'?');					// insert "?" after "u" in UK word
			
			p = Pattern.compile("\\b" + uk.toString() + "\\b");		// add word boundaries and compile as pattern 
			
			m = p.matcher(str);										// match with the entire string built earlier
			
			count = 0;
			while (m.find()) ++count;								// iterate through matches and count them
			
			System.out.println(count);								// print output
		}
    }
}