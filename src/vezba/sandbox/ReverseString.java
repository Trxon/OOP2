package vezba.sandbox;

public class ReverseString {

	public static void main(String[] args) {
		
		String s = "main";
		StringBuilder reverse = new StringBuilder();
		
		for (int i = 0; i < s.length(); i++)
			reverse.append(s.charAt(s.length() - i - 1));
		
		System.out.println(reverse.toString());
	}
}
