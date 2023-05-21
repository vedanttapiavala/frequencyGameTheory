public class RandomPlayer extends Player {
    public RandomPlayer() {
        super();
    }

    @Override
    public int genNote() {
        return ((int) (Math.random() * 4159)) + 28; //range from 28 to 4186
    }
}
