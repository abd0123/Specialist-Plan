import java.io.*;
import java.util.*;

public class LongestIncreasingSubsequence {
	
	static int LISDP(int[] a) {
		int memo[][] = new int[a.length+2][a.length+2];
		
		for(int i=a.length;i>0;i--) {
			for(int j=a.length;j>-1;j--) {
				int take = -1;
				int leave = memo[i+1][j];
				
				if(j==0||a[i-1]>a[j-1])
					take = 1 + memo[i+1][i];
				
				memo[i][j] = Math.max(take, leave);
			}
		}
		
		return memo[1][0];
	}
	
	static int LISNLOGN(int[] a) {
		int n = a.length;
		ArrayList<Integer> ans = new ArrayList<>();
		
		for(int i=0;i<n;i++) {
			if(ans.size()==0||ans.get(ans.size()-1)<a[i])
				ans.add(a[i]);
			else {
				int l = 0;
				int r = ans.size()-1;
				int pos = -1;
				while(l<=r) {
					int mid = (l+r)/2;
					
					if(ans.get(mid)>=a[i]) {
						if(mid>0&&ans.get(mid-1)>=a[i])
							r = mid-1;
						else {
							pos = mid;
							break;
						}
					}
					else l = mid+1;
				}
					
				ans.set(pos, a[i]);
			}
			
		}
		
		return ans.size();
	}
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		
		int[] a = {1,4,-1,2,8,8};
		int[] b = {1,4,-1,2,8,9,-5,4,12};
		
		pw.println(LISDP(a));
		pw.println(LISNLOGN(b));
		
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