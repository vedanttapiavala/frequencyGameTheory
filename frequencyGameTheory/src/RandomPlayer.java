import java.util.*;

public class RandomPlayer extends Player {
    private ArrayList<Integer> prevNotes;

    public RandomPlayer() {
        super();
        prevNotes = new ArrayList<Integer>();
    }

    @Override
    public int genNote() {
        return ((int) (Math.random() * 4789)) + 28; //range from 28 to 4816
    }

    @Override
    public void addPrevNote(int freq) {
        prevNotes.add(freq);
    }
}
