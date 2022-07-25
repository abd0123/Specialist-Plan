import java.util.*;
import java.io.*;

public class Bitmask {
	static int n,s;
	static int costs[][];
	static int memo[][];
	
	static int dp(int curr, int mask) {
		if(Integer.bitCount(mask)==n) return costs[curr][s]; // Returning to starting city
		if(memo[curr][mask]!=-1) return memo[curr][mask];
		
		int result = (int) 1e9;
		
		int bitmask = mask;
		for(int i=0;i<n;i++) {
			if((bitmask&(1<<i))==0) {
				int answer = dp(i,mask|(1<<i)) + costs[curr][i]; // Adding cost of going to this city
				result = Math.min(result, answer);
			}
		}
		
		memo[curr][mask] = result;
		return result;
	}
	
	
/*Test Input
input format:
numberofcities startingcity(1-indexed)
city1(1-indexed) city2(1-indexed) costofpath
... 
 
5 1
1 2 8
1 3 50
1 4 8
1 5 1
2 3 1
2 4 48
2 5 55
3 4 1
3 5 84
4 5 1

*/
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		n = sc.nextInt();
		s = sc.nextInt(); s--; // decreasing to make s 0 indexed
		costs = new int[n+1][n+1];
		int edgeCount = (n*(n-1))/2;
		
		for(int i=0;i<edgeCount;i++) {
			int u = sc.nextInt();
			int v = sc.nextInt();
			int c = sc.nextInt();
			u--;v--; //decreasing to make them 0 indexed
			costs[u][v] = c;
			costs[v][u] = c;
		}
		
		memo = new int[n+1][1<<n];
		
		for(int e[] : memo)
			Arrays.fill(e, -1);
		
		int ans = dp(s,1<<s);
		pw.println(ans);
		
		pw.close();
	}
	
}
