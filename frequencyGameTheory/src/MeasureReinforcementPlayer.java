import java.util.Arrays;

/**
 * Measure Reinforcement Strategy:
 * Same as SimpleReinforcementPlayer but has different probabilities arrays by chord
 */
public class MeasureReinforcementPlayer extends Player {
    private double[][] probabilities = new double[Main.notesFreqMap.size()][4];
    final int[] bb7 = {116, 147, 175, 208}; 
    final int[] eb7 = {156, 196, 233, 277}; 
    final int[] cm7 = {131, 156, 196, 233};
    final int[] f7 = {175, 220, 262, 311}; 

    private int currNoteInd = 0;

    public MeasureReinforcementPlayer() {
        super();
        for (int chord = 0; chord < 4; chord++) {
            for (int i = 0; i < probabilities[chord].length; i++) {
                probabilities[chord][i] = 1; //set initial weighting of each potential note
            }
        }
    }

    @Override
    public int genNote() {
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
        int i = 0;
        for (String s: Main.notesFreqMap.keySet()) {
            if (i == index) {
                double[] possibleNoteRange = Main.notesFreqMap.get(s); //possibleNoteRange[0] is the lowest bound, possibleNoteRange[1] is the highest
                currNoteInd = i;
                return ((int) (Math.random() * (possibleNoteRange[1]-possibleNoteRange[0]+1) + possibleNoteRange[0]));
            }
            else {
                i++;
            }
        }
        return 0;
    }

    @Override
    protected void update(double recentPayoff) {
        int currChord = Arrays.equals(bb7, Main.chordProgressionFreq) ? 0 : Arrays.equals(eb7, Main.chordProgressionFreq) ? 1 : Arrays.equals(cm7, Main.chordProgressionFreq) ? 2 : 3;
        probabilities[currChord][currNoteInd]+=recentPayoff;
        if (probabilities[currChord][currNoteInd] < 0) {
            probabilities[currChord][currNoteInd] = 0;
        }
    }
}
