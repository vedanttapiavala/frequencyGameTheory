import java.io.*;

public class Script {
    public static void main(String[] args) throws Exception {
        BufferedWriter bwMain = new BufferedWriter(new FileWriter(new File("allPayoffs.txt")));
        for (int i = 0; i < 9; i++) {
            for (int j = i; j < 9; j++) {
                Player p1, p2;
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
                for (int k = 0; k < 100; k++) {
                    bwMain.write(String.valueOf(Main.main(p1, p2)));
                    if (k != 99) {
                        bwMain.write("\t");
                    }
                    bwMain.flush();
                    System.out.println(p1.getClass().getName() + " " + p2.getClass().getName());
                }
                bwMain.write("\n");
                bwMain.flush();
            }
        }
        bwMain.close();
    }
}
