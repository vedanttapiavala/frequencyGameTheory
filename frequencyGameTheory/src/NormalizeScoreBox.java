import java.util.ArrayList;

public class NormalizeScoreBox extends ScoreBox {
    private double[] normalizationFactors;

    public NormalizeScoreBox(ArrayList<Score> scores) {
        super(scores);
        // Generate normalization scores by examining average scores with random players
        Player p1 = new RandomPlayer();
        int numTrials = 12; // Arbitrarally chosen number
        int numLoops = 8;
        ArrayList<double[]> chordProgression = new ArrayList<double[]>(); //every int[] is one chord (usually 4 notes)
        double[] bb7 = {116.54, 147.83, 174.61, 207.65}; // Bb7 -(equal temperament approx.frequencies)-> 116.54(Bb2),146.83(D3), 174.61(F3), Ab(207.65)
        double[] eb7 = {155.56, 196.0, 233.08, 277.18}; // Eb7 -(equal temperament approx.frequencies)-> Eb3 155.56, G3 196.00, Bb3 233.08, Db4 277.18
        double[] cm7 = {130.81, 155.56, 196.0, 233.08}; // Cm7 -(equal temperament approx.frequencies)-> C3 130.81, Eb3 155.56, G3 196.00, Bb3 233.08
        double[] f7 = {174.61, 220.0, 261.63, 311.13}; // F7 -(equal temperament approx.frequencies)-> F3	174.61, A3 220.00, C4 261.63, Eb4 311.13
        /**
         * Add the chords in the right order
         * 12 bar Bb blues progression: Bb7, Eb7, Bb7, Bb7, Eb7, Eb7, Bb7, Bb7, Cm7, F7, Bb7, F7
         */
        for (int i = 0; i < numLoops; i++) { //goes through chord progression numLoops times
            chordProgression.add(bb7);
            chordProgression.add(eb7);
            chordProgression.add(bb7);
            chordProgression.add(bb7);
            chordProgression.add(eb7);
            chordProgression.add(eb7);
            chordProgression.add(bb7);
            chordProgression.add(bb7);
            chordProgression.add(cm7);
            chordProgression.add(f7);
            chordProgression.add(bb7);
            chordProgression.add(f7);
        }
        double[] trialSums = new double[scores.size()];
        for (int trialNum = 0; trialNum < numTrials; trialNum++) {
            double[] sums = new double[scores.size()];
            ArrayList<Double> p1Freq = new ArrayList<Double>(); //saves player 1 notes to list
            ArrayList<Double> p2Freq = new ArrayList<Double>(); //saves player 2 notes to list
            double[] chordProgressionFreq;
            int measureNum = -1; // counts the measures
            for (int beatNum = 0; beatNum < 96*numLoops; beatNum++) { // subdividing by eight notes there will be 96 beats in a 12 bar blues
                if (beatNum % 8 == 0) measureNum++; // increments measureNum at the start of every 8 beats --> one measure
                chordProgressionFreq = chordProgression.get(measureNum);
                p1Freq.add(p1.genNote()); //add the note to the correct list
                p2Freq.add(p1.genNote());
                for (int i=0; i < scores.size(); i++) {
                    sums[i] += scores.get(i).calcScore(chordProgressionFreq, p1Freq, p2Freq);
                }
            }
            for (int i =0; i < sums.length; i++) {
                sums[i] = sums[i] / (96*numLoops);
                trialSums[i] += sums[i];
            }
            for (Score score : scores) {
                score.reset();
            }
        }
        for (int i = 0; i < trialSums.length; i++) {
            trialSums[i] = trialSums[i] / numTrials;
        }
        normalizationFactors = new double[scores.size()];
        for (int i=0; i < scores.size(); i++) {
            normalizationFactors[i] = 1.0;
            for (int j=0; j < scores.size(); j++) {
                if (i != j) normalizationFactors[i] = normalizationFactors[i] * trialSums[j];
            }
        }
    }
  
    @Override
    public ArrayList<Double> calcScore(double[] chord, ArrayList<Double> p1PastNotes, ArrayList<Double> p2PastNotes) {
        ArrayList<Double> result = new ArrayList<Double>();
        double numSum = 0.0;
        double denomSum = 0.0;
        for (int i=0; i<scores.size(); i++) {
            double payoff = scores.get(i).calcScore(chord, p1PastNotes, p2PastNotes);
            result.add(payoff);
            payoff = payoff * normalizationFactors[i];
            numSum += payoff * scores.get(i).getDirection();
            denomSum += payoff;
        }
        result.add(0, numSum / denomSum);
        return result;
    }

    public double[] getNormalizationFactors() {return normalizationFactors;}

}
