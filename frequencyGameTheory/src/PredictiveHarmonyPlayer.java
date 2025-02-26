/**
 * Player class for the Harmony Prediction player
 * Plays a note that would harmonize well with the last note the player's opponent played
 */
public class PredictiveHarmonyPlayer extends Player {
    private double lastPlayedOpponentNote = 0.0;

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
    public double genNote() {
        if (lastPlayedOpponentNote == 0.0) {
            return Math.pow(2, (((int) (Math.random() * 88)) / 12.0)) * 27.5; //plays random note if this is the first note
        }
        //Playing a high frequency note that's a multiple of lastPlayedOpponentNote
        int lastPlayedOpponentInt = (int) lastPlayedOpponentNote;
        int lastPlayedOpponentCeil = (int) Math.ceil(lastPlayedOpponentNote);
        int maxMultiplicationFactor = ((int) 4186/(lastPlayedOpponentInt)) > 9 ? 9 : ((int) 4186/lastPlayedOpponentInt);
        int minDivisionFactor = ((int) lastPlayedOpponentCeil/28) > 9 ? 9 : ((int) lastPlayedOpponentCeil/28);
        int possibleFactors = maxMultiplicationFactor + minDivisionFactor - 2;
        if (possibleFactors != 0) { //a high frequency multiple or low frequency factor is possible
            int factor = ((int) (Math.random() * (possibleFactors)) + 1);
            if (factor +1 <= maxMultiplicationFactor) {
                return lastPlayedOpponentNote * (factor + 1);
            }
            else if (factor - maxMultiplicationFactor + 2 <= minDivisionFactor) {
                return lastPlayedOpponentNote / (factor - maxMultiplicationFactor + 2);
            }
            else { // the factor should always be within bounds, but if not:
                return lastPlayedOpponentNote + 1;
            }
        }
        // There should always be a possible multiplication or division factor, but if not:
        return lastPlayedOpponentNote + 1;
    }

    @Override
    public void update(double opponentNote) {
        lastPlayedOpponentNote = opponentNote;
    }
}
