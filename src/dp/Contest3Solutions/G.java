import java.io.*;
import java.util.*;

public class G {

	static int LIS(Triplet[] arr) {
		int n = arr.length;
		int memo[][] = new int[n+2][n+2];

		for(int i=n-1;i>0;i--) {
			for(int j=n-1;j>-1;j--) {

				int take = -1;
				int leave = memo[i+1][j];

				if(j==0||(arr[i-1].first>arr[j-1].first&&arr[i-1].second>arr[j-1].second))
					take = arr[i-1].third + memo[i+1][i];

				memo[i][j] = Math.max(take, leave);
			}
		}

		return memo[1][0];
	}

	static class Triplet implements Comparable {
		int first;
		int second;
		int third;

		public Triplet(int first, int second, int third) {
			this.first = first;
			this.second = second;
			this.third = third;
		}

		public int compareTo(Object o) {
			Triplet other = (Triplet) o;

			if(this.first<other.first) return -1;
			else if(this.first>other.first) return 1;
			else if(this.second<other.second) return -1;
			else if(this.second>other.second) return 1;
			else if(this.third<other.third) return -1;
			else if(this.third>other.third) return 1;

			return 0;
		}

	}

	//The solution for this problem relies on us applying the LIS algorithm in a slightly different way.
	//We are told we can use any rotation of the objects we have any number of times however since each
	//box that is placed on top of another box must be strictly smaller on both bottom facing dimensions, to simulate
	//the ability to use all 6 faces of the object we can simply place all 6 possible dimensions in the
	//array of objects we apply LIS over. Then we perform the LIS on the array(the array size at this point
	//is 6*n), instead of adding +1 for each possible object we instead add that objects height(since this
	//is what we are trying to maximise). we then continue the LIS as usual on the triplets and the final
	//result will be the answer we require

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int cs = 1;
		while(true) {
			int n = sc.nextInt();
			if(n==0) break;

			Triplet arr[] = new Triplet[6*n];

			for(int i=0;i<n;i++) {
				int a = sc.nextInt();
				int b = sc.nextInt();
				int c = sc.nextInt();

				arr[(i*6)+0] = new Triplet(a,b,c);
				arr[(i*6)+1] = new Triplet(a,c,b);
				arr[(i*6)+2] = new Triplet(b,a,c);
				arr[(i*6)+3] = new Triplet(b,c,a);
				arr[(i*6)+4] = new Triplet(c,a,b);
				arr[(i*6)+5] = new Triplet(c,b,a);
			}

			Arrays.sort(arr);

			int ans = LIS(arr);

			pw.println("Case " + cs + ": maximum height = " + ans);
			cs++;
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