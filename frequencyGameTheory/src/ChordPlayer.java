import java.util.ArrayList;

/**
 * Player class for the Chord Following player
 * Only generates notes that are either in the chord or higher/lower octaves of the notes in the current chord
 */
public class ChordPlayer extends Player {
    public ChordPlayer() {
        super();
    }

    //Similar to genNote for Chord-Following Reinforcement Learning
    @Override
    public int genNote() {
        ArrayList<Integer> possibleNotes = new ArrayList<Integer>();
        for (int x: Main.chordProgressionFreq) {
            int higherOctave = x*2;
            while (higherOctave <= 4186) {
                possibleNotes.add(higherOctave);
                higherOctave*=2;
            }
            double lowerOctave = x/2.0;
            while (lowerOctave >= 28) {
                possibleNotes.add((int) lowerOctave);
                lowerOctave/=2.0;
            }
            possibleNotes.add(x);
        }
        return possibleNotes.get((int) (Math.random()*possibleNotes.size())); //returns random int from possible notes
    }
}
