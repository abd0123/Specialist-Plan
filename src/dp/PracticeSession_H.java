

import java.util.*;
import java.io.*;

public class PracticeSession_H {// changing string
	static Scanner sc = new Scanner(System.in);
	static PrintWriter pw = new PrintWriter(System.out);

	static int n, m;
	static char[] s;
	static char[] t;
	static long[][] memo;

	public static void main(String[] args) throws IOException {
		s = sc.next().toCharArray();
		t = sc.next().toCharArray();
		n = s.length;
		m = t.length;

		memo = new long[n + 1][m + 1];

		for (long[] e : memo)
			Arrays.fill(e, -1);

		pw.println(sol(0, 0));
		trace(0, 0);
		for (String e : arr) {
			pw.println(e);
		}
		pw.close();
	}

	static long sol(int i, int j) {
		if (i == n & j == m)
			return 0;

		if (memo[i][j] != -1)
			return memo[i][j];

		if (i < n && j < m && s[i] == t[j]) {
			return memo[i][j] = sol(i + 1, j + 1);
		}
		long replace = Integer.MAX_VALUE;
		long insert = Integer.MAX_VALUE;
		long delete = Integer.MAX_VALUE;

		if (i < n && j < m) {
			replace = 1 + sol(i + 1, j + 1);
		}

		if (i < n) {
			delete = 1 + sol(i + 1, j);
		}

		if (j < m) {
			insert = 1 + sol(i, j + 1);
		}

		return memo[i][j] = Math.min(replace, Math.min(insert, delete));
	}

	static ArrayList<String> arr = new ArrayList<String>();

	static void trace(int i, int j) {
		if (i == n & j == m)
			return;

		if (i < n && j < m && s[i] == t[j]) {
			trace(i + 1, j + 1);
			return;
		}
		long replace = Integer.MAX_VALUE;
		long insert = Integer.MAX_VALUE;
		long delete = Integer.MAX_VALUE;

		if (i < n && j < m) {
			replace = 1 + sol(i + 1, j + 1);
		}

		if (i < n) {
			delete = 1 + sol(i + 1, j);
		}

		if (j < m) {
			insert = 1 + sol(i, j + 1);
		}
		if (memo[i][j] == insert) {
			arr.add("INSERT " + (j + 1) + " " + t[j]);
			trace(i, j + 1);
		} else if (memo[i][j] == replace) {
			arr.add("REPLACE " + (j + 1) + " " + t[j]);
			trace(i + 1, j + 1);
		} else {
			arr.add("DELETE " + (j + 1));
			trace(i + 1, j);
		}

		return;
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
