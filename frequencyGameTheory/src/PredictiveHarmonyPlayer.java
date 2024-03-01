import java.util.ArrayList;
import java.util.Collections;

/**
 * Player class for the Harmony Prediction player
 * Plays a note that would harmonize well with the last note the player's opponent played
 */
public class PredictiveHarmonyPlayer extends Player {
    private int lastPlayedOpponentNote = 0;

    public PredictiveHarmonyPlayer() {
        super();
    }

    @Override
    /**
     * If this is the first note, the PredictiveHarmonyPlayer will play a random note
     * Otherwise, this player will try to play a high frequency note that has a low harmony score with the previous note.
     * The player can do this by selecting a random multiple of lastPlayedOppponentNote within the game's bounds
     * If that is not possible (lastPlayedOpponentNote * 2 > 4186), the player will try to play a low frequency note with a low harmony score
     * The player does this by playing a random factor of lastPlayedOpponentNote
     */
    public int genNote() {
        if (lastPlayedOpponentNote == 0) {
            return ((int) (Math.random() * 4159)) + 28; //plays random note if this is the first note
        }
        //Playing a high frequency note that's a multiple of lastPlayedOpponentNote
        int maxMultiplicationFactor = ((int) 4186/lastPlayedOpponentNote) > 9 ? 9 : ((int) 4186/lastPlayedOpponentNote);
        if (maxMultiplicationFactor != 1) { //a high frequency multiple is possible
            int multiplicationFactor = ((int) (Math.random() * (maxMultiplicationFactor-1)) + 2); //between 2 and maxMultiplicationFactor, inclusive
            return lastPlayedOpponentNote * multiplicationFactor;
        }
        //As mentioned in the paper, 1 is added to the last played opponent note if that note were prime
        //Otherwise, the Harmony Prediction player would be stuck on this note
        //This error would be most significant for 2 players both playing the Harmony Prediction strategies and causes an extremely high standard deviation
        if (isPrime(lastPlayedOpponentNote)) {
            lastPlayedOpponentNote++;
        }
        //need a lower frequency note since a high frequency multiple is not possible
        ArrayList<Integer> factors = findFactors(lastPlayedOpponentNote);
        Collections.sort(factors);
        while (factors.get(0) < 28) {
            factors.remove(0);
        }
        return factors.get((int) (Math.random()*factors.size()));
    }

    private boolean isPrime(int note) {
        //Do not need to check if note = 1,2,3 since note>=28
       if (note % 2 == 0 || note % 3 == 0) {
        return false;
       }
       for (int i = 5; i <= Math.sqrt(note); i+=6)
       {
        if (note % i == 0 || note % (i+2) == 0)
        {
            return false;
        }
       }
       return true;
    }

    private static ArrayList<Integer> findFactors(int note) {
        ArrayList<Integer> factors = new ArrayList<Integer>();
        int upperLimit = ((int) (Math.sqrt(note)));
        int inc = note % 2 == 0 ? 1 : 2; //skips even numbers if note is odd
        for (int i = 1; i <= upperLimit; i+=inc) {
            if (note % i == 0) {
                factors.add(i);
                factors.add(note/i);
            }
        }
        return factors;
    }

    @Override
    public void update(double opponentNote) {
        lastPlayedOpponentNote = ((int) opponentNote);
    }
}
