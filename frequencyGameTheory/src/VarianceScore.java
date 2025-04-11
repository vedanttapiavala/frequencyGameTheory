import java.util.ArrayList;

public class VarianceScore extends Score {
    private int[] noteCounts;

    public static void main(String[] args) {
        ArrayList<Double> p1Freq = new ArrayList<Double>();
        ArrayList<Double> p2Freq = new ArrayList<Double>();
        p1Freq.add(300.0);
        p2Freq.add(400.0);
        Score varScore = new VarianceScore();
        System.out.println(varScore.calcScore(null, p1Freq, p2Freq));

        System.out.println("More tests ------");
    }

    public VarianceScore() {this.noteCounts = new int[12];}

    //counts octaves as the same note
    //Applies Shannon Diversity Index as mentioned in the paper
    @Override
    public double calcScore(double[] chord, ArrayList<Double> p1PastNotes, ArrayList<Double> p2PastNotes) {
      double freqOne = p1PastNotes.get(p1PastNotes.size()-1);
      double freqTwo = p2PastNotes.get(p2PastNotes.size()-1);
      noteCounts[Util.freqToNote(freqOne) % 12] += 1;
      noteCounts[Util.freqToNote(freqTwo) % 12] += 1;
      //Calculating Shannon Diversity Index
      double diversityIndex = 0;
      for (int x : noteCounts) {
          double Pi = (double)x / (p1PastNotes.size() + p2PastNotes.size());
          double calc = -Pi*Math.log(Pi); //-Pi * ln(Pi)
          if (Double.isNaN(calc)) {
              calc = 0;
          }
          diversityIndex+=calc;
      }
      return diversityIndex/Math.log(noteCounts.length); //H/Hmax
    }

    public void reset() {
        noteCounts = new int[12];
    }

    // Returns 1 to indicate higher is better
    @Override
    public int getDirection() {
        return 1;
    }
}
