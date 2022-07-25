import java.io.*;
import java.util.*;

public class Trace {

	static int n,s;
	static int memo[][];
	static int values[];
	static int weights[];
    static ArrayList<Integer> traced;

	static int dp(int idx, int remw) {
		if(idx==n) return 0;
		if(memo[idx][remw]!=-1) return memo[idx][remw];
		
		int result = 0;
		
		int take = 0;
		if(remw-weights[idx]>=0) {
			take = dp(idx+1,remw-weights[idx]) + values[idx];
		}
		
		int leave = dp(idx+1,remw);
		
		result = Math.max(take, leave);
		memo[idx][remw] = result;
		return result;
	}
	
	
	static void trace(int idx, int remw) {
		if(idx==n) return;
		
		int result = 0;
		
		int take = -1;
		if(remw-weights[idx]>=0) {
			take = dp(idx+1,remw-weights[idx]) + values[idx];
		}
		
		int leave = dp(idx+1,remw);
		
		result = Math.max(take, leave);
		
		if(result==take) {
			traced.add(idx);
			trace(idx+1,remw-weights[idx]);
			
		}
		else {
			//not adding the index
			trace(idx+1,remw);
		}
		
	}

/* Test Input

typical knapsack input of:
numberofitems sizeofsack
valuei weighti

4 12
100 10
70 4
50 6
10 12

*/

    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(System.out);
        n = sc.nextInt();
        s = sc.nextInt();

        memo = new int[n+1][s+1];

        values = new int[n+1];
        weights = new int[n+1];
        traced = new ArrayList<>();

        for(int e[] : memo)
            Arrays.fill(e, -1);
        
        for(int i=0;i<n;i++) {
            values[i] = sc.nextInt();
            weights[i] = sc.nextInt();
        }

        dp(0,s);
        pw.println(memo[0][s]);
        
        trace(0,s);
        for(Integer x : traced) 
        	pw.println(x);

        pw.close();
    }


}