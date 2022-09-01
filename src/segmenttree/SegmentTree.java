import java.util.*;
import java.io.*;

public class SegmentTree {
	static PrintWriter pw;
	static Scanner sc;

	public static void main(String[] args) throws Exception {
		pw = new PrintWriter(System.out);
		sc = new Scanner(System.in);
		int n = sc.nextInt();
		int N = 1;
		while (n > N)
			N <<= 1;
		int[] arr = new int[N + 1];
		for (int i = 1; i <= n; i++) {
			arr[i] = sc.nextInt();
		}
		SegmentTree st = new SegmentTree(arr);
		pw.println(st.query(1, n));
		st.updatePoint(2, -2);
		pw.println(st.query(1, n));
		pw.flush();
	}

	static class SegmentTree {
		long[] sg;
		int N;

		public SegmentTree(int N) { // there is no input array , initially empty
			this.N = N;
			sg = new long[N << 1];
		}

		public SegmentTree(int[] arr) { // the input array is power of 2 and 1-indexed
			this.N = arr.length - 1;
			sg = new long[N << 1];
			build(1, 1, N, arr);
		}

		public void build(int node, int start, int end, int[] array) {
			if (start == end) {
				sg[node] = array[start];
			} else {
				int leftChild = node << 1, rightChild = leftChild | 1;
				int mid = start + end >> 1;
				build(leftChild, start, mid, array);
				build(rightChild, mid + 1, end, array);
				sg[node] = sg[leftChild] + sg[rightChild];
			}
		}

		public void updatePoint(int idx, int val) { // update = set
			idx += N - 1;
			sg[idx] = val;
			while (idx > 1) {
				idx >>= 1;
				sg[idx] = sg[idx << 1] + sg[(idx << 1) | 1];
			}
		}

		public long query(int l, int r) {
			return query(1, 1, N, l, r);
		}

		public long query(int node, int start, int end, int l, int r) {
			if (start >= l && end <= r)
				return sg[node];
			if (start > r || end < l)
				return 0; // value does not affect the answer
			int mid = start + end >> 1;
			int leftChild = node << 1, rightChild = leftChild | 1;
			return query(leftChild, start, mid, l, r) + query(rightChild, mid + 1, end, l, r);
		}
	}

}
