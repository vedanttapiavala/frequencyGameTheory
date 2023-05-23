/**
 * Simple Reinforcement Strategy:
 * Starts as a random player but updates the probability by notes working/not working well
 */
public class SimpleReinforcementPlayer extends Player {
    private double[] probabilities = new double[Main.notesFreqMap.size()];
    private int currNoteInd = 0;

    public SimpleReinforcementPlayer() {
        super();
        for (int i = 0; i < probabilities.length; i++) {
            probabilities[i] = 1; //set initial weighting of each potential note
        }
    }

    @Override
    public int genNote() {
        double probabilitySum = 0;
        for (double x: probabilities) {
            probabilitySum+=x;
        }
        double rand = Math.random() * probabilitySum;
        int index; //index of note that we will play
        double cumulativeSum = 0;
        for (index = 0; index < probabilities.length; index++) {
            cumulativeSum+=probabilities[index];
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
        //TODO: Make it so that probabilites cannot be negative; if probability is negative, set it to 0
        probabilities[currNoteInd]+=recentPayoff;
    }
}
