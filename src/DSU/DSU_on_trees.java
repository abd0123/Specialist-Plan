import java.util.*;
import java.io.*;

public class DSU_on_trees {
	static PrintWriter pw;
	static Scanner sc;
	static Random rn = new Random();
	static ArrayList<Integer>[] graph;
	static int[] colors, ans;

//	static HashSet<Integer> dfs(int node, int par) {
//		HashSet<Integer> cur_set = new HashSet<>();
//		cur_set.add(colors[node]);
//		for (int nxt : graph[node]) {
//			if (nxt == par) {
//				continue;
//			}
//			HashSet<Integer> cur_child_set = dfs(nxt, node);
//			if (cur_child_set.size() > cur_set.size()) {
//				HashSet<Integer> temp = cur_set;
//				cur_set = cur_child_set;
//				cur_child_set = temp;
//			}
//			for (int c : cur_child_set) {
//				cur_set.add(c);
//			}
//		}
//		// n*log^2(n) assuming the HashSet.add works in O(log n);
//		// 1, 2, 3, 4, 5, 6, 7
//		// cur_set = h, cur_child_set = k => k <= h => result_set >= 2 * k 2^j <= n => j
//		// = log2(n);
//		// x , y , a , b , c
//		// u , v, y
//		ans[node] = cur_set.size();
//		return cur_set;
//	}

	static int cnt_color[];
	static int cnt_distinct;
	static int[] big_child, subtree_size;

	static void pre_big_child(int node, int par) {
		big_child[node] = -1;
		subtree_size[node] = 1;
		for (int nxt : graph[node]) {
			if (nxt == par) {
				continue;
			}
			pre_big_child(nxt, node);
			subtree_size[node] += subtree_size[nxt];
			if (big_child[node] == -1 || subtree_size[big_child[node]] < subtree_size[nxt]) {
				big_child[node] = nxt;
			}
		}

	}

	static void add_color(int node) {
		if (cnt_color[colors[node]]++ == 0) {
			cnt_distinct++;
		}
	}

	static void remove_color(int node) {
		if (--cnt_color[colors[node]] == 0) {
			cnt_distinct--;
		}
	}

	static void add_or_remove_subtree(int node, int par, boolean add) {
		if (add) {
			add_color(node);
		} else
			remove_color(node);
		for (int nxt : graph[node]) {
			if (nxt == par) {
				continue;
			}
			add_or_remove_subtree(nxt, node, add);
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
		add_color(node);
		for (int nxt : graph[node]) {
			if (nxt == par || nxt == big_child[node]) {
				continue;
			}
			add_or_remove_subtree(nxt, node, true);
		}
		ans[node] = cnt_distinct;
		if(!keep) {
			add_or_remove_subtree(node, par, false);
		}
	}

	public static void main(String[] args) throws Exception {
		pw = new PrintWriter(System.out);
		sc = new Scanner(System.in);
		int n = sc.nextInt();
		colors = new int[n];
		for (int i = 0; i < n; i++) {
			colors[i] = sc.nextInt();
		}
		ans = new int[n];
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
		cnt_color = new int[n];
		pre_big_child(0, -1);
		solve(0, -1, true);
		for (int x : ans) {
			pw.print(x + " ");
		}
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