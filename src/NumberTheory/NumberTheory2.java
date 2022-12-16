import java.util.*;
import java.io.*;

public class NumberTheory2 {
    static Scanner sc = new Scanner(System.in);
    static PrintWriter pw = new PrintWriter(System.out);

    static long mod = (int)1e9 + 7;

    static long[] fac = new long[(int)1e6];
    static long[] fib = new long[(int)1e6];

    static void pre(){
        fac[0] = 1;
        for(int i = 1; i < fac.length; i++){
            fac[i] = i * fac[i - 1] % mod;
        }

        fib[0] = 0;
        fib[1] = 1;
        for(int i = 0; i < fib.length; i++){
            fib[i] = fib[i - 1] + fib[i - 2];
            fib[i] %= mod;
        }
    }

    static long fastPower1(long a, long n) {
        if(n == 0){
            return 1;
        }
        long a2 = fastPower1(a, n / 2);
        long res = a2 * a2 % mod * (n%2 == 1? a : 1);
        return res;
    }
    
    static long fastPower2(long a, long n) {
        long res = 1;
        while(n != 0){
            if((n & 1) == 1){
                res *= a;
                res %= mod;
            }
            a *= a;
            a %= mod;
            n >>= 1;
        }
        return res;
    }

    static long modInverse(long b) {
        return fastPower1(b, mod - 2);
    }

    static long C(int n, int k) {
        return fac[n] * modInverse(fac[n - k] * fac[k] % mod) % mod;
    }

    static long[][] matrixMul(long[][] a, long[][] b) {
        if(a[0].length != b.length){
            return null;
        }

        long[][] res = new long[a.length][b[0].length];
        for(int i = 0; i < a.length; i++){
            for(int j = 0; j < b[0].length; j++){
                for(int k = 0; k < a[0].length; k++){
                    res[i][j] += a[i][k] * b[k][j];
                    res[i][j] %= mod;
                }
            }
        }
        return res;
    }

    static long[][] matrixPower(long[][] a, long n) {
        long[][] res = new long[a.length][a.length]; // Identity matrix
        /*
         * 1 0 0
         * 0 1 0
         * 0 0 1
         */
        for(int i = 0; i < a.length; i++){
            res[i][i] = 1;
        }

        while(n != 0) {
            if((n & 1) == 1) {
                res = matrixMul(res, a);
            }
            a = matrixMul(a, a);
            n >>= 1;
        }
        return res;
    }

    public static void main(String[] args){
        pw.println(fastPower1(10, 1000000));
        pw.println(fastPower2(10, 1000000));
        int n = sc.nextInt();
        int m = sc.nextInt();
        pw.println(C(n, 2) * C(m, 2) % mod);
        pre();
        long[][] mat = new long[][] {
            {0, 1},
            {1, 1}
        };
        mat = matrixPower(mat, 100);
        long[][] base = new long[][]{{0, 1}};
        pw.println(fib[100]);
        pw.println(matrixMul(base, mat)[0][0]);
    }
}
