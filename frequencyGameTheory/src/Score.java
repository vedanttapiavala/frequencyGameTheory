import java.util.ArrayList;

abstract class Score {
    abstract public double calcScore(double[] chord, ArrayList<Double> p1PastNotes, ArrayList<Double> p2PastNotes);

    abstract public int getDirection();

    public void reset() {};
}
