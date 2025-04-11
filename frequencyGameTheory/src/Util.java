public class Util {
    // Takes note on the piano (from 0-87) and returns equal temperament frequency
    public static double noteToFreq(int note) {
      return Math.pow(2, ((note) / 12.0)) * 27.5; //range from 27.5 to 4186.01 (88 keys on the piano)
    }

    // Takes frequency and converts to closest integer note on the piano (numbered 0-87)
    public static int freqToNote(double freq) {
      return (int) Math.round(Math.log(freq / 27.5) / Math.log(2.0) * 12);
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
