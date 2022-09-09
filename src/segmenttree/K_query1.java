import java.util.*;
import java.io.*;

public class K_query1 {
	static Scanner sc = new Scanner(System.in);
	static PrintWriter pw = new PrintWriter(System.out);

	static class Element implements Comparable{
		int val;
		int l, r;
		int idx;
		boolean isQuery;

		public Element(int val, int idx) {
			this.val = val;
			this.idx = idx;
			this.isQuery = false;
		}

		public Element(int val, int l, int r, int idx) {
			this.val = val;
			this.idx = idx;
			this.l = l;
			this.r = r;
			this.isQuery = true;
		}

		@Override
		public int compareTo(Object e) {
			Element o = (Element)e;
			return this.val - o.val;
		}
		
		@Override
		public String toString() {
			return val + "";
		}
	}

	public static void sort(Element[] a) {
		mergesort(a, 0, a.length - 1);
	}
	
	static void mergesort(Element[] arr, int b, int e) {
		if (b < e) {
			int m = b + (e - b) / 2;
			mergesort(arr, b, m);
			mergesort(arr, m + 1, e);
			merge(arr, b, m, e);
		}
		return;

	}
	static void merge(Element[] arr, int b, int m, int e) {
		int len1 = m - b + 1, len2 = e - m;
		Element[] l = new Element[len1];
		Element[] r = new Element[len2];
		for (int i = 0; i < len1; i++)
			l[i] = arr[b + i];
		for (int i = 0; i < len2; i++)
			r[i] = arr[m + 1 + i];
		int i = 0, j = 0, k = b;
		while (i < len1 && j < len2) {
			if (l[i].compareTo(r[j])<0)
				arr[k++] = l[i++];
			else if(l[i].compareTo(r[j]) == 0) {
				if(l[i].isQuery) {
					arr[k++] = r[j++];
				}else {
					arr[k++] = l[i++];
				}
			}
			else
				arr[k++] = r[j++];
		}
		while (i < len1)
			arr[k++] = l[i++];
		while (j < len2)
			arr[k++] = r[j++];
		return;
	}
	
	public static void main(String[] args) throws Exception {
		int n = sc.nextInt();
		int[] a = sc.nextArrint(n);
		int q = sc.nextInt();
		Element[] all = new Element[n + q];
		for (int i = 0; i < a.length; i++) {
			all[i] = new Element(a[i], i);
		}
		for (int i = a.length; i < all.length; i++) {
			int l = sc.nextInt();
			int r = sc.nextInt();
			int val = sc.nextInt();
			all[i] = new Element(val, l, r, i);
		}
		sort(all);
		int[] ans = new int[q];
		STree t = new STree(n);
		for (int i = all.length - 1; i >= 0; i--) {
			if (all[i].isQuery) {
				ans[all[i].idx - n] = t.query(all[i].l, all[i].r);
			} else {
				t.updatePoint(all[i].idx + 1);
			}
		}
		for(int e:ans)pw.println(e);
		pw.close();
	}

	static class STree {
		int n;
		int[] tree;

		public STree(int len) {
			n = 1;
			while (n < len) {
				n <<= 1;
			}
			tree = new int[n << 1 | 1];
		}

		public void updatePoint(int idx) {
			int node = idx + n - 1;
			while (node > 0) {
				tree[node]++;
				node >>= 1;
			}
		}

		public int query(int l, int r) {
			return query(1, n, 1, l, r);
		}

		public int query(int s, int e, int node, int l, int r) {
			if (l > e || r < s)
				return 0;
			if (s >= l && e <= r) {
				return tree[node];
			}
			int mid = s + e >> 1;
			return query(s, mid, node << 1, l, r) + query(mid + 1, e, node << 1 | 1, l, r);
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