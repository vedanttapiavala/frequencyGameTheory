import java.util.ArrayList;

public class QuadraticHarmonyScore extends Score {
    public QuadraticHarmonyScore() {};

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
        double sum = 0.0;
        for (int i = 0; i < allNotes.size()-1; i++) {
            for (int j = i + 1; j < allNotes.size(); j++) {
                sum+=addNumDenomSimplifiedFraction(allNotes.get(i), allNotes.get(j));
            }
        }
        int numTimesOfLoop = (allNotes.size()*(allNotes.size()-1))/2;
        return (int) (Math.round(sum/((double)(numTimesOfLoop))));
    }

    /**
     * Takes in two doubles, x and y
     * Finds the simplest fraction p/q within 1% of x/y and returns the sum of the numerator and denominator
     */
    private static double addNumDenomSimplifiedFraction(double x, double y) {
        double ratio = x / y;
        int[] fraction = Util.sternBrocot(ratio, 0.01); // we use 1% error due to human recognizable deviation
        return Math.pow((fraction[0]) + (fraction[1]), 2);
    }

    // Returns -1 to indicate lower is better
    @Override
    public int getDirection() {
        return -1;
    }
  
}
