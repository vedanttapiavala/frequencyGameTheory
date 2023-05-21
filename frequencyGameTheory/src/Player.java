abstract class Player {
    public Player() {}

    abstract public int genNote();
    protected void updateProbabilities(double recentPayoff) {}; //for reinforcement learning players only
}
