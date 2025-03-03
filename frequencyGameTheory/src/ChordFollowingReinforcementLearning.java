import java.util.*;

/*
 * Player class for the Chord-Following Reinforcement Learning player
 */

public class ChordFollowingReinforcementLearning extends Player {
    final double[] bb7 = {116.54, 147.83, 174.61, 207.65}; // Bb7 -(equal temperament approx.frequencies)-> 116.54(Bb2),146.83(D3), 174.61(F3), Ab(207.65)
    final double[] eb7 = {155.56, 196.0, 233.08, 277.18}; // Eb7 -(equal temperament approx.frequencies)-> Eb3 155.56, G3 196.00, Bb3 233.08, Db4 277.18
    final double[] cm7 = {130.81, 155.56, 196.0, 233.08}; // Cm7 -(equal temperament approx.frequencies)-> C3 130.81, Eb3 155.56, G3 196.00, Bb3 233.08
    final double[] f7 = {174.61, 220.0, 261.63, 311.13}; // F7 -(equal temperament approx.frequencies)-> F3	174.61, A3 220.00, C4 261.63, Eb4 311.13
    /*
     * probabilities will contain 4 HashMaps, one for each possible chord (index 0 --> bb7, 1 --> eb7, 2 --> cm7, 3 --> f7)
     * This will map each possible note for those chords to a probability value
     * Execution will be done similar to SimpleReinforcementPlayer.java
     */
    private ArrayList<HashMap<Double, Double>> probabilities = new ArrayList<HashMap<Double, Double>>(4);
    private int prevChord;
    private double prevNote = 0.0;

    public ChordFollowingReinforcementLearning() {
        super(); //instantiate a Player object
        ArrayList<Double> possibleBb7Notes = getPossibleNotes(bb7);
        ArrayList<Double> possibleEb7Notes = getPossibleNotes(eb7);
        ArrayList<Double> possibleCm7Notes = getPossibleNotes(cm7);
        ArrayList<Double> possiblef7Notes = getPossibleNotes(f7);
        //Create a hashmap to store probabilities and set each one to a default of 1
        HashMap<Double, Double> bb7Probabilities = new HashMap<Double, Double>();
        while (possibleBb7Notes.size() > 0) {
            bb7Probabilities.put(possibleBb7Notes.remove(0),1.0);
        }
        probabilities.add(bb7Probabilities);
        HashMap<Double, Double> eb7Probabilities = new HashMap<Double, Double>();
        while (possibleEb7Notes.size() > 0) {
            eb7Probabilities.put(possibleEb7Notes.remove(0), 1.0);
        }
        probabilities.add(eb7Probabilities);
        HashMap<Double, Double> cm7Probabilities = new HashMap<Double, Double>();
        while (possibleCm7Notes.size() > 0) {
            cm7Probabilities.put(possibleCm7Notes.remove(0), 1.0);
        }
        probabilities.add(cm7Probabilities);
        HashMap<Double, Double> f7Probabilities = new HashMap<Double, Double>();
        while (possiblef7Notes.size() > 0) {
            f7Probabilities.put(possiblef7Notes.remove(0), 1.0);
        }
        probabilities.add(f7Probabilities);
    }

    @Override
    public double genNote() {
        //finds the index of the probabilities array that corresponds to the current chord being played in the game
        int currChord = Arrays.equals(bb7, Main.chordProgressionFreq) ? 0 : Arrays.equals(eb7, Main.chordProgressionFreq) ? 1 : Arrays.equals(cm7, Main.chordProgressionFreq) ? 2 : 3;
        double probabilitySum = 0; //find the sum of all the current probabilities
        for (double x: probabilities.get(currChord).values()) {
            probabilitySum+=x;
        }
        double rand = Math.random() * probabilitySum;
        double cumulativeSum = 0;
        for (double note: probabilities.get(currChord).keySet()) {
            cumulativeSum+=probabilities.get(currChord).get(note); //adds the probability corresponding to this note
            if (cumulativeSum >= rand) {
                prevChord = currChord; prevNote = note;
                return note; //will always run since cumulativeSum <= sum of all the notes' probabilities
            }
        }
        return 0;
    }

    /*
     * Input: Takes an array of frequencies from a voicing of a chord in a particular octave
     * Output: ArrayList of all frequencies corresponding to that chord in all octaves allowed within the simulation
     */
    private ArrayList<Double> getPossibleNotes(double[] arr) {
        ArrayList<Double> possibleNotes = new ArrayList<Double>();
        for (double x: arr) {
            double higherOctave = x*2;
            while (higherOctave <= 4186) {
                possibleNotes.add(higherOctave);
                higherOctave*=2;
            }
            double lowerOctave = x/2.0;
            while (lowerOctave >= 28) {
                possibleNotes.add(lowerOctave);
                lowerOctave/=2.0;
            }
            possibleNotes.add(x);
        }
        return possibleNotes;
    }

    //Used to update reinforcement learning algorithm weights
    @Override
    protected void update(double recentPayoff) {
        double newProbability = probabilities.get(prevChord).get(prevNote)+recentPayoff;
        if (newProbability < 0) {
            newProbability = 0;
        }
        probabilities.get(prevChord).replace(prevNote, newProbability);
    }
}
