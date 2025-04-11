import java.io.*; //BufferedWriter, FileWriter, and File
import java.util.*; //ArrayList, HashMap, and Map

public class Main {
    protected static Map<String, double[]> notesFreqMap;
    protected static double[] chordProgressionFreq;

    public static double main(Player p1, Player p2, int numLoops) throws Exception {
        buildNotesFrequenciesMap();
        Score harmScore = new FlexibleHarmonyScore();
        Score varScore = new VarianceScore();
        String fileName = getFileName(p1, p2);
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName))); //will replace file if simulation has already been run
        ArrayList<double[]> chordProgression = new ArrayList<double[]>(); //every int[] is one chord (usually 4 notes)
        double[] bb7 = {116.54, 147.83, 174.61, 207.65}; // Bb7 -(equal temperament approx.frequencies)-> 116.54(Bb2),146.83(D3), 174.61(F3), Ab(207.65)
        double[] eb7 = {155.56, 196.0, 233.08, 277.18}; // Eb7 -(equal temperament approx.frequencies)-> Eb3 155.56, G3 196.00, Bb3 233.08, Db4 277.18
        double[] cm7 = {130.81, 155.56, 196.0, 233.08}; // Cm7 -(equal temperament approx.frequencies)-> C3 130.81, Eb3 155.56, G3 196.00, Bb3 233.08
        double[] f7 = {174.61, 220.0, 261.63, 311.13}; // F7 -(equal temperament approx.frequencies)-> F3	174.61, A3 220.00, C4 261.63, Eb4 311.13
        /**
         * Add the chords in the right order
         * 12 bar Bb blues progression: Bb7, Eb7, Bb7, Bb7, Eb7, Eb7, Bb7, Bb7, Cm7, F7, Bb7, F7
         */
        for (int i = 0; i < numLoops; i++) { //goes through chord progression numLoops times
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
        }
        ArrayList<Double> p1Freq = new ArrayList<Double>(); //saves player 1 notes to list
        ArrayList<Double> p2Freq = new ArrayList<Double>(); //saves player 2 notes to list
        double payoffSum = 0.0;
        int measureNum = -1; // counts the measures
        for (int beatNum = 0; beatNum < 96*numLoops; beatNum++) { // subdividing by eight notes there will be 96 beats in a 12 bar blues
            if (beatNum % 8 == 0) measureNum++; // increments measureNum at the start of every 8 beats --> one measure
            chordProgressionFreq = chordProgression.get(measureNum);
            double freqOne = p1.genNote(); //gets note based on p1's strategy
            double freqTwo = p2.genNote();
            p1Freq.add(freqOne); //add the note to the correct list
            p2Freq.add(freqTwo);
            double varianceScore = varScore.calcScore(chordProgressionFreq, p1Freq, p2Freq);
            if (beatNum == 0) {
                //calculate payoff in here
                varianceScore = 0;
            }
            double harmonyScore = harmScore.calcScore(chordProgressionFreq, p1Freq, p2Freq);
            final double multiplicationFactor = 17.68731856; //calculation shown in paper; uses raw variance/harmony scores for normalization
            varianceScore*=multiplicationFactor;
            //without this if statement, the initial variance score would be 0, causing a high magnitude negative number to be the first payoff
            //thus, the first payoff is artificially set to 0
            if (beatNum == 0) {
                varianceScore = harmonyScore; //makes payoff 0 instead of negative for the first beat
            }
            //Corresponds to equation for payoff shown in paper; varianceScore was already multiplied by multiplication factor
            double payoff = (varianceScore - harmonyScore)/(varianceScore + harmonyScore);
            //Updates weightings for Reinforcement Learning algorithms
            if (p1 instanceof SimpleReinforcementPlayer || p1 instanceof StepwisePlayer || p1 instanceof ChordFollowingReinforcementLearning || p1 instanceof ChordSpecificReinforcementPlayer || p1 instanceof ChordSpecificMarkovPlayer) {
                p1.update(payoff);
            }
            if (p2 instanceof SimpleReinforcementPlayer || p2 instanceof StepwisePlayer || p2 instanceof ChordFollowingReinforcementLearning || p2 instanceof ChordSpecificReinforcementPlayer || p2 instanceof ChordSpecificMarkovPlayer) {
                p2.update(payoff);
            }
            if (p1 instanceof DoubleReinforcementPlayer) {
                p1.update(payoff, freqTwo);
            }
            if (p2 instanceof DoubleReinforcementPlayer) {
                p2.update(payoff, freqOne);
            }
            if (p1 instanceof PredictiveHarmonyPlayer) {
                p1.update(freqTwo);
            }
            if (p2 instanceof PredictiveHarmonyPlayer) {
                p2.update(freqOne);
            }
            //writes variance score, harmony score, and payoff for each beat into the generated file
            bw.write(varianceScore + "\t" + harmonyScore + "\t" + payoff + "\n");
            payoffSum+=payoff;
            bw.flush();
        }
        Musician.play(p1Freq, p2Freq, fileName); //generates MIDI files for player 1
        bw.write("\nAverage Payoff: " + String.format("%.4f", payoffSum/(96.0*numLoops)));
        bw.flush(); //write to relevant notepad
        bw.close(); //prevent resource leaks
        return payoffSum/(96.0*numLoops); //payoff divided by number of beats = average payoff
    }

    /**
     * Builds a mapping from any decimal frequency to a note on the piano
     */
    private static void buildNotesFrequenciesMap() {
        notesFreqMap = new LinkedHashMap<String, double[]>();
        notesFreqMap.put("A0", new double[]{28, 28.31}); //starts at 28 Hz
        notesFreqMap.put("A#0/Bb0", new double[]{28.31, 29.99});
        notesFreqMap.put("B0", new double[]{29.99, 31.77});
        notesFreqMap.put("C1", new double[]{31.77, 33.66});
        notesFreqMap.put("C#1/Db1", new double[]{33.66, 35.67});
        notesFreqMap.put("D1", new double[]{35.67, 37.78});
        notesFreqMap.put("D#1/Eb1", new double[]{37.78, 40.03});
        notesFreqMap.put("E1", new double[]{40.03, 42.41});
        notesFreqMap.put("F1", new double[]{42.41, 44.93});
        notesFreqMap.put("F#1/Gb1", new double[]{44.93, 47.61});
        notesFreqMap.put("G1", new double[]{47.61, 50.43});
        notesFreqMap.put("G#1/Ab1", new double[]{50.43, 53.43});
        notesFreqMap.put("A1", new double[]{53.43, 56.61});
        notesFreqMap.put("A#1/Bb1", new double[]{56.61, 60.00});
        notesFreqMap.put("B1", new double[]{60.00, 63.55});
        notesFreqMap.put("C2", new double[]{63.55, 67.32});
        notesFreqMap.put("C#2/Db2", new double[]{67.32, 71.34});
        notesFreqMap.put("D2", new double[]{71.34, 75.57});
        notesFreqMap.put("D#2/Eb2", new double[]{75.57, 80.07});
        notesFreqMap.put("E2", new double[]{80.07, 84.83});
        notesFreqMap.put("F2", new double[]{84.83, 89.87});
        notesFreqMap.put("F#2/Gb2", new double[]{89.87, 95.22});
        notesFreqMap.put("G2", new double[]{95.22, 100.87});
        notesFreqMap.put("G#2/Ab2", new double[]{100.87, 106.87});
        notesFreqMap.put("A2", new double[]{106.87, 113.23});
        notesFreqMap.put("A#2/Bb2", new double[]{113.23, 120.00});
        notesFreqMap.put("B2", new double[]{120.00, 127.10});
        notesFreqMap.put("C3", new double[]{127.10, 134.64});
        notesFreqMap.put("C#3/Db3", new double[]{134.64, 142.68});
        notesFreqMap.put("D3", new double[]{142.68, 151.14});
        notesFreqMap.put("D#3/Eb3", new double[]{151.14, 160.14});
        notesFreqMap.put("E3", new double[]{160.14, 169.65});
        notesFreqMap.put("F3", new double[]{169.65, 179.74});
        notesFreqMap.put("F#3/Gb3", new double[]{179.74, 190.44});
        notesFreqMap.put("G3", new double[]{190.44, 201.74});
        notesFreqMap.put("G#3/Ab3", new double[]{201.74, 213.74});
        notesFreqMap.put("A3", new double[]{213.74, 226.46});
        notesFreqMap.put("A#3/Bb3", new double[]{226.46, 240.00});
        notesFreqMap.put("B3", new double[]{240.00, 254.20});
        notesFreqMap.put("C4", new double[]{254.20, 269.29});
        notesFreqMap.put("C#4/Db4", new double[]{269.29, 285.36});
        notesFreqMap.put("D4", new double[]{285.36, 302.28});
        notesFreqMap.put("D#4/Eb4", new double[]{302.28, 320.29});
        notesFreqMap.put("E4", new double[]{320.29, 339.30});
        notesFreqMap.put("F4", new double[]{339.30, 359.48});
        notesFreqMap.put("F#4/Gb4", new double[]{359.48, 380.89});
        notesFreqMap.put("G4", new double[]{380.89, 403.49});
        notesFreqMap.put("G#4/Ab4", new double[]{403.49, 427.48});
        notesFreqMap.put("A4", new double[]{427.48, 452.92});
        notesFreqMap.put("A#4/Bb4", new double[]{452.92, 480.00});
        notesFreqMap.put("B4", new double[]{480.00, 508.40});
        notesFreqMap.put("C5", new double[]{508.40, 538.58});
        notesFreqMap.put("C#5/Db5", new double[]{538.58, 570.72});
        notesFreqMap.put("D5", new double[]{570.72, 604.56});
        notesFreqMap.put("D#5/Eb5", new double[]{604.56, 640.57});
        notesFreqMap.put("E5", new double[]{640.57, 678.60});
        notesFreqMap.put("F5", new double[]{678.60, 718.96});
        notesFreqMap.put("F#5/Gb5", new double[]{718.96, 761.77});
        notesFreqMap.put("G5", new double[]{761.77, 806.97});
        notesFreqMap.put("G#5/Ab5", new double[]{806.97, 854.96});
        notesFreqMap.put("A5", new double[]{854.96, 905.84});
        notesFreqMap.put("A#5/Bb5", new double[]{905.84, 960.00});
        notesFreqMap.put("B5", new double[]{960.00, 1016.80});
        notesFreqMap.put("C6", new double[]{1016.80, 1077.16});
        notesFreqMap.put("C#6/Db6", new double[]{1077.16, 1141.44});
        notesFreqMap.put("D6", new double[]{1141.44, 1209.12});
        notesFreqMap.put("D#6/Eb6", new double[]{1209.12, 1281.14});
        notesFreqMap.put("E6", new double[]{1281.14, 1357.20});
        notesFreqMap.put("F6", new double[]{1357.20, 1437.92});
        notesFreqMap.put("F#6/Gb6", new double[]{1437.92, 1523.55});
        notesFreqMap.put("G6", new double[]{1523.55, 1613.94});
        notesFreqMap.put("G#6/Ab6", new double[]{1613.94, 1709.92});
        notesFreqMap.put("A6", new double[]{1709.92, 1811.68});
        notesFreqMap.put("A#6/Bb6", new double[]{1811.68, 1920.00});
        notesFreqMap.put("B6", new double[]{1920.00, 2033.60});
        notesFreqMap.put("C7", new double[]{2033.60, 2154.32});
        notesFreqMap.put("C#7/Db7", new double[]{2154.32, 2282.88});
        notesFreqMap.put("D7", new double[]{2282.88, 2418.24});
        notesFreqMap.put("D#7/Eb7", new double[]{2418.24, 2562.28});
        notesFreqMap.put("E7", new double[]{2562.28, 2714.40});
        notesFreqMap.put("F7", new double[]{2714.40, 2875.84});
        notesFreqMap.put("F#7/Gb7", new double[]{2875.84, 3047.09});
        notesFreqMap.put("G7", new double[]{3047.09, 3227.88});
        notesFreqMap.put("G#7/Ab7", new double[]{3227.88, 3419.84});
        notesFreqMap.put("A7", new double[]{3419.84, 3623.36});
        notesFreqMap.put("A#7/Bb7", new double[]{3623.36, 3840.00});
        notesFreqMap.put("B7", new double[]{3840.00, 4067.20});
        notesFreqMap.put("C8", new double[]{4067.20, 4186}); //no notes past 4186
    }

    // //counts octaves as the same note
    // //Applies Shannon Diversity Index as mentioned in the paper
    // private static double calcVarianceScore(ArrayList<Double> allPastNotes) {
    //     Map<String, Integer> noteCounts = new HashMap<String, Integer>();
    //     final String[] notes = new String[]{"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
    //     for (String s: notes) {
    //         noteCounts.put(s, 0);
    //     }
    //     for (double x: allPastNotes) {
    //         for (String s: notesFreqMap.keySet()) {
    //             //if else necessary so that C and C#, etc. are not counted as the same note
    //             if (x >= notesFreqMap.get(s)[0] && x < notesFreqMap.get(s)[1]) {
    //                 if (s.contains("#")) {
    //                     for (int i =notes.length-1; i >=0; i--) {
    //                         String note = notes[i];
    //                         if (s.startsWith(note)) {
    //                             noteCounts.put(note, noteCounts.get(note)+1);
    //                             break;
    //                         }
    //                     }
    //                 }
    //                 else {
    //                     for (String note : notes) {
    //                         if (s.startsWith(note)) {
    //                             noteCounts.put(note, noteCounts.get(note)+1);
    //                             break;
    //                         }
    //                     }
    //                 }
    //             }
    //         }
    //     }
    //     //Calculating Shannon Diversity Index
    //     double diversityIndex = 0;
    //     for (int x: noteCounts.values()) {
    //         double Pi = (double)x / allPastNotes.size();
    //         double calc = -Pi*Math.log(Pi); //-Pi * ln(Pi)
    //         if (Double.isNaN(calc)) {
    //             calc = 0;
    //         }
    //         diversityIndex+=calc;
    //     }
    //     return diversityIndex/Math.log(noteCounts.size()); //H/Hmax
    // }

    // //Calculates harmony score as described in the paper
    // private static double calcHarmonyScore(double[] chord, double freqOne, double freqTwo) {
    //     ArrayList<Double> allNotes = new ArrayList<Double>();
    //     for (double x: chord) {
    //         allNotes.add(x);
    //     }
    //     allNotes.add(freqOne); allNotes.add(freqTwo);
    //     int sum = 0;
    //     for (int i = 0; i < allNotes.size()-1; i++) {
    //         for (int j = i + 1; j < allNotes.size(); j++) {
    //             sum+=addNumDenomSimplifiedFraction(allNotes.get(i), allNotes.get(j));
    //         }
    //     }
    //     int numTimesOfLoop = (allNotes.size()*(allNotes.size()-1))/2;
    //     return (int) (Math.round(sum/((double)(numTimesOfLoop))));
        
    // }

    // /**
    //  * Takes in two doubles, x and y
    //  * Finds the simplest fraction p/q within 1% of x/y and returns the sum of the numerator and denominator
    //  */
    // private static int addNumDenomSimplifiedFraction(double x, double y) {
    //     double ratio = x / y;
    //     int[] fraction = Util.sternBrocot(ratio, 0.01); // we use 1% error due to human recognizable deviation
    //     return (fraction[0]) + (fraction[1]);
    // }

    /**
     * Creates a file name unique to this pair of player types
     * For example, two random players would result in  "Pair: Random Random.txt"
    **/
    private static String getFileName(Player p1, Player p2) throws Exception {
        String fileName = "Pair_ ";
        fileName+=getPlayerBasedFileID(p1);
        fileName+=getPlayerBasedFileID(p2);
        return fileName + ".txt";
    }

    private static String getPlayerBasedFileID(Player p) throws Exception {
        if (p instanceof RandomPlayer) {
            return "Random";
        }
        else if (p instanceof SimpleReinforcementPlayer) {
            return "Simple";
        }
        else if (p instanceof ChordPlayer) {
            return "Chord";
        }
        else if (p instanceof ScalePlayer) {
            return "Scale";
        }
        else if (p instanceof PredictiveHarmonyPlayer) {
            return "Predictive";
        }
        else if (p instanceof StepwisePlayer) {
            return "Stepwise";
        }
        else if (p instanceof ChordFollowingReinforcementLearning) {
            return "ChordReinforce";
        }
        else if (p instanceof ChordSpecificReinforcementPlayer) {
            return "Measure";
        }
        else if (p instanceof DoubleReinforcementPlayer) {
            return "Double";
        }
        else if (p instanceof ChordSpecificMarkovPlayer) {
            return "Markov";
        }
        throw new Exception("There's a player type without a file ID");
    }
}
