import java.io.*;
import java.util.*;

public class E {
	static int m,d;
	static String l,r;
	final static int md = (int) 1e9+7;
	static int memo[][][][];
	static int pc[];
	
	//These are just helper functions to help us
	//Add, Subtract and Multiply using modular arithmetic. 
	static int madd(int a, int b, int mod) {
		return (((a%mod) + (b%mod)) % mod);
	}
	
	static int msub(int a, int b, int mod){
		return (((((a+mod)%mod)-((b+mod)%mod))+mod)%mod);
	}
	
	static int mmul(int a, int b, int mod) {
		return (((a%mod) * (b%mod)) % mod);
	}
	
	//Here is our memo function, since the original idea of doing each string
	//by itself and then subtracting both answers kept TLEing in java, here is another
	//approach where we stay in the range between L and R during the DP.
	//the states are: 
	//[low] a 0/1 value which tells us whether a previous value was greater than l or not
	//		we use this to know if we have to start our range from l or 1.
	//[high] a 0/1 value which tells us whether a previous value was less than the values in r
	//		or not, we use this to know if our upper bound is r or 9.
	//[idx] the current index we are at while constructing the number in the dp.
	//[mod] the current mod of the previous values we have taken mod m, this value needs to
	//be 0 in the end so that we can make sure that our final number is divisible by m
	
	static int dp(int low, int high, int idx, int mod) {
		//Our base case, here we check that the previous transitions have led to a 
		//number which is divisible by m by checking if the mod state is equal to 0
		if(idx==l.length()) {
			if(mod==0) return 1;
			return 0;
		}
		
		if(memo[low][high][idx][mod]!=-1) return memo[low][high][idx][mod];
		
		//here we are determining the ranges of our possible dp given the states
		//of our current function call.
		int start = low==0?l.charAt(idx)-'0':0;
		int end = high==0? r.charAt(idx)-'0':9;
		int ans = 0;
		
		//Here we are solving the odd and even positions differently to ensure that the final number
		//is D-Magic
		if(idx%2==0) { 
			for(int i=start;i<=end;i++) {
                // skipping d as number cannot be d-magic with d in an odd position
				if(i==d) continue; 
				
				//Here we are calculating the new low state, if a number I have taken is greater
				//than the current value of l then all future values no matter what they are will be
				//greater, therefore i can start at 0 in the future and be sure that l<number
				int nlow = low==1?1:i==l.charAt(idx)-'0'?0:1;
				
				//Here we are doing the opposite for high, if we take a number that is lower than
				//the current index of r then no matter what numbers we take in the future the resulting
				//value will always be less than r, thus satisfying our 2nd condition of number<r;
				int nhigh = high==1?1:i==r.charAt(idx)-'0'?0:1;
				
				//Here we are calculating our new mod function after adding our current chosen digit in
				//the loop which is i, multiplies by a precalculated array which contains 10^0, 10^1, etc..
				//taken mod m. This is just to speed things up and make sure that the final mod value is
				//correct.
				int nmod = 	madd(mod,mmul(pc[l.length()-1-idx],i,m),m);
				
				//Here we are adding this calculated answer after having figured out the new states
				//all mod 1e9+7, as requested by the question.
				int calc = dp(nlow,nhigh,idx+1,nmod);
				ans = madd(ans,calc,md);
			}
			
		}
		else {
			//Even positions have to have a value of D, so we are just checking if d is a viable
			//number given our possible ranges and calculating its answer in the same way as odd positions
			if(start<=d&&d<=end) {
				int nlow = low==1?1:d==l.charAt(idx)-'0'?0:1;
				int nhigh = high==1?1:d==r.charAt(idx)-'0'?0:1;
				int nmod = 	madd(mod,mmul(pc[l.length()-1-idx],d,m),m);
				int calc = dp(nlow,nhigh,idx+1,nmod);
				ans = madd(ans,calc,md);
			}
		}
		
		memo[low][high][idx][mod] = ans;
		return ans;
	}
	
	//If you have any further inquiries please ask them on discord.
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		m = sc.nextInt();
		d = sc.nextInt();
		l = sc.next();
		r = sc.next();
		
		//Here we are just making the precalculated array
		//of 10^i mod m, this will help us later in the dp to properly calculate
		//the sum of the values we are taking in the dp.
		pc = new int[2003]; pc[0] = 1%m;
		for(int i=1;i<2003;i++) 
			pc[i] = (int) mmul(pc[i-1],10,m);
		
		
		memo = new int[2][2][l.length()+1][m+1];
		
		//Initialising the memo to -1;
		for(int a[][][] : memo)
			for(int b[][] : a) 
				for(int c[] : b) 
					Arrays.fill(c, -1);
		
		
		//Printing the answer from the DP
		pw.println(dp(0,0,0,0));
		
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