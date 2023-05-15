import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        Player p1 = new RandomPlayer();
        Player p2 = new RandomPlayer();
        ArrayList<int[]> chordProgression = new ArrayList<int[]>(); //every four integers is one chord
        //TODO: JOSH HERE, set chord progression list
        ArrayList<Integer> allPastNotes = new ArrayList<Integer>();
        for (int trialNum = 0; trialNum < 1000; trialNum++) {
            int freqOne = p1.genNote();
            int freqTwo = p2.genNote();
            int[] chordProgressionFreq = chordProgression.get(trialNum);
            /**
             * weighted average for the current note: chord progression frequency is weighted at 60%
             * Each individual player's played note's frequency is weighted at 20%
             **/
            allPastNotes.add(freqOne);
            allPastNotes.add(freqTwo);
            double varianceScore = 0;
            if (trialNum != 0) {
                //calculate payoff in here
                calcVarianceScore(allPastNotes);
            }
            double harmonyScore = calcHarmonyScore(chordProgressionFreq, freqOne, freqTwo);
        }
    }

    private static double calcVarianceScore(ArrayList<Integer> allPastNotes) {
        /**
         * Separate past notes played into buckets into actual notes
         * Find frequencies
         * Find variance of those frequencies
         */
        
        return 0;
    }

    private static double calcHarmonyScore(int[] chord, int freqOne, int freqTwo) {
        ArrayList<Integer> allNotes = new ArrayList<Integer>();
        for (int x: chord) {
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
        return (int) (Math.round(sum/((double)(numTimesOfLoop))));
    }

    /**
     * Takes in two integers, x and y
     * Simplifies the fraction x/y and returns the sum of the numerator and denominator
     */
    private static int addNumDenomSimplifiedFraction(int x, int y) {
        int gcd = findGCD(x,y);
        return (x/gcd) + (y/gcd);
    }

    //Credit for Algorithm: Geeks for Geeks
    //https://www.geeksforgeeks.org/program-to-find-gcd-or-hcf-of-two-numbers/
    private static int findGCD(int x, int y) {
        int result = Math.min(x, y);
        while (result > 0) {
            if (x % result == 0 && y % result == 0) {
                break;
            }
            result--;
        }
        return result;
    }
}
