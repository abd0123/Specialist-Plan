package dp;

import java.util.Arrays;

public class Fibonacci {

	static long[] memo;

	public static void main(String[] args) {
		/* 
		 * first 11 terms of fibonacci
		 * 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89
		 */
		
		int n = 10; // ans = 89;
		
		// fill memo
		memo = new long[n + 1];
		Arrays.fill(memo, -1);

		System.out.println(fibonacci(n));
	}

	static long fibonacci(int n) {
		if (n == 0)
			return 1;
		if (n == 1)
			return 1;
		
		// already computed
		if (memo[n] != -1)
			return memo[n];
		
		
		long ans = fibonacci(n - 1) + fibonacci(n - 2);
		
		// record res
		memo[n] = ans;
		
		return ans;
	}
}
