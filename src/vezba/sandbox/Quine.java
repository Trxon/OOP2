package vezba.sandbox;

import java.io.FileReader;
import java.io.IOException;

public class Quine {

	public static void main(String[] args) throws IOException {
		
		FileReader fr = new FileReader(
				"/Users/nikolavetnic/Library/Mobile Documents/com~apple~CloudDocs/Documents/EclipseWorkspace/OOP2/src/vezba/sandbox/Quine.java");    
        
		int i;    
        
        while ((i = fr.read()) != -1)    
        	System.out.print((char)i);    
        
        fr.close(); 
	}
}
