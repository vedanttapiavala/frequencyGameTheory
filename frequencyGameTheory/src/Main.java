import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        Player p1 = new RandomPlayer();
        Player p2 = new RandomPlayer();
        ArrayList<int[]> chordProgression = new ArrayList<int[]>(); //every int[] is one chord (usually 4 notes)
        int[] bb7 = {116, 147, 175, 208}; // Bb7 -(equal temperament approx.frequencies)-> 116.54(Bb2),146.83(D3), 174.61(F3), Ab(207.65)
        int[] eb7 = {156, 196, 233, 277}; // Eb7 -(equal temperament approx.frequencies)-> Eb3 	155.56, G3	196.00, Bb3 	233.08, Db4 	277.18
        int[] cm7 = {131, 156, 196, 233}; // Cm7 -(equal temperament approx.frequencies)-> C3	130.81, Eb3 	155.56, G3	196.00, Bb3 	233.08
        int[] f7 = {175, 220, 262, 311}; // F7 -(equal temperament approx.frequencies)-> F3	174.61, A3	220.00, C4	261.63, Eb4 	311.13
        /**
         * Add the chords in the right order
         * 12 bar Bb blues progression: Bb7, Eb7, Bb7, Bb7, Eb7, Eb7, Bb7, Bb7, Cm7, F7, Bb7, F7
         */
        chordProgression.add(bb7);
        chordProgression.add(eb7);
        chordProgression.add(bb7);
        chordProgression.add(bb7);
        chordProgression.add(eb7);
        chordProgression.add(eb7);
        chordProgression.add(bb7);
        chordProgression.add(bb7);
        chordProgression.add(cm7);
        chordProgression.add(f7);
        chordProgression.add(bb7);
        chordProgression.add(f7);
        ArrayList<Integer> allPastNotes = new ArrayList<Integer>();
        int measureNum = -1; // counts the measures
        for (int beatNum = 0; beatNum < 96; beatNum++) { // subdividing by eight notes there will be 96 beats in a 12 bar blues
            if (beatNum % 8 == 0) measureNum++; // increments measureNum at the start of every 8 beats --> one measure
            int freqOne = p1.genNote();
            int freqTwo = p2.genNote();
            int[] chordProgressionFreq = chordProgression.get(measureNum);
            /**
             * weighted average for the current note: chord progression frequency is weighted at 60%
             * Each individual player's played note's frequency is weighted at 20%
             **/
            allPastNotes.add(freqOne);
            allPastNotes.add(freqTwo);
            double varianceScore = 0;
            if (beatNum != 0) {
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
