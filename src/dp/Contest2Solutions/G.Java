import java.io.*;
import java.util.*;
public class G {
	static int n;
	static int pos[][];
	static int memo[];
	static PrintWriter pw;
	
	//This is just a helper function to help us calculate the distance between any 2 points
	//easily, using the calculation specified in the problem statement.(Sum of vertical and horizontal
	//distances squared.
	static int dist(int idx1, int idx2) {
		int a = Math.abs(pos[idx1][0] - pos[idx2][0]) * Math.abs(pos[idx1][0] - pos[idx2][0]);
		int b = Math.abs(pos[idx1][1] - pos[idx2][1]) * Math.abs(pos[idx1][1] - pos[idx2][1]);
		return a+b;	
	}
	
	//Here is our dp function, the only state is the visited bitmask.
	//We can avoid having additional states due to a major observation in the question,
	//that the order of visiting the pairs of handbags/individual handbags does not really matter/
	//therefore each bag is in a pair or a single value space, and we just have to find the order
	//for it in that pair or attempt to take it by itself if its optimal location is to go to it
	//and then back.
	static int dp(int visited) {
		if(Integer.bitCount(visited)==n+1) return 0;
		
		if(memo[visited]!=-1) return memo[visited];
		
		int mask = visited;
		// since we will be finding the minimum answer we just initially set it to a very high value
		int ans = (int) 1e9; 
		
		for(int i=0;i<n+1;i++) {
			
			//Here when we find a viable index in the mask we can 
			//continue to loop over all viable indices(even including the same one to simulate
			//going to a location and then coming back, therefore i can be equal to j) and transition
			//to what would happen if we took both indices together and minimise over both possible 
			//orders : 0->i->j->0 or 0->j->i->0, since we are minimising over all possible other indices
			//we will ultimately find the best possible answer for this individual element, and since 
			//the order ultimately doesnt matter then we will continue to do that for each element/
			//pair of elements
			if(((1<<i)&mask)==0) {
				for(int j=0;j<n+1;j++) {
					if(((1<<j)&mask)==0) {
						int a = dp((mask|1<<i)|1<<j) + dist(0,i) + dist(i,j) + dist(j,0);
						int b = dp((mask|1<<i)|1<<j) + dist(0,j) + dist(j,i) + dist(i,0);
						ans = Math.min(ans, Math.min(a, b));						
					}
				}
				
				//here we are exiting the initial loop as we cannot continue to bruteforce the rest
				//of the pairs complexity wise, but it ultimately does not really matter as 
				//the pair we have just chosen(or single element since j can be equal to i) must
				//occur at some point in the sequence of visits from the bag to the locations
				i = n+1;
			}
		}
		
		memo[visited] = ans;
		return ans;
	}	
	
	//Here the tracing function is nothing special, it calculates the transition 
	//values similar to the dp function and stores which state is the most optimal to transition
	//to next and continues printing/tracing its order of steps from there.
	static void trace(int visited) {
		if(Integer.bitCount(visited)==n+1) return;
		int mask = visited;
		
		int ans = memo[visited];
		int first = -1;
		int second = -1;
		int nextmask = -1;
		
		
		for(int i=0;i<n+1;i++) {
			if(((1<<i)&mask)==0) {
				for(int j=0;j<n+1;j++) {
					if(((1<<j)&mask)==0) {						
						int a = dp((mask|1<<i)|1<<j) + dist(0,i) + dist(i,j) + dist(j,0);
						int b = dp((mask|1<<i)|1<<j) + dist(0,j) + dist(j,i) + dist(i,0);
						if(ans==a) {
							first = i;
							second = j;
							nextmask = (mask|1<<i)|1<<j;
						}
						else if(ans==b) {
							first = j;
							second = i;
							nextmask = (mask|1<<i)|1<<j;
						}
					}
				}
				i = n+1;
			}
		}
		
		if(first==second)second = -1;
		
		pw.print("0 " + first + " ");
		if(second!=-1) pw.print(second + " ");
		trace(nextmask);
	}
	
	//If you have any further inquiries please ask them on discord.
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		pw = new PrintWriter(System.out);
		int sx = sc.nextInt();
		int sy = sc.nextInt();
		n = sc.nextInt();
		
		memo = new int[1<<(n+1)];
		pos = new int[n+1][2];
		pos[0][0] = sx;
		pos[0][1] = sy;
		
		//Here we are just taking the input and putting it all in the pos array
		//index 0 is the starting position/bag position and the rest of the positions are 1 indexed.
		for(int i=1;i<=n;i++) {
			pos[i][0] = sc.nextInt();
			pos[i][1] = sc.nextInt();
		}
		
		//Initialising the memo to -1
		Arrays.fill(memo, -1);
		
		//Calling the dp function to print the optimal path cost
		//The mask is 1(1<<0) initially as index 0 in our array is the bag/starting location
		pw.println(dp(1));
		
		//Calling the trace function to print the optimal path
		trace(1);
		
		//Printing the final 0 to signal that at the end the trace goes back to the original position
		pw.print("0");
		
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