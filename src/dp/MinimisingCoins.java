import java.io.*;
import java.util.*;

public class MinimisingCoins {
	
	static int MinimisingCoinsDP(int nums[], int x) {
		int n = nums.length;
		int ans[] = new int[x+1];
		for(int i=0;i<n;i++)
			if(nums[i]<x+1)
				ans[nums[i]] = 1;
		
		for(int i=0;i<x+1;i++) {
			if(ans[i]==1) continue;
			ans[i] = (int) 1e7;
			for(int j=0;j<n;j++) {
				if(i-nums[j]>0)
					ans[i] = Math.min(ans[i], 1+ans[i-nums[j]]);
			}
		}
		
		return ans[x];
	}
	
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		
		int nums[] = {1,5,7};
		int x = 11;
		
		pw.println(MinimisingCoinsDP(nums,x));
		
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