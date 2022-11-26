import java.util.*;
import java.io.*;

public class NumberTheory {
    static Scanner sc = new Scanner(System.in);
    static PrintWriter pw = new PrintWriter(System.out);

    public static void main(String[] args){
        // GCD
        pw.println(gcd(12, 15));

        // LCM
        pw.println(LCM(1l << 34, 1l << 34));

        // Extended GCD
        pw.println(Arrays.toString(ExtendedGCD(3, 5)));

        // next Solution 3 * 2 + 5 * 1 = 11
        pw.println(Arrays.toString(nextSol(2, 1, 3, 5)));

        // Sieve
        sieve(); // fill the prime array

        // get all primes power     primes -> p     powers -> x
        int n = sc.nextInt();
		ArrayList<Integer> p = new ArrayList<>();
		ArrayList<Integer> x = new ArrayList<Integer>();
		for(int i = 2 ; i  < N ; i++) {
			int prime = primes[i];
			if(prime == i) { // check if the number is prime;
				if(n % prime == 0) {
					int c = 0;
					while(n % prime == 0) { // get the power of the current prime
						c++;
						n /= prime;
					}
					p.add(prime);
					x.add(c);
				}	
			}
			if(n == 1)
			{
				break;
			}
		}
		pw.println(p+"\n"+x);
    }

    // GCD
    static long gcd(long a, long b){
        if(b == 0){
            return a;
        }
        return gcd(b, a % b);
    }

    // LCM
    static long LCM(long a, long b) {
        return a / gcd(a, b) * b;
    }

    // Extended Eculidian (GCD)
    static long[] ExtendedGCD(long a, long b) {
		if(b == 0) {
			return new long[] {1, 0};
		}
		long[] next = ExtendedGCD(b, a % b); // solution of the next call

		long x1 = next[0];
		long y1 = next[1];
		
        long[] res = new long[2];
		
        res[0] = y1;
		res[1] = x1 - a / b * y1;
		
        return res;
	}


    // next sol
    static long[] nextSol(long x, long y, long a, long b) {
        long g = gcd(a, b);
		return new long[] { x + b / g , y - a / g};
	}

    // Sieve
    static int N = (int)1e7;
	static int[] primes = new int[N];
	
	static void sieve() {
		for(int i = 2; i < N; i++) {
			if(primes[i] == 0) { // i is prime
				for(int j = i; j < N; j += i) {
					primes[j] = i;
				}
			}
		}
	}
	
}