import java.util.*;
/**
 * Simple Reinforcement Strategy:
 * Starts as a random player but updates the probability by notes working/not working well
 */
public class SimpleReinforcementPlayer extends Player {
    private double[] probabilities = new double[10]; //TODO: Figure out how many potential notes there are

    public SimpleReinforcementPlayer() {
        super();
    }

    @Override
    public int genNote() {
        return 0;
    }
}
