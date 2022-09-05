import java.io.*;
import java.util.*;

public class B {

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

	//The solution for this question is a very straight forward application of LCS for
	//all of the arrays, the hardest part is mostly parsing the input. Other than that the LCS
	//function returns the answer we want and we just have to maximise between them.

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);

		int t = sc.nextInt();

		while(t-->0) {

			ArrayList<Integer> agnes = new ArrayList<>();
			ArrayList<int[]> racers = new ArrayList<>();
			int n;
			while(true) {
				n = sc.nextInt();
				if(n==0) break;
				agnes.add(n);
			}

			int a[] = new int[agnes.size()];
			for(int i=0;i<a.length;i++)
				a[i] = agnes.get(i);

			while(true) {
				int first = sc.nextInt();
				if(first==0) break;
				else {
					ArrayList<Integer> cur = new ArrayList<>();
					cur.add(first);
					while(true) {
						int next = sc.nextInt();
						if(next==0) break;
						cur.add(next);
					}
					int cr[] = new int[cur.size()];
					for(int i=0;i<cr.length;i++)
						cr[i] = cur.get(i);

					racers.add(cr);
				}
			}

			int ans = 0;

			for(int[] racer : racers) {
				int cans = LCS(racer,a);
				ans = Math.max(ans,cans);
			}


			pw.println(ans);

		}


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