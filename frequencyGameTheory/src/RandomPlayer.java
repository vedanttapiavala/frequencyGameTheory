/*
 * Player class for the Randomness player; generates a random note within the simulation range and returns it
 */
public class RandomPlayer extends Player {
    public RandomPlayer() {
        super();
    }

    @Override
    public int genNote() {
        return ((int) (Math.random() * 4159)) + 28; //range from 28 to 4186
    }
}
