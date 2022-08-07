import java.io.*;
import java.util.*;

public class Kadane {

	static long kadane1D(int[] a) {
		int n = a.length;
		long badpref = 0;
		long sum = 0;
		long ans = (long) - 1e17;
		
		for(int i=0;i<n;i++) {
			sum += a[i];
			ans = Math.max(ans, sum-badpref);
			badpref = Math.min(badpref, sum);
		}
		
		return ans;
	}
	
	
	static long kadane2D(int[][] b) {
		int n = b.length;
		int m = b[0].length;
		
		long ans = (long) - 1e17;
		
		for(int i=0;i<n;i++) {
			int[] subrct = new int[m];
			for(int j=i;j<m;j++) {
				
				for(int k=0;k<m;k++)
					subrct[k] += b[j][k];
				
				ans = Math.max(ans, kadane1D(subrct));
			}
		}
		
		return ans;
	}
	
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		
		int[] a = {2,-3,1,4,5,-12};
		
		int[][] b = 
        {{-1,2,4,6},
        {-7,8,7,-2},
        {-9,1,4,-8},
        {-1,-2,-3,-4}};
		
		long ans1d = kadane1D(a);
		
		long ans2d = kadane2D(b);
		
		pw.println(ans1d);
		pw.println(ans2d);
		
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