import java.util.*;
import java.io.*;

public class Digit {
	static int n,k;
	static int vals[];
	static int memo[][]; 
	
	static int dp(int idx, int mod) {
		if(idx==n) {
			if(mod==0) return 1;
			return 0;
		}
		if(memo[idx][mod]!=-1) return memo[idx][mod];
		
		int newmod = ((mod%k)+(vals[idx]%k))%k; // Calculating mod + vals[idx] % k
		int take = dp(idx+1,newmod);
		int leave = dp(idx+1,mod);
		int result = take+leave;
		
		memo[idx][mod] = result;
		return result;
	}
	
	/* Test Input

n k 
a0 a1 a2.... an-1

where 

n is the number of elements in the array
k is the number we want the sum of the subsets to be divisible by

4 4
1 3 4 8
	*/
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		n = sc.nextInt();
		k = sc.nextInt();
		vals = new int[n];
		memo = new int[n+1][k+1];
		
		for(int i=0;i<n;i++)
			vals[i] = sc.nextInt();
		
		for(int[] e : memo)
			Arrays.fill(e, -1);
		
		int ans = dp(0,0);
		pw.println(ans);
		
		pw.close();
	}

}
