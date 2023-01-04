import java.util.*;
import java.io.*;

public class cnt_paths_of_length_between_k1_and_k2 {
	static PrintWriter pw;
	static Scanner sc;
	static Random rn = new Random();
	static ArrayList<Integer>[] graph;
	static int level[];
	static FenwickTree cnt_level;
	static int k;
	static long tot;
	static int[] big_child, subtree_size;

	static void pre_big_child(int node, int par) {
		big_child[node] = -1;
		subtree_size[node] = 1;
		for (int nxt : graph[node]) {
			if (nxt == par) {
				continue;
			}
			level[nxt] = level[node] + 1;
			pre_big_child(nxt, node);
			subtree_size[node] += subtree_size[nxt];
			if (big_child[node] == -1 || subtree_size[big_child[node]] < subtree_size[nxt]) {
				big_child[node] = nxt;
			}
		}

	}

	static void add_level(int node) {
		cnt_level.point_update(level[node], 1);
	}

	static void remove_level(int node) {
		cnt_level.point_update(level[node], -1);
	}

	static void add_or_remove_subtree(int node, int par, boolean add) {
		if (add) {
			add_level(node);
		} else
			remove_level(node);
		for (int nxt : graph[node]) {
			if (nxt == par) {
				continue;
			}
			add_or_remove_subtree(nxt, node, add);
		}
	}

	static void solve_for_subtree(int node, int par, int lca_level) {
		int my_pair_level = k - level[node] + 2 * lca_level;
		if (my_pair_level >= 0) {
			tot += cnt_level.rsq(my_pair_level, cnt_level.n);
		}
		for (int nxt : graph[node]) {
			if (nxt == par) {
				continue;
			}
			solve_for_subtree(nxt, node, lca_level);
		}
	}

	static void solve(int node, int par, boolean keep) {
		for (int nxt : graph[node]) {
			if (nxt == par || nxt == big_child[node]) {
				continue;
			}
			solve(nxt, node, false);
		}
		if (big_child[node] != -1)
			solve(big_child[node], node, true);
		for (int nxt : graph[node]) {
			if (nxt == par || nxt == big_child[node]) {
				continue;
			}
			solve_for_subtree(nxt, node, level[node]);
			add_or_remove_subtree(nxt, node, true);
		}
		add_level(node);
		tot += cnt_level.rsq(level[node] + k, cnt_level.n);
		if (!keep) {
			add_or_remove_subtree(node, par, false);
		}
	}

	public static void main(String[] args) throws Exception {
		pw = new PrintWriter(System.out);
		sc = new Scanner(System.in);
		int n = sc.nextInt();
		int k1 = sc.nextInt(), k2 = sc.nextInt();
		graph = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			graph[i] = new ArrayList<>();
		}
		for (int i = 0; i < n - 1; i++) {
			int a = sc.nextInt() - 1, b = sc.nextInt() - 1;
			graph[a].add(b);
			graph[b].add(a);
		}
		subtree_size = new int[n];
		big_child = new int[n];
		cnt_level = new FenwickTree(4 * n);
		level = new int[n];
		pre_big_child(0, -1);
		k = k1;
		solve(0, -1, false);
		long ans = tot;
		k = k2 + 1;
		tot = 0;
		solve(0, -1, false);
		ans -= tot;
		pw.println(ans);
		pw.flush();
	}

	static class FenwickTree { // one-based DS
		int n;
		long[] ft;

		FenwickTree(int size) {
			n = size;
			ft = new long[n + 1];
		}

		long rsq(int b) // O(log n)
		{
			b++;
			long sum = 0;
			while (b > 0) {
				sum += ft[b];
				b -= b & -b;
			} // min?
			return sum;
		}

		long rsq(int a, int b) {
			b = Math.min(b, n - 1);
			return rsq(b) - rsq(a - 1);
		}

		void point_update(int k, long val) // O(log n), update = increment
		{
			k++;
			while (k <= n) {
				ft[k] += val;
				k += k & -k;
			}
		}
	}

	static class Scanner {
		StringTokenizer st;
		BufferedReader br;

		Scanner(InputStream in) {
			br = new BufferedReader(new InputStreamReader(in));
		}

		String next() throws Exception {
			while (st == null || !st.hasMoreTokens()) {
				st = new StringTokenizer(br.readLine());
			}
			return st.nextToken();
		}

		int nextInt() throws Exception {
			return Integer.parseInt(next());
		}

		long nextLong() throws Exception {
			return Long.parseLong(next());
		}

		double nextDouble() throws Exception {
			return Double.parseDouble(next());
		}
	}

}