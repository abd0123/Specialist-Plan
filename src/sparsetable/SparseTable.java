import java.io.*;
import java.util.*;

public class SparseTable {
	
	
	//0 - INDEXED
	static class ST {
		int table[][];
		
		//Adjust k according to N;
		// if N <= 1e6, K should be ~20
		// if N <= 1e7, K should be ~25
		// if N <= 1e9, K should be ~30
		final int k = 25;
		int logs[];
		
		int op(int a, int b) {
			return Math.min(a, b);
		}
		
		public ST(int[] nums) {
			int n = nums.length;
			table = new int[k][n];
			logs = new int[n+1];
			
			for(int i=2;i<=n;i++) {
				logs[i] = logs[i/2] + 1;
			}
			
			for(int i=0;i<n;i++) {
				table[0][i] = nums[i];
			}
			
			for(int j=1;j<k;j++) {
				for(int i=0;i-1+(1<<j)<n;i++) {
					table[j][i] = op(table[j-1][i], table[j-1][i+(1<<(j-1))]); 
				}
			}
			
		}
		
		int query(int a, int b) {
			int sz = logs[(b-a)+1];
			return op(table[sz][a],table[sz][b-(1<<sz)+1]);
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		
		int arr[] = {8,6,2,3,6,9,2,1,3,1,7};
		
		ST st = new ST(arr);
		
		pw.println(st.query(0, 1));
		
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
