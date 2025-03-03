import java.util.ArrayList;

abstract class MusicRules {
  public MusicRules() {}

  abstract public double calcVarianceScore(ArrayList<Integer> allPastNotes);

  abstract public double calcHarmonyScore(int[] chord, int freqOne, int freqTwo);

  
}
