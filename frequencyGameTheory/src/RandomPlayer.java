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
        return Math.pow(2, (((int) (Math.random() * 88)) / 12.0)) * 27.5; //range from 27.5 to 4186.01 (88 keys on the piano)
    }
}
