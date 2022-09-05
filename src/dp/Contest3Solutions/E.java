import java.io.*;
import java.util.*;

public class E {

	//The solution for this question mostly requires to apply the same idea of kadanes algorithm, but instead of choosing
	//any minimum prefix we have to choose a minimum prefix from a specific range of values before us. To do this we just
	//require any data structure which allows us to easily maintain the minimum, we use this data structure to determine what is
	//the maximal subarray for any given ending point, and we update the viable start prefixes according to the a and b input values.

	//We also use a pair class to maintain duplicates of the same prefix over a given range(as the range of values in the input can
	//be non-positive).

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int n = sc.nextInt();
		int a = sc.nextInt();
		int b = sc.nextInt();
		long arr[] = new long[n];

		for(int i=0;i<n;i++) {
			arr[i] = sc.nextLong();
		}

		int ss = (b - a) + 1;

		TreeSet<Pair> ts = new TreeSet<>();

		long ps[] = new long[n+1];
		for(int i=0;i<n;i++)
			ps[i+1] = ps[i] + arr[i];

		long ans = (long) -1e16;
		int addpointer =  1 - a;
		int erasepointer = 1 - b;

		for(int i=1;i<=n;i++) {
			if(addpointer>-1)
				ts.add(new Pair(ps[addpointer],addpointer));


			if(ts.size()>0) {
				long cans = ps[i] - ts.first().first;
				ans = Math.max(ans, cans);
			}

			if(erasepointer>-1)
				ts.remove(new Pair(ps[erasepointer],erasepointer));

			addpointer++;
			erasepointer++;
		}

		pw.println(ans);

		pw.close();
	}

	static class Pair implements Comparable {
		long first;
		long second;

		Pair(long first, long second){
			this.first = first;
			this.second = second;
		}

		public int compareTo(Object o) {
			Pair p = (Pair) o;
			if(this.first>p.first) return 1;
			if(this.first<p.first) return -1;
			if(this.second<p.second) return 1;
			if(this.second>p.second) return -1;
			return 0;
		}

		public String toString() {
			return "(" + this.first + "," + this.second + ")";
		}

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