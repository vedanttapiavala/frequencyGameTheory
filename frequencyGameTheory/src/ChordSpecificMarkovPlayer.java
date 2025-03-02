import java.util.Arrays;

public class ChordSpecificMarkovPlayer extends Player {
    private double[][][] probabilities = new double[4][Main.notesFreqMap.size()][Main.notesFreqMap.size()];
    final double[] bb7 = {116.54, 147.83, 174.61, 207.65}; // Bb7 -(equal temperament frequencies)-> 116.54(Bb2),146.83(D3), 174.61(F3), Ab(207.65)
    final double[] eb7 = {155.56, 196.0, 233.08, 277.18}; // Eb7 -(equal temperament frequencies)-> Eb3 155.56, G3 196.00, Bb3 233.08, Db4 277.18
    final double[] cm7 = {130.81, 155.56, 196.0, 233.08}; // Cm7 -(equal temperament frequencies)-> C3 130.81, Eb3 155.56, G3 196.00, Bb3 233.08
    final double[] f7 = {174.61, 220.0, 261.63, 311.13}; // F7 -(equal temperament frequencies)-> F3	174.61, A3 220.00, C4 261.63, Eb4 311.13

    private int[] currNoteInd = {-1, -1};

    public ChordSpecificMarkovPlayer() {
        super();
        for (int chord = 0; chord < 4; chord++) {
            for (int i = 0; i < probabilities[chord].length; i++) {
                for (int j = 0; j < probabilities[chord][i].length; j++) {
                    probabilities[chord][i][j] = 1; //set initial weighting of each potential note transition
                }
            }
        }
    }

    //Generates the next note based on the previously updated weightings
    @Override
    public double genNote() {
        int currChord = Arrays.equals(bb7, Main.chordProgressionFreq) ? 0 : Arrays.equals(eb7, Main.chordProgressionFreq) ? 1 : Arrays.equals(cm7, Main.chordProgressionFreq) ? 2 : 3;
        if ((currNoteInd[0] == -1.0)) {
            int noteInd = (int) (Math.random() * 88);
            currNoteInd[1] = noteInd;
            return Math.pow(2, ((noteInd) / 12.0)) * 27.5; //plays random note if this is the first note
        }
        double probabilitySum = 0;
        for (double x : probabilities[currChord][currNoteInd[0]]) {
            probabilitySum+=x;
        }
        double rand = Math.random() * probabilitySum;
        int index; //index of note that we will play
        double cumulativeSum = 0;
        for (index = 0; index < probabilities[currChord].length; index++) {
            cumulativeSum+=probabilities[currChord][currNoteInd[0]][index];
            if (cumulativeSum >= rand) {
                break;
            }
        }
        currNoteInd[1] = index;
        return Math.pow(2, (index) / 12.0) * 27.5;
    }

    //Figures out what chord we're in and then updates note transition probability for that chord
    @Override
    protected void update(double recentPayoff) {
        //Assigns an int for each chord; then used to update the probabilities for the chord that we are currently playing
        int currChord = Arrays.equals(bb7, Main.chordProgressionFreq) ? 0 : Arrays.equals(eb7, Main.chordProgressionFreq) ? 1 : Arrays.equals(cm7, Main.chordProgressionFreq) ? 2 : 3;
        //Adds a baseline update of note probabilites for each initial note (increases speed of learning)
        double baseLinePayoff = 0.5 * recentPayoff;
        for (int i = 0; i < probabilities[currChord].length; i++) {
          probabilities[currChord][i][currNoteInd[1]]+=baseLinePayoff;
          if (probabilities[currChord][i][currNoteInd[1]] < 0) {
              probabilities[currChord][i][currNoteInd[1]] = 0;
          }
        }
        //Updates transition probability from prevNote to currNote if prevNote exists
        if (currNoteInd[0] != -1) {
            probabilities[currChord][currNoteInd[0]][currNoteInd[1]] += 0.5 * recentPayoff;
            if (probabilities[currChord][currNoteInd[0]][currNoteInd[1]] < 0.0) {
                probabilities[currChord][currNoteInd[0]][currNoteInd[1]] = 0.0;
            }
        }
        currNoteInd[0] = currNoteInd[1]; //update previous note index with current note index for next iteration
    }  

    public double[][][] getProbabilities() {
        return probabilities;
    }
}
