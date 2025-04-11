import java.io.*;
import java.util.Random;
import java.util.ArrayList;

/*
 * Runs 100 trials of each possible strategy pair, including the strategies against themselves
 * CAUTION: Takes time to run since over 7,000,000 notes are played during these trials
 */
public class Simulation {
    public static void main(String[] args) throws Exception {
        //Creates a file, allPayoffs.txt, to store simulation results
        BufferedWriter bwMain = new BufferedWriter(new FileWriter(new File("allPayoffs.txt")));
        //Goes through each of the 9 strategies
        for (int i = 0; i < 10; i++) {
            //Goes through remaining strategies, including strategy with index i
            for (int j = i; j < 10; j++) {
                //k-loop used to run exactly 100 trials
                for (int k = 0; k < 100; k++) {
                    Player p1, p2; //Assigned the correct strategies with the switch statements
                    p1 = switch(i) {
                        case 0 -> new RandomPlayer();
                        case 1 -> new ChordPlayer();
                        case 2 -> new ScalePlayer();
                        case 3 -> new PredictiveHarmonyPlayer();
                        case 4 -> new StepwisePlayer();
                        case 5 -> new SimpleReinforcementPlayer();
                        case 6 -> new ChordFollowingReinforcementLearning();
                        case 7 -> new ChordSpecificReinforcementPlayer();
                        case 8 -> new DoubleReinforcementPlayer();
                        default -> new ChordSpecificMarkovPlayer();
                    };
                    p2 = switch(j) {
                        case 0 -> new RandomPlayer();
                        case 1 -> new ChordPlayer();
                        case 2 -> new ScalePlayer();
                        case 3 -> new PredictiveHarmonyPlayer();
                        case 4 -> new StepwisePlayer();
                        case 5 -> new SimpleReinforcementPlayer();
                        case 6 -> new ChordFollowingReinforcementLearning();
                        case 7 -> new ChordSpecificReinforcementPlayer();
                        case 8 -> new DoubleReinforcementPlayer();
                        default -> new ChordSpecificMarkovPlayer();
                    };
                    bwMain.write(String.valueOf(Main.main(p1, p2, 8)));
                    if (k != 99) { //tab between each player for ease of copying into Microsoft Excel or Google Sheets
                        bwMain.write("\t");
                    }
                    bwMain.flush();
                    System.out.println(p1.getClass().getName() + " " + p2.getClass().getName()); //Prints progress updates
                }
                bwMain.write("\n");
                bwMain.flush();
            }
        }
        bwMain.close();
    }

    // public static void main(String[] args) throws Exception{
    //     Player p1 = new RandomPlayer();
    //     Player p2 = new RandomPlayer();
    //     ArrayList<Score> scores = new ArrayList<Score>();
    //     scores.add(new FlexibleHarmonyScore());
    //     scores.add(new VarianceScore());
    //     ScoreBox scoreBox = new NormalizeScoreBox(scores);
    //     System.out.println(Main.main(p1, p2, 8));

    //     scoreBox.reset();
    //     scoreBox = new NormalizeScoreBox(scores);
    //     scoreBox.reset();
        

    //     Player player1 = new ChordFollowingReinforcementLearning();
    //     Player player2 = new ChordFollowingReinforcementLearning();
    //     // double[][][] probs1 = player1.getProbabilities();
    //     // double[][][] probs2 = player2.getProbabilities();
    //     System.out.println(Main.main(player1, player2, 8));
        

    //     // System.out.println("---------------- PROBS1 ------------------------");
    //     // BufferedWriter bw = new BufferedWriter(new FileWriter(new File("Chord Specific Markov Probabilities.txt")));
    //     // for (int i = 0; i < probs1.length; i++) {
    //     //     bw.write("Chord: " + i + ": ");
    //     //     for (int j = 0; j < probs1[i].length; j++) {
    //     //         for (int k = 0; k < probs1[i][j].length; k++) {
    //     //             bw.write(probs1[i][j][k] + ",");
    //     //         }
    //     //         if (j != probs1[i].length-1 || i != probs1.length-1) {
    //     //             bw.write("\n");
    //     //         }
    //     //     }
    //     //     bw.flush();
    //     // }
    //     // bw.flush();
    //     // bw.close();
    // }
}
