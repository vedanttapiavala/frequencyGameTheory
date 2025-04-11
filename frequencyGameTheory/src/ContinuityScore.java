import java.util.ArrayList;

public class ContinuityScore extends Score {

  @Override
  public double calcScore(double[] chord, ArrayList<Double> p1PastNotes, ArrayList<Double> p2PastNotes) {
    // TODO Auto-generated method stub
    if (p1PastNotes.size() > 1) {
        double diffOne = Math.abs(p1PastNotes.get(p1PastNotes.size()-1) - p1PastNotes.get(p1PastNotes.size()-2));
        double diffTwo = Math.abs(p2PastNotes.get(p2PastNotes.size()-1) - p2PastNotes.get(p2PastNotes.size()-2));
        double diffThree = Math.abs(p1PastNotes.get(p1PastNotes.size()-1) - p2PastNotes.get(p2PastNotes.size()-1));
        return 1.0 / (double) (diffOne + diffTwo + diffThree);
    }
    else {
      return Math.abs(p1PastNotes.get(p1PastNotes.size()-1) - p2PastNotes.get(p2PastNotes.size()-1));
    }
  }

  // Returns 1 because bigger is better
  @Override
  public int getDirection() {
    return 1;
  }
  
}
