import java.util.ArrayList;

/**
 * Player class for Stepwise Change player
 * If last payoff was positive, shift the frequency slightly
 * Otherwise, play the same note in a different octave
 */
public class StepwisePlayer extends Player {
    private int currNote;
    private ArrayList<Double> pastTwoPayoffs;
    public StepwisePlayer() {
        super();
        currNote = ((int) (Math.random() * 4159)) + 28;
        pastTwoPayoffs = new ArrayList<Double>();
    }

    @Override
    public int genNote()  {
        if (pastTwoPayoffs.size() == 0 || pastTwoPayoffs.size() == 1) {
            if (pastTwoPayoffs.size() == 0) {
                currNote = ((int) (Math.random() * 4159)) + 28;
            }
            return currNote;
        }
        if (pastTwoPayoffs.get(1) > 0) { //payoff positive
            if (Math.random() < .5) {
                currNote/=((Math.random()*.1)+1); //divides it by between 1-1.1
            }
            else {
                currNote*=((Math.random()*.1)+1); //multiplies it by between 1-1.1
            }
            if (currNote < 28) {
                currNote = 28; //floor of 28
            }
            else if (currNote > 4186) {
                currNote = 4186; //caps it at 4186
            }
            return currNote;
        }
        //payoff was negative
        int maxMultiplicationFactor = ((int) 4186/currNote) > 9 ? 9 : ((int) 4186/currNote);
        if (maxMultiplicationFactor != 1) { //a high frequency multiple is possible
            int multiplicationFactor = ((int) (Math.random() * (maxMultiplicationFactor-1)) + 2); //between 2 and maxMultiplicationFactor, inclusive
            currNote*=multiplicationFactor;
            return currNote;
        }
        else {
            currNote/=((int) (Math.random() * 9)+1); //int divides by random int between 1-9
            return currNote;
        }
    }

    @Override
    public void update(double recentPayoff) {
        pastTwoPayoffs.add(recentPayoff);
        if (pastTwoPayoffs.size() > 2) {
            pastTwoPayoffs.remove(0);
        }
    }
}
