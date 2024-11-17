package org.poo.fileio;

import java.util.ArrayList;

/**
 * The type Game input.
 */
public final class GameInput {
        private StartGameInput startGame;
        private ArrayList<ActionsInput> actions;

    /**
     * Instantiates a new Game input.
     */
    public GameInput() {
        }

    /**
     * Gets start game.
     *
     * @return the start game
     */
    public StartGameInput getStartGame() {
                return startGame;
        }

    /**
     * Sets start game.
     *
     * @param startGame the start game
     */
    public void setStartGame(final StartGameInput startGame) {
                this.startGame = startGame;
        }

    /**
     * Gets actions.
     *
     * @return the actions
     */
    public ArrayList<ActionsInput> getActions() {
                return actions;
        }

    /**
     * Sets actions.
     *
     * @param actions the actions
     */
    public void setActions(final ArrayList<ActionsInput> actions) {
                this.actions = actions;
        }

        @Override
        public String toString() {
                return "GameInput{"
                        +  "startGame="
                        + startGame
                        + ", actions="
                        + actions
                        + '}';
        }
}
