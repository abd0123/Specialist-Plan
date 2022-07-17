package dp;

import java.util.Arrays;

public class Knapsack {
	static int n;
	static int[][] memo;
	static int[] values;
	static int[] weights;

	public static void main(String[] args) {
		// input
		n = 4;
		int w = 13;

		values = new int[] { 100, 70, 50, 10 };
		weights = new int[] { 10, 4, 6, 12 };

		memo = new int[4][13+1];// +1 to avoid index out of bound error
		
		// fill memo;
		for (int[] e : memo)
			Arrays.fill(e, -1);

		// output
		System.out.println(bruteForce(0, w)); // complexity: O(2^(n))
		System.out.println(dp(0, w)); // complexity: O(2*(n*w))
		
		/* Bottom-Up*/
		// create the memo
		int[][] bottomUp = new int[4+1][13+1];
		
		// initialize the base case (no need here because array already initialized with zeroes)
		
		/*for(int i = 0; i < 14; i++){
		 *	bottomUp[4][i] = 0;
		 *}
		 */
		
		for(int idx = 3; idx > -1; idx--) {
			for (int remw = 0; remw < 14; remw++) {
				// compute results
				int take = 0;
				int leave = 0;
				int ans = 0;

				// take
				if (remw >= weights[idx]) {
					take = bottomUp[idx+1][remw - weights[idx]] + values[idx];
				}

				// leave
				leave = bottomUp[idx+1][remw];
				
				ans = Math.max(take, leave);
				bottomUp[idx][remw] = ans;
			}
		}
		
		System.out.println(bottomUp[0][w]);
		
	}

	public static int bruteForce(int idx, int remw) {
		if (idx == n)
			return 0;

		// compute results
		int take = 0;
		int leave = 0;
		int ans = 0;

		// take
		if (remw >= weights[idx]) {
			take = values[idx] + bruteForce(idx + 1, remw - weights[idx]);
		}

		// leave
		leave = bruteForce(idx + 1, remw);
		
		ans = Math.max(take, leave);
		return ans;
	}

	public static int dp(int idx, int remw) {
		if (idx == n)
			return 0;
		
		// Added line(already computed)
		if (memo[idx][remw] != -1)
			return memo[idx][remw];

		// compute results
		int take = 0;
		int leave = 0;
		int ans = 0;

		// take
		if (remw >= weights[idx]) {
			take = values[idx] + dp(idx + 1, remw - weights[idx]);
		}

		// leave
		leave = dp(idx + 1, remw);
		
		ans = Math.max(take, leave);

		// Added line(record results)
		memo[idx][remw] = ans;
		return ans;
	}

}
