/**
 * Player class for Two-Player Reinforcement Learning player
 * Double Reinforcement Strategy:
 * Starts as a random player but updates the probability by notes working/not working well for both it and its opponent
 */
public class DoubleReinforcementPlayer extends Player {
    private double[] probabilities = new double[Main.notesFreqMap.size()];
    private int currNoteInd = 0;

    public DoubleReinforcementPlayer() {
        super();
        for (int i = 0; i < probabilities.length; i++) {
            probabilities[i] = 1; //set initial weighting of each potential note
        }
    }

    @Override
    public double genNote() {
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
                return ((Math.random() * (possibleNoteRange[1]-possibleNoteRange[0]+1) + possibleNoteRange[0]));
            }
            else {
                i++;
            }
        }
        return 0;
    }

    //Updates reinforcement learning algorithm weightings based on both recently played notes and the recent payoff
    @Override
    protected void update(double recentPayoff, double opponentNoteFreq) {
        //find the index of the note that corresponds to opponentNoteFreq
        int opponentNoteInd = 0;
        for (String s: Main.notesFreqMap.keySet()) {
            if (opponentNoteFreq >= Main.notesFreqMap.get(s)[0] && opponentNoteFreq <= Main.notesFreqMap.get(s)[1]) {
                break;
            }
            opponentNoteInd++;
        }
        probabilities[currNoteInd]+=recentPayoff;
        if (probabilities[currNoteInd] < 0) {
            probabilities[currNoteInd] = 0; //floor of probability is 0
        }
        probabilities[opponentNoteInd]+=recentPayoff;
        if (probabilities[opponentNoteInd] < 0) {
            probabilities[opponentNoteInd] = 0;
        }
    }
}
