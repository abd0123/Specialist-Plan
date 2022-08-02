import java.io.*;
import java.util.*;

public class I {
	static int n,m;
	static int nums[];
	static int pos[];
	static int pc[][][];
	static int memo[][][];
	
	//Here the states of the dp represent
	//[pl] - I am standing at pile number pl
	//[pnc] - I have already placed pnc pancakes
	//[pmx] - the maximum number of pancakes I can place
	//		  in my current pile is pmx
	static int dp(int pl, int pnc, int pmx) {
		//If we have placed m pancakes then there are no more moves to be made
		if(pnc==m)
			return 0;
		//If we are here then the number of pancakes placed is less than m
		//however i cannot place any more pancakes in any of the future piles
		//as the sequence has to remain non-increasing, therefore this is an
		//invalid state
		if(pmx==0)
			return (int) 1e9;
		//If we are here then once again I have reached the end of the array without using
		//all my pancakes which makes this another invalid state
		if(pl==n)
			return (int) 1e9;
		
		if(memo[pl][pnc][pmx]!= -1)
			return memo[pl][pnc][pmx];
		
		
		//Setting my answer to a very high value to minimise later
		int z = (int) 1e9;
		
		//Here I am looping over all of the possible number of pancakes that I
		//can place in my pile, while making sure I use the pc array we calculated
		//earlier to know how much it would cost to move those pancakes there and
		//minimising the overall answer
		for(int i=pmx;i>0;i--){
			if(pnc+i>m) continue;
			int u = dp(pl+1,pnc+i,i) + pc[pl][pnc][i-1];
			z = Math.min(z,u);
		}
		
		memo[pl][pnc][pmx] = z;
		return z;		
	}
	
	//If you have any further inquiries please ask them on discord.
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		n = sc.nextInt();
		m = sc.nextInt();
		nums = new int[n];
		for(int i=0;i<n;i++)
			nums[i] = sc.nextInt();
		
		//Here we are determining the column position of the ith
		//pancake, this will help us determine the distance of the pancakes
		//when calculating the pc array
		pos = new int[m];
		int cnt = 0;
		for(int i=0;i<n;i++)
			for(int j=0;j<nums[i];j++)
				pos[cnt++] = i;
	
		
		//Here we are making a precalculation array which will help us calculate the dp
		//in time, each index in the array represents
		//[n+1]-i if I am standing at the ith column
		//[m+1]-j if I have already placed the leftmost j pancakes
		//[m+1]-k if I want to add k pancakes to the column I am standing at 
		pc = new int[n+1][m+1][m+1];	
		for(int i=0;i<n;i++){
			for(int j=0;j<m;j++){
				pc[i][j][0] = Math.abs(i-pos[j]);
				for(int k=1;j+k<m;k++){
					pc[i][j][k] = pc[i][j][k-1] + Math.abs(i-pos[j+k]);	
				}
			}
		}
		
		//Here we are just initialising the whole memo to -1 as usual
		memo = new int[n+1][m+1][m+1];
		for(int[][] a : memo)
			for(int[] b : a)
				Arrays.fill(b, -1);
	
		
		//Calling the dp function to calculate the value.
		pw.println(dp(0,0,m));
		
		pw.close();
	}
	
	static class Scanner {
		StringTokenizer st;
		BufferedReader br;
		public Scanner(InputStream s) {
			br = new BufferedReader(new InputStreamReader(s));
		}
		public Scanner(String file) throws FileNotFoundException {
			br = new BufferedReader(new FileReader(file));
		}
		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}
		public String nextLine() throws IOException {
			return br.readLine();
		}
		public double nextDouble() throws IOException {
			return Double.parseDouble(next());
		}
		public boolean ready() throws IOException {
			return br.ready();
		}
	}
}
