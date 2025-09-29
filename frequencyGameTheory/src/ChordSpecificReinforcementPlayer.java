import java.util.Arrays;

/**
 * Player class for the Chord-Specific Reinforcement Learning Player
 * Chord-Specific Reinforcement Learning Strategy:
 * Same as SimpleReinforcementPlayer but has different probabilities arrays by chord
 */
public class ChordSpecificReinforcementPlayer extends Player {
    private double[][] probabilities = new double[4][Main.notesFreqMap.size()];
    final double[] bb7 = {116.54, 147.83, 174.61, 207.65}; // Bb7 -(equal temperament approx.frequencies)-> 116.54(Bb2),146.83(D3), 174.61(F3), Ab(207.65)
    final double[] eb7 = {155.56, 196.0, 233.08, 277.18}; // Eb7 -(equal temperament approx.frequencies)-> Eb3 155.56, G3 196.00, Bb3 233.08, Db4 277.18
    final double[] cm7 = {130.81, 155.56, 196.0, 233.08}; // Cm7 -(equal temperament approx.frequencies)-> C3 130.81, Eb3 155.56, G3 196.00, Bb3 233.08
    final double[] f7 = {174.61, 220.0, 261.63, 311.13}; // F7 -(equal temperament approx.frequencies)-> F3	174.61, A3 220.00, C4 261.63, Eb4 311.13

    private int currNoteInd = 0;

    public ChordSpecificReinforcementPlayer() {
        super();
        for (int chord = 0; chord < 4; chord++) {
            for (int i = 0; i < probabilities[chord].length; i++) {
                probabilities[chord][i] = 1; //set initial weighting of each potential note
            }
        }
    }

    //Generates the next note based on the previously updated weightings
    @Override
    public double genNote() {
        int currChord = Arrays.equals(bb7, Main.chordProgressionFreq) ? 0 : Arrays.equals(eb7, Main.chordProgressionFreq) ? 1 : Arrays.equals(cm7, Main.chordProgressionFreq) ? 2 : 3;
        double probabilitySum = 0;
        for (double x: probabilities[currChord]) {
            probabilitySum+=x;
        }
        double rand = Math.random() * probabilitySum;
        int index; //index of note that we will play
        double cumulativeSum = 0;
        for (index = 0; index < probabilities[currChord].length; index++) {
            cumulativeSum+=probabilities[currChord][index];
            if (cumulativeSum >= rand) {
                break;
            }
        }
        currNoteInd = index;
        return Math.pow(2, (index) / 12.0) * 27.5;
        // int i = 0;
        // for (String s: Main.notesFreqMap.keySet()) {
        //     if (i == index) {
        //         double[] possibleNoteRange = Main.notesFreqMap.get(s); //possibleNoteRange[0] is the lowest bound, possibleNoteRange[1] is the highest
        //         currNoteInd = i;
        //         return ((Math.random() * (possibleNoteRange[1]-possibleNoteRange[0]+1) + possibleNoteRange[0]));
        //     }
        //     else {
        //         i++;
        //     }
        // }
        // return 0;
    }

    //Figures out what chord we're in and then updates that chord's specific probability
    @Override
    protected void update(double recentPayoff) {
        //Assigns an int for each chord; then used to update the probabilities for the chord that we are currently playing
        int currChord = Arrays.equals(bb7, Main.chordProgressionFreq) ? 0 : Arrays.equals(eb7, Main.chordProgressionFreq) ? 1 : Arrays.equals(cm7, Main.chordProgressionFreq) ? 2 : 3;
        probabilities[currChord][currNoteInd]+=recentPayoff;
        if (probabilities[currChord][currNoteInd] < 0) {
            probabilities[currChord][currNoteInd] = 0;
        }
    }
}
