import java.io.*;
import java.util.*;

public class FenwickTree {
	
	//1 - INDEXED
	static class FT1 {
		int tree[];
		int sz;
		
		final int passive = 0;
		
		int op(int a, int b) {
			return a+b;
		}
		
		int rv(int a, int b) {
			return a-b;
		}
		
		public FT1(int n) {
			tree = new int[n+5];
			int t = 1;
			sz = 0;
			while(t<=n) {
				t<<=1;
				sz++;
			}
		}
		
		void rvupdate(int i, int v) {
			while(i<tree.length) {
				tree[i] = rv(tree[i],v);
				i = goup(i);
			}
		}
		
		//Range Query
		int query(int l, int r) {
			int base1 = passive;
			int base2 = passive;
			
			while(r>0) {
				base1 = op(base1,tree[r]);
				r = godown(r);
			}
			
			l--;
			
			while(l>0) {
				base1 = op(base1,tree[l]);
				l = godown(l);
			}
			
			return rv(base1,base2);
		}
		
		//Point Update
		void update(int i, int v) {
			while(i<tree.length) {
				tree[i] = op(tree[i],v);
				i = goup(i);
			}
		}
		
		//Point Assign
		void assign(int i, int v) {
			int ogv = query(i,i);
			rvupdate(i,ogv);
			update(i,v);
		}
		
		//Range Update(Using Point Update)
		void rupdate(int l, int r,int v) {
			update(l,v);
			rvupdate(r+1,v);
		}
		
		//Point Query(Using Range Query)
		int pquery(int i) {
			return query(1,i);
		}
		
		//KthElement Query (1 - INDEXED INPUT)
		int kthElm(int k) {
			k--;
			int sum = 0;
			int pos = 0;
			
			for(int j=sz;j>=0;j--) {
				int npos = pos + (1<<j);
				if(npos < tree.length && sum + tree[npos] <= k) {
					sum += tree[npos];
					pos = npos;
				}
			}
			
			return pos+1;
		}
		
		int goup(int x) {
			return x+(x&(-x));
		}
		
		int godown(int x) {
			return x&(x-1);
		}
		
	}
	
	
	//0 - INDEXED
	static class FT0 {
		int tree[];
		int sz;
		
		final int passive = 0;
		
		int op(int a, int b) {
			return a+b;
		}
		
		int rv(int a, int b) {
			return a-b;
		}
		
		public FT0(int n) {
			tree = new int[n+5];
			int t = 1;
			sz = 0;
			while(t<n) {
				t<<=1;
				sz++;
			}
		}
		
		void rvupdate(int i, int v) {
			while(i<tree.length) {
				tree[i] = rv(tree[i],v);
				i = goup(i);
			}
		}
		
		//Range Query
		int query(int l, int r) {
			int base1 = passive;
			int base2 = passive;
			
			while(r>-1) {
				base1 = op(base1,tree[r]);
				r = godown(r);
			}
			
			l--;
			
			while(l>-1) {
				base1 = op(base1,tree[l]);
				l = godown(l);
			}
			
			return rv(base1,base2);
		}
		
		//Point Update
		void update(int i, int v) {
			while(i<tree.length) {
				tree[i] = op(tree[i],v);
				i = goup(i);
			}
		}
		
		//Point Assign
		void assign(int i, int v) {
			int ogv = query(i,i);
			rvupdate(i,ogv);
			update(i,v);
		}
		
		//Range Update(Using Point Update)
		void rupdate(int l, int r,int v) {
			update(l,v);
			rvupdate(r+1,v);
		}
		
		//Point Query(Using Range Query)
		int pquery(int i) {
			return query(0,i);
		}
		
		//KthElement Query (0 - INDEXED INPUT)
		int kthElm(int k) {
			
			int sum = 0;
			int pos = -1;
			
			for(int j=sz;j>=0;j--) {
				int npos = pos + (1<<j);
				if(npos < tree.length && sum + tree[npos] <= k) {
					sum += tree[npos];
					pos = npos;
				}
			}
			
			return pos+1;
		}
		
		int goup(int x) {
			return x|(x+1);
		}
		
		int godown(int x) {
			return (x&(x+1))-1;
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		
		int arr[] = {1,0,0,0,0,1,0,0,1};
		int n = arr.length;
		FT1 ft = new FT1(n);
		for(int i=0;i<n;i++)
			ft.update(i+1,arr[i]);
		
		FT0 ft0 = new FT0(n);
		for(int i=0;i<n;i++)
			ft0.update(i,arr[i]);
		
		pw.println(ft.query(1, 6));
		pw.println(ft.kthElm(3));
		
		pw.println(ft0.query(0, 5));
		pw.println(ft0.kthElm(2));
		
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