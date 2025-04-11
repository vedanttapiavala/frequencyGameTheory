import java.util.ArrayList;

abstract class ScoreBox {
    protected ArrayList<Score> scores;

    public ScoreBox(ArrayList<Score> scores) {this.scores = scores;};

    abstract public ArrayList<Double> calcScore(double[] chord, ArrayList<Double> p1PastNotes, ArrayList<Double> p2PastNotes);

    public ArrayList<Score> getScores() {return this.scores;}

    public void reset() {
        for (Score score : scores) {
            score.reset();
        }
    }
}
