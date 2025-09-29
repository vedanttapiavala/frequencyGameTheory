// Implement Stern-Brocot
// Time Complexity: O(log(max(a, b)))
// Space Complexity: O(1)

import java.util.*;
// import java.lang.*;

public class SternBrocot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double x = sc.nextDouble(), d = sc.nextDouble();
        System.out.println(sternBrocot(x, d));
        sc.close();
    }

 
    /*
     * Args:
     *  x: the decimal being approximated by a fraction
     *  d: the error threshold in percentage (d = 0.01 --> 1%)
     */
    public static int[] sternBrocot(double x, double d) {
        int al = (int) x, bl = 1, ar = (int) x + 1, br = 1;
        int ta = 0, tb = 0;
        // int count = 0;
        while (true) {
            // count++;
            // System.out.println(count);
            // System.out.println("" + al + "/" + bl + " " + ar + "/" + br);
            if (Math.abs( ( (double)al/(double)bl ) - (double)x ) /(double)x < d ) {
                ta = al;
                tb = bl;
                break;
            }
            else if (Math.abs( ((double)ar/(double)br) - (double)x ) /(double)x < d) {
                ta = ar;
                tb = br;
                break;
            }
            
            int am = al + ar, bm = bl + br;
            if ((double)am/(double)bm == x) {
                ta = am;
                tb = bm;
                return new int[]{ta, tb};
            }
            else if ((double)am/(double)bm < x) {
                al = am;
                bl = bm;
            }
            else {
                ar = am;
                br = bm;
            }
        }

        return new int[]{ta, tb};
    }
}


