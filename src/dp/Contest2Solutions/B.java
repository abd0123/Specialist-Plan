import java.io.*;
import java.util.*;
public class B {
	
	static int n;
	static int nums[][];
	static int memo[][];
	static int splits[][];
	
	//The main idea of the DP function is to start with
	//a range of matrices that we want to find the optimal answer for,
	//initially this is the entire array as we are considering all the matrices.
	
	//We can only multiply 2 matrices at a time, so in order to calculate the dp answer
	//of the current state what we will do is try to split the current range(given to us
	//in the state) into 2 matrices, this is the equivalent of placing a close and open bracket
	//after every possibility in the array. While looping over the possible divisions into 2 ranges
	//we calculate how much this division into 2 seperate matrices would cost to multiply and 
	//store the minimal answer there.
	
	//e.g if we have (A B C D) and we want to split the full range into 2 matrices, our possibilities
	//are: - ((A) x (B x C x D))
	//     - ((A x B) x (C x D))
	//     - ((A x B x C) x (D))
	static int dp(int start, int end) {
		//Base case is if start==end, this would mean we are calculating the 
		//most optimal answer for multiplying a matrix with nothing else, which obviously
		//would be 0
		if(start==end) return 0;
		
		if(memo[start][end]!=-1) return memo[start][end];
		int ans = (int) 1e9;
		
		
		//Here we are iterating over every possible division into 2 matrices possible
		//for our given range, and minimising ans over all possible answers
		for(int split = start;split<end;split++) {
			//calc here is the actual cost of multiplying the 2 matrices which are the results
			//of the individual bracket multiplication after we split them
			//if you are confused by this calculation it is just the number of rows of the first
			//bracket matrix * the number of columns of the 2nd bracket matrix * the number
			//of columns in the first bracket matrix.
			int calc = nums[start][0]*nums[end][1]*nums[split][1];
			
			//The cost of this division overall would be the cost of the 2 seperated brackets
			//by themselves and then the cost of multiplying them together in the end(calc)
			int cans = dp(start,split) + dp(split+1,end) + calc;
			if(cans<ans) {
				ans = cans;
				splits[start][end] = split;
			}
		}
		
		//Here we are just memoising the answer from the ans variable
		memo[start][end] = ans;
		return ans;
	}
	
	static void trace(int start, int end) {
		if(start>end) return;
		
		//The base case for the trace is that we have finally reached
		//a matrix by itself, here we just print the matrix's number and return
		if(start==end) {
			int z = start+1;
			String ed = "" + z;
			String print = "A" + ed;
			System.out.print(print);
			return;
		}
		
		
		//Otherwise we have a range that we want to print the answer for
		//we have already stored where the ranges brackets are split in the splits array
		//therefore for example if we had (A B C D) and the optimal division was 
		//((A x B) x (C x D)) then what we would want to do is print the first left bracket ( of the range,
		//then print the first bracket matrix subproblem by itself, then print the x in between,
		//then the 2nd bracket and then finally close our ranges bracket with ).
		System.out.print("(");
		trace(start,splits[start][end]);
		System.out.print(" x ");
		trace(splits[start][end]+1,end);
		System.out.print(")");
	}
	
	//If you have any further inquiries please ask them on discord.
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int z = 1;
		while(true) {
			n = sc.nextInt();
			if(n==0) break;
			
			//Setting up the memo array for the dp,
			//and the splits array which we will use
			//to trace the answer
			memo = new int[n+1][n+1];
			splits = new int[n+1][n+1];
			for(int e[] : memo)
				Arrays.fill(e, -1);
			
			//Here we are taking the input 
			nums = new int[n][2];
			for(int i=0;i<n;i++) {
				nums[i][0] = sc.nextInt();
				nums[i][1] = sc.nextInt();
			}
			
			//Calling the dp to calculate the most optimal answer 
			dp(0,n-1);
			System.out.print("Case " + z +": ");
			
			//Calling the tracing function to print the multiplication order. 
			trace(0,n-1);
			
			//Newline character between test cases
			System.out.println();
			z++;
		}

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