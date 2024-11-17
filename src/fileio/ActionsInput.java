package fileio;

/**
 * The type Actions input.
 */
public final class ActionsInput {
    private String command;
    private int handIdx;
    private Coordinates cardAttacker;
    private Coordinates cardAttacked;
    private int affectedRow;
    private int playerIdx;
    private int x;
    private int y;

    /**
     * Instantiates a new Actions input.
     */
    public ActionsInput() {
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Sets command.
     *
     * @param command the command
     */
    public void setCommand(final String command) {
        this.command = command;
    }

    /**
     * Gets hand idx.
     *
     * @return the hand idx
     */
    public int getHandIdx() {
        return handIdx;
    }

    /**
     * Sets hand idx.
     *
     * @param handIdx the hand idx
     */
    public void setHandIdx(final int handIdx) {
        this.handIdx = handIdx;
    }

    /**
     * Gets card attacker.
     *
     * @return the card attacker
     */
    public Coordinates getCardAttacker() {
        return cardAttacker;
    }

    /**
     * Sets card attacker.
     *
     * @param cardAttacker the card attacker
     */
    public void setCardAttacker(final Coordinates cardAttacker) {
        this.cardAttacker = cardAttacker;
    }

    /**
     * Gets card attacked.
     *
     * @return the card attacked
     */
    public Coordinates getCardAttacked() {
        return cardAttacked;
    }

    /**
     * Sets card attacked.
     *
     * @param cardAttacked the card attacked
     */
    public void setCardAttacked(final Coordinates cardAttacked) {
        this.cardAttacked = cardAttacked;
    }

    /**
     * Gets affected row.
     *
     * @return the affected row
     */
    public int getAffectedRow() {
        return affectedRow;
    }

    /**
     * Sets affected row.
     *
     * @param affectedRow the affected row
     */
    public void setAffectedRow(final int affectedRow) {
        this.affectedRow = affectedRow;
    }

    /**
     * Gets player idx.
     *
     * @return the player idx
     */
    public int getPlayerIdx() {
        return playerIdx;
    }

    /**
     * Sets player idx.
     *
     * @param playerIdx the player idx
     */
    public void setPlayerIdx(final int playerIdx) {
        this.playerIdx = playerIdx;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(final int x) {
        this.x = x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(final int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "ActionsInput{"
                +  "command='"
                + command + '\''
                +  ", handIdx="
                + handIdx
                +  ", cardAttacker="
                + cardAttacker
                +  ", cardAttacked="
                + cardAttacked
                + ", affectedRow="
                + affectedRow
                + ", playerIdx="
                + playerIdx
                + ", x="
                + x
                + ", y="
                + y
                + '}';
    }
}
