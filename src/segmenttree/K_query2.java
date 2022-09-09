import java.util.*;
import java.io.*;

public class K_query2 {
	static Scanner sc = new Scanner(System.in);
	static PrintWriter pw = new PrintWriter(System.out);

	public static void main(String[] args) throws Exception {
		int n = sc.nextInt();
		int[]a = sc.nextArrint(n);
		int q = sc.nextInt();
		STree t = new STree(a);
		while(q-->0) {
			int i = sc.nextInt();
			int j = sc.nextInt();
			int k = sc.nextInt();
			t.query(i, j);
			LinkedList<int[]> cur = t.l;
			int res = 0;
			while(!cur.isEmpty()) {
				res+=bs(k, cur.removeFirst());
			}
			pw.println(res);
		}
		pw.close();
	}

	static class STree {
		int n;
		int[][] arr;
		int[][] tree;
		
		LinkedList<int[]> l;
		
		public STree(int[] a) {
			n = 1;
			while (n < a.length) {
				n <<= 1;
			}

			arr = new int[n | 1][];
			Arrays.fill(arr, new int[0]);
			for (int i = 0; i < a.length; i++) {
				arr[i + 1] = new int[] { a[i] };
			}

			tree = new int[n << 1 | 1][];
			build(1, n, 1);
		}

		void build(int s, int e, int node) {
			if (s == e) {
				tree[node] = arr[s];
				return;
			}
			int mid = s + e >> 1;
			build(s, mid, node << 1);
			build(mid + 1, e, node << 1 | 1);
			tree[node] = merge(tree[node << 1], tree[node << 1 | 1]);
		}

		static int[] merge(int[] a, int[] b) {
			int[] res = new int[a.length + b.length];
			int i = 0;
			int j = 0;
			int k = 0;
			while (i < a.length && j < b.length) {
				if (a[i] <= b[j]) {
					res[k++] = a[i++];
				} else {
					res[k++] = b[j++];
				}
			}
			while (i < a.length) {
				res[k++] = a[i++];
			}
			while (j < b.length) {
				res[k++] = b[j++];
			}
			return res;
		}
		
		void query(int l, int r) {
			this.l = new LinkedList<int[]>();
			query(1, n, 1, l, r);
		}
		
		void query(int s, int e, int node, int l, int r) {
			if(l>e||r<s)
				return;
			if(s>=l&&e<=r) {
				this.l.add(tree[node]);
				return;
			}
			int mid = s + e >> 1;
			query(s, mid, node << 1, l, r);
			query(mid+1, e, node << 1 | 1, l, r);
		}
		
	}

	static int bs(int val, int[] a) {
		if (val >= a[a.length - 1])
			return 0;
		else if (val < a[0])
			return a.length;

		int i = 0;
		int j = a.length - 1;
		while (i < j) {
			int mid = i + j >> 1;
			if (a[mid] > val && a[mid - 1] <= val) {
				return a.length - mid;
			} else if (a[mid] > val && a[mid - 1] > val) {
				j = mid - 1;
			} else {
				if (i == mid)
					return a.length - j;
				i = mid;

			}
		}
		return -1;
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