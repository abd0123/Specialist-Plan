

import java.util.*;
import java.io.*;

public class PracticeSession_D {
	static Scanner sc = new Scanner(System.in);
	static PrintWriter pw = new PrintWriter(System.out);

	static int n;
	static int[] val;
	static int[] weights;
	static long[][] memo;

	public static void main(String[] args) throws IOException {
		n = sc.nextInt();
		int w = sc.nextInt();

		val = new int[n];
		weights = new int[n];

		int sum = 0;

		for (int i = 0; i < n; i++) {
			weights[i] = sc.nextInt();
			val[i] = sc.nextInt();
			sum += val[i];
		}

		memo = new long[n][sum + 1];
		for (long[] e : memo)
			Arrays.fill(e, -1);

		for (int i = sum; i >= 0; i--) {
			if (sol(0, i) <= w) {
				pw.println(i);
				break;
			}
		}

		pw.close();
	}

	static long sol(int idx, int value) {
		if (idx == n) {
			if (value == 0)
				return 0;
			else
				return (long) 1e15;
		}
		if (memo[idx][value] != -1)
			return memo[idx][value];

		long take = (long) 1e15;
		long leave = (long) 1e15;

		if (val[idx] <= value) {
			take = weights[idx] + sol(idx + 1, value - val[idx]);
		}
		leave = sol(idx + 1, value);

		return memo[idx][value] = Math.min(take, leave);
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

		public int[] nextArrint(int size) throws IOException {
			int[] a = new int[size];
			for (int i = 0; i < a.length; i++) {
				a[i] = sc.nextInt();
			}
			return a;
		}

		public long[] nextArrlong(int size) throws IOException {
			long[] a = new long[size];
			for (int i = 0; i < a.length; i++) {
				a[i] = sc.nextLong();
			}
			return a;
		}

		public int[][] next2dArrint(int rows, int columns) throws IOException {
			int[][] a = new int[rows][columns];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					a[i][j] = sc.nextInt();
				}
			}
			return a;
		}

		public long[][] next2dArrlong(int rows, int columns) throws IOException {
			long[][] a = new long[rows][columns];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					a[i][j] = sc.nextLong();
				}
			}
			return a;
		}
	}
}
