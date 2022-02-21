package vezba.sandbox;

public class RecPrintStar {

	
	public static String pr(int n) {
		return pr(n, n);
	}
	
	
	private static String pr(int n, int k) {
		if (k == 1) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i <= n - k; i++) sb.append("*");
			
			return sb.toString();
		} else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i <= n - k; i++) sb.append("*");
			
			return sb.append("\n").toString() + pr(n, k - 1);
		}
	}


	public static void main(String[] args) {
		
		System.out.println(pr(4));
	}
}
