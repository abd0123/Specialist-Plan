import java.util.*;
import java.io.*;

public class cnt_paths_of_length_k {
	static PrintWriter pw;
	static Scanner sc;
	static Random rn = new Random();
	static ArrayList<Integer>[] graph;
	static int cnt_level[], level[];
	static int k;
	static long tot_of_length_k;
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
		cnt_level[level[node]]++;
	}

	static void remove_level(int node) {
		cnt_level[level[node]]--;
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
			tot_of_length_k += cnt_level[my_pair_level];
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
		tot_of_length_k += cnt_level[level[node] + k];
		if (!keep) {
			add_or_remove_subtree(node, par, false);
		}
	}

	public static void main(String[] args) throws Exception {
		pw = new PrintWriter(System.out);
		sc = new Scanner(System.in);
		int n = sc.nextInt();
		k = sc.nextInt();
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
		cnt_level = new int[4 * n];
		level = new int[n];
		pre_big_child(0, -1);
		solve(0, -1, true);
		pw.println(tot_of_length_k);
		pw.flush();
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