package src.dp;
import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class Digit_Sum {
    static Scanner sc = new Scanner(System.in);
    static PrintWriter pw = new PrintWriter(System.out);

    static char[] a;
    static int n, d;
    static long[][][] dp;
    static long mod = (long)1e9 + 7;
    
    public static void main(String[] args) throws Exception {
        
        // The input string is turned to array of chars
        a = sc.next().toCharArray();
        n = a.length;
        
        
        d = sc.nextInt();
        
        
        dp = new long[2][d][n];
        for(long[][] e1: dp) {
            for(long[] e: e1) {
                Arrays.fill(e, -1);
            }
        }
        
        
        pw.println((sol(false, 0, 0) + mod - 1) % mod); // sol (fixed, modD, idx);
        
        pw.close();
    }
    
    static long  sol (boolean fixed, int modD, int idx) {
        if(idx == n) {
            return modD == 0? 1: 0;
        }
        if(dp[fixed?0:1][modD][idx] != -1) {
            return dp[fixed?0:1][modD][idx];
        }
        int max = 0;
        // check if the state so far is fixed or not
        if(!fixed) {
            max = a[idx] - '0';
        }else {
            max = 9;
        }
        
        long ans = 0;
        
        for (int i = 0; i <= max; i++) {
            
            boolean newFixed = i == max ? fixed: true;
            
            ans += sol(newFixed, (modD + i) % d, idx + 1);
            ans %= mod;
        }
        
        return dp[fixed?0:1][modD][idx] = ans;
    }
}