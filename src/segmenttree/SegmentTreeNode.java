import java.util.*;
import java.io.*;

public class SegmentTreeNode {
	static PrintWriter pw;
	static Scanner sc;
	public static void main(String[] args) throws Exception {
		pw = new PrintWriter(System.out);
		sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = sc.nextInt();
		}
		N = 1;
		while (n > N)
			N <<= 1;
		SegmentTreeNode root = build(1, N, arr);
		pw.println(root.query(1, 5));
		root.updatePoint(1, 3);
		pw.println(root.query(1, 5));
		pw.flush();
	}

	static int N;

	public static SegmentTreeNode build(int l, int r, int[] arr) {
		if (l == r) {
			if (l > arr.length)
				return new SegmentTreeNode(0);
			return new SegmentTreeNode(arr[l - 1]);
		} else {
			int mid = (l + r) >> 1;
			SegmentTreeNode res = new SegmentTreeNode(build(l, mid, arr), build(mid + 1, r, arr));
			return res;
		}
	}

	static class SegmentTreeNode {
		SegmentTreeNode left, right;
		int val;

		public SegmentTreeNode(int val) {
			this.val = val;
		}

		public SegmentTreeNode(SegmentTreeNode left, SegmentTreeNode right) {
			this.left = left;
			this.right = right;
			this.val = left.val + right.val;
		}

		public int query(int l, int r) {
			return query(1, N, l, r);
		}

		public int query(int s, int e, int l, int r) {
			if (s >= l && e <= r) {
				return this.val;
			}
			if (e < l || s > r)
				return 0;
			int mid = (s + e) >> 1;
			return this.left.query(s, mid, l, r) + this.right.query(mid + 1, e, l, r);
		}

		public SegmentTreeNode updatePoint(int idx, int val) {
			return updatePoint(1, N, idx, val);
		}

		public SegmentTreeNode updatePoint(int s, int e, int idx, int value) {
			if (s == e) {
				return new SegmentTreeNode(value);
			} else {
				int mid = (s + e) >> 1;
				if (idx <= mid) {
					this.left = left.updatePoint(s, mid, idx, value);
					this.val = left.val + right.val;
					return this;
				} else {
					this.right = right.updatePoint(mid + 1, e, idx, value);
					this.val = left.val + right.val;
					return this;
				}
			}
		}
	}
}
