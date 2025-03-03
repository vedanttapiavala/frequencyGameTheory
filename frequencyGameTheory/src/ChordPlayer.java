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
    public double genNote() {
        ArrayList<Double> possibleNotes = new ArrayList<Double>();
        for (double x: Main.chordProgressionFreq) {
            double higherOctave = x*2;
            while (higherOctave <= 4186.01) { // highest possible is 4186.01
                possibleNotes.add(higherOctave);
                higherOctave*=2;
            }
            double lowerOctave = x/2.0;
            while (lowerOctave >= 27.5) { // lowest possible is 27.5
                possibleNotes.add(lowerOctave);
                lowerOctave/=2.0;
            }
            possibleNotes.add(x);
        }
        return possibleNotes.get((int) (Math.random()*possibleNotes.size())); //returns random double from possible notes
    }
}
