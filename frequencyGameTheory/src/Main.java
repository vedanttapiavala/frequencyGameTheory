import java.io.*; //BufferedWriter, FileWriter, and File
import java.util.*; //ArrayList, HashMap, and Map

public class Main {
    protected static Map<String, double[]> notesFreqMap;

    public static void main(String[] args) throws Exception {
        buildNotesFrequenciesMap();
        Player p1 = new RandomPlayer();
        Player p2 = new RandomPlayer();
        String fileName = getFileName(p1, p2);
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName))); //will replace file if simulation has already been run
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
            int freqOne = p1.genNote(); //gets note based on p1's strategy
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
                varianceScore = calcVarianceScore(allPastNotes);
            }
            double harmonyScore = calcHarmonyScore(chordProgressionFreq, freqOne, freqTwo);
            double payoff = (varianceScore - harmonyScore)/(varianceScore + harmonyScore);
        }
        bw.flush(); //write to relevant notepad
        bw.close(); //prevent resource leaks
    }

    private static void buildNotesFrequenciesMap() {
        notesFreqMap = new HashMap<String, double[]>();
        //TODO: Josh: make the buckets
        Map<String, double[]> noteToBucket = new HashMap<>();
        noteToBucket.put("C0", new double[]{16.35, 16.83});
        noteToBucket.put("C#0/Db0", new double[]{16.83, 17.83});
        noteToBucket.put("D0", new double[]{17.83, 18.89});
        noteToBucket.put("D#0/Eb0", new double[]{18.89, 20.02});
        noteToBucket.put("E0", new double[]{20.02, 21.21});
        noteToBucket.put("F0", new double[]{21.21, 22.47});
        noteToBucket.put("F#0/Gb0", new double[]{22.47, 23.80});
        noteToBucket.put("G0", new double[]{23.80, 25.22});
        noteToBucket.put("G#0/Ab0", new double[]{25.22, 26.72});
        noteToBucket.put("A0", new double[]{26.72, 28.31});
        noteToBucket.put("A#0/Bb0", new double[]{28.31, 29.99});
        noteToBucket.put("B0", new double[]{29.99, 31.77});
        noteToBucket.put("C1", new double[]{31.77, 33.66});
        noteToBucket.put("C#1/Db1", new double[]{33.66, 35.67});
        noteToBucket.put("D1", new double[]{35.67, 37.78});
        noteToBucket.put("D#1/Eb1", new double[]{37.78, 40.03});
        noteToBucket.put("E1", new double[]{40.03, 42.41});
        noteToBucket.put("F1", new double[]{42.41, 44.93});
        noteToBucket.put("F#1/Gb1", new double[]{44.93, 47.61});
        noteToBucket.put("G1", new double[]{47.61, 50.43});
        noteToBucket.put("G#1/Ab1", new double[]{50.43, 53.43});
        noteToBucket.put("A1", new double[]{53.43, 56.61});
        noteToBucket.put("A#1/Bb1", new double[]{56.61, 60.00});
        noteToBucket.put("B1", new double[]{60.00, 63.55});
        noteToBucket.put("C2", new double[]{63.55, 67.32});
        noteToBucket.put("C#2/Db2", new double[]{67.32, 71.34});
        noteToBucket.put("D2", new double[]{71.34, 75.57});
        noteToBucket.put("D#2/Eb2", new double[]{75.57, 80.07});
        noteToBucket.put("E2", new double[]{80.07, 84.83});
        noteToBucket.put("F2", new double[]{84.83, 89.87});
        noteToBucket.put("F#2/Gb2", new double[]{89.87, 95.22});
        noteToBucket.put("G2", new double[]{95.22, 100.87});
        noteToBucket.put("G#2/Ab2", new double[]{100.87, 106.87});
        noteToBucket.put("A2", new double[]{106.87, 113.23});
        noteToBucket.put("A#2/Bb2", new double[]{113.23, 120.00});
        noteToBucket.put("B2", new double[]{120.00, 127.10});
        noteToBucket.put("C3", new double[]{127.10, 134.64});
        noteToBucket.put("C#3/Db3", new double[]{134.64, 142.68});
        noteToBucket.put("D3", new double[]{142.68, 151.14});
        noteToBucket.put("D#3/Eb3", new double[]{151.14, 160.14});
        noteToBucket.put("E3", new double[]{160.14, 169.65});
        noteToBucket.put("F3", new double[]{169.65, 179.74});
        noteToBucket.put("F#3/Gb3", new double[]{179.74, 190.44});
        noteToBucket.put("G3", new double[]{190.44, 201.74});
        noteToBucket.put("G#3/Ab3", new double[]{201.74, 213.74});
        noteToBucket.put("A3", new double[]{213.74, 226.46});
        noteToBucket.put("A#3/Bb3", new double[]{226.46, 240.00});
        noteToBucket.put("B3", new double[]{240.00, 254.20});
        noteToBucket.put("C4", new double[]{254.20, 269.29});
        noteToBucket.put("C#4/Db4", new double[]{269.29, 285.36});
        noteToBucket.put("D4", new double[]{285.36, 302.28});
        noteToBucket.put("D#4/Eb4", new double[]{302.28, 320.29});
        noteToBucket.put("E4", new double[]{320.29, 339.30});
        noteToBucket.put("F4", new double[]{339.30, 359.48});
        noteToBucket.put("F#4/Gb4", new double[]{359.48, 380.89});
        noteToBucket.put("G4", new double[]{380.89, 403.49});
        noteToBucket.put("G#4/Ab4", new double[]{403.49, 427.48});
        noteToBucket.put("A4", new double[]{427.48, 452.92});
        noteToBucket.put("A#4/Bb4", new double[]{452.92, 480.00});
        noteToBucket.put("B4", new double[]{480.00, 508.40});
        noteToBucket.put("C5", new double[]{508.40, 538.58});
        noteToBucket.put("C#5/Db5", new double[]{538.58, 570.72});
        noteToBucket.put("D5", new double[]{570.72, 604.56});
        noteToBucket.put("D#5/Eb5", new double[]{604.56, 640.57});
        noteToBucket.put("E5", new double[]{640.57, 678.60});
        noteToBucket.put("F5", new double[]{678.60, 718.96});
        noteToBucket.put("F#5/Gb5", new double[]{718.96, 761.77});
        noteToBucket.put("G5", new double[]{761.77, 806.97});
        noteToBucket.put("G#5/Ab5", new double[]{806.97, 854.96});
        noteToBucket.put("A5", new double[]{854.96, 905.84});
        noteToBucket.put("A#5/Bb5", new double[]{905.84, 960.00});
        noteToBucket.put("B5", new double[]{960.00, 1016.80});
        noteToBucket.put("C6", new double[]{1016.80, 1077.16});
        noteToBucket.put("C#6/Db6", new double[]{1077.16, 1141.44});
        noteToBucket.put("D6", new double[]{1141.44, 1209.12});
        noteToBucket.put("D#6/Eb6", new double[]{1209.12, 1281.14});
        noteToBucket.put("E6", new double[]{1281.14, 1357.20});
        noteToBucket.put("F6", new double[]{1357.20, 1437.92});
        noteToBucket.put("F#6/Gb6", new double[]{1437.92, 1523.55});
        noteToBucket.put("G6", new double[]{1523.55, 1613.94});
        noteToBucket.put("G#6/Ab6", new double[]{1613.94, 1709.92});
        noteToBucket.put("A6", new double[]{1709.92, 1811.68});
        noteToBucket.put("A#6/Bb6", new double[]{1811.68, 1920.00});
        noteToBucket.put("B6", new double[]{1920.00, 2033.60});
        noteToBucket.put("C7", new double[]{2033.60, 2154.32});
        noteToBucket.put("C#7/Db7", new double[]{2154.32, 2282.88});
        noteToBucket.put("D7", new double[]{2282.88, 2418.24});
        noteToBucket.put("D#7/Eb7", new double[]{2418.24, 2562.28});
        noteToBucket.put("E7", new double[]{2562.28, 2714.40});
        noteToBucket.put("F7", new double[]{2714.40, 2875.84});
        noteToBucket.put("F#7/Gb7", new double[]{2875.84, 3047.09});
        noteToBucket.put("G7", new double[]{3047.09, 3227.88});
        noteToBucket.put("G#7/Ab7", new double[]{3227.88, 3419.84});
        noteToBucket.put("A7", new double[]{3419.84, 3623.36});
        noteToBucket.put("A#7/Bb7", new double[]{3623.36, 3840.00});
        noteToBucket.put("B7", new double[]{3840.00, 4067.20});
        noteToBucket.put("C8", new double[]{4067.20, 4308.64});
        //example: notesFreqMap.put("A#", {200,300});
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

    /**
     * Creates a file name unique to this pair of player types
     * For example, two random players would result in  "Pair: Random Random.txt"
    **/
    private static String getFileName(Player p1, Player p2) throws Exception {
        String fileName = "Pair: ";
        fileName+=getPlayerBasedFileID(p1);
        fileName+=getPlayerBasedFileID(p2);
        return fileName + ".txt";
    }

    private static String getPlayerBasedFileID(Player p) throws Exception {
        if (p instanceof RandomPlayer) {
            return "Random";
        }
        throw new Exception("There's a player type without a file ID");
    }
}
