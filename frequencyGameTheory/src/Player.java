abstract class Player {
    public Player() {}

    abstract public int genNote();
    /*
     * Sends information from the Main.java to the player
     * For reinforcement learning players, this would be the most recent payoff so that they can take that into account
     * For a predictive harmony player, this would be their opponent's most recently played note
     */
    protected void update(double d) {};
}
