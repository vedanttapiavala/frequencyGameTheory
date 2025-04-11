import java.lang.Math;

/*
 * Player class for the Randomness player; generates a random note within the simulation range and returns it
 */
public class RandomPlayer extends Player {
    public RandomPlayer() {
        super();
    }

    @Override
    public double genNote() {
        return Util.noteToFreq((int) (Math.random() * 88));
    }
}
