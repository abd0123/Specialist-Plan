import java.util.*;
import java.io.*;
// problem link : https://codeforces.com/edu/course/2/lesson/4/2/practice/contest/273278/problem/A
public class MaxSubarraySumSegmentTree {
	static PrintWriter pw;
	static Scanner sc;

	public static void main(String[] args) throws Exception {
		pw = new PrintWriter(System.out);
		sc = new Scanner(System.in);
		int n = sc.nextInt();
		int q = sc.nextInt();
		int N = 1;
		while (n > N)
			N <<= 1;
		int[] arr = new int[N + 1];
		for (int i = 1; i <= n; i++) {
			arr[i] = sc.nextInt();
		}
		SegmentTree st = new SegmentTree(arr);
		pw.println(Math.max(0, st.query(1, N)));
		while (q-- > 0) {
			int idx = sc.nextInt() + 1;
			int val = sc.nextInt();
			st.updatePoint(idx, val);
			pw.println(Math.max(0, st.query(1, N)));
		}
		pw.flush();
	}

	static class Node {
		long pre, suf, total, ans;

		public Node(long val) {
			pre = suf = total = ans = val;
		}
	}

	static Node merge(Node left, Node right) {
		if (left == null)
			return right;
		if (right == null)
			return left;
		Node res = new Node(-1);
		res.total = left.total + right.total;
		res.pre = Math.max(Math.max(left.pre, left.total + right.pre), left.total + right.total);
		res.suf = Math.max(Math.max(right.suf, right.total + left.suf), right.total + left.total);
		res.ans = Math.max(Math.max(Math.max(res.pre, res.suf), left.ans), Math.max(left.suf + right.pre, right.ans));
		return res;
	}

	static class SegmentTree {
		Node[] sg;
		int N;

		public SegmentTree(int N) { // there is no input array , initially empty
			this.N = N;
			sg = new Node[N << 1];
		}

		public SegmentTree(int[] arr) { // the input array is power of 2 and 1-indexed
			this.N = arr.length - 1;
			sg = new Node[N << 1];
			build(1, 1, N, arr);
		}

		public void build(int node, int start, int end, int[] array) { // take care when filling the input array with
																		// default value , the default value should be
																		// -1e9
			if (start == end) {
				sg[node] = new Node(array[start]);
			} else {
				int leftChild = node << 1, rightChild = leftChild | 1;
				int mid = start + end >> 1;
				build(leftChild, start, mid, array);
				build(rightChild, mid + 1, end, array);
				sg[node] = merge(sg[leftChild], sg[rightChild]);
			}
		}

		public void updatePoint(int idx, int val) { // update = set
			idx += N - 1;
			sg[idx] = new Node(val);
			while (idx > 1) {
				idx >>= 1;
				sg[idx] = merge(sg[idx << 1], sg[(idx << 1) | 1]);
			}
		}

		public long query(int l, int r) {
			return query(1, 1, N, l, r).ans;
		}

		public Node query(int node, int start, int end, int l, int r) {
			if (start >= l && end <= r)
				return sg[node];
			if (start > r || end < l)
				return null; // value does not affect the answer
			int mid = start + end >> 1;
			int leftChild = node << 1, rightChild = leftChild | 1;
			return merge(query(leftChild, start, mid, l, r), query(rightChild, mid + 1, end, l, r));
		}
	}

}
