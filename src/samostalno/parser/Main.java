package samostalno.parser;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	
	
	private static final String ISPIT = "Muzicka komunikacija - usmeni ispit, 14. jun 2021.";
	

	public static void main(String[] args) {
		
		load();
	}
	

	private static void load() {
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("sample2.csv"), "UTF-8"))) {
			
			String line;
			StringBuilder sb = new StringBuilder();
			
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			
			Pattern p0 = Pattern.compile("\\\"\\d.*?\\\",\\\"(?<prezime>.*?)\\\",\\\"(?<ime>.*?)\\\",\\\"(?<idx>.*?)\\\"\\s*?");
			Matcher m0 = p0.matcher(sb.toString());
			
			OutputStream os = new FileOutputStream("res//parsedInput2.csv");
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
			
			pw.println(ISPIT + ";;");
			pw.println("Br. ind.;Prezime;Ime");
			
			int count = 0;
			int hour = 7;
			
			while (m0.find()) {
				
				if ((count % 25) == 0) {
					hour++;
					pw.println(String.format("%02d:00 - %02d:00;;", hour, hour + 1));
				}

				count++;
				pw.println("s" + m0.group("idx") + ";" + m0.group("prezime") + ";" + m0.group("ime"));
				System.out.println("s" + m0.group("idx") + ";" + m0.group("prezime") + ";" + m0.group("ime"));
			}
			
			pw.close();
			
			System.out.println("Successfully parsed!");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
