

import java.util.*;
import java.io.*;

public class PracticeSession_G {// Tetrahedron
	static Scanner sc = new Scanner(System.in);
	static PrintWriter pw = new PrintWriter(System.out);

	static long[][] memo;
	static long mod = (int) 1e9 + 7;

	public static void main(String[] args) throws IOException {
		int n = sc.nextInt();
		memo = new long[2][4];// 0 -> A 1 -> B 2 -> C 3 -> D

		// left == 0;
		for (int i = 0; i < 4; i++) {
			memo[0][i] = i == 3 ? 1 : 0;
		}

		for (int i = 1; i <= n; i++) {
			// left == i;
			long sum = 0;
			for (int j = 0; j < 4; j++) {
				sum += memo[0][j];
			}
			for (int j = 0; j < 4; j++) {
				memo[1][j] = sum - memo[0][j];
				memo[1][j] %= mod;
			}

			for (int j = 0; j < 4; j++) {
				memo[0][j] = memo[1][j];
			}
		}
		pw.println(memo[0][3]);
		pw.close();
	}

	static long sol(int curr, int left) {
		if (left == 0) {
			return curr == 3 ? 1 : 0;
		}
		if (memo[curr][left] != -1)
			return memo[curr][left];
		return memo[curr][left] = (sol((curr + 1) % 4, left - 1) + sol((curr + 2) % 4, left - 1)
				+ sol((curr + 3) % 4, left - 1)) % mod;
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