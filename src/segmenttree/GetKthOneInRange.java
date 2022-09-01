import java.util.*;
import java.io.*;

public class GetKthOneInRange {
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
		pw.println(st.getKthOneInRangeFromLtoR(1, 2, 3));
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

		public int getKthOne(int k) { // the value in the input arrray is either 0 or 1
			return getKthOne(1, 1, N, k);
		}

		public int getKthOne(int node, int start, int end, int k) {
			if (start == end)
				return start;
			int mid = start + end >> 1;
			int leftChild = node << 1, rightChild = leftChild | 1;
			if (k <= sg[leftChild]) {
				return getKthOne(leftChild, start, mid, k);
			} else {
				k -= sg[leftChild];
				return getKthOne(rightChild, mid + 1, end, k);
			}
		}

		public int getKthOneInRangeFromLtoR(int k, int l, int r) { // the value in the input arrray is either 0 or 1
			// will be the same as get kth one in the whole array if we count the number of
			// ones before the given range .i.e
			// from [0,L-1] ,note that we don't handle the case that when k is greater than the number of ones in the range
			// which can be handled easily by checking if the return value in the range [L,R]
			if (l > 1)
				k += query(1, l - 1);
			return getKthOne(1, 1, N, k);
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
