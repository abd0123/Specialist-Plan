import java.io.*;
import java.util.*;

public class LongestCommonSubsequence {
	
	static int LCS(int[] a, int[] b) {
		int memo[][] =  new int[a.length+1][b.length+1];
		
		for(int i=a.length-1;i>-1;i--) {
			for(int j=b.length-1;j>-1;j--) {
				
				int take = -1;
				int leavea = memo[i+1][j];
				int leaveb = memo[i][j+1];
				
				if(a[i]==b[j])
					take = 1 + memo[i+1][j+1];
				
				memo[i][j] = Math.max(take, Math.max(leavea, leaveb));
			}
		}
		
		return memo[0][0];
	}
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		
		int[] a = {1,2,3,4};
		int[] b = {1,2,4,3};
		
		pw.println(LCS(a,b));
		
		pw.close();
	}
	
	static class Scanner {
		StringTokenizer st;
		BufferedReader br;
		public Scanner(InputStream s) {
			br = new BufferedReader(new InputStreamReader(s));
		}
		public Scanner(String file) throws FileNotFoundException {
			br = new BufferedReader(new FileReader(file));
		}
		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}
		public String nextLine() throws IOException {
			return br.readLine();
		}
		public double nextDouble() throws IOException {
			return Double.parseDouble(next());
		}
		public boolean ready() throws IOException {
			return br.ready();
		}
	}
}