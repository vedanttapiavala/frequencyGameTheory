import java.util.ArrayList;

public class FlexibleHarmonyScore extends Score {
    public FlexibleHarmonyScore() {};

    public static void main(String[] args) {
        // for (int interval = 0; interval < 13; interval++) {
        //     int[][] fractions = {
        //         {1, 1},
        //         {256, 243},
        //         {9,8},
        //         {32,27},
        //         {81,64},
        //         {4,3},
        //         {729,512},
        //         {3,2},
        //         {128,81},
        //         {27,16},
        //         {16,9},
        //         {243,128},
        //         {2,1}
        //     };
        //     System.out.println("Interval: --------------- "+interval+" ---------------------");
        //     for (int baseNote = 0; baseNote +interval < 88; baseNote++) {
        //         addNumDenomSimplifiedFraction(Util.noteToFreq(baseNote+interval), Util.noteToFreq(baseNote));
        //         double pythRatio = (double) fractions[interval][0]  / fractions[interval][1];
        //         double tempRatio = (Util.noteToFreq(baseNote+interval) / Util.noteToFreq(baseNote));
        //         if (baseNote == 0) {
        //             System.out.println((pythRatio - tempRatio) / (tempRatio));
        //         }
        //     }
        // }

        double freqOne = Util.noteToFreq(0);
        double freqTwo = Util.noteToFreq(11);
        addNumDenomSimplifiedFraction(freqOne, freqTwo);

    }

    // Calculate Harmony Score as described in the paper
    @Override
    public double calcScore(double[] chord, ArrayList<Double> p1PastNotes, ArrayList<Double> p2PastNotes) {
        double freqOne = p1PastNotes.get(p1PastNotes.size()-1);
        double freqTwo = p2PastNotes.get(p2PastNotes.size()-1);
        ArrayList<Double> allNotes = new ArrayList<Double>();
        for (double x: chord) {
            allNotes.add(x);
        }
        allNotes.add(freqOne); allNotes.add(freqTwo);
        int sum = 0;
        for (int i = 0; i < allNotes.size()-1; i++) {
            for (int j = i + 1; j < allNotes.size(); j++) {
                sum+=addNumDenomSimplifiedFraction(allNotes.get(i), allNotes.get(j));
            }
        }
        int numTimesOfLoop = (allNotes.size()*(allNotes.size()-1))/2;
        return sum/((double)(numTimesOfLoop));
    }

    /**
     * Takes in two doubles, x and y
     * Finds the simplest fraction p/q within 1% of x/y and returns the sum of the numerator and denominator
     */
    private static int addNumDenomSimplifiedFraction(double x, double y) {
        double ratio = x / y;
        int[] fraction = Util.sternBrocot(ratio, 0.01); // we use 1% error due to human recognizable deviation
        return (fraction[0]) + (fraction[1]);
    }

    // Returns -1 to indicate lower is better
    @Override
    public int getDirection() {
        return -1;
    }
  
}
