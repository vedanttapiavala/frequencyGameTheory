import java.io.*;

/*
 * Runs 100 trials of each possible strategy pair, including the strategies against themselves
 * CAUTION: Takes time to run since over 7,000,000 notes are played during these trials
 */
public class Simulation {
    public static void main(String[] args) throws Exception {
        //Creates a file, allPayoffs.txt, to store simulation results
        BufferedWriter bwMain = new BufferedWriter(new FileWriter(new File("allPayoffs.txt")));
        //Goes through each of the 9 strategies
        for (int i = 0; i < 9; i++) {
            //Goes through remaining strategies, including strategy with index i
            for (int j = i; j < 9; j++) {
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
                        default -> new DoubleReinforcementPlayer();
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
                        default -> new DoubleReinforcementPlayer();
                    };
                    bwMain.write(String.valueOf(Main.main(p1, p2)));
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
}
